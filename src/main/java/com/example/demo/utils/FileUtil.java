package com.example.demo.utils;

import org.springframework.util.ResourceUtils;

import java.io.*;


public class FileUtil {
    //public static String portraitsPath="src/main/resources/static/portraits/";target\classes\static\portraits
    public static String portraitsPath="static/portraits/";
    public static String coversPath="static/covers/";
    public static String videosPath="static/videos/";
    public static String pdfsPath="static/pdfs/";
    static {
        try {
            String path2 = ResourceUtils.getURL("classpath:").getPath();
            portraitsPath=path2+portraitsPath;
            coversPath=path2+coversPath;
            videosPath=path2+videosPath;
            pdfsPath=path2+pdfsPath;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public static void saveFile(byte[] file,String path)
    {

        BufferedOutputStream bufferedOutputStream=null;
        try {
            bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(path));
            bufferedOutputStream.write(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static class deleteFileRunnable implements Runnable
    {
        public deleteFileRunnable(String path) {
            this.path = path;
        }

        String path;
        @Override
        public void run() {
            File file = new File(path);
            // 判断目录或文件是否存在

            if (!file.exists()) {  // 不存在返回 false
                return;
            } else {

                file.delete();
            }

        }
    }
    public static void deleteFile(String path)
    {
        new Thread(new deleteFileRunnable(path)).start();
    }
}
