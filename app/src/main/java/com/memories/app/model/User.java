package com.memories.app.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.memories.app.model.GenericEnum.Gender;
import com.memories.app.model.GenericEnum.UserType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Node
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends GenericEntity implements UserDetails{
	private static final long serialVersionUID = -3206802778184562065L;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private LocalDate birthDay;
	private String password;
	private String profilePicture;
	private String backgroundPicture;
	private String bio;
	private Gender gender;
	private UserType type;
	private Adress adress;
	private String refreshTokenId;
	private String verificationCode;
	private boolean enabled;
	
	@Relationship("HAS_A")
    private List<Role> roles;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (Objects.isNull(this.roles) || this.roles.isEmpty())
            return Collections.emptyList();

        return this.roles.stream()
               .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
               .collect(Collectors.toList());
	}
	@Override
	public String getUsername() {
		return this.email;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
}
