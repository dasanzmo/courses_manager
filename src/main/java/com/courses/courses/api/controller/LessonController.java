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
import com.courses.courses.api.dto.request.LessonReq;
import com.courses.courses.api.dto.response.LessonResp;
import com.courses.courses.api.dto.response.LessonRespWithActivities;
import com.courses.courses.infrastructure.abstract_services.ILessonService;
import com.courses.courses.utils.enums.SortType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    private final ILessonService lessonService;

    @Operation(summary = "Get the entire lessons list in a paginated manner")
    @GetMapping
    public ResponseEntity<Page<LessonResp>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestHeader(required = false) SortType sortType) {
        if (Objects.isNull(sortType)) {
            sortType = SortType.NONE;
        }
        return ResponseEntity.ok(this.lessonService.findAll(page - 1, size, sortType));
    }

    @Operation(summary = "Get an lesson by its ID number")
    @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @GetMapping("/{id}")
    public ResponseEntity<LessonResp> getById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.findByIdWithDetails(id));
    }

    @Operation(summary = "Get an course with lessons by its ID number")
  @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
  })
  @GetMapping("/{id}/activities")
  public ResponseEntity<LessonRespWithActivities> getByIdWithLessons(@PathVariable Long id) {
    return ResponseEntity.ok(lessonService.getLessonWithActivities(id));
  }

    @Operation(summary = "Create an lesson")
    @ApiResponse(responseCode = "400", description = "When the request is not valid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @PostMapping
    public ResponseEntity<LessonResp> createLesson(@Validated @RequestBody LessonReq lessonReq) {
        return ResponseEntity.ok(lessonService.create(lessonReq));
    }

    @Operation(summary = "Update an lesson by its ID number")
    @ApiResponse(responseCode = "400", description = "When the request is not valid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @PutMapping("{id}")
    public ResponseEntity<LessonResp> updateLesson(@Validated @RequestBody LessonReq lessonReq, @PathVariable Long id) {
        return ResponseEntity.ok(lessonService.update(lessonReq, id));
    }

    @Operation(summary = "Delete an lesson by its ID number")
    @ApiResponse(responseCode = "204", description = "Lesson deleted successfully")
    @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
