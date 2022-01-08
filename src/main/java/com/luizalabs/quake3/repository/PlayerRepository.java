package com.luizalabs.quake3.repository;

import com.luizalabs.quake3.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    public Player findByNickname(String nickname);
}
