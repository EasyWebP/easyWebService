package easyweb.easywebservice.domain.Like.exception;

public class LikeDoesntExist extends IllegalArgumentException {
    public LikeDoesntExist() {
        super("좋아요가 존재하지 않습니다");
    }

    public LikeDoesntExist(String s) {
        super(s);
    }
}
