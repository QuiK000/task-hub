package com.dev.quikkkk.service.Impl;

import com.dev.quikkkk.dto.request.CreateCommentRequest;
import com.dev.quikkkk.dto.response.CommentResponse;
import com.dev.quikkkk.exception.BusinessException;
import com.dev.quikkkk.exception.ErrorCode;
import com.dev.quikkkk.mapper.CommentMapper;
import com.dev.quikkkk.repository.ICommentRepository;
import com.dev.quikkkk.repository.ITaskRepository;
import com.dev.quikkkk.repository.IUserRepository;
import com.dev.quikkkk.service.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {
    private final ICommentRepository commentRepository;
    private final ITaskRepository taskRepository;
    private final IUserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentResponse createComment(CreateCommentRequest request, String taskId, String authorId) {
        var user = userRepository.findById(authorId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        var task = taskRepository.findById(taskId).orElseThrow(() -> new BusinessException(ErrorCode.TASK_NOT_FOUND));

        var comment = commentMapper.toComment(request, taskId, authorId);
        var saved = commentRepository.save(comment);

        return commentMapper.toCommentResponse(saved);
    }

    @Override
    public List<CommentResponse> getAllCommentsByTaskId(String taskId) {
        taskRepository
                .findById(taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.TASK_NOT_FOUND));

        return commentRepository
                .findByTaskId(taskId)
                .stream()
                .map(commentMapper::toCommentResponse)
                .toList();
    }
}
