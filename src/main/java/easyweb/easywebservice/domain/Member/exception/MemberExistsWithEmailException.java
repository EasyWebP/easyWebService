package easyweb.easywebservice.domain.Member.exception;

public class MemberExistsWithEmailException extends IllegalArgumentException {
    public MemberExistsWithEmailException() {
        super("해당 이메일로 멤버가 존재합니다.");
    }

    public MemberExistsWithEmailException(String s) {
        super(s);
    }
}
