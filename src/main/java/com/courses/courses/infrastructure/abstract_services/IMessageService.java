package com.courses.courses.infrastructure.abstract_services;

import java.util.List;

import com.courses.courses.api.dto.request.MessageReq;
import com.courses.courses.api.dto.response.MessageResp;

public interface IMessageService extends CrudService<MessageReq,MessageResp,Long>{

    public final String FIELD_BY_SORT = "sentDate";

    public List<MessageResp> getMessagesBetweenUsers(Long senderId, Long receiverId);
    public List<MessageResp> findAllMessages();

}
