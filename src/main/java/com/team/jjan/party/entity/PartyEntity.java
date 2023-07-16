package com.team.jjan.party.entity;

import com.team.jjan.config.BaseTimeEntity;
import jakarta.persistence.*;
import com.team.jjan.party.dto.PartyUpdateRequestDto;
import com.team.jjan.user.entitiy.UserEntity;
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

    public void update(PartyUpdateRequestDto requestDto, List<String> partyImages) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.maxPartyNum = requestDto.getMaxPartyNum();
        this.partyLatitude = requestDto.getPartyLatitude();
        this.partyLongitude = requestDto.getPartyLongitude();
        this.partyDate = requestDto.getPartyDate();
        this.partyTags = requestDto.getPartyTags();
        this.partyImages = partyImages;
    }
}
