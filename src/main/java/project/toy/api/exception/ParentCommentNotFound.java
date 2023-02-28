package project.toy.api.exception;

public class ParentCommentNotFound extends GlobalException {

    public static final String MESSAGE = "부모 댓글이 없습니다.";

    public ParentCommentNotFound() { super(MESSAGE); };

    @Override
    public int getStatusCode() {
        return 0;
    }
}
