package com.example.springboot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity @Getter @Setter @NoArgsConstructor
@Table(name = "countries")
public class Country extends BaseObject {
    @Column(name = "NAME",nullable = false, unique = true)
    private String countryName;

    public Country(String countryName) {
        this.countryName = countryName;
    }
}
