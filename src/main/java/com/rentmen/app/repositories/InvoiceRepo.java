package com.rentmen.app.repositories;

import com.rentmen.app.entities.Invoice;
import com.rentmen.app.entities.Job;
import com.rentmen.app.entities.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepo extends JpaRepository<Invoice, Long> {
	
	Optional<List<Invoice>> findByJobId(Long jobId);
	Optional<Invoice> findByJobIdAndServiceProvider(Long jobId, ServiceProvider serviceProvider);

	List<Invoice> findByInvoiceNumber(Long invoiceNumber);
	public void deleteByJob(Job job);
	
}
