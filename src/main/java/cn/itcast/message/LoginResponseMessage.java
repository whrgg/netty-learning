package cn.itcast.message;

public class LoginResponseMessage extends AbstractResponseMessage{
    public LoginResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return LoginResponseMessage;
    }

    public boolean isSuccess() {
        return getSuccess();
    }
}
