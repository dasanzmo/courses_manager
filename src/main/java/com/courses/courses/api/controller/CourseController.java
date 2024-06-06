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
import com.courses.courses.api.dto.request.CourseReq;
import com.courses.courses.api.dto.response.CourseResp;
import com.courses.courses.api.dto.response.CourseRespWithLessons;
import com.courses.courses.api.dto.response.CourseRespWithStudents;
import com.courses.courses.infrastructure.abstract_services.ICourseService;
import com.courses.courses.utils.enums.SortType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/courses")
public class CourseController {

  @Autowired
  private final ICourseService courseService;

  @Operation(summary = "Get the entire courses list in a paginated manner")
  @GetMapping
  public ResponseEntity<Page<CourseResp>> getAll(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestHeader(required = false) SortType sortType) {
    if (Objects.isNull(sortType)) {
      sortType = SortType.NONE;
    }
    return ResponseEntity.ok(this.courseService.findAll(page - 1, size, sortType));
  }

  @Operation(summary = "Get an course by its ID number")
  @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
  })
  @GetMapping("/{id}")
  public ResponseEntity<CourseResp> getById(@PathVariable Long id) {
    return ResponseEntity.ok(courseService.findByIdWithDetails(id));
  }

  @Operation(summary = "Get an course with lessons by its ID number")
  @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
  })
  @GetMapping("/{id}/lessons")
  public ResponseEntity<CourseRespWithLessons> getByIdWithLessons(@PathVariable Long id) {
    return ResponseEntity.ok(courseService.getCourseWithLessons(id));
  }

   @Operation(summary = "Get an course with students by its ID number")
  @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
  })
  @GetMapping("/{id}/users")
  public ResponseEntity<CourseRespWithStudents> getByIdWithStudents(@PathVariable Long id) {
    return ResponseEntity.ok(courseService.getCourseWithStudents(id));
  }

  @Operation(summary = "Create an course")
  @ApiResponse(responseCode = "400", description = "When the request is not valid", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
  })
  @PostMapping
  public ResponseEntity<CourseResp> createCourse(@Validated @RequestBody CourseReq courseReq) {
    return ResponseEntity.ok(courseService.create(courseReq));
  }

  @Operation(summary = "Update an course by its ID number")
  @ApiResponse(responseCode = "400", description = "When the request is not valid", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
  })
  @PutMapping("{id}")
  public ResponseEntity<CourseResp> updateCourse(@Validated @RequestBody CourseReq courseReq, @PathVariable Long id) {
    return ResponseEntity.ok(courseService.update(courseReq, id));
  }

  @Operation(summary = "Delete an course by its ID number")
  @ApiResponse(responseCode = "204", description = "course deleted successfully")
  @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
    courseService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
