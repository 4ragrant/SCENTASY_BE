package fouragrant.scentasy.config;

import fouragrant.scentasy.jwt.JWTFilter;
import fouragrant.scentasy.jwt.JwtBlacklist;
import fouragrant.scentasy.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// 직접 만든 TokenProvider 와 JwtFilter 를 SecurityConfig 에 적용할 때 사용
@RequiredArgsConstructor
public class JWTSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
     private final TokenProvider tokenProvider;
    private final JwtBlacklist jwtBlacklist;

    // TokenProvider 를 주입받아서 JwtFilter 를 통해 Security 로직에 필터를 등록
    @Override
    public void configure(HttpSecurity http) {
        JWTFilter customFilter = new JWTFilter(tokenProvider, jwtBlacklist);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
