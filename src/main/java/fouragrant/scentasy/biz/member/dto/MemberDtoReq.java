package fouragrant.scentasy.biz.member.dto;

import fouragrant.scentasy.biz.member.domain.Age;
import fouragrant.scentasy.biz.member.domain.Gender;
import fouragrant.scentasy.biz.member.domain.Season;
import lombok.*;

public class MemberDtoReq {
    @Getter
    @Setter
    @ToString
    public static class SignUpDto{
        private String email;
        private String password;
        private String nickname;


        @Builder
        public SignUpDto(String email, String password, String nickname){
            this.email = email;
            this.password = password;
            this.nickname = nickname;
        }

    }

    @Getter
    @Setter
    @ToString
    public static class ExtraInfoDto{
        private Gender gender;
        private Age age;
        private Season season;


        @Builder
        public ExtraInfoDto(Gender gender, Age age, Season season){
            this.gender = gender;
            this.age = age;
            this.season = season;
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class LoginDto{
        private String email;
        private String password;



        @Builder
        public LoginDto(String email, String password) {
            this.email = email;
            this.password = password;
        }

    }


}
