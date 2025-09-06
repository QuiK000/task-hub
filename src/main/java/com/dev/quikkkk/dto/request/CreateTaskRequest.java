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
public class CreateTaskRequest {
    @NotBlank(message = "VALIDATION.TASK.TITLE.NOT_BLANK")
    @Size(min = 3, max = 50, message = "VALIDATION.TASK.TITLE.SIZE")
    @Schema(example = "Title of task")
    private String title;

    @NotBlank(message = "VALIDATION.TASK.DESCRIPTION.NOT_BLANK")
    @Size(min = 3, max = 500, message = "VALIDATION.TASK.DESCRIPTION.SIZE")
    @Schema(example = "Description of task")
    private String description;

    @Schema(example = "cc7ac71b-8eae-4bbe-b249-a2b3272bad22")
    private String assigneeId;
}
