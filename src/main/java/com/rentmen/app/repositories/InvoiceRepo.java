package com.rentmen.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentmen.app.entities.Invoice;

public interface InvoiceRepo extends JpaRepository<Invoice, Long> {
	
	Optional<List<Invoice>> findByJobId(Long jobId);
	
}
