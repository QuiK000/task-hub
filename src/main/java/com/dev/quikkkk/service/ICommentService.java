package com.dev.quikkkk.service;

import com.dev.quikkkk.dto.request.CreateCommentRequest;
import com.dev.quikkkk.dto.response.CommentResponse;

import java.util.List;

public interface ICommentService {
    CommentResponse createComment(CreateCommentRequest request, String taskId, String authorId);

    List<CommentResponse> getAllCommentsByTaskId(String taskId);
}
