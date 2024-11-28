package cn.itcast.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupJoinRequestMessage extends AbstractResponseMessage {

    String username;
    String groupName;

    @Override
    public int getMessageType() {
        return GroupJoinRequestMessage;
    }
}
