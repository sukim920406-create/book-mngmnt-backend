package com.aivle.bookapp.dto.response;

import com.aivle.bookapp.domain.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BookResponse {
    private Long id;

    @NotBlank(message = "도서 제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자를 넘을 수 없습니다.")
    private String title;

    @NotBlank(message = "저자명은 필수입니다.")
    private String author;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @Size(max = 200, message = "한줄소개는 200자를 넘을 수 없습니다.")
    private String summary;

    private String copy;

    private String coverImageUrl;

    private Integer likes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<String> tags;

    public static BookResponse fromBookAndTags(Book book, List<String> tags) {
        return new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getContent(), book.getSummary(), book.getCopy(), book.getCoverImageUrl(), book.getLikes(), book.getCreatedAt(), book.getUpdatedAt(), tags);
    }
}
