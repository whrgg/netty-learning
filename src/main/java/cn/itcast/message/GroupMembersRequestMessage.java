package cn.itcast.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMembersRequestMessage extends AbstractResponseMessage{

    String groupName;

    @Override
    public int getMessageType() {
        return GroupMembersRequestMessage;
    }
}
