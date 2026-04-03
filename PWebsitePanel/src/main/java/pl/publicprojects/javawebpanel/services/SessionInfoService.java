package pl.publicprojects.javawebpanel.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.publicprojects.javawebpanel.repositories.SessionRepository;
import pl.publicprojects.javawebpanel.session.Session;
import pl.publicprojects.javawebpanel.session.SessionInfo;

import java.util.Optional;

@Service
public class SessionInfoService implements UserDetailsService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Session databaseSession = sessionRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
        return new SessionInfo(databaseSession);
    }
}
