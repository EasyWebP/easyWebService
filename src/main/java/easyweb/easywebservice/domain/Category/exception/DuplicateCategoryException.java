package easyweb.easywebservice.domain.Category.exception;

public class DuplicateCategoryException extends IllegalArgumentException {
    public DuplicateCategoryException() {
        super("기존에 존재하는 카테고리와 이름이 겹칩니다");
    }

    public DuplicateCategoryException(String s) {
        super(s);
    }
}
