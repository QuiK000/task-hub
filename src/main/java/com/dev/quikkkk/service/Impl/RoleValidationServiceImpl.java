package com.dev.quikkkk.service.Impl;

import com.dev.quikkkk.entity.Role;
import com.dev.quikkkk.exception.BusinessException;
import com.dev.quikkkk.exception.ErrorCode;
import com.dev.quikkkk.repository.IRoleRepository;
import com.dev.quikkkk.service.IRoleValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleValidationServiceImpl implements IRoleValidationService {
    private final IRoleRepository roleRepository;

    @Override
    public void validateRoles(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) return;

        Set<String> validRoleNames = roleRepository
                .findAll()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        for (Role role : roles) {
            if (!validRoleNames.contains(role.getName())) {
                throw new BusinessException(ErrorCode.INVALID_ROLE);
            }
        }
    }
}
