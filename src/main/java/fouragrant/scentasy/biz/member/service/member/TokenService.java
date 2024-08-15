package fouragrant.scentasy.biz.member.service.member;

import fouragrant.scentasy.biz.member.domain.Refresh;
import fouragrant.scentasy.biz.member.dto.TokenDto;
import fouragrant.scentasy.jwt.JWTUtil;
import fouragrant.scentasy.biz.member.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    public static long REFRESH_TOKEN_VALIDITY = 30L * 24 * 60 * 60 * 1000; // 30일을 밀리초로 변환

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public TokenService(JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    public TokenDto reissue(TokenDto tokenDto) {
        String refresh = tokenDto.getRefreshToken();
        // 토큰 존재 확인
        if (refresh == null || refresh.equals("")) {
            throw new IllegalArgumentException("리프레시 토큰이 없습니다.");
        }
        // 토큰 만료 확인
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("리프레시 토큰이 만료되었습니다.");
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            throw new IllegalArgumentException("유효한 토큰이 아닙니다.");
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            throw new IllegalArgumentException("유효한 토큰이 아닙니다.");
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", username, role, REFRESH_TOKEN_VALIDITY);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, REFRESH_TOKEN_VALIDITY);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefresh(refresh);
        saveRefreshToken(username, newRefresh);

        TokenDto dto = new TokenDto();
        dto.setAccessToken(newAccess);
        dto.setRefreshToken(newRefresh);
        return dto;
    }


    public void logout(TokenDto tokenDto) {
        String refresh = tokenDto.getRefreshToken();
        // 토큰 존재 확인
        if (refresh == null || refresh.equals("")) {
            throw new IllegalArgumentException("리프레시 토큰이 없습니다.");
        }
        // 토큰 만료 확인
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("리프레시 토큰이 만료되었습니다.");
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            throw new IllegalArgumentException("유효한 토큰이 아닙니다.");
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            throw new IllegalArgumentException("유효한 토큰이 아닙니다.");
        }

        //로그아웃 진행
        //Refresh 토큰 DB에서 제거
        refreshRepository.deleteByRefresh(refresh);
    }

    // 리프레시 토큰 저장
    public void saveRefreshToken(String username, String refresh) {

        Date date = new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY);

        // 유저의 리프레시가 이미 존재한다면 업데이트
        Refresh existRefresh = refreshRepository.findByUsername(username);
        if (existRefresh != null) {
            existRefresh.setRefresh(refresh);
            existRefresh.setExpiration(date.toString());
            refreshRepository.save(existRefresh);
            return;
        }

        Refresh refreshEntity = new Refresh();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}
