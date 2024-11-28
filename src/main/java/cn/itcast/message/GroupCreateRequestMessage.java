package cn.itcast.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class GroupCreateRequestMessage extends AbstractResponseMessage{
    String groupName;
    Set<String> members;

    @Override
    public int getMessageType() {
        return GroupCreateRequestMessage;
    }
}
