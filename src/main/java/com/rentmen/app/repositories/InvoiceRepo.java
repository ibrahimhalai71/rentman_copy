package com.rentmen.app.repositories;

import com.rentmen.app.entities.Invoice;
import com.rentmen.app.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepo extends JpaRepository<Invoice, Long> {
	
	Optional<List<Invoice>> findByJobId(Long jobId);
	
	public void deleteByJob(Job job);
	
}
