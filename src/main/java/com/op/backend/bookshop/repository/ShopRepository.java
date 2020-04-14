package com.op.backend.bookshop.repository;

import com.op.backend.bookshop.model.Shop;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends CrudRepository<Shop,String> {

        @Query("SELECT s from Shop s join s.books b where b.bookName=:bookName")
        List<Shop> findShopsByBookName(@Param("bookName") String bookName);

}
