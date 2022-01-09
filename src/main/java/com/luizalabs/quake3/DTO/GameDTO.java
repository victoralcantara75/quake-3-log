package com.luizalabs.quake3.DTO;

import java.util.List;

public class GameDTO {

    private Integer total_kills;
    List<String> players;
    List<PlayerKillsByGame> kills;

    public Integer getTotal_kills() {
        return total_kills;
    }

    public void setTotal_kills(Integer total_kills) {
        this.total_kills = total_kills;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public List<PlayerKillsByGame> getKills() {
        return kills;
    }

    public void setKills(List<PlayerKillsByGame> kills) {
        this.kills = kills;
    }
}
