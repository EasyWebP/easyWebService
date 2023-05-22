package easyweb.easywebservice.domain.Member.model;

import easyweb.easywebservice.domain.Like.model.Like;
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
    private String username;
    private String nickname;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Like> likeProducts = new ArrayList<>();
    @Builder
    public Member(Long id, String email, String password, Authority authority, UserLoginType userLoginType, String userName, String nickName, Boolean deleted) {
        super(id, email, password, authority, userLoginType);
        this.username = userName;
        this.nickname = nickName;
        this.deleted = deleted;
    }



    public void delete() {
        this.deleted = true;
    }

    public void updatePassword(String newPassword) {
        super.updatePassword(newPassword);
    }


    public void updateUserName(String userName) {
        this.username = userName;
    }

    public void updateNickName(String nickName) {
        this.nickname = nickName;}
}
