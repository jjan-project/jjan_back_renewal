package jjan_back_renewal.party.entity;

import jakarta.persistence.*;
import jjan_back_renewal.config.BaseTimeEntity;
import jjan_back_renewal.party.dto.PartyUpdateRequestDto;
import jjan_back_renewal.user.entitiy.UserEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    private double partyLatitude;

    private double partyLongitude;

    private String partyDate;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> partyImages = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    private List<PartyTag> partyTags = new ArrayList<>();

    public void update(PartyUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.maxPartyNum = requestDto.getMaxPartyNum();
        this.partyLatitude = requestDto.getPartyLatitude();
        this.partyLongitude = requestDto.getPartyLongitude();
        this.partyDate = requestDto.getPartyDate();
        this.partyImages = requestDto.getPartyImages();
        this.partyTags = requestDto.getPartyTags();
    }
}
