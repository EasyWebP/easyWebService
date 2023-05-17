package easyweb.easywebservice.domain.Member.model;

import easyweb.easywebservice.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class MemberBase extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    protected Authority authority;

    @Enumerated(EnumType.STRING)
    private UserLoginType userLoginType;

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

}
