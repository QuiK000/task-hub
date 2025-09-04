package com.dev.quikkkk.dto.request;

import com.dev.quikkkk.entity.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    @NotNull(message = "VALIDATION.USER.UPDATE.USERNAME.NOT_NULL")
    @Size(min = 3, max = 50, message = "VALIDATION.USER.UPDATE.USERNAME.SIZE")
    private String username;

    @NotEmpty(message = "VALIDATION.USER.UPDATE.ROLES.NOT_EMPTY")
    private Set<Role> roles;
}
