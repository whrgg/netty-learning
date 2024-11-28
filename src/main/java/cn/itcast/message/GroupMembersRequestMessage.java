package cn.itcast.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class GroupMembersRequestMessage extends AbstractResponseMessage{

    String groupName;

    @Override
    public int getMessageType() {
        return GroupMembersRequestMessage;
    }
}
