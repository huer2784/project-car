package com.packt.cardatabase.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
	Optional<Owner> findByFirstname(String firstName);
}
