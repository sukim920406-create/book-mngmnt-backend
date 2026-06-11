package com.aivle.bookapp.dto.request;

import com.aivle.bookapp.exception.BadRequestException;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUpdateRequest {
    private Long id;

    @Builder.Default
    private Optional<String> title = Optional.empty();

    @Builder.Default
    private Optional<String> author = Optional.empty();

    @Builder.Default
    private Optional<String> content = Optional.empty();

    @Builder.Default
    private Optional<@Size(max = 200, message = "한줄소개는 200자를 넘을 수 없습니다.") String> summary = Optional.empty();

    @Builder.Default
    private Optional<String> copy = Optional.empty();

    @Builder.Default
    private Optional<String> coverImageUrl = Optional.empty();

    @Builder.Default
    private Optional<List<String>> tags = Optional.empty();

    @Builder.Default
    private Optional<List<Float>> embeddingJson = Optional.empty();

    @Builder.Default
    private Optional<Long> embeddingDurationMs = Optional.empty();

    @Builder.Default
    private Optional<Integer> likes = Optional.empty();

    public boolean hasTitle() {
        return hasValue(title);
    }

    public String getTitle() {
        return getRequiredValue(title, "title");
    }

    public boolean hasAuthor() {
        return hasValue(author);
    }

    public String getAuthor() {
        return getRequiredValue(author, "author");
    }

    public boolean hasContent() {
        return hasValue(content);
    }

    public String getContent() {
        return getRequiredValue(content, "content");
    }

    public boolean hasSummary() {
        return hasValue(summary);
    }

    public String getSummary() {
        return getRequiredValue(summary, "summary");
    }

    public boolean hasCopy() {
        return hasValue(copy);
    }

    public String getCopy() {
        return getRequiredValue(copy, "copy");
    }

    public boolean hasCoverImageUrl() {
        return hasValue(coverImageUrl);
    }

    public String getCoverImageUrl() {
        return getRequiredValue(coverImageUrl, "coverImageUrl");
    }

    public boolean hasTags() {
        return hasValue(tags);
    }

    public List<String> getTags() {
        return getRequiredValue(tags, "tags");
    }

    public boolean hasEmbeddingJson() {
        return hasValue(embeddingJson);
    }

    public List<Float> getEmbeddingJson() {
        return getRequiredValue(embeddingJson, "embeddingJson");
    }

    public boolean hasEmbeddingDurationMs() {
        return hasValue(embeddingDurationMs);
    }

    public Long getEmbeddingDurationMs() {
        return getRequiredValue(embeddingDurationMs, "embeddingDurationMs");
    }

    public boolean hasLikes() {
        return hasValue(likes);
    }

    public Integer getLikes() {
        return getRequiredValue(likes, "likes");
    }

    private static boolean hasValue(Optional<?> value) {
        return value != null && value.isPresent();
    }

    private static <T> T getRequiredValue(Optional<T> value, String fieldName) {
        if (!hasValue(value)) {
            throw new BadRequestException(fieldName + " 값이 필요합니다.");
        }
        return value.get();
    }
}
