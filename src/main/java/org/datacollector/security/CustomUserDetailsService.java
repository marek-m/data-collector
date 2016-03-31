package org.datacollector.security;

import java.util.ArrayList;
import java.util.List;

import org.datacollector.db.Userr;
import org.datacollector.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserService userService;
	
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Userr user = userService.findByEmail(email);
        System.out.println("User : "+user);
        if(user==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getGrantedAuthorities(user));
    }
	
    private List<GrantedAuthority> getGrantedAuthorities(Userr user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
         
        authorities.add(new SimpleGrantedAuthority("ROLE_"+ user.getRole().name()));
        System.out.print("authorities :"+authorities);
        return authorities;
    }

}
