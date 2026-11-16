package com.rentmen.app.repositories;

import com.rentmen.app.entities.InvoiceLines;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceLinesRepo extends JpaRepository<InvoiceLines, Long> {
}

