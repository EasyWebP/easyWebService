package easyweb.easywebservice.domain.Like.exception;

public class LikeAlreadyExists extends IllegalArgumentException {
    public LikeAlreadyExists() {
        super("좋아요가 이미 존재합니다");
    }

    public LikeAlreadyExists(String s) {
        super(s);
    }
}
