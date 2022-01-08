package com.luizalabs.quake3.controller;

import com.luizalabs.quake3.business.FileBusiness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "file")
public class FileController {

    private final FileBusiness fileBusiness;

    public FileController(FileBusiness fileBusiness){
        this.fileBusiness = fileBusiness;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> importFile(@RequestBody MultipartFile file){
        try{
            fileBusiness.readFile(file);
            return new ResponseEntity<>("Arquivo lido com sucesso", HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
