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
import com.courses.courses.api.dto.request.UserReq;
import com.courses.courses.api.dto.response.UserResp;
import com.courses.courses.api.dto.response.UserRespWithCourses;
import com.courses.courses.api.dto.response.UserRespWithSubmissions;
import com.courses.courses.infrastructure.abstract_services.IUserService;
import com.courses.courses.utils.enums.SortType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

  @Autowired
  private final IUserService userService;

  @Operation(summary = "Get the entire users list in a paginated manner")
  @GetMapping
  public ResponseEntity<Page<UserResp>> getAll(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestHeader(required = false) SortType sortType) {
    if (Objects.isNull(sortType)) {
      sortType = SortType.NONE;
    }
    return ResponseEntity.ok(this.userService.findAll(page - 1, size, sortType));
  }

  @Operation(summary = "Get an user by its ID number")
  @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
  })
  @GetMapping("/{id}")
  public ResponseEntity<UserResp> getById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.findByIdWithDetails(id));
  }

  @Operation(summary = "Get an user with courses by its ID number")
  @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
  })
  @GetMapping("/{id}/courses")
  public ResponseEntity<UserRespWithCourses> getByIdWithCourses(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUsersWithCourses(id));
  }

  @Operation(summary = "Get an user with submissions by its ID number")
  @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
  })
  @GetMapping("/{id}/submissions")
  public ResponseEntity<UserRespWithSubmissions> getByIdWithSubmisions(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUserWithSubmissions(id));
  }

  @Operation(summary = "Create an user")
  @ApiResponse(responseCode = "400", description = "When the request is not valid", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
  })
  @PostMapping
  public ResponseEntity<UserResp> createUser(@Validated @RequestBody UserReq userReq) {
    return ResponseEntity.ok(userService.create(userReq));
  }

  @Operation(summary = "Update an user by its ID number")
  @ApiResponse(responseCode = "400", description = "When the request is not valid", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
  })
  @PutMapping("{id}")
  public ResponseEntity<UserResp> updateUser(@Validated @RequestBody UserReq userReq, @PathVariable Long id) {
    return ResponseEntity.ok(userService.update(userReq, id));
  }

  @Operation(summary = "Delete an user by its ID number")
  @ApiResponse(responseCode = "204", description = "User deleted successfully")
  @ApiResponse(responseCode = "400", description = "When the ID is not found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResp.class))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
