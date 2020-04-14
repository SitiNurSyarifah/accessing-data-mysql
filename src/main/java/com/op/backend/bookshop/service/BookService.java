package com.op.backend.bookshop.service;

import com.op.backend.bookshop.model.Book;
import com.op.backend.bookshop.model.dto.BookDTO;
import com.op.backend.bookshop.repository.BookRepository;
import com.op.backend.bookshop.utils.DToBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Clock;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService {
    protected Map<LocalDate, Integer> discounts = new HashMap<>();

    @Autowired
    BookRepository bookRepository;
    @Autowired
    DToBuilder dToBuilder;


    public BookService() {
        discounts.put(LocalDate.parse("2020-02-14"), 80);
        discounts.put(LocalDate.parse("2020-12-25"), 50);
    }

    @Transactional
    public Book saveBook(BookDTO bookDTO) {
        Book book = dToBuilder.convertToBookEntity(bookDTO);
        return book;
    }

    public List<BookDTO> findAllDtoBooks() {
        List<BookDTO> bookDTOList =  dToBuilder.convertListToBookDto(bookRepository.findAll());
        return bookDTOList;
    }

    public List<BookDTO> findByBookName(String book) {
        return dToBuilder.convertListToBookDto(bookRepository.findByBookName(book));
    }

    public BookDTO findById(Integer id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new EntityNotFoundException();
        }
        return dToBuilder.convertToBookDto(book);
    }

    @Transactional
    public void deleteById(Integer id) {
        this.findById(id);
        bookRepository.deleteById(id);
    }


    public List<BookDTO> getBooksWithPromotionPrice(Clock clock) {
        List<BookDTO> bookDTOList = this.findAllDtoBooks();
        for (Map.Entry<LocalDate, Integer> discount : discounts.entrySet()) {
            if (LocalDate.now(clock).equals(discount.getKey())) {
                bookDTOList.stream().forEach(bookDTO -> bookDTO.setPromotionPrice(calculateDiscount(discount, bookDTO)));
            }
        }
        return bookDTOList;
    }

    private double calculateDiscount(Map.Entry<LocalDate, Integer> discount, BookDTO book) {
        double priceAfterDiscount = 0;
        priceAfterDiscount = book.getPrice() * discount.getValue() / 100;
        return priceAfterDiscount;
    }

}
