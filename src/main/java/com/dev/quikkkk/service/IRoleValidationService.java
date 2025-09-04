package com.dev.quikkkk.service;

import com.dev.quikkkk.entity.Role;

import java.util.Set;

public interface IRoleValidationService {
    void validateRoles(Set<Role> roles);
}
