package com.luizalabs.quake3.controller;

import com.luizalabs.quake3.DTO.GameDTO;
import com.luizalabs.quake3.business.GameBusiness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "games")
public class GameController {

    private final GameBusiness gameBusiness;

    public GameController(GameBusiness gameBusiness){
        this.gameBusiness = gameBusiness;
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getGames(){
        try{
            List<GameDTO> gameDTOList = gameBusiness.getGamesSummary();
            return new ResponseEntity<>(gameDTOList, HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getGames(@PathVariable("id") Long id){
        try{
            GameDTO gameDTO = gameBusiness.getGameSummaryById(id);
            return new ResponseEntity<>(gameDTO, HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
