package jjan_back_renewal.party.entity;


import jakarta.persistence.*;
import jjan_back_renewal.config.BaseTimeEntity;
import jjan_back_renewal.user.entitiy.UserEntity;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "party")
public class PartyEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity author;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private int maxPartyNum;

    private String partyLocation;

    private String partyDate;
}
