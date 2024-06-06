package com.courses.courses.infrastructure.helper;

import com.courses.courses.api.dto.request.MessageReq;
import com.courses.courses.api.dto.response.MessageResp;
import com.courses.courses.domain.entities.Message;

public class MessageHelper {

    public static MessageResp messageToResp(Message message){
        return MessageResp.builder()
        .id(message.getId())
        .sentDate(message.getSentDate())
        .messageContent(message.getMessageContent())
        .sender(UserHelper.userToResp(message.getSender()))
        .receiver(UserHelper.userToResp(message.getReceiver()))
        .build();
    }

    public static Message reqToMessage(MessageReq messageReq){
        return Message.builder()
        .messageContent(messageReq.getMessageContent())
        .build();
    }

}
