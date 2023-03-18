package com.jep.p2p.http;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String fileUrl = "https://www.7-zip.org/a/7z2201-linux-x64.tar.xz", saveDir = "/home/your_user/Downloads/";
        try {
            HttpDownloader.downloadFile(fileUrl, saveDir);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
