package mk.ukim.finki.wp.project.service.impl;

import lombok.SneakyThrows;
import mk.ukim.finki.wp.project.model.*;
import mk.ukim.finki.wp.project.repository.*;
import mk.ukim.finki.wp.project.service.UserService;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository ur;
    private final PasswordEncoder pwd;

    public UserServiceImpl(UserRepository ur, PasswordEncoder pwd) {
        this.ur = ur;
        this.pwd = pwd;
    }

    @Override
    public User create(String username, String password, Role role) {
        String pass = this.pwd.encode(password);
        User u = new User(username,pass,role);
        return ur.save(u);
    }

    @Override
    public User find(String username) throws Exception {
        return ur.findByUsername(username).orElseThrow(Exception::new);
    }

    @Override
    public List<User> listAll() {
        return ur.findAll();
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User user = this.ur.findByUsername(username).orElseThrow(Exception::new);
         UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                 user.getUsername(),user.getPassword(),
                 Stream.of(new SimpleGrantedAuthority(user.getRole().toString())).collect(Collectors.toList()));

        return userDetails;
    }
}
