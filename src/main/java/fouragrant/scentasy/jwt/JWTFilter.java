package fouragrant.scentasy.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

// 요청에 대해서 한 번만 동작하는 필터
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 Authorization 에 담긴 토큰을 꺼냄
        String accessToken = jwtUtil.resolveToken(request);

// 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {

            filterChain.doFilter(request, response);

            return;
        }

        // Bearer 검증 없으면 다음 필터
        if (!accessToken.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
            filterChain.doFilter(request, response);
            return;
        } else {
            accessToken = accessToken.split(" ")[1].trim();
        }

// 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

// 토큰이 access인지 확인 (발급시 페이로드에 명시)
        // 이 프로젝트에서는 패스
//        String category = jwtUtil.getCategory(accessToken);
//
//        if (!category.equals("access")) {
//
//            //response body
//            PrintWriter writer = response.getWriter();
//            writer.print("invalid access token");
//
//            //response status code
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }

// username, role 값을 획득
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        UserEntity userEntity = UserEntity.builder()
                .phone(username)
                .build();
        userEntity.setPhone(username);
        userEntity.setRole(role);
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
