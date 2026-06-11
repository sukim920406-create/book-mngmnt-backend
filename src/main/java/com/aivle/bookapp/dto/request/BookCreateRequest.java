package com.aivle.bookapp.dto.request;

import com.aivle.bookapp.domain.Book;
import com.aivle.bookapp.domain.BookTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BookCreateRequest {
    private Long id;

    @NotBlank(message = "도서 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "저자명은 필수입니다.")
    private String author;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @Size(max = 200, message = "한줄소개는 200자를 넘을 수 없습니다.")
    private String summary;

    private String copy;

    private String coverImageUrl;

    @Builder.Default
    private List<String> tags = new ArrayList<>();

    private String embeddingJson;

    private Long embeddingDurationMs;

    @Builder.Default
    private Integer likes = 0;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Book makeBook() {
        return new Book(id, title, author, content, summary, copy, coverImageUrl, likes, createdAt, updatedAt);
    }
}
