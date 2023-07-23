package com.stpg.distrinet.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="document_type")
public class DocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


//    @JsonIgnoreProperties({"brand"})
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
//    private List<Document> documentList;
}
