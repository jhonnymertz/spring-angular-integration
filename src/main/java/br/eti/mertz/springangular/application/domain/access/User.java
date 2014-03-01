package br.eti.mertz.springangular.application.domain.access;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="`user`")
public class User implements UserDetails, CredentialsContainer {
	
	private static final long serialVersionUID = 6859063592236975281L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, length=50)
	private String name;
	
	@Column(nullable=false, unique=true)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@Transient
	private String passwordConfirm;
	
	private boolean enabled;
	
	@Transient
	private String token;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private Profile profile;
	
	@Transient
	private Password passwordChange;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	@JsonIgnore
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	@JsonProperty
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	
	@JsonIgnore
	public Password getPasswordChange() {
		return this.passwordChange;
	}
	
	@JsonProperty
	public void setPasswordChange(Password passwordChange) {
		this.passwordChange = passwordChange;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Profile getProfile() {
		return profile;
	}
	
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return this.enabled;
	}

	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public void eraseCredentials() {
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + this.profile);
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(authority);
		return authorities;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof User) {
			User user = (User) obj;
			if(user.getUsername() != null) {
				return user.getUsername().equals(username);
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return username.hashCode();
	}
	
	public void setToken(String token){
		this.token = token;
	}
	
	public String getToken(){
		return token;
	}
	
}