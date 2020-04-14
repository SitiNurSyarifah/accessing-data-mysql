package com.op.backend.bookshop.controller;

import com.op.backend.bookshop.model.Book;
import com.op.backend.bookshop.model.dto.BookDTO;
import com.op.backend.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequestMapping(path = "/books")
@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping(value = "/")
    public ResponseEntity<List<BookDTO>> getBookWithPromotionPrice() {
        List<BookDTO> bookDTO = bookService.findAllDtoBooks();
        System.out.println(bookDTO.size());
        return ResponseEntity.ok(bookDTO);
    }

    @PostMapping(value = "/")
    public @ResponseBody Book saveBook(@RequestBody BookDTO bookDTO) {
        Book books = bookService.saveBook(bookDTO);
        return books;
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<BookDTO>> search(@RequestParam String bookName) {
        List<BookDTO> bookDTO = bookService.findByBookName(bookName);
        return ResponseEntity.ok(bookDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable(value = "id") Integer id) {
        try {
            BookDTO bookDTO = bookService.findById(id);
            return ResponseEntity.ok(bookDTO);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();

        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable(value = "id") Integer id) {
        try {
            bookService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody BookDTO bookDTODetails, @PathVariable(value = "id") Integer id) {
        BookDTO bookDTO = bookService.findById(id);
        bookDTO.setBookName(bookDTODetails.getBookName());
        bookDTO.setPrice(bookDTODetails.getPrice());
        bookDTO.setDateOfPublished(bookDTODetails.getDateOfPublished());
        bookDTO.setTimeOfPublished(bookDTODetails.getTimeOfPublished());
        bookDTO.setShops(bookDTODetails.getShops());
        Book updatedBookDto = bookService.saveBook(bookDTO);
        return ResponseEntity.ok(updatedBookDto);
    }


}
