package com.op.backend.service;


import com.op.backend.bookshop.model.Book;
import com.op.backend.bookshop.model.Shop;
import com.op.backend.bookshop.model.dto.BookDTO;
import com.op.backend.bookshop.model.dto.ShopDTO;
import com.op.backend.bookshop.service.BookService;
import com.op.backend.configuration.OpServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@OpServiceTest
public class BookServiceTest {

    public static final String VALENTINES_DAY = "2020-02-14T10:15:30.00Z";
    @Autowired
    EntityManager entityManager;

    @Autowired
    BookService bookService;


    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    @BeforeEach
    void setUp() {

        Shop s1=new Shop("Popular");
        Shop s2 = new Shop("Kinokuniya");

        Book b1 = new Book("Cinderella", 12.0, LocalDate.parse("2010-09-23"), OffsetTime.of(LocalTime.parse("13:50:23"), ZoneOffset.of("+08:00")));
        b1.addShop(s1);

        Book b2 = new Book("Twilight Saga", 14.0, LocalDate.parse("2015-05-09"), OffsetTime.of(LocalTime.parse("00:00:14"), ZoneOffset.of("+08:00")));
        b2.addShop(s2);

        entityManager.persist(s1);
        entityManager.persist(s2);
        entityManager.persist(b1);
        entityManager.persist(b2);
        flushAndClear();

    }

    @Test
    public void shouldGetPromotionPriceFromDtoBook() {

        Clock fixedClock = Clock.fixed(Instant.parse(VALENTINES_DAY),
                ZoneId.of("Asia/Singapore"));

        List<BookDTO> bookDTO = bookService.getBooksWithPromotionPrice(fixedClock);
        assertThat(bookDTO.get(0).getPromotionPrice()).isEqualTo(9.60);
    }

    @Test
    public void shouldSaveShopToBook() {
        BookDTO bookDTO = new BookDTO();
        ShopDTO shopDTO = new ShopDTO();

        shopDTO.setShopName("Times");
        bookDTO.setBookName("Twilight");
        bookDTO.addShop(shopDTO);
        Book books = bookService.saveBook(bookDTO);

        assertThat(books.getBookName()).isEqualTo("Twilight");
        System.out.println(books);
        System.out.println(books.getShops());
        assertThat(books.getShops().get(0).getShopName()).isEqualTo("Times");
    }

    @Test
    public void shouldUpdateEntity() {

        BookDTO dto = bookService.findAllDtoBooks().get(0);
        dto.setBookName("Cinderella 2");
        Book books = bookService.saveBook(dto);

        assertThat(books.getBookName()).isEqualTo("Cinderella 2");
    }

    @Test
    public void shouldFindById() {
        BookDTO bookDTO = bookService.findAllDtoBooks().get(0);
        BookDTO bookDTO1 = bookService.findById(bookDTO.getId());
        assertThat(bookDTO1.getBookName()).isEqualTo("Cinderella");
    }

    @Test
    public void shouldDeleteEntityWhenIdExist() {
        BookDTO bookDTO = bookService.findAllDtoBooks().get(0);
        bookService.deleteById(bookDTO.getId());
        flushAndClear();
    }

    @Test
    public void shouldReturnErrorWhenDeletingEntityWhenIdDidNotExist() {
        try {
            bookService.deleteById(1232398645);
            fail("ERROR ID EXIST");
        } catch (EntityNotFoundException e) {
        }
    }

    @Test
    public void shouldGetAllDtoBooks() {
        List<BookDTO> bookDTOList = bookService.findAllDtoBooks();
        assertThat(bookDTOList.get(0).getShops().size()).isEqualTo(1);
    }


    @Test
    public void shouldFindByBookDTOName() {
        List<BookDTO> bookDTOList = bookService.findByBookName("Cinderella");
        assertThat(bookDTOList.size()).isEqualTo(1);
    }

    @Test
    public void shouldReturnNotFoundExceptionWhenIdDoesNotExist() {
        try {
            bookService.findById(1234);
            fail("ERROR ID EXIST");
        } catch (EntityNotFoundException e) {
        }
    }
}
