package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.game.AbstractShapeUpGame;

import java.awt.*;
import java.util.List;

public class ScoreDrawer
{

    protected AbstractShapeUpGame model;

    public ScoreDrawer(AbstractShapeUpGame model)
    {
        this.model = model;
    }



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
