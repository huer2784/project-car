package com.packt.cardatabase.domain;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//public interface CarRepository extends CrudRepository<Car, Long> {
public interface CarRepository extends JpaRepository<Car, Long> {
//	@Query("select c from Car c where c.brand = ?1")
//	@Query("select c from Car c where c.brand = :brand")
	List<Car> findByBrand(@Param("brand") String brand);
	
//	List<Car> findByBrand(@Param("brand") String brand, Pageable pageable);
	
	List<Car> findByColor(String color);
	
	List<Car> findByModelYear(int modelYear);
	
	List<Car> findByBrandAndModel(String brand, String model);
	
	List<Car> findByBrandOrColor(String brand, String color);
	
	List<Car> findByBrandOrderByModelYearAsc(String brand);
	
	List<Car> findByBrandOrderByModelYearDesc(String brand);
	
	
	
	
}
