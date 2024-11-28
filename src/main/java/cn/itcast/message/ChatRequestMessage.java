package cn.itcast.message;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequestMessage extends Message {
    private String from;
    private String to;
    private String content;

    @Override
    public int getMessageType() {
        return ChatRequestMessage;
    }
}