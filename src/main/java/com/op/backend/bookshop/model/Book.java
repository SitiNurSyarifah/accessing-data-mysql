package com.op.backend.bookshop.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String bookName;
    private Double price;
    private LocalDate dateOfPublished;
    private OffsetTime timeOfPublished;

    public Book() {
    }

    @ManyToMany()
    @JoinTable(name = "book_shop",
            joinColumns = @JoinColumn(name = "shop_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
    List<Shop> shops = new ArrayList<>();

    public Book(String bookName, Double price, LocalDate dateOfPublished, OffsetTime timeOfPublished) {
        this.bookName = bookName;
        this.price = price;
        this.dateOfPublished = dateOfPublished;
        this.timeOfPublished = timeOfPublished;
    }


    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    public void addShop(Shop shop) {
        this.shops.add(shop);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getDateOfPublished() {
        return dateOfPublished;
    }

    public void setDateOfPublished(LocalDate dateOfPublished) {

        this.dateOfPublished = dateOfPublished;
    }

    public OffsetTime getTimeOfPublished() {
        return timeOfPublished;
    }

    public void setTimeOfPublished(OffsetTime timeOfPublished) {
        this.timeOfPublished = timeOfPublished;
    }

    @Override
    public String toString() {
        return String.format(
                "Book[id=%d, bookName='%s', price='%s', dateOfPublished='%s', timeOfPublished='%s']",
                id, bookName, price, dateOfPublished, timeOfPublished);
    }

}
