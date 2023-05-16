package easyweb.easywebservice.domain.Member.exception;

public class MemberNotFoundByEmail extends IllegalArgumentException {
    public MemberNotFoundByEmail() {
        super("해당 이메일로 존재하는 멤버가 없습니다.");
    }

    public MemberNotFoundByEmail(String s) {
        super(s);
    }
}

