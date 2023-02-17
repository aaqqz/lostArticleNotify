package project.toy.api.exception;

/**
 * status -> 401
 */
public class JwtAuthFail extends GlobalException {

    public JwtAuthFail(String MESSAGE) {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
