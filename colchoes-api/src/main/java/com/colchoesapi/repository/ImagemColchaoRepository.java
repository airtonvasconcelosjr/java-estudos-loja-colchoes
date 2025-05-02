package com.colchoesapi.repository;

import com.colchoesapi.model.ImagemColchao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemColchaoRepository extends JpaRepository<ImagemColchao, Long> {
    List<ImagemColchao> findByColchaoId(Long colchaoId);
}
