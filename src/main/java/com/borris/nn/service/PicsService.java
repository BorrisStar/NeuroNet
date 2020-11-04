package com.borris.nn.service;

import com.borris.nn.model.Pic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

@Service
public class PicsService {
    @Value("${dirPath}")
    private String dirPath;

//    private final String regex = "\\D";

    public List<Pic> getPics() {

        File dir = new File(dirPath);
        List<Pic> pics = new ArrayList<>();
        Queue<File> fileTree = new PriorityQueue<>();

        Collections.addAll(fileTree, dir.listFiles());

        while (!fileTree.isEmpty()) {
            File currentFile = fileTree.remove();
            if (currentFile.isDirectory()) {
                Collections.addAll(fileTree, currentFile.listFiles());
            } else {
                Pic pic = new Pic();
                pic.setFileName(currentFile.getName());
                pic.setDigit(Integer.parseInt(String.valueOf(currentFile.getAbsolutePath().charAt(currentFile.getAbsolutePath().indexOf(pic.getFileName()) - 2))));
                pics.add(pic);
            }
        }
        return pics;
    }
}
