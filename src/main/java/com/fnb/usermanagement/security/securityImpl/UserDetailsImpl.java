package com.fnb.usermanagement.security.securityImpl;

import com.fnb.usermanagement.model.User;
import com.fnb.usermanagement.model.Credentials;
import com.fnb.usermanagement.repository.UserCredentialRepository;
import com.fnb.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserCredentialRepository userCredentialsRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        Credentials userCredential = userCredentialsRepository.findByUser_CustomerId(user.getCustomerId());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(userCredential.getPassword_hash())
                .authorities(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                .build();
    }
}