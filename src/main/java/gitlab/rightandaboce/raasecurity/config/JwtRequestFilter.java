package gitlab.rightandaboce.raasecurity.config;

import gitlab.rightandaboce.raasecurity.util.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import org.springframework.lang.NonNull;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractToken(request);

        if (token != null) {
            String username = jwtTokenProvider.getUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                setAuthentication(username);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(@NonNull HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION);
        return (authorization != null && authorization.startsWith("Bearer ")) ? authorization.substring(7) : null;
    }

    private void setAuthentication(@NonNull String username) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

