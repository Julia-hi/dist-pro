package com.stpg.distrinet.repository;

import com.stpg.distrinet.models.Document;
import com.stpg.distrinet.models.DocumentPinned;
import com.stpg.distrinet.payload.request.PinDocRequest;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DocumentPinnedRepository extends JpaRepository<DocumentPinned, Long> {

    // NO FUNCIONA ---------------------------------------
//    @Query(value = "SELECT * FROM document_pinned dp WHERE document_id= :docid AND user_id=:uid", nativeQuery = true)
//    @Query(value = "SELECT d FROM DocumentPinned d WHERE d.document.id = ?1 AND d.user.id = ?2")
//    DocumentPinned findByUserAndDoc(@Param("docid") long docid, @Param("uid") long uid);

    Optional<DocumentPinned> findAllByUser_IdAndDocument_Id(Long user_id, Long doc_id);

    @EntityGraph(attributePaths = {"document", "document.brand", "document.document_type"})
    Set<DocumentPinned> findAllByUser_Id(Long id);

}
