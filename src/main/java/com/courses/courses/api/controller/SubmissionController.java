package com.courses.courses.api.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.courses.courses.api.dto.errors.ErrorResp;
import com.courses.courses.api.dto.request.SubmissionReq;
import com.courses.courses.api.dto.response.SubmissionResp;
import com.courses.courses.infrastructure.abstract_services.ISubmissionService;
import com.courses.courses.utils.enums.SortType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/submissions")
public class SubmissionController {

    @Autowired
    private final ISubmissionService submissionService;

    @Operation(summary = "Get the entire submissions list in a paginated manner")
    @GetMapping
    public ResponseEntity<Page<SubmissionResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestHeader(required = false) SortType sortType) {
        if (Objects.isNull(sortType)) {
            sortType = SortType.NONE;
        }
        return ResponseEntity.ok(this.submissionService.findAll(page - 1, size, sortType));
    }

    @Operation(summary = "Get an submission by its ID number")
    @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @GetMapping("/{id}")
    public ResponseEntity<SubmissionResp> getById(@PathVariable Long id) {
        return ResponseEntity.ok(submissionService.findByIdWithDetails(id));
    }

    @Operation(summary = "Create an submission")
    @ApiResponse(responseCode = "400", description = "When the request is not valid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @PostMapping
    public ResponseEntity<SubmissionResp> createSubmission(@Validated @RequestBody SubmissionReq submissionReq) {
        return ResponseEntity.ok(submissionService.create(submissionReq));
    }

    @Operation(summary = "Update an submission by its ID number")
    @ApiResponse(responseCode = "400", description = "When the request is not valid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @PutMapping("{id}")
    public ResponseEntity<SubmissionResp> updateSubmission(@Validated @RequestBody SubmissionReq submissionReq, @PathVariable Long id) {
        return ResponseEntity.ok(submissionService.update(submissionReq, id));
    }

    @Operation(summary = "Delete an submission by its ID number")
    @ApiResponse(responseCode = "204", description = "Submission deleted successfully")
    @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) {
        submissionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
