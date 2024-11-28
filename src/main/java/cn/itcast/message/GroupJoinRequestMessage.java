package cn.itcast.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class GroupJoinRequestMessage extends AbstractResponseMessage {

    String username;
    String groupName;

    @Override
    public int getMessageType() {
        return GroupJoinRequestMessage;
    }
}
