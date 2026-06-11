package com.aivle.bookapp.service;

import com.aivle.bookapp.domain.BookEmbedding;
import com.aivle.bookapp.exception.BadRequestException;
import com.aivle.bookapp.exception.BookEmbeddingNotFoundException;
import com.aivle.bookapp.repository.BookEmbeddingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookEmbeddingService {
    private static final String EMBEDDING_MODEL = "text-embedding-3-small";

    private final BookEmbeddingRepository bookEmbeddingRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public BookEmbedding findById(Long bookId) {
        return bookEmbeddingRepository.findById(bookId).orElseThrow(() -> new BookEmbeddingNotFoundException(bookId));
    }

    @Transactional(readOnly = true)
    public BookEmbedding findByBookId(Long bookId) {
        return bookEmbeddingRepository.findByBookId(bookId);
    }

    @Transactional
    public BookEmbedding save(Long bookId, List<Float> values, Long durationMs) {
        if (values == null || values.isEmpty()) {
            return null;
        }
        validateDuration(durationMs);

        BookEmbedding embedding = BookEmbedding.builder()
                .bookId(bookId)
                .embeddingJson(serialize(values))
                .embeddingModel(EMBEDDING_MODEL)
                .embeddingDurationMs(durationMs)
                .embeddingUpdatedAt(LocalDateTime.now())
                .build();

        return bookEmbeddingRepository.save(embedding);
    }

    @Transactional
    public BookEmbedding update(Long bookId, List<Float> values, Long durationMs) {
        if (values == null || values.isEmpty()) {
            throw new BadRequestException("embeddingJson 값이 필요합니다.");
        }
        validateDuration(durationMs);

        BookEmbedding embedding = bookEmbeddingRepository.findByBookId(bookId);
        if (embedding == null) {
            embedding = BookEmbedding.builder()
                    .bookId(bookId)
                    .embeddingJson(serialize(values))
                    .embeddingModel(EMBEDDING_MODEL)
                    .embeddingDurationMs(durationMs)
                    .embeddingUpdatedAt(LocalDateTime.now())
                    .build();
        } else {
            embedding.setEmbeddingJson(serialize(values));
            embedding.setEmbeddingModel(EMBEDDING_MODEL);
            embedding.setEmbeddingDurationMs(durationMs);
            embedding.setEmbeddingUpdatedAt(LocalDateTime.now());
        }

        return bookEmbeddingRepository.save(embedding);
    }

    private void validateDuration(Long durationMs) {
        if (durationMs == null) {
            throw new BadRequestException("embeddingDurationMs 값이 필요합니다.");
        }
    }

    private String serialize(List<Float> values) {
        try {
            return objectMapper.writeValueAsString(values);
        } catch (JacksonException e) {
            throw new BadRequestException("임베딩 배열을 JSON 문자열로 변환할 수 없습니다.");
        }
    }

    @Transactional
    public void deleteByBookId(Long bookId) {
        // 정리용 삭제이므로 idempotent: 임베딩이 없으면 조용히 통과
        // (임베딩 없는 책 삭제/임베딩 최초 저장 시 404 나던 버그 수정)
        if (bookEmbeddingRepository.existsByBookId(bookId)) {
            bookEmbeddingRepository.deleteByBookId(bookId);
        }
    }

    /**
     * 전체 도서 임베딩 목록 조회
     * @return List<BookEmbedding> 전체 임베딩 목록
     */
    @Transactional(readOnly = true)
    public List<BookEmbedding> findAll() {
        return bookEmbeddingRepository.findAll();
    }

}
