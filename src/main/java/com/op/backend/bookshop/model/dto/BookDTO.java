package com.op.backend.bookshop.model.dto;


import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.List;

public class BookDTO {
    private Integer id;
    private String bookName;
    private Double price;
    private LocalDate dateOfPublished;
    private OffsetTime timeOfPublished;
    private Double promotionPrice;

    List<ShopDTO> shops = new ArrayList<>();

    public List<ShopDTO> getShops() {
        return shops;
    }

    public void setShops(List<ShopDTO> shops) {
        this.shops = shops;
    }
    public void addShop(ShopDTO shopDTO) {
        this.shops.add(shopDTO);
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

    public Double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }
}
