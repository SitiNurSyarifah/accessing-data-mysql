package com.op.backend.mockito;

import com.op.backend.bookshop.model.dto.BookDTO;
import com.op.backend.bookshop.model.dto.ShopDTO;
import com.op.backend.bookshop.repository.BookRepository;
import com.op.backend.bookshop.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class mockitoTest {


    @Autowired
    EntityManager entityManager;

    @MockBean
    BookService bookService;

    @MockBean
    BookRepository bookRepository;

    @Mock
    List<BookDTO> bookDTOMck;
 

    @BeforeEach
    public void setUp() {
        bookService = mock(BookService.class);

        bookDTOMck = new ArrayList<>();
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setShopName("Popular");
        BookDTO bookDto = new BookDTO();
        bookDto.setId(1);
        bookDto.setBookName("Harry Potter");
        bookDto.setPrice(24.0);
        bookDto.setDateOfPublished(LocalDate.parse("2017-11-15"));
        bookDto.setTimeOfPublished(OffsetTime.of(LocalTime.parse("15:30:18"), ZoneOffset.of("+08:00")));
        bookDto.addShop(shopDTO);
        bookDTOMck.add(bookDto);

    }

    @Test
    public void findAllBooks() {
        when(bookService.findAllDtoBooks()).thenReturn(bookDTOMck);
        bookService.findByBookName("Harry");
        assertEquals(1, bookDTOMck.size());
    }


    @Test
    public void findBookByName() {
        when(bookService.findByBookName("Harry Potter")).thenReturn(bookDTOMck);
        List<BookDTO> bookDTOMck = this.bookService.findByBookName("Harry Potter");
        verify(bookService).findByBookName("Harry Potter");
        assertThat(bookDTOMck.get(0).getPrice()).isEqualTo(24.0);
        assertEquals(1, bookDTOMck.size());
    }

    @Test
    public void findBookById() {
        when(bookService.findById(1)).thenReturn(bookDTOMck.get(0));
        BookDTO bookDTOMck = this.bookService.findById(1);
        verify(bookService).findById(1);
        assertThat(bookDTOMck.getId()).isEqualTo(1);
    }

//    @Test
//    public void addNewBook(){
//        ShopDTO shopDTO = new ShopDTO();
//        shopDTO.setShopName("Popular");
//        BookDTO bookDto = new BookDTO();
//        bookDto.setId(1);
//        bookDto.setBookName("Twilight");
//        bookDto.setPrice(14.0);
//        bookDto.setDateOfPublished(LocalDate.parse("2015-05-09"));
//        bookDto.setTimeOfPublished(OffsetTime.of(LocalTime.parse("00:00:14"), ZoneOffset.of("+08:00")));
//        bookDto.addShop(shopDTO);
//        bookDTOMck.add(bookDto);
//        when(bookService.saveBook(bookDto)).thenReturn(bookDto);
//        verify(bookService, times(1)).saveBook(bookDto);
//    }

}
