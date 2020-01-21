package com.example.materialtest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ReadtxtUtil {
    public static List<String> getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        List<String> storeinfo= new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                storeinfo.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storeinfo;
    }
}
