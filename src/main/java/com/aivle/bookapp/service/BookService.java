package com.aivle.bookapp.service;

import com.aivle.bookapp.domain.Book;
import com.aivle.bookapp.exception.BookNotFoundException;
import com.aivle.bookapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


// 임시로 작성 나중에 병합 해서 테스트 해볼예정
/**
 * 도서 비즈니스 로직 처리 서비스
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    /**
     * 모든 도서 조회 또는 제목 검색
     */
    public List<Book> findAll(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return bookRepository.findByTitleContaining(keyword);
        }
        return bookRepository.findAll();
    }

    /**
     * 태그로 도서 검색
     */
    public List<Book> findByTagName(String tagName) {
        log.info("Searching books by tag: {}", tagName);
        return bookRepository.findByTagsContaining(tagName);
    }

    /**
     * ID로 도서 상세 조회
     */
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("도서를 찾을 수 없습니다. ID: " + id));
    }

    /**
     * 도서 신규 저장
     */
    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    /**
     * 도서 정보 수정 (더티 체킹 활용)
     */
    @Transactional
    public Book update(Long id, Book book) {
        Book existingBook = findById(id);
        
        if (book.getTitle() != null) existingBook.setTitle(book.getTitle());
        if (book.getAuthor() != null) existingBook.setAuthor(book.getAuthor());
        if (book.getIsbn() != null) existingBook.setIsbn(book.getIsbn());
        if (book.getDescription() != null) existingBook.setDescription(book.getDescription());
        if (book.getContent() != null) existingBook.setContent(book.getContent());
        if (book.getSummary() != null) existingBook.setSummary(book.getSummary());
        if (book.getCopy() != null) existingBook.setCopy(book.getCopy());
        if (book.getCoverImageUrl() != null) existingBook.setCoverImageUrl(book.getCoverImageUrl());
        if (book.getLikes() != null) existingBook.setLikes(book.getLikes());
        if (book.getTags() != null) existingBook.setTags(book.getTags());
        
        return existingBook;
    }

    /**
     * 도서 삭제
     */
    @Transactional
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("삭제할 도서를 찾을 수 없습니다. ID: " + id);
        }
        bookRepository.deleteById(id);
    }
}
