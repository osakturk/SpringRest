package com.example.springboot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity @Getter @Setter @NoArgsConstructor
@Table(name = "teams")
public class Team extends BaseObject {

    @Column(name = "NAME", nullable = false)
    private String teamName;
    @Column(name = "DESCRIPTION")
    private String description;
    @ManyToOne(cascade = { CascadeType.REMOVE })
    @JoinColumn(name = "LEAGUE_ID", nullable = false)
    private League league;
    @Column(name = "BOSS_NAME")
    private String bossName;
    @Column(name = "ESTABLISHED", nullable = false)
    private long established;
    @Column(name = "CURRENCY", nullable = false)
    private String currency;

    public Team(String teamName, String description, League league, String bossName, long established) {
        this.teamName = teamName;
        this.description = description;
        this.league = league;
        this.bossName = bossName;
        this.established = established;
    }
}
