package com.dev.quikkkk.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProjectRequest {
    @NotBlank(message = "VALIDATION.PROJECT.TITLE.NOT_BLANK")
    @Size(min = 3, max = 50, message = "VALIDATION.PROJECT.TITLE.SIZE")
    @Schema(example = "Title of project")
    private String title;

    @NotBlank(message = "VALIDATION.PROJECT.DESCRIPTION.NOT_BLANK")
    @Size(min = 3, max = 500, message = "VALIDATION.PROJECT.DESCRIPTION.SIZE")
    @Schema(example = "Description of project")
    private String description;
}
