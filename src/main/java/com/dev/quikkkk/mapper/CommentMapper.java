package com.dev.quikkkk.mapper;

import com.dev.quikkkk.dto.request.CreateCommentRequest;
import com.dev.quikkkk.dto.response.CommentResponse;
import com.dev.quikkkk.entity.Comment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentMapper {
    public Comment toComment(CreateCommentRequest request, String taskId, String authorId) {
        return Comment
                .builder()
                .taskId(taskId)
                .authorId(authorId)
                .text(request.getText())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public CommentResponse toCommentResponse(Comment comment) {
        return CommentResponse
                .builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorId(comment.getAuthorId())
                .taskId(comment.getTaskId())
                .createdDate(comment.getCreatedAt().toString())
                .build();
    }
}
