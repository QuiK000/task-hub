package com.dev.quikkkk.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class CreateCommentRequest {
    @NotNull(message = "VALIDATION.COMMENT.TEXT.NOT_NULL")
    @Schema(example = "Comment text")
    @Size(min = 3, max = 500, message = "VALIDATION.COMMENT.TEXT.SIZE")
    private String text;
}
