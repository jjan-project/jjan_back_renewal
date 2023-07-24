package com.team.jjan.party.entity;

import jakarta.persistence.*;
import com.team.jjan.party.dto.PartyUpdateRequestDto;
import com.team.jjan.user.entitiy.UserEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
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

    @Embedded
    private Location location;

    private int maxPartyNum;

    private Date partyDate;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<PartyTag> partyTags = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> partyImages = new ArrayList<>();

    public void update(PartyUpdateRequestDto partyUpdateRequestDto, List<String> updateImages){
        this.title = partyUpdateRequestDto.getTitle();
        this.content = partyUpdateRequestDto.getContent();
        this.location = partyUpdateRequestDto.getLocation();
        this.maxPartyNum = partyUpdateRequestDto.getMaxPartyNum();
        this.partyDate = partyUpdateRequestDto.getPartyDate();
        this.partyTags = partyUpdateRequestDto.getPartyTags();
        this.partyImages = updateImages;
    }
}
