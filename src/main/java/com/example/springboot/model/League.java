package com.example.springboot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity @Getter @Setter @NoArgsConstructor
@Table(name = "leagues")
public class League extends BaseObject {
    @Column(name = "NAME",nullable = false, unique = true)
    private String leagueName;
    @ManyToOne(cascade = { CascadeType.REMOVE })
    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    private Country country;


    public League(String leagueName, Country country) {
        this.leagueName = leagueName;
        this.country = country;
    }
}
