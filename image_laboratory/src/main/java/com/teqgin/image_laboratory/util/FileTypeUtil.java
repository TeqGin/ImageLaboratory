package com.teqgin.image_laboratory.util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author TeqGin
 * 这是用来判断文件类型的工具类
 */

public class FileTypeUtil {
    private static HashMap<String, ArrayList<String>> fileType = new HashMap<>();
    static {
        String type1 = "document" ;
        ArrayList<String> typeName1 = new ArrayList<>();
        typeName1.add(".baiASCII".toLowerCase());
        typeName1.add(".MIME".toLowerCase());
        typeName1.add(".txt");
        typeName1.add(".doc");
        typeName1.add(".docx");
        typeName1.add(".xls");
        typeName1.add(".xlsx");
        typeName1.add(".pdf");
        fileType.put(type1,typeName1);

        String type2 = "video" ;
        ArrayList<String> typeName2 = new ArrayList<>();
        typeName2.add(".wmv");
        typeName2.add(".asf");
        typeName2.add(".asx");
        typeName2.add(".rm");
        typeName2.add(".rmvb");
        typeName2.add(".mp4");
        typeName2.add(".3gp");
        typeName2.add(".mov");
        typeName2.add(".m4v");
        typeName2.add(".avi");
        typeName2.add(".dat");
        typeName2.add(".mkv");
        typeName2.add(".flv");
        typeName2.add(".vob");

        String type3 = "audio" ;
        ArrayList<String> typeName3 = new ArrayList<>();
        typeName3.add(".Wave".toLowerCase());
        typeName3.add(".AIFF".toLowerCase());
        typeName3.add(".Audio".toLowerCase());
        typeName3.add(".MPEG".toLowerCase());
        typeName3.add(".RealAudio".toLowerCase());
        typeName3.add(".MIDI".toLowerCase());
        typeName3.add(".mp3");

        String type4 = "image" ;
        ArrayList<String> typeName4 = new ArrayList<>();
        typeName4.add(".JPEG".toLowerCase());
        typeName4.add(".JPG".toLowerCase());
        typeName4.add(".TIFF".toLowerCase());
        typeName4.add(".RAW".toLowerCase());
        typeName4.add(".BMP".toLowerCase());
        typeName4.add(".GIF".toLowerCase());
        typeName4.add(".PNG".toLowerCase());

        String type5 = "zipFile" ;
        ArrayList<String> typeName5 = new ArrayList<>();
        typeName5.add(".zip");
        typeName5.add(".rar");
        typeName5.add(".7z");
        typeName5.add(".jar");
        typeName5.add(".arj");
        typeName5.add(".gz");

        fileType.put(type1,typeName1);
        fileType.put(type2,typeName2);
        fileType.put(type3,typeName3);
        fileType.put(type4,typeName4);
        fileType.put(type5,typeName5);

    }

    public static String getFileType(String suffix){
        String fileTypeName = "other";
        for (String key: fileType.keySet()){
            for (String name: fileType.get(key)){
                if (suffix.equals(name)){
                    return key;
                }
            }
        }
        return fileTypeName;
    }
}
