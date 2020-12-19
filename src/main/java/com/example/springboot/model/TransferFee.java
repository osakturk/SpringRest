package com.example.springboot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "transfer_fees")
public class TransferFee extends BaseObject {
    @Column(name = "FEE")
    private Double fee;
    @ManyToOne(cascade = { CascadeType.REMOVE })
    @JoinColumn(name="PLAYER_ID")
    private Player player;

    public TransferFee(Double fee, Player player) {
        this.fee = fee;
        this.player = player;
    }
}
