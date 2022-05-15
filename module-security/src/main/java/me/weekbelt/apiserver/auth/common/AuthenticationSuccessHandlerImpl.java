package me.weekbelt.apiserver.auth.common;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.weekbelt.apiserver.auth.TokenProvider;
import me.weekbelt.apiserver.auth.dto.MemberContext;
import me.weekbelt.apiserver.auth.dto.TokenDto;
import me.weekbelt.auth.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MemberContext memberContext = (MemberContext) authentication.getPrincipal();
        Member member = memberContext.getMember();
        List<String> roles = getRoles(memberContext);

        log.info("Authentication Success! username: {}", member.getUsername());
//
//        setContextHolder(authentication);

        setResponse(response, tokenProvider.createTokenDto(member, roles));
    }

    private List<String> getRoles(MemberContext memberContext) {
        return memberContext.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(toList());
    }

    private void setContextHolder(Authentication authentication) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private void setResponse(HttpServletResponse response, TokenDto tokenDto) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        OutputStream outputStream = response.getOutputStream();
        objectMapper.writeValue(outputStream, tokenDto);
        outputStream.flush();
    }
}

