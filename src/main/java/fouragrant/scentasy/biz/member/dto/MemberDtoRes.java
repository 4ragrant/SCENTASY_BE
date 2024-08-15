package fouragrant.scentasy.biz.member.dto;

import fouragrant.scentasy.biz.member.domain.Member;
import lombok.*;

public class MemberDtoRes {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenDto {
        private String accessToken;
        private String refreshToken;
        private String email;
        private Long id;
    }

    @Getter
    public static class UserRes {
        private String email;

        private String nickname;

        private String gender;

        private String age;

        private String season;

        @Builder
        public UserRes(Member member) {
            this.email = member.getEmail();
            this.nickname = member.getNickname();

        }
    }

}
