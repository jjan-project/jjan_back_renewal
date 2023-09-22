package com.team.jjan.chat.repository;

import com.team.jjan.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, List> {
}
