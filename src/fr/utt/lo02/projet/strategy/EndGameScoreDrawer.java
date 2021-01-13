package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.game.AbstractShapeUpGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EndGameScoreDrawer extends ScoreDrawer
{

    public EndGameScoreDrawer(AbstractShapeUpGame model)
    {
        super(model);
    }

    public void drawEndGameScores(Graphics2D g2d)
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

        java.util.List<Color> colors = new ArrayList<>();
        colors.add(blue);
        colors.add(green);
        colors.add(red);

        // Title "Scores"
        int offsetX = 200;
        drawRainbowTitle(g2d, font, colors, offsetX, "FINAL SCORES");
        Color curr;


        // Draw player name and their scores
        g2d.setFont(new Font(font.getName(), Font.PLAIN, 70));

        curr = colors.get(0);
        g2d.setColor(curr);

        List<Player> players = model.getPlayers();
        for (int i = 0; i < players.size(); i++)
        {
            Player player = players.get(i);
            curr = colors.get(i);
            g2d.setColor(curr);
            g2d.drawString(player.getName(), 500 + i * 200, 300);

        }

        for (int i = 0; i < 4; i++)
        {
            // draw round number
            g2d.setColor(Color.white);
            g2d.drawString("ROUND " + (i + 1), 50, 400 + 100 * i);

            // draw scores for the round

            for (int j = 0; j < players.size(); j++)
            {

                curr = colors.get(j);
                g2d.setColor(curr);

                g2d.drawString(String.valueOf(players.get(j).getScoresRound().get(i)), 500 + 200 * j, 400 + 100 * i);
            }

        }
        int scoresGame = -1;
        Player winner = null;

        for (int i = 0; i < players.size(); i++)
        {
            Player p = players.get(i);
            int playerSum = p.getScoresRound().stream().mapToInt(a -> a).sum();

            if (playerSum > scoresGame)
            {
                scoresGame = playerSum;
                curr = colors.get(i);
                winner = p;
            }
        }
        // Win text (player won the game)
        g2d.setColor(curr);
        g2d.drawString(winner.getName(), 350, 800);
        g2d.setColor(Color.white);
        g2d.drawString("WON THE GAME", 350 + winner.getName().length() * 50 - 60, 800);

    }


}
