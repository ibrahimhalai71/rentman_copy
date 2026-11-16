package com.rentmen.app.repositories;

import com.rentmen.app.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, Long> {

}
