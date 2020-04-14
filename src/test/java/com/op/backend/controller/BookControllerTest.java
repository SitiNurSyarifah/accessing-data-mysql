package com.op.backend.controller;

import com.op.backend.bookshop.model.Book;
import com.op.backend.bookshop.model.Shop;
import com.op.backend.bookshop.repository.BookRepository;
import com.op.backend.bookshop.service.BookService;
import com.op.backend.configuration.OpControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@OpControllerTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    private Book b1,b2;
    private Shop s1,s2;

    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    @BeforeEach
    void setUp() {
        s1=new Shop("Popular");
        s2 = new Shop("Kinokuniya");

        b1 = new Book("Cinderella", 12.0, LocalDate.parse("2010-09-23"), OffsetTime.of(LocalTime.parse("13:50:23"), ZoneOffset.of("+08:00")));
        b1.addShop(s1);

        b2 = new Book("Twilight Saga", 14.0, LocalDate.parse("2015-05-09"), OffsetTime.of(LocalTime.parse("00:00:14"), ZoneOffset.of("+08:00")));
        b2.addShop(s2);

        entityManager.persist(s1);
        entityManager.persist(s2);
        entityManager.persist(b1);
        entityManager.persist(b2);
        flushAndClear();
    }


    @Test
    public void findAllDtoBooks() throws Exception {
        mockMvc.perform(get("/books/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createNewBook() throws Exception {
        String inputJson = "  {\n" +
                "        \"bookName\": \"Twilight\",\n" +
                "        \"price\": 14.0,\n" +
                "        \"dateOfPublished\": \"2015-05-09\",\n" +
                "        \"timeOfPublished\": \"00:00:14+08:00\",\n" +
                "        \"promotionPrice\": null,\n" +
                "        \"shops\": [\n" +
                "        \t{\n" +
                "        \t\t\"id\": 2,\n" +
                "                \"shopName\": \"Kinokuniya\"\n" +
                "        \t\t\n" +
                "        \t}\n" +
                "        ]\n" +
                "    }";
        mockMvc.perform(post("/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isOk());
    }

    @Test
    public void findById() throws Exception {

        mockMvc.perform(get("/books/{id}", b1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void returnNotFoundWhenIdNotExist() throws Exception {

        mockMvc.perform(get("/books/{id}", 23)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/books/{id}", 23))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteBook() throws Exception {

        mockMvc.perform(delete("/books/{id}", b1.getId()))
                .andExpect(status().isOk());


        mockMvc.perform(get("/books/{id}", b1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByBookName() throws Exception {
        mockMvc.perform(get("/books/search")
                .contentType(MediaType.APPLICATION_JSON).param("bookName", "Cinderella"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateBook() throws Exception {

        String inputJson = " {\n" +
                "        \"id\": 1,\n" +
                "        \"bookName\": \"Larry Potter\",\n" +
                "        \"price\": 24.0,\n" +
                "        \"dateOfPublished\": \"2017-11-15\",\n" +
                "        \"timeOfPublished\": \"15:30:18+08:00\",\n" +
                "        \"promotionPrice\": null,\n" +
                "        \"shops\": [\n" +
                "            {\n" +
                "                \"id\": 1,\n" +
                "                \"shopName\": \"Popular\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }";

        mockMvc.perform(put("/books/{id}", b1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isOk());
    }

}
