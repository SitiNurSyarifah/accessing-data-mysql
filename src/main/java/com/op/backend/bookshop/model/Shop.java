package com.op.backend.bookshop.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String shopName;

    public Shop() {
    }

    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "shops")
    List<Book> books = new ArrayList<>();

    public Shop(String shopName) {
        this.shopName = shopName;
    }


    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.addShop(this);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }


    @Override
    public String toString() {
        return String.format(
                "Shop[id=%d, shopName='%s',books='%s']",
                id, shopName,books);
    }
}
