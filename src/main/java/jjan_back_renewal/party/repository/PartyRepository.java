package jjan_back_renewal.party.repository;

import jjan_back_renewal.party.entity.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<PartyEntity, Long> {
}
