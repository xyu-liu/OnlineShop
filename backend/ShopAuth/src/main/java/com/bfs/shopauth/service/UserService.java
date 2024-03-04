package com.bfs.shopauth.service;

import com.bfs.shopauth.dao.UserDao;
import com.bfs.shopauth.domain.RegisterRequest;
import com.bfs.shopauth.entity.Permission;
import com.bfs.shopauth.entity.User;
import com.bfs.shopauth.security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username does not exist");
        }

        return AuthUserDetail.builder() // spring security's userDetail
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(getAuthoritiesFromUser(user))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

    private List<GrantedAuthority> getAuthoritiesFromUser(User user){
        List<GrantedAuthority> userAuthorities = new ArrayList<>();
        List<String> permissions = user.getPermissionList().stream().map(Permission::getValue).collect(Collectors.toList());

        for (String permission :  permissions){
            userAuthorities.add(new SimpleGrantedAuthority(permission));
        }

        return userAuthorities;
    }

    @Transactional
    public Boolean addNewUser(RegisterRequest request) {
        if (this.userDao.isUserNameTaken(request.getUsername()) || this.userDao.isEmailTaken(request.getEmail())) {
            return false;
        } else {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
            user.setUsername(request.getUsername());
            user.setRole(0);
            ArrayList<Permission> permissions = new ArrayList<>();
            user.setPermissionList(permissions);
            Permission permission = new Permission();
            permission.setValue("customer");
            permission.setUser(user);
            permissions.add(permission);
            this.userDao.addUser(user);


            return true;
        }

    }
}
