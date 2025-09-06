package com.dev.quikkkk.controller;

import com.dev.quikkkk.dto.request.CreateCommentRequest;
import com.dev.quikkkk.dto.response.ApiResponse;
import com.dev.quikkkk.dto.response.CommentResponse;
import com.dev.quikkkk.security.UserPrincipal;
import com.dev.quikkkk.service.ICommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Comments controller", description = "Comments API")
public class CommentController {
    private final ICommentService commentService;

    @PostMapping("/{taskId}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @RequestBody @Valid CreateCommentRequest request,
            @PathVariable String taskId,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        return ResponseEntity.ok(ApiResponse.ok(commentService.createComment(request, taskId, user.id())));
    }

    @GetMapping("/{taskId}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getAllCommentsByTaskId(
            @PathVariable String taskId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(commentService.getAllCommentsByTaskId(taskId)));
    }
}
