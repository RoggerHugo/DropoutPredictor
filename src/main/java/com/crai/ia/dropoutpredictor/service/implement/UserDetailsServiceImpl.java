package com.crai.ia.dropoutpredictor.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crai.ia.dropoutpredictor.repository.AppUserRepository;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


private final AppUserRepository userRepository;


@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
return userRepository.findByUsername(username)
.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
}
}