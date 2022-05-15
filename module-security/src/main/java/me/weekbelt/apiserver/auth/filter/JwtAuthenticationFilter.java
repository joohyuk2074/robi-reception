package me.weekbelt.apiserver.auth.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.weekbelt.apiserver.auth.TokenProvider;
import me.weekbelt.apiserver.auth.dto.MemberContext;
import me.weekbelt.apiserver.auth.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    private final MemberService memberService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        return servletPath.equals("/auth/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        String accessToken = tokenProvider.getTokenFromAuthHeader(authHeaderValue);

        if (!StringUtils.hasText(accessToken)) {
            throw new IllegalArgumentException("accessToken is empty!");
        }

        try {
            tokenProvider.verifyToken(accessToken);
        } catch (RuntimeException e) {
            log.info("token exception : {}", e.getMessage());
            request.setAttribute("exception", e);
        }

        setSecurityContext(accessToken);

        filterChain.doFilter(request, response);
    }

    private void setSecurityContext(String accessToken) {
        String username = tokenProvider.getUsername(accessToken);
        MemberContext memberContext = (MemberContext) memberService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberContext, null, memberContext.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);
    }
}
