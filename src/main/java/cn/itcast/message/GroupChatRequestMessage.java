package cn.itcast.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatRequestMessage extends AbstractResponseMessage{

    String from;
    String groupName;
    String content;

    @Override
    public int getMessageType() {
        return GroupChatRequestMessage;
    }
}
