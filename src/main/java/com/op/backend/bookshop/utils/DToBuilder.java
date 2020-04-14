package com.op.backend.bookshop.utils;

import com.op.backend.bookshop.model.Book;
import com.op.backend.bookshop.model.dto.BookDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DToBuilder {
    public BookDTO convertToBookDto(Book book) {
        BookDTO bookDTO = new ModelMapper().map(book, BookDTO.class);
        return bookDTO;
    }

    public Book convertToBookEntity(BookDTO bookDTO) {
        Book book = new ModelMapper().map(bookDTO, Book.class);
        return book;
    }

    public List<BookDTO> convertListToBookDto(List<Book> books){
        List<BookDTO> bookDTOList = books.stream().map(book -> convertToBookDto(book))
                .collect(Collectors.toList());
        return bookDTOList;
    }

//    public Shop convertToShopEntity(ShopDTO shopDTO) {
//        Shop shop = new ModelMapper().map(shopDTO, Shop.class);
//        shop.setShopName(shopDTO.getShopName());
//        return shop;
//    }


}
