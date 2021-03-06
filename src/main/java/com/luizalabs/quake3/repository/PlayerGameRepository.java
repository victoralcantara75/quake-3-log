package com.luizalabs.quake3.repository;

import com.luizalabs.quake3.entity.PlayerGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerGameRepository extends JpaRepository<PlayerGame, Long> {
    PlayerGame findByPlayerIdAndGameId(Long playerId, Long gameId);

    List<PlayerGame> findByGameId(Long gameId);

}
