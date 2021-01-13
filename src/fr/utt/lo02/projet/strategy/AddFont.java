package fr.utt.lo02.projet.strategy;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class AddFont {

    private static Font ttfBase = null;
    private static Font paintFont = null;
    private static InputStream myStream = null;
    private static final String FONT_PATH = "res/font/paint2.ttf";

    public static Font createFont() {


            try {
                myStream = new BufferedInputStream(
                        new FileInputStream(FONT_PATH));
                ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
                paintFont = ttfBase.deriveFont(Font.PLAIN, 24);               
            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("Font not loaded.");
            }
            return paintFont;
    }
}
