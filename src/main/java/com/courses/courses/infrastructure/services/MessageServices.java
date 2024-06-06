package com.courses.courses.infrastructure.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.courses.courses.api.dto.request.MessageReq;
import com.courses.courses.api.dto.response.MessageResp;
import com.courses.courses.domain.entities.Course;
import com.courses.courses.domain.entities.Message;
import com.courses.courses.domain.entities.User;
import com.courses.courses.domain.repositories.CourseRepository;
import com.courses.courses.domain.repositories.MessageRepository;
import com.courses.courses.domain.repositories.UserRepository;
import com.courses.courses.infrastructure.abstract_services.IMessageService;
import com.courses.courses.infrastructure.helper.MessageHelper;
import com.courses.courses.utils.enums.SortType;
import com.courses.courses.utils.exception.BadRequestException;
import com.courses.courses.utils.message.ErrorMessage;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageServices implements IMessageService {

    @Autowired
    private final MessageRepository messageRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CourseRepository courseRepository;

    @Override
    public Page<MessageResp> findAll(int page, int size, SortType sortType) {
        if (page < 0)
            page = 0;

        PageRequest pageRequest = null;

        switch (sortType) {
            case NONE -> pageRequest = PageRequest.of(page, size);
            case ASC -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
            default -> throw new IllegalArgumentException("No valid sort: " + sortType);
        }

        Pageable pageable = pageRequest;
        return messageRepository.findAll(pageable).map(message -> MessageHelper.messageToResp(message));
    }

    @Override
    public List<MessageResp> findAllMessages() {
        return messageRepository.findAll().stream().map(message -> MessageHelper.messageToResp(message)).toList();
    }

    @Override
    public MessageResp findByIdWithDetails(Long id) {
        return MessageHelper.messageToResp(findById(id));
    }

    @Override
    public MessageResp create(MessageReq request) {
        User sender = userRepository.findById(request.getSenderId()).orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("user")));
        User receiver = userRepository.findById(request.getReceiverId()).orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("user")));
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("course")));
        
        Message message = MessageHelper.reqToMessage(request);

        message.setCourse(course);
        message.setSender(sender);
        message.setReceiver(receiver);
        return MessageHelper.messageToResp(messageRepository.save(message));
    }

    @Override
    public MessageResp update(MessageReq request, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<MessageResp> getMessagesBetweenUsers(Long senderId, Long receiverId) {
        return getMessagesBetweenUsersById(senderId, receiverId).stream().map(message -> MessageHelper.messageToResp(message)).toList();
    }

    private List<Message> getMessagesBetweenUsersById(Long senderId, Long receiverId) {
        userRepository.findById(senderId).orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("user")));
        userRepository.findById(receiverId).orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("user")));
        return messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    private Message findById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.idNotFound("message")));
    }

}
