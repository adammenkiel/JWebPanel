package pl.publicprojects.javawebpanel.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;
import pl.publicprojects.javawebpanel.jwt.JwtUtil;
import pl.publicprojects.javawebpanel.repositories.RoleRepository;
import pl.publicprojects.javawebpanel.repositories.SessionRepository;
import pl.publicprojects.javawebpanel.requests.LoginRequest;
import pl.publicprojects.javawebpanel.requests.RegisterRequest;
import pl.publicprojects.javawebpanel.responses.SessionInfoResponse;
import pl.publicprojects.javawebpanel.session.Role;
import pl.publicprojects.javawebpanel.session.Session;
import pl.publicprojects.javawebpanel.session.SessionInfo;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        SessionInfo sessionInfo = (SessionInfo) authentication.getPrincipal();

        ResponseCookie cookie = jwtUtil.generateJwtCookie(sessionInfo);

        System.out.println("Logging in for user: " + sessionInfo.getUsername());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new SessionInfoResponse(
                        sessionInfo.getId(),
                        sessionInfo.getUsername(),
                        sessionInfo.getEmail(),
                        sessionInfo.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList()
                        ));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        String defaultRole = "USER";

        if(this.sessionRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.ok("User of that's name already exists!");
        }
        if(this.sessionRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.ok("User of that's email already exists!");
        }

        Session session = new Session(request.getUsername(), request.getEmail(), this.encoder.encode(request.getPassword()));

        Optional<Role> defaultRoleOptional = roleRepository.findByRoleName(defaultRole);
        if(defaultRoleOptional.isEmpty()) {
            defaultRoleOptional = Optional.of(new Role(defaultRole));
            roleRepository.save(defaultRoleOptional.get());
        }

        Role role = defaultRoleOptional.get();
        session.getUserRoles().add(role);
        sessionRepository.save(session);
        
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/status")
    public ResponseEntity<?> authStatus(HttpServletRequest request) {
        Cookie jwtCookie = WebUtils.getCookie(request, this.jwtUtil.getJwtName());
        if(jwtCookie == null) {
            return ResponseEntity.ok("false");
        }
        boolean correct = this.jwtUtil.validateJwtToken(jwtCookie.getValue());
        if(!correct)
            return ResponseEntity.ok("false");

        return ResponseEntity.ok("true");
    }
}
