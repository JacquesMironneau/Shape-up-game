package fr.utt.lo02.projet.view.hmi;

import java.awt.*;
import java.io.InputStream;

/**
 * Font initializer, simply load the font
 */
public class AddFont
{

    private static Font paintFont = null;
    private static final String FONT_PATH = "font/paint2.ttf";


    /**
     * Read the font from file and return it
     * @return the game font
     */
    public static Font createFont()
    {
        try
        {
            InputStream resource = AddFont.class.getClassLoader().getResourceAsStream(FONT_PATH);

            assert resource != null;
            Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, resource);
            paintFont = ttfBase.deriveFont(Font.PLAIN, 24);
        } catch (Exception ex)
        {
            ex.printStackTrace();
            System.err.println("Font not loaded.");
        }
        return paintFont;
    }
}
