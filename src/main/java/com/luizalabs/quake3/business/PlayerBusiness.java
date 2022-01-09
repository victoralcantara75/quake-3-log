package com.luizalabs.quake3.business;

import com.luizalabs.quake3.entity.Player;
import com.luizalabs.quake3.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerBusiness {

    @Autowired
    PlayerRepository playerRepository;

    public Player createPlayer(Player player){
        return playerRepository.save(player);
    }

    public String getPlayerName(String line){
        return line.split("\\\\")[1];
    }

    public Player findPlayerByName(String name){
        return playerRepository.findByNickname(name);
    }

    public List<Player> getPlayerByIdIn(List<Long> playersIds){
        return playerRepository.findByIdIn(playersIds);
    }


}
