package cn.itcast.message;

import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
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
