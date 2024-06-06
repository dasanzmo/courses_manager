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
import com.courses.courses.api.dto.request.ActivityReq;
import com.courses.courses.api.dto.response.ActivityResp;
import com.courses.courses.api.dto.response.ActivityRespWithSubmissions;
import com.courses.courses.infrastructure.abstract_services.IActivityService;
import com.courses.courses.utils.enums.SortType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private final IActivityService activityService;

    @Operation(summary = "Get the entire activities list in a paginated manner")
    @GetMapping
    public ResponseEntity<Page<ActivityResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestHeader(required = false) SortType sortType) {
        if (Objects.isNull(sortType)) {
            sortType = SortType.NONE;
        }
        return ResponseEntity.ok(this.activityService.findAll(page - 1, size, sortType));
    }

    @Operation(summary = "Get an activity by its ID number")
    @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ActivityResp> getById(@PathVariable Long id) {
        return ResponseEntity.ok(activityService.findByIdWithDetails(id));
    }

    @Operation(summary = "Get an activity with submissions by its ID number")
    @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @GetMapping("/{id}/submissions")
    public ResponseEntity<ActivityRespWithSubmissions> getByIdWithSubmissions(@PathVariable Long id) {
        return ResponseEntity.ok(activityService.getActivityWithSubmissions(id));
    }

    @Operation(summary = "Create an activity")
    @ApiResponse(responseCode = "400", description = "When the request is not valid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @PostMapping
    public ResponseEntity<ActivityResp> createActivity(@Validated @RequestBody ActivityReq activityReq) {
        return ResponseEntity.ok(activityService.create(activityReq));
    }

    @Operation(summary = "Update an activity by its ID number")
    @ApiResponse(responseCode = "400", description = "When the request is not valid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @PutMapping("{id}")
    public ResponseEntity<ActivityResp> updateActivity(@Validated @RequestBody ActivityReq activityReq,
            @PathVariable Long id) {
        return ResponseEntity.ok(activityService.update(activityReq, id));
    }

    @Operation(summary = "Delete an activity by its ID number")
    @ApiResponse(responseCode = "204", description = "Activity deleted successfully")
    @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
