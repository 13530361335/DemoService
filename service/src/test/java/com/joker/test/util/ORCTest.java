/*
package com.joker.test.util;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

*/
/**
 * @author Joker Jing
 * @date 2019/7/14
 *//*

public class ORCTest {

    @Test
    public void getFilesSize() throws TesseractException, IOException {
        ITesseract instance = new Tesseract();
        instance.setDatapath("C:\\Softwares\\Script\\orc\\tessdata");
        File imageDir = new File("C:\\Download\\1.files");
        File[] files = imageDir.listFiles();
        int count = 1;
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".jpg")) {
                String result = instance.doOCR(file);
                File txt = new File("C:\\Download\\text\\" + count + ".txt");
                if(!txt.exists()){
                    txt.createNewFile();
                }
                PrintWriter pw = new PrintWriter(txt);
                pw.print(result);
                pw.close();
                count++;
            }
        }
    }

}
*/
