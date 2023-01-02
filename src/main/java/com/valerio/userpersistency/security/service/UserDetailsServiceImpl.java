package com.valerio.userpersistency.security.service;

import com.valerio.userpersistency.persistency.dao.RoleRepository;
import com.valerio.userpersistency.persistency.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    RoleRepository roleRepository;

    public UserDetailsServiceImpl() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Roles> usRoles = roleRepository.findByUsername(username);
        if (usRoles.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Do note that a defined role for this user \'%s\' are not found", username));
        }
        com.valerio.userpersistency.persistency.model.User user = usRoles.get(0).getUser();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        usRoles.forEach(a -> grantedAuthorities.add(new SimpleGrantedAuthority(a.getRolename())));

        UserDetails userDetails = (UserDetails) new User(user.getUsername(), user.getPassword(), grantedAuthorities);

        return userDetails;
    }
}
