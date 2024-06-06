package com.courses.courses.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.courses.courses.api.dto.errors.ErrorResp;
import com.courses.courses.api.dto.request.EnrollmentReq;
import com.courses.courses.api.dto.response.EnrollmentResp;
import com.courses.courses.infrastructure.abstract_services.IEnrollmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private final IEnrollmentService enrollmentService;

    @Operation(summary = "Get an enrollment by its ID number")
    @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResp> getById(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.findByIdWithDetails(id));
    }

    @Operation(summary = "Create an enrollment")
    @ApiResponse(responseCode = "400", description = "When the request is not valid", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @PostMapping
    public ResponseEntity<EnrollmentResp> createEnrollment(@Validated @RequestBody EnrollmentReq enrollmentReq) {
        return ResponseEntity.ok(enrollmentService.create(enrollmentReq));
    }
    
    @Operation(summary = "Delete an enrollment by its ID number")
    @ApiResponse(responseCode = "204", description = "Enrollment deleted successfully")
    @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // @Operation(summary = "Update an enrollment by its ID number")
    // @ApiResponse(responseCode = "400", description = "When the request is not valid", content = {
        //         @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    // })
    // @PutMapping("{id}")
    // public ResponseEntity<EnrollmentResp> updateEnrollment(@Validated @RequestBody EnrollmentReq enrollmentReq, @PathVariable Long id) {
    //     return ResponseEntity.ok(enrollmentService.update(enrollmentReq, id));
    // }

    // @Operation(summary = "Get the entire enrollments list in a paginated manner")
    // @GetMapping
    // public ResponseEntity<Page<EnrollmentResp>> getAll(
    //         @RequestParam(defaultValue = "1") int page,
    //         @RequestParam(defaultValue = "5") int size,
    //         @RequestHeader(required = false) SortType sortType) {
    //     if (Objects.isNull(sortType)) {
    //         sortType = SortType.NONE;
    //     }
    //     return ResponseEntity.ok(this.enrollmentService.findAll(page - 1, size, sortType));
    // }


}
