package com.ryan.bookstore.repository;

import com.ryan.bookstore.entity.BooksEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BooksRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BooksRepository booksRepository;

    private BooksEntity newBook() {
        return BooksEntity.builder()
                .title("도서 제목")
                .author("저자")
                .price(10000)
                .cnt(1)
                .build();
    }

    @Test
    void TEST001_createBook() {
        BooksEntity newBook = newBook();

        BooksEntity savedBook = booksRepository.save(newBook);

        assertThat(savedBook.getBid()).isGreaterThan(0);
        assertThat(entityManager.find(BooksEntity.class, savedBook.getBid())).isEqualTo(savedBook);
    }

    @Test
    void TEST002_retrieveBook() {
        BooksEntity book = entityManager.persistAndFlush(newBook());
        BooksEntity retrieveBook = booksRepository.findById(book.getBid()).orElseThrow();

        assertThat(retrieveBook).isEqualTo(book);
    }

    @Test
    void TEST003_updateBook() {
        BooksEntity book = entityManager.persistAndFlush(newBook());

        String newTitle = "New Title";
        String newAuthor = "New Author";
        int newPrice = 100000;
        int newCnt = 100;

        book.setTitle(newTitle)
                .setAuthor(newAuthor)
                .setPrice(newPrice)
                .setCnt(newCnt);

        BooksEntity updatedBook = booksRepository.saveAndFlush(book);

        assertThat(updatedBook.getTitle()).isEqualTo(newTitle);
        assertThat(updatedBook.getAuthor()).isEqualTo(newAuthor);
        assertThat(updatedBook.getPrice()).isEqualTo(newPrice);
        assertThat(updatedBook.getCnt()).isEqualTo(newCnt);
    }

    @Test
    void TEST004_deleteBook() {
        BooksEntity book = entityManager.persistAndFlush(newBook());

        booksRepository.deleteById(book.getBid());

        assertThat(booksRepository.findById(book.getBid())).isEmpty();
    }
}
