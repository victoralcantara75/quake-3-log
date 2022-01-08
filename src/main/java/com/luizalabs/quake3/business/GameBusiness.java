package com.luizalabs.quake3.business;

import com.luizalabs.quake3.entity.Game;
import com.luizalabs.quake3.entity.Player;
import com.luizalabs.quake3.entity.PlayerGame;
import com.luizalabs.quake3.enums.GameActionEnum;
import com.luizalabs.quake3.repository.GameRepository;
import com.luizalabs.quake3.util.ReadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Service
public class GameBusiness {

    @Autowired
    PlayerBusiness playerBusiness;
    @Autowired
    PlayerGameBusiness playerGameBusiness;
    @Autowired
    GameRepository gameRepository;

    public Game salvar(Game game){
        return gameRepository.saveAndFlush(game);
    }

    private void playerAction(String line, Game game){
        String nickname = playerBusiness.getPlayerName(line);
        Player player = playerBusiness.findPlayerByName(nickname);
        PlayerGame playerGame;

        if (player == null){
            player = new Player(nickname);
            playerBusiness.createPlayer(player);
            playerGame = new PlayerGame(player.getId(), game.getId());
        }
        else{
            playerGame = playerGameBusiness.findByPlayerIdAndGameId(player.getId(), game.getId());
            if (playerGame == null) playerGame = new PlayerGame(player.getId(), game.getId());
        }

        playerGameBusiness.salvar(playerGame);
    }

    private void killAction(List<String> splitLine, Game game){
        String killer = splitLine.get(5);
        String killed = splitLine.get(7);
        PlayerGame playerGame;

        if(killer.equals(GameActionEnum.WORLD.getValue())){
            Player player = playerBusiness.findPlayerByName(killed);
            if (player != null){
                playerGame = playerGameBusiness.findByPlayerIdAndGameId(player.getId(), game.getId());
                playerGame.setKills(playerGame.getKills()-1);
                game.setTotalKills(game.getTotalKills()+1);
                playerGameBusiness.salvar(playerGame);
                this.salvar(game);
            }
        }
        else{
            Player player = playerBusiness.findPlayerByName(killer);
            if (player != null){
                playerGame = playerGameBusiness.findByPlayerIdAndGameId(player.getId(), game.getId());
                playerGame.setKills(playerGame.getKills()+1);
                game.setTotalKills(game.getTotalKills()+1);
                playerGameBusiness.salvar(playerGame);
                this.salvar(game);
            }
        }

    }

    public void newGame(BufferedReader bufferedReader) throws IOException {

        Game game = new Game();
        this.salvar(game);
        String line;

        while((line = bufferedReader.readLine()) != null){

            List<String> splitLine = ReadFileUtil.getSplitLine(line);

            if(splitLine.get(1).equals(GameActionEnum.NEW_PLAYER.getValue())){
                playerAction(line, game);
            }
            else if (splitLine.get(1).equals(GameActionEnum.KILL.getValue())){
                killAction(splitLine, game);
            }
            else if(splitLine.get(1).equals(GameActionEnum.END_GAME.getValue())){
                break;
            }
        }

    }

}
