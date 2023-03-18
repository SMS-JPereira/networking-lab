package com.jep.p2p.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import static java.lang.System.out;

public class HttpDownloader {

    private static final int BUFFER_SIZE = 4096;

    public static void downloadFile(String fileUrl, String saveDir) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = connection.getHeaderField("Content-Disposition");
            String contentType = connection.getContentType();
            int contentLength = connection.getContentLength();
            String date = connection.getHeaderField("Date");

            if (disposition != null) {
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10, disposition.length() - 1);
                }
            } else {
                fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            }
            out.println("Content-Type : " + contentType);
            out.println("Content-Disposition : " + disposition);
            out.println("Content-Length : " + contentLength);
            out.println("File Name : " + fileName);
            out.println("Date : " + date);

            InputStream inputStream = connection.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;

            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
