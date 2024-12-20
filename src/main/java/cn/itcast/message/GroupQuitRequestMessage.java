package cn.itcast.message;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class GroupQuitRequestMessage extends AbstractResponseMessage{

    String username;
    String groupName;

    public GroupQuitRequestMessage(String username, String groupName) {
        this.groupName = groupName;
        this.username = username;
    }

    @Override
    public int getMessageType() {
        return GroupQuitResponseMessage;
    }
}
