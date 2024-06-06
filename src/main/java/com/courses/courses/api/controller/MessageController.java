package com.courses.courses.api.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.courses.courses.api.dto.errors.ErrorResp;
import com.courses.courses.api.dto.request.MessageReq;
import com.courses.courses.api.dto.response.MessageResp;
import com.courses.courses.infrastructure.abstract_services.IMessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private final IMessageService messageService;

    @Operation(summary = "Get an message by its ID number")
    @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MessageResp> getById(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.findByIdWithDetails(id));
    }

    @Operation(summary = "Create an message")
    @ApiResponse(responseCode = "400", description = "When the request is not valid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @PostMapping
    public ResponseEntity<MessageResp> createMessage(@Validated @RequestBody MessageReq messageReq) {
        return ResponseEntity.ok(messageService.create(messageReq));
    }

    @Operation(summary = "Get the entire messages list in a paginated manner")
    @GetMapping
    public ResponseEntity<List<MessageResp>> getMessagesBetweenUsers(
            @RequestParam(required = false) Long senderId,
            @RequestParam(required = false) Long receiverId
            ) {
        if (Objects.isNull(senderId) || Objects.isNull(receiverId)) {
            if (Objects.isNull(senderId) && Objects.isNull(receiverId)) {
                return ResponseEntity.ok(messageService.findAllMessages());
            } else {
                throw new IllegalArgumentException("Both senderId and receiverId are required");
            }
        }
        return ResponseEntity.ok(messageService.getMessagesBetweenUsers(senderId,receiverId));
    }

    // @Operation(summary = "Update an message by its ID number")
    // @ApiResponse(responseCode = "400", description = "When the request is not valid", content = {
    //         @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    // })
    // @PutMapping("{id}")
    // public ResponseEntity<MessageResp> updateMessage(@Validated @RequestBody MessageReq messageReq, @PathVariable Long id) {
    //     return ResponseEntity.ok(messageService.update(messageReq, id));
    // }

    // @Operation(summary = "Get the entire messages list in a paginated manner")
    // @GetMapping
    // public ResponseEntity<Page<MessageResp>> getAll(
    //         @RequestParam(defaultValue = "1") int page,
    //         @RequestParam(defaultValue = "5") int size,
    //         @RequestHeader(required = false) SortType sortType) {
    //     if (Objects.isNull(sortType)) {
    //         sortType = SortType.NONE;
    //     }
    //     return ResponseEntity.ok(this.messageService.findAll(page - 1, size, sortType));
    // }

    // @Operation(summary = "Delete an message by its ID number")
    // @ApiResponse(responseCode = "204", description = "Message deleted successfully")
    // @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
    //         @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    // })
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
    //     messageService.delete(id);
    //     return ResponseEntity.noContent().build();
    // }

}
