package com.stpg.distrinet.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DocumentFavRepository extends JpaRepository<DocumentFav, Long> {


    Optional<DocumentFav> findAllByUser_IdAndDocument_Id(Long user_id, Long doc_id);
    @EntityGraph(attributePaths = {"document", "document.brand", "document.document_type"})
    Set<DocumentFav> findAllByUser_Id(Long id);
}
