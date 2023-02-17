package project.toy.api.exception;

/**
 * status -> 400
 */
public class InvalidLoginInformation extends GlobalException {

    private static final String MESSAGE = "아이디/비밀번호가 올바르지 않습니다.";

    public InvalidLoginInformation() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
