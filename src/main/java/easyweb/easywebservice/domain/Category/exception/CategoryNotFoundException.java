package easyweb.easywebservice.domain.Category.exception;

public class CategoryNotFoundException extends IllegalArgumentException {
    public CategoryNotFoundException() {
        super("해당 카테고리를 찾을 수 없습니다");

    }

    public CategoryNotFoundException(String s) {
        super(s);
    }
}
