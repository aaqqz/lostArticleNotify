package project.toy.api.exception;

public class MemberExist extends GlobalException{

    public static final String MESSAGE = "동일한 email이 존재합니다.";

    public MemberExist(){
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 500;
    }
}
