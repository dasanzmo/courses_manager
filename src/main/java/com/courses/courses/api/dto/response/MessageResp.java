package com.courses.courses.api.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResp {
    private Long id;
    private String messageContent;
    private LocalDate sentDate;
    private UserResp sender;
    private UserResp receiver;

}
