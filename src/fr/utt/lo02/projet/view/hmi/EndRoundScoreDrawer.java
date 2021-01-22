package fr.utt.lo02.projet.view.hmi;

import fr.utt.lo02.projet.model.game.AbstractShapeUpGame;
import fr.utt.lo02.projet.model.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static fr.utt.lo02.projet.view.hmi.SwingHmiView.PLAYER_HAND_Y;

/**
 * Draws the end round score using Graphics2D
 * Scores, players names and round number are here strings that are drawn with the loaded font, with different colors
 *
 * @see AddFont
 */
public class EndRoundScoreDrawer extends ScoreDrawer
{

    public EndRoundScoreDrawer(AbstractShapeUpGame model)
    {
        super(model);
    }

    /**
     * draws the end round scores, with the players' scores for this round
     * and the winner announcement
     *
     * @param g2d the Graphics2D of the JPanel
     */
    public void drawEndRoundScores(Graphics2D g2d)
    {

        Font font = null;
        try
        {
            font = AddFont.createFont();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        Color blue = new Color(102, 153, 255);
        Color green = new Color(102, 204, 102);
        Color red = new Color(204, 51, 51);
        Color yellow = new Color(204, 204, 153);


        java.util.List<Color> colors = new ArrayList<>();
        colors.add(blue);
        colors.add(green);
        colors.add(red);

        // Title "Scores"
        int offsetX = 450;
        drawRainbowTitle(g2d, font, colors, offsetX, "SCORES");
        Color curr;


        // Draw player name and their scores
        g2d.setFont(new Font(font.getName(), Font.PLAIN, 70));

        curr = colors.get(0);
        g2d.setColor(curr);
        int y = 300;
        int space = 12;
        for (Player player : model.getPlayers())
        {

            int realSpaces = space - player.getName().length();
            StringBuilder bs = new StringBuilder();
            bs.append(player.getName());
            bs.append(" ".repeat(Math.max(0, realSpaces)));
            bs.append(player.getScoresRound().get(player.getScoresRound().size() - 1));
            g2d.drawString(bs.toString().toUpperCase(), 450, y);
            curr = colors.get((colors.indexOf(curr) + 1) % colors.size());
            g2d.setColor(curr);

            y += 150;
        }

        int scoresRound = -1;
        Player winner = null;
        List<Player> players = model.getPlayers();
        for (int i = 0; i < players.size(); i++)
        {
            Player player = players.get(i);
            int playerScore = player.getScoresRound().get(player.getScoresRound().size() - 1);

            if (playerScore > scoresRound)
            {
                scoresRound = playerScore;
                curr = colors.get(i);
                winner = players.get(i);
            }

        }
        g2d.setColor(curr);
        g2d.drawString(winner.getName().toUpperCase(), 350, PLAYER_HAND_Y + 30);
        g2d.setColor(Color.white);
        g2d.drawString("WON THIS ROUND", 425 + winner.getName().length() * 50 - 60, PLAYER_HAND_Y + 30);

    }
}
