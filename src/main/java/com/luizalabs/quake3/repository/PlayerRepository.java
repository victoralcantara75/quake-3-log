package com.luizalabs.quake3.repository;

import com.luizalabs.quake3.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByNickname(String nickname);

    List<Player> findByIdIn(List<Long> playersIds);
}
