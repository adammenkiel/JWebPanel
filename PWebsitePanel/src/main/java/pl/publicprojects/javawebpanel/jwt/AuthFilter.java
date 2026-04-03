package pl.publicprojects.javawebpanel.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;
import pl.publicprojects.javawebpanel.services.SessionInfoService;
import pl.publicprojects.javawebpanel.session.SessionInfo;

import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SessionInfoService sessionInfoService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Cookie jwtCookie = WebUtils.getCookie(request, this.jwtUtil.getJwtName());
            if (jwtCookie == null) {
                filterChain.doFilter(request, response);
                return;
            }
            String jwtToken = jwtCookie.getValue();
            if (!this.jwtUtil.validateJwtToken(jwtToken)) {
                filterChain.doFilter(request, response);
                return;
            }

            SessionInfo sessionInfo = (SessionInfo) sessionInfoService.loadUserByUsername(
                    this.jwtUtil.getUserNameFromJwtToken(jwtToken)
            );

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    sessionInfo,
                    null,
                    sessionInfo.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        filterChain.doFilter(request, response);
    }
}
