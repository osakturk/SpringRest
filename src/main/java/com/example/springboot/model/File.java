package com.example.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files")
public class File extends BaseObject{
    @Column(name = "PATH",nullable = false)
    private String filePath;
    @Column(name = "SIZE",nullable = false)
    private String fileSize;
    @Column(name = "NAME",nullable = false, unique = true)
    private String fileName;
    @Column(name = "EXTENSION",nullable = false)
    private String fileExtension;
}
