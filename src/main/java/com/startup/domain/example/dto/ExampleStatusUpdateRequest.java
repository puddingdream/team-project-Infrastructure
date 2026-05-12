package com.startup.domain.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Example 상태 변경 요청")
public record ExampleStatusUpdateRequest(
        @Schema(description = "변경할 상태", example = "ARCHIVED")
        @NotBlank(message = "상태는 필수입니다.")
        String status
) {
}
