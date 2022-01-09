package com.luizalabs.quake3.business;

import com.luizalabs.quake3.entity.PlayerGame;
import com.luizalabs.quake3.repository.PlayerGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerGameBusiness {

    @Autowired
    PlayerGameRepository playerGameRepository;

    public PlayerGame salvar(PlayerGame playerGame){
        return playerGameRepository.saveAndFlush(playerGame);
    }

    public PlayerGame findByPlayerIdAndGameId(Long playerId, Long gameId){
        return playerGameRepository.findByPlayerIdAndGameId(playerId, gameId);
    }

    public List<PlayerGame> findByGameId(Long gameId){
        return playerGameRepository.findByGameId(gameId);
    }

}
