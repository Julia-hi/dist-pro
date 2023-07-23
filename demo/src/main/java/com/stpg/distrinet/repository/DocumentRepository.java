package com.stpg.distrinet.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stpg.distrinet.models.Document;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Override
    @EntityGraph(attributePaths = {"brand","document_type"})
    List<Document>findAll();
}
