package com.luizalabs.quake3.business;

import com.luizalabs.quake3.enums.GameActionEnum;
import com.luizalabs.quake3.util.ReadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Service
public class FileBusiness {

    @Autowired
    GameBusiness gameBusiness;

    public void readFile(MultipartFile file) throws IOException {
        BufferedReader bufferedReader = ReadFileUtil.getFileReader(file);
        String line;

        while( (line = bufferedReader.readLine()) != null){

            List<String> splitLine = ReadFileUtil.getSplitLine(line);
            if (splitLine.get(1).equals(GameActionEnum.START_GAME.getValue())){
                try{
                    gameBusiness.newGame(bufferedReader);
                }catch (Exception e){
                    System.out.println(e);
                }

            }
        }

    }



}
