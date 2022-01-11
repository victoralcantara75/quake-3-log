package com.luizalabs.quake3.business;

import com.luizalabs.quake3.DTO.GameDTO;
import com.luizalabs.quake3.DTO.PlayerKillsByGame;
import com.luizalabs.quake3.entity.Game;
import com.luizalabs.quake3.entity.Player;
import com.luizalabs.quake3.entity.PlayerGame;
import com.luizalabs.quake3.enums.GameActionEnum;
import com.luizalabs.quake3.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void playerAction(String line, Game game){
        String nickname = playerBusiness.getPlayerName(line);
        Player player = playerBusiness.findPlayerByName(nickname);
        PlayerGame playerGame;

        if (player == null){
            player = new Player(nickname);
            playerBusiness.createPlayer(player);
        }
        else{
            playerGame = playerGameBusiness.findByPlayerIdAndGameId(player.getId(), game.getId());
            if (playerGame != null) return;
        }

        playerGame = new PlayerGame(player.getId(), game.getId());
        playerGameBusiness.salvar(playerGame);
    }

    public void killAction(List<String> splitLine, Game game){
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

    public Game newGame(){

        Game game = new Game();
        this.salvar(game);
        return game;

    }

    public List<GameDTO> getGamesSummary(){

        List<GameDTO> gameDtoList = new ArrayList<>();
        List<Game> gameList = gameRepository.findAll();

        for (Game game : gameList){

            GameDTO gameDTO = new GameDTO();
            gameDTO.setTotal_kills(game.getTotalKills());

            List<PlayerGame> playerGames = playerGameBusiness.findByGameId(game.getId());

            List<Long> playersIds = playerGames.stream().map(PlayerGame::getPlayerId).collect(Collectors.toList());
            List<Player> players = playerBusiness.getPlayerByIdIn(playersIds);
            List<String> playersNicknames = players.stream().map(Player::getNickname).collect(Collectors.toList());
            gameDTO.setPlayers(playersNicknames);

            List<PlayerKillsByGame> playerKillsByGame = getPlayersKillByGame(game.getId());
            gameDTO.setKills(playerKillsByGame);
            gameDtoList.add(gameDTO);
        }

        return gameDtoList;
    }

    private List<PlayerKillsByGame> getPlayersKillByGame(Long gameId){
        return gameRepository.findPlayersKillByGame(gameId);
    }

}
