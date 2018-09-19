package project.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.Model.User;
import project.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        // logger.debug(login + " " + password);
        System.out.println("Login " + login);
        System.out.println("Password " + password);

        User user = userRepository.findByName(login);
        System.out.println ("password "+user.getPassword());
        final List<GrantedAuthority> grantedAuths = new ArrayList<>();
        if (user == null) {
            return null;
        }
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (!matches) {
            return null;
        }
        else
            grantedAuths.add(new SimpleGrantedAuthority("USER"));

        final UserDetails principal = new org.springframework.security.core.userdetails.User(login, password, grantedAuths);
        return new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);


    }

    @Override
    public boolean supports(java.lang.Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}