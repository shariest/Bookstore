package com.ryan.bookstore.repository;

import com.ryan.bookstore.entity.BooksEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<BooksEntity, Long> {
    @Query("""
            SELECT b
            FROM BooksEntity b
            WHERE
                :keyword IS NULL
                OR :keyword = ''
                OR b.title ILIKE %:keyword%
                OR b.author ILIKE %:keyword%
            """)
    Page<BooksEntity> searchBooks(@Param("keyword") String keyword, Pageable pageable);
}