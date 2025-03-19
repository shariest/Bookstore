package com.ryan.bookstore.service;

import com.ryan.bookstore.entity.BooksEntity;
import com.ryan.bookstore.repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 책의 CRUD를 수행하는 서비스 입니다.
 *
 * @version 1.0
 * @auther ryan
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BooksRepository booksRepository;

    /**
     * 책 정보를 저장합니다.
     *
     * @param booksEntity
     * @return
     */
    public long createBook(BooksEntity booksEntity) {
        return booksRepository.save(booksEntity)
                .getBid();
    }

    /**
     * 책의 상세 정보를 조회합니다.
     *
     * @param bid
     * @return
     */
    public BooksEntity retrieveBook(long bid) {
        return booksRepository.findById(bid).orElseThrow();
    }

    /**
     * 책 목록을 검색합니다.
     *
     * @param keyword
     * @param pageable
     * @return
     */
    public Page<BooksEntity> searchBooks(String keyword, Pageable pageable) {
        return booksRepository.searchBooks(keyword, pageable);
    }

    /**
     * 책 정보를 수정합니다.
     *
     * @param booksEntity
     * @return
     */
    public long updateBook(BooksEntity booksEntity) {
        return booksRepository.save(booksEntity)
                .getBid();
    }

    /**
     * 책을 삭제합니다.
     *
     * @param bid
     * @return
     */
    public long deleteBook(long bid) {
        booksRepository.deleteById(bid);
        return bid;
    }
}