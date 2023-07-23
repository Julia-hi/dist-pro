package com.stpg.distrinet.models.file;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "file")
@Getter
@Setter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fileName;
}
