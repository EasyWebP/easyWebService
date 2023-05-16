package easyweb.easywebservice.domain.Member.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends MemberBase {
    private Boolean deleted;
    private String userName;

    private String nickName;


    @Builder
    public Member(Long id, String email, String password, Authority authority, UserLoginType userLoginType, String userName, String nickName, Boolean deleted) {
        super(id, email, password, authority, userLoginType);
        this.userName = userName;
        this.nickName = nickName;
        this.deleted = deleted;
    }



    public void delete() {
        this.deleted = true;
    }

    public void updatePassword(String newPassword) {
        super.updatePassword(newPassword);
    }



    public void updateNickname(String nickName) {
        this.nickName = nickName;
    }


    public void updateUserName(String userName) {
        this.userName = userName;
    }
}
