package com.luizalabs.quake3;

import com.luizalabs.quake3.DTO.PlayerKillsByGame;
import com.luizalabs.quake3.business.FileBusiness;
import com.luizalabs.quake3.business.GameBusiness;
import com.luizalabs.quake3.entity.Game;
import com.luizalabs.quake3.entity.Player;
import com.luizalabs.quake3.repository.GameRepository;
import com.luizalabs.quake3.repository.PlayerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class FileBusinessUnitTest {
    
    @Autowired
    FileBusiness fileBusiness;
    @Autowired
    GameBusiness gameBusiness;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    PlayerRepository playerRepository;

    @Test
    @Transactional
    void shouldReadNewGame() throws IOException {

        String content = "  0:00 InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0";
        MultipartFile file = new MockMultipartFile("arquivo.txt", "original.txt", "text/plain" , content.getBytes(StandardCharsets.ISO_8859_1));

        fileBusiness.readFile(file);
        List<Game> gameList = gameRepository.findAll();
        Assert.assertEquals(1, gameList.size());
    }

    @Test
    @Transactional
    void shouldReadNewPlayer() throws IOException {

        String content = "  0:00 InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0\n" +
                         " 20:34 ClientUserinfoChanged: 2 n\\Isgalamido\\t\\0\\model\\xian/default\\hmodel\\xian/default\\g_redteam\\\\g_blueteam\\\\c1\\4\\c2\\5\\hc\\100\\w\\0\\l\\0\\tt\\0\\tl\\0";
        MultipartFile file = new MockMultipartFile("arquivo.txt", "original.txt", "text/plain" , content.getBytes(StandardCharsets.ISO_8859_1));

        fileBusiness.readFile(file);
        List<Player> players = playerRepository.findAll();
        Assert.assertEquals(1, players.size());
    }

    @Test
    @Transactional
    void shouldDecreasePlayerKillAndCountOnGameTotalKills() throws IOException {

        String content = "  0:00 InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0\n" +
                         " 20:34 ClientUserinfoChanged: 2 n\\Isgalamido\\t\\0\\model\\xian/default\\hmodel\\xian/default\\g_redteam\\\\g_blueteam\\\\c1\\4\\c2\\5\\hc\\100\\w\\0\\l\\0\\tt\\0\\tl\\0\n" +
                         " 20:54 Kill: 1022 2 22: <world> killed Isgalamido by MOD_TRIGGER_HURT";

        MultipartFile file = new MockMultipartFile("arquivo.txt", "original.txt", "text/plain" , content.getBytes(StandardCharsets.ISO_8859_1));

        fileBusiness.readFile(file);

        List<Game> game = gameRepository.findAll();
        List<PlayerKillsByGame> playerKillsByGames = gameBusiness.getPlayersKillByGame(game.get(0).getId());

        Assert.assertEquals(-1, (int)playerKillsByGames.get(0).getKills());
        Assert.assertEquals(1, (int)game.get(0).getTotalKills());

    }

    @Test
    @Transactional
    void shouldIncreasePlayerKillAndCountOnGameTotalKills() throws IOException {

        String content = "  0:00 InitGame: \\sv_floodProtect\\1\\sv_maxPing\\0\\sv_minPing\\0\\sv_maxRate\\10000\\sv_minRate\\0\\sv_hostname\\Code Miner Server\\g_gametype\\0\\sv_privateClients\\2\\sv_maxclients\\16\\sv_allowDownload\\0\\dmflags\\0\\fraglimit\\20\\timelimit\\15\\g_maxGameClients\\0\\capturelimit\\8\\version\\ioq3 1.36 linux-x86_64 Apr 12 2009\\protocol\\68\\mapname\\q3dm17\\gamename\\baseq3\\g_needpass\\0\n" +
                         " 20:34 ClientUserinfoChanged: 2 n\\Isgalamido\\t\\0\\model\\xian/default\\hmodel\\xian/default\\g_redteam\\\\g_blueteam\\\\c1\\4\\c2\\5\\hc\\100\\w\\0\\l\\0\\tt\\0\\tl\\0\n" +
                         "  2:29 Kill: 3 4 10: Isgalamido killed Zeh by MOD_RAILGUN";

        MultipartFile file = new MockMultipartFile("arquivo.txt", "original.txt", "text/plain" , content.getBytes(StandardCharsets.ISO_8859_1));

        fileBusiness.readFile(file);

        List<Game> game = gameRepository.findAll();
        List<PlayerKillsByGame> playerKillsByGames = gameBusiness.getPlayersKillByGame(game.get(0).getId());

        Assert.assertEquals(1, (int)playerKillsByGames.get(0).getKills());
        Assert.assertEquals(1, (int)game.get(0).getTotalKills());

    }






}
