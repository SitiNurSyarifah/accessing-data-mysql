package com.op.backend.repository;

import com.op.backend.bookshop.model.Book;
import com.op.backend.bookshop.model.Shop;
import com.op.backend.bookshop.repository.BookRepository;
import com.op.backend.bookshop.repository.ShopRepository;
import com.op.backend.configuration.OpRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@OpRepositoryTest
public class BookShopRepositoryTest {
    @Autowired
    EntityManager entityManager;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    BookRepository bookRepository;


    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    @BeforeEach
    void setUp() {
        Shop s1 = new Shop("Popular");
        s1.addBook(new Book("Harry Potter", 24.0, LocalDate.parse("2017-11-15"), OffsetTime.of(LocalTime.parse("15:30:18"), ZoneOffset.of("+08:00"))));
        s1.addBook(new Book("Twilight Saga", 14.0, LocalDate.parse("2015-05-09"), OffsetTime.of(LocalTime.parse("00:00:14"), ZoneOffset.of("+08:00"))));
        s1.addBook(new Book("Cinderella", 12.0, LocalDate.parse("2010-09-23"), OffsetTime.of(LocalTime.parse("13:50:23"), ZoneOffset.of("+08:00"))));

        Shop s2 = new Shop("Kinokuniya");
        s2.addBook(new Book("Twilight Saga", 14.0, LocalDate.parse("2015-05-09"), OffsetTime.of(LocalTime.parse("00:00:14"), ZoneOffset.of("+08:00"))));
        s2.addBook(new Book("Harry Potter", 24.0, LocalDate.parse("2017-11-15"), OffsetTime.of(LocalTime.parse("15:30:18"), ZoneOffset.of("+08:00"))));
        entityManager.persist(s1);
        entityManager.persist(s2);
        flushAndClear();
    }

    @Test
    void shouldFindShopByBookName() {
        List<Shop> result = shopRepository.findShopsByBookName("Harry Potter");
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getShopName()).isEqualTo("Popular");
        assertThat(result.get(0).getBooks().size()).isEqualTo(3);
    }

    @Test
    void shouldFindBookByShop() {
        List<Book> result = bookRepository.findBooksByShopName("Popular");
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getBookName()).isEqualTo("Harry Potter");
    }



}
