package com.aivle.bookapp.dto.response;

import com.aivle.bookapp.domain.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookSummaryResponse {
    private Long id;

    @NotBlank(message = "도서 제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자를 넘을 수 없습니다.")
    private String title;

    @NotBlank(message = "저자명은 필수입니다.")
    private String author;

    private String coverImageUrl;

    private Integer likes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double similarityScore;

    public static BookSummaryResponse from(Book book) {
        return from(book, null);
    }

    public static BookSummaryResponse from(Book book, Double similarityScore) {
        return new BookSummaryResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getCoverImageUrl(),
                book.getLikes(),
                similarityScore
        );
    }
}
