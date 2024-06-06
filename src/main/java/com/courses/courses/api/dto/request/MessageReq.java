package com.courses.courses.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageReq {

    private String messageContent;
    @NotNull(message = "Sender id required")
    private Long senderId;
    @NotNull(message = "Receiver id required")
    private Long receiverId;
    @NotNull(message = "Course id required")
    private Long courseId;
}
