package fr.utt.lo02.projet.view.hmi;

import fr.utt.lo02.projet.model.game.AbstractShapeUpGame;

import java.awt.*;
import java.util.List;

/**
 * UI class Use to display the score and the associated rainbow title
 */
public class ScoreDrawer
{

    /**
     * The game used in the mvc model
     */
    protected final AbstractShapeUpGame model;

    public ScoreDrawer(AbstractShapeUpGame model)
    {
        this.model = model;
    }


    /**
     * Draws a rainbow title based on the given colors
     *
     * @param g2d the Graphics2D of the JPanel
     * @param font the font to draw the title
     * @param colors the bunch of colors
     * @param offsetX the offset for the first letter
     * @param text the text to display
     */
    protected void drawRainbowTitle(Graphics2D g2d, Font font, List<Color> colors, int offsetX, String text)
    {
        g2d.setFont(new Font(font.getName(), Font.PLAIN, 140));
        Color curr = colors.get(0);
        g2d.setColor(curr);

        for (int i = 0; i < text.length(); i++)
        {
            g2d.drawString(String.valueOf(text.charAt(i)), 80 * i + offsetX, 200);

            if (text.charAt(i) == ' ') continue;
            curr = colors.get((colors.indexOf(curr) + 1) % colors.size());
            g2d.setColor(curr);

        }
    }
}
