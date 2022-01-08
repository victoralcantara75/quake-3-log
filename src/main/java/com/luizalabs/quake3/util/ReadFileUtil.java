package com.luizalabs.quake3.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadFileUtil {

    public static BufferedReader getFileReader(MultipartFile file) throws IOException {
        return new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.ISO_8859_1));
    }

    public static List<String> getSplitLine(String line){
        String[] splitLineArray = line.split(" ");

        List<String> splitLine = Arrays.asList(splitLineArray);
        List<String> result = new ArrayList<>();

        for (String s : splitLine){
            if(!s.equals(""))
                result.add(s);
        }

        return result;
    }
}
