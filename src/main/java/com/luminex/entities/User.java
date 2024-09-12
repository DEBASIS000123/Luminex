package com.luminex.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.relation.RoleList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="user")
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User implements UserDetails{
	
	@Id
	private String userId;
	@Column(name="user_name",nullable = false)
	private String name;
	@Column(unique = true,nullable = false)
	private String email;
	private String password;
	@Column(length = 5000)
	private String about;
	@Column(length = 5000)
	private String profilePic;
	private String phoneNo;
	
	@Getter(value=AccessLevel.NONE)
	private boolean enebled=true;
	private boolean emailVerfied=false;
	private boolean phoneVerified=false;
	
	@Enumerated(value = EnumType.STRING)
	private Providers provider=Providers.SELF;
	private String providerUserId;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	private List<Contact> contacts=new ArrayList<>();

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> RoleList=new ArrayList<>();
	
	  @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        // list of roles[USER,ADMIN]
	        // Collection of SimpGrantedAuthority[roles{ADMIN,USER}]
	        Collection<SimpleGrantedAuthority> roles = RoleList.stream().map(role -> new SimpleGrantedAuthority(role))
	                .collect(Collectors.toList());
	        return roles;
	    }


	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.enebled;
	}
	
	
}
