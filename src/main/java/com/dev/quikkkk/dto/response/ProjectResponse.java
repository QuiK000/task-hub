package com.dev.quikkkk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponse implements Serializable {
    private String id;
    private String title;
    private String description;
    private String ownerId;
    private LocalDateTime createdDate;
}
