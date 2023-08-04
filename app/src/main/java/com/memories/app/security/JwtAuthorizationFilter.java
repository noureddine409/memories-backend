package com.memories.app.security;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.memories.app.exception.BusinessException;
import com.memories.app.exception.UnauthorizedException;
import com.memories.app.model.GenericEnum.JwtTokenType;
import com.memories.app.utils.JwtUtil;

import static java.util.Arrays.stream;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	final Logger LOG = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final JwtUtil jwtProvider;
    private final List<String> authorizationWhiteList;
    private final HandlerExceptionResolver handlerExceptionResolver;


    public JwtAuthorizationFilter(JwtUtil jwtProvider, List<String> authorizationWhiteList,
                                  HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtProvider = jwtProvider;
        this.authorizationWhiteList = authorizationWhiteList;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws BusinessException {

        try {

            String accessToken = jwtProvider.extractTokenFromRequest(request);

            DecodedJWT decodedJWT = jwtProvider.getDecodedJWT(accessToken, JwtTokenType.ACCESS);

            Long id = Long.valueOf(decodedJWT.getSubject());
            String[] rolesClaim = decodedJWT.getClaim("roles").asArray(String.class);

            List<GrantedAuthority> roles = stream(rolesClaim)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            Authentication authToken = new UsernamePasswordAuthenticationToken(id, null, roles);
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);

        } catch (IOException | ServletException e) {
            LOG.error("Error", e);
            throw new BusinessException(e.getMessage(), e.getCause(), null, null);
        } catch (UnauthorizedException e) {
            LOG.error("Error", e);
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<RequestMatcher> antPathRequestMatchers = authorizationWhiteList.stream()
                .map(AntPathRequestMatcher::new)
                .collect(Collectors.toList());

        OrRequestMatcher requestMatcher = new OrRequestMatcher(antPathRequestMatchers);

        return requestMatcher.matches(request);
    }
}
