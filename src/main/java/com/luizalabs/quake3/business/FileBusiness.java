package com.luizalabs.quake3.business;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class FileBusiness {

    public void readFile(MultipartFile file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.ISO_8859_1));
        String line;
        String[] splitLine;

    }

}
