package cn.itcast.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreateRequestMessage extends AbstractResponseMessage{
    String groupName;
    Set<String> members;

    @Override
    public int getMessageType() {
        return GroupCreateRequestMessage;
    }
}
