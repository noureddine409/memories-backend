package com.memories.app.controller;





import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.memories.app.commun.CoreConstant;
import com.memories.app.commun.CoreConstant.Exception;
import com.memories.app.dto.JwtToken;
import com.memories.app.dto.JwtTokenResponseDto;
import com.memories.app.dto.ResetPasswordDto;
import com.memories.app.dto.UserDto;
import com.memories.app.dto.UserLoginDto;
import com.memories.app.exception.BusinessException;
import com.memories.app.exception.ElementAlreadyExistException;
import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.exception.UnauthorizedException;
import com.memories.app.model.ForgetPasswordToken;
import com.memories.app.model.GenericEnum.JwtTokenType;
import com.memories.app.model.User;
import com.memories.app.service.UserService;
import com.memories.app.utils.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtProvider;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtProvider, UserService userService,
                          ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }
    
    public UserDto convertToDto(User entity) {
        return modelMapper.map(entity, UserDto.class);
    }

    public User convertToEntity(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }
    
    private String getSiteURL(HttpServletRequest request) {
    	String siteURL = request.getRequestURL().toString();
    	return siteURL.replace(request.getServletPath(), "");
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto userDto, HttpServletRequest request)
    		throws ElementAlreadyExistException, UnsupportedEncodingException, MessagingException  {
    	User convertedUser = convertToEntity(userDto);
    	userService.generateVerificationCode(convertedUser);
    	User savedUser = userService.save(convertedUser);
    	
    	userService.sendVerificationEmail(savedUser, getSiteURL(request));
    	
    	return ResponseEntity.ok().body(convertToDto(savedUser));
    }
    
    @GetMapping("/register/resendVerificationMail")
    public ResponseEntity<?> resendVerification(@RequestParam("email") String email, HttpServletRequest request) {
    	User user = userService.findUserByEmail(email);
    	if(!user.isEnabled() || user.getVerificationCode()!=null) {
    		userService.sendVerificationEmail(user, getSiteURL(request));
        	return ResponseEntity.ok().build();
    	}
    	return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/forgetPassword")
    public ResponseEntity<UserDto> forgetPassword(@RequestParam("email") String email, HttpServletRequest request) {
    	User userFound = userService.findUserByEmail(email);
    	
    	ForgetPasswordToken token = userService.generateForgetPasswordToken(userFound);
    	
    	userService.sendForgetPasswordEmail(token, getSiteURL(request));
    	
    	return ResponseEntity.ok().body(convertToDto(userFound));
    }
    
    @GetMapping("/forgetPassword/resend")
    public ResponseEntity<?> resendToken(@RequestParam("email") String email, HttpServletRequest request) {
    	ForgetPasswordToken token = userService.findForgetPasswordToken(email);
    	userService.sendForgetPasswordEmail(token, getSiteURL(request));
    	return ResponseEntity.ok().build();
    }
    
    @PostMapping("/resetPassword")
    public ResponseEntity<Boolean> resetPassword(@Valid @RequestBody ResetPasswordDto dto) {
    	ForgetPasswordToken entity = modelMapper.map(dto.getToken(), ForgetPasswordToken.class);
    	Boolean response = userService.verifyForgetPasswordToken(entity, dto.getNewPassword());
    	
    	
    	return new ResponseEntity<Boolean>(response, HttpStatus.OK);
    }
    
    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("code") String code) {
    	if( userService.verify(code)) {
    		return new ResponseEntity<String>("verify_success", HttpStatus.OK);
    	}
    	return new ResponseEntity<String>("verify_fail", HttpStatus.UNAUTHORIZED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponseDto> login(@Valid @RequestBody UserLoginDto userLoginDto)
            throws UnauthorizedException, ElementNotFoundException {

        Authentication authToken =
                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword());

        Authentication authResult;

        try {
            authResult = authenticationManager.authenticate(authToken);
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new UnauthorizedException(null, e.getCause(), CoreConstant.Exception.AUTHENTICATION_BAD_CREDENTIALS, null);
        }

        User authenticatedUser = (User) authResult.getPrincipal();

        JwtToken accessToken = jwtProvider.generateToken(authenticatedUser, JwtTokenType.ACCESS);
        JwtToken refreshToken = jwtProvider.generateToken(authenticatedUser, JwtTokenType.REFRESH);
        String refreshTokenId = jwtProvider.getDecodedJWT(refreshToken.getToken(), JwtTokenType.REFRESH).getId();
        
        User connectedUser = userService.findById(authenticatedUser.getId());
        connectedUser.setRefreshTokenId(refreshTokenId);
        userService.update(connectedUser.getId(), connectedUser);
        return ResponseEntity
                .ok()
                .body(
                        JwtTokenResponseDto.builder()
                                .accessToken(accessToken)
                                .refreshToken(refreshToken)
                                .build()
                );
    }
    
    @PostMapping("/token")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<JwtTokenResponseDto> refreshToken(HttpServletRequest request, HttpServletResponse response) throws BusinessException {

        String refreshToken = jwtProvider.extractTokenFromRequest(request);

        DecodedJWT decodedRefreshToken = jwtProvider.getDecodedJWT(refreshToken, JwtTokenType.REFRESH);
        Long userId = Long.valueOf(decodedRefreshToken.getSubject());
        String refreshTokenId = decodedRefreshToken.getId();

        User user = userService.findById(userId);

        try {
            if (!refreshTokenId.equals(user.getRefreshTokenId()))
                throw new UnauthorizedException(null, new UnauthorizedException(), Exception.AUTHORIZATION_INVALID_TOKEN, null);
        } catch (NullPointerException e) {
            throw new BusinessException(e.getMessage(), e.getCause(), null, null);
        }

        JwtToken newAccessToken = jwtProvider.generateToken(user, JwtTokenType.ACCESS);
        JwtToken newRefreshToken = jwtProvider.generateToken(user, JwtTokenType.REFRESH);

        user.setRefreshTokenId(jwtProvider.getDecodedJWT(newRefreshToken.getToken(), JwtTokenType.REFRESH).getId());
        userService.update(userId, user);

        return ResponseEntity
                .ok()
                .body(
                        JwtTokenResponseDto.builder()
                                .accessToken(newAccessToken)
                                .refreshToken(newRefreshToken)
                                .build()
                );
    }

}
