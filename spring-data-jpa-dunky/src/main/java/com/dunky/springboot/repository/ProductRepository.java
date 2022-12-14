package com.dunky.springboot.repository;

import com.dunky.springboot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Returns the found product entry by using its name as search criteria.
     * If no product entry is found, this method returns null.
     */
    public Product findByName (String name);

    /**
     * Returns an Optional which contains the found product
     * entry by using its id as search criteria.
     * If no product entry is found, this method returns an empty Optional.
     */
    public Optional<Product> findById(Long id);


    /**
     * Returns the found list of product entries whose name or description is given
     * as a method parameter.
     * If no product entries is found, this method returns an empty list.
     */
    public List<Product> findByNameOrDescription (String name, String Description);

    /**
     * Returns the found list of product entries whose name and description is given
     * as a method parameter.
     * If no product entries is found, this method returns an empty list.
     */
    public List<Product> findByNameAndDescriptionAndPrice (String name, String Description, BigDecimal price);

    /**
     * Returns the distinct product entry entries whose name is given
     * as a method parameter.
     * If no product entries is found, this method returns null.
     */
    public List<Product> findByPriceGreaterThan (BigDecimal price);

    /**
     * Returns the filtered product records that match
     * the given text.
     */
    public List<Product> findByNameContaining (String name);

    /**
     * Returns the product based on SQL LIKE condition
     * the given text.
     */
    public List<Product> findByNameLike (String name);

    /**
     * Returns the product whose price BETWEEN startPrice AND endPrice.
     *
     */
    public List<Product> findByPriceBetween (BigDecimal startPrice, BigDecimal endPrice);

    /**
     * Returns the products whose creation date lies BETWEEN startDate AND endDate.
     *
     */
    public List<Product> findByDateCreatedBetween (LocalDateTime startDate, LocalDateTime endDate);

    public List<Product> findFirst3ByOrderByNameAsc();

    /**
     * Define JPQL queries using @Query annotation with named parameters.
     *
     */
    @Query("SELECT p FROM Product p WHERE p.name = :name or p.description = :description")
    public Product findByNameOrDescriptionJPQLNamedParam (@Param("name") String name,
                                                          @Param("description") String description);

    /**
     * Define Native SQL queries using @Query annotation with named parameters.
     *
     */
    @Query(value = "SELECT * FROM products p WHERE p.name = :name OR p.description = :description",
                                                                               nativeQuery = true)
    public Product findByNameOrDescriptionSQLNamedParam (@Param("name") String name,
                                                          @Param("description") String description);

    /**
     * Define Named Native SQL queries using @Query annotation with named parameters.
     *
     */
    @Query(nativeQuery = true)
    public Product findByDescription(@Param("description") String description);

    /**
     * Implementing the search functionality in Spring Data JPA using JPQL query.
     *
     */
    @Query("SELECT p FROM Product p WHERE " +
            "p.name LIKE CONCAT('%',:query, '%')" +
            "Or p.description LIKE CONCAT('%', :query, '%')")
    List<Product> searchProducts(String query);

    @Query(value = "SELECT * FROM products p WHERE " +
            "p.name LIKE CONCAT('%',:query, '%')" +
            "Or p.description LIKE CONCAT('%', :query, '%')", nativeQuery = true)
    List<Product> searchProductsSQL(String query);
}

