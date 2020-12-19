package com.example.springboot.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity @Getter @Setter @NoArgsConstructor
@Table(name = "players")
public class Player extends BaseObject {

    @Column(name = "FULL_NAME",nullable = false)
    private String fullName;
    @Column(name = "POSITION",nullable = false)
    private String position;
    @Column(name = "AGE",nullable = false)
    private int age;
    @Column(name = "MATCH_COUNT", nullable = false)
    private int matchCount;
    @Column(name = "MONTH_OF_EXPRIENCE", nullable = false)
    private int monthOfExprience;
    @ManyToOne(cascade = { CascadeType.REMOVE })
    @JoinColumn(name = "TEAM_ID", nullable = false)
    private Team team;
    private boolean isFree;

    public Player(String fullName, String position, int age, int matchCount, int monthOfExprience, Team team, boolean isFree) {
        this.fullName = fullName;
        this.position = position;
        this.age = age;
        this.matchCount = matchCount;
        this.monthOfExprience = monthOfExprience;
        this.team = team;
        this.isFree = isFree;
    }
}
