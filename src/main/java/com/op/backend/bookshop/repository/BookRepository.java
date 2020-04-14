package com.op.backend.bookshop.repository;

import com.op.backend.bookshop.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {

    @Query("SELECT b from Book b join fetch b.shops where b.bookName=:bookName")
    List<Book> findByBookName(@Param("bookName") String bookName);

    @Query("SELECT b from Book b join fetch b.shops where b.id=:id")
    Book findById(@Param("id") Integer id);

    void deleteById(Integer id);

    @Query("SELECT b from Book b join fetch b.shops s where s.shopName=:shopName")
    List<Book> findBooksByShopName(@Param("shopName") String shopName);

    @Query("SELECT b from Book b join fetch b.shops")
    List<Book> findAll();
}
