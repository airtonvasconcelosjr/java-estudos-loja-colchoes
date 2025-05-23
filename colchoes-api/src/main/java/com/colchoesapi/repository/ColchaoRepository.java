package com.colchoesapi.repository;

import com.colchoesapi.model.Colchao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColchaoRepository extends JpaRepository<Colchao, Long> {
}
