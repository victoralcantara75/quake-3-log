package com.luizalabs.quake3;

import com.luizalabs.quake3.entity.Game;
import com.luizalabs.quake3.entity.Player;
import com.luizalabs.quake3.entity.PlayerGame;
import com.luizalabs.quake3.repository.GameRepository;
import com.luizalabs.quake3.repository.PlayerGameRepository;
import com.luizalabs.quake3.repository.PlayerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
class RepositoryUnitTest {
    
    @Autowired
    GameRepository gameRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    PlayerGameRepository playerGameRepository;

    @Test
    @Transactional
    void shouldCreateGame() {
        Game game = new Game();
        game = gameRepository.save(game);
        Assert.assertNotNull(game);
    }

    @Test
    @Transactional
    void shouldCreatePlayer() {
        Player player = new Player("nickname");
        player = playerRepository.save(player);
        Assert.assertNotNull(player);
    }

    @Test
    @Transactional
    void shouldCreatePlayerGame() {
        Game game = new Game();
        game = gameRepository.save(game);

        Player player = new Player("nickname");
        player = playerRepository.save(player);

        PlayerGame playerGame = new PlayerGame(player.getId(), game.getId());
        playerGame = playerGameRepository.save(playerGame);

        Assert.assertNotNull(playerGame);
    }

}
