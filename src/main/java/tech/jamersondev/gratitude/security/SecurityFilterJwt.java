package tech.jamersondev.gratitude.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.jamersondev.gratitude.core.model.User;
import tech.jamersondev.gratitude.core.repository.UserRepository;
import tech.jamersondev.gratitude.core.service.TokenService;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilterJwt extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilterJwt(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> tokenJwtRequest = recoverTokenRequest(request);
        if(tokenJwtRequest.isPresent()){
            String subjectToken = this.tokenService.getSubject(tokenJwtRequest.get());
            User user = this.userRepository.findByEmail(subjectToken)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> recoverTokenRequest(HttpServletRequest request) {
        String tokenHeader = request.getHeader("Authorization");
        return tokenHeader != null ? Optional.of(tokenHeader) : Optional.empty();
    }

}
