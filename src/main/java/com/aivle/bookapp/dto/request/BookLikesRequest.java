package com.aivle.bookapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookLikesRequest {
    @NotNull(message = "좋아요 수는 필수입니다.")
    private Integer likes;
}
