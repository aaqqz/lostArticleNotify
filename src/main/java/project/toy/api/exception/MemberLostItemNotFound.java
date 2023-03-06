package project.toy.api.exception;

public class MemberLostItemNotFound extends GlobalException{

    public static final String MESSAGE = "분실물 정보가 없습니다.";

    public MemberLostItemNotFound(){
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 500;
    }
}
