package com.luizalabs.quake3.repository;

import com.luizalabs.quake3.DTO.PlayerKillsByGame;
import com.luizalabs.quake3.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query(value = "SELECT PLAYER.NICKNAME, PLAYER_GAME.KILLS" +
                " FROM PLAYER" +
                " JOIN PLAYER_GAME ON PLAYER.ID = PLAYER_GAME.PLAYER_ID" +
                " JOIN GAME ON GAME.ID = PLAYER_GAME.GAME_ID" +
                " WHERE GAME.ID = :gameId", nativeQuery = true)
    List<PlayerKillsByGame> findPlayersKillByGame(@Param("gameId") Long gameId);

}
