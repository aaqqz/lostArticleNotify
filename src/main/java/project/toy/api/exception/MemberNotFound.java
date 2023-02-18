package project.toy.api.exception;

public class MemberNotFound extends GlobalException{

    public static final String MESSAGE = "멤버 정보가 없습니다.";

    public MemberNotFound(){
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 500;
    }
}
