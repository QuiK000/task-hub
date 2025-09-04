package com.dev.quikkkk.service.Impl;

import com.dev.quikkkk.entity.Project;
import com.dev.quikkkk.exception.BusinessException;
import com.dev.quikkkk.repository.IProjectRepository;
import com.dev.quikkkk.security.UserPrincipal;
import com.dev.quikkkk.service.IProjectSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dev.quikkkk.exception.ErrorCode.PROJECT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectSecurityServiceImpl implements IProjectSecurityService {
    private final IProjectRepository projectRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean isProjectOwner(String projectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) return false;

        Object principal = authentication.getPrincipal();
        String userId;

        if (principal instanceof UserPrincipal userPrincipal) {
            userId = userPrincipal.id();
        } else {
            log.warn("Unexpected principal type: {}", principal.getClass().getName());
            return false;
        }

        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new BusinessException(PROJECT_NOT_FOUND));

        return project.getOwnerId().getId().equals(userId);
    }
}
