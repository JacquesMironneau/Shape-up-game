package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.game.AbstractShapeUpGame;
import fr.utt.lo02.projet.game.ShapeUpGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class EndGameScorePanel extends JPanel
{

	private AbstractShapeUpGame model;

	public EndGameScorePanel(AbstractShapeUpGame model)
	{
		Dimension preferredSize = new Dimension(1280, 720);


		setPreferredSize(preferredSize);
		setBounds(0, 0, 1280, 720);

		this.model = model;

	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;


		int nbRound = 5;

		int x = 100;
		int y = 170;

		g2d.setColor(Color.BLUE);
		g2d.drawString("Rounds", x, y);
		x+=50;
		for (int i = 0; i < nbRound; i++)
		{
			x+=30;

			g2d.drawString(Integer.toString(i), x, 170);
		}
		y +=30;
		x = 100;
		for (Player player : model.getPlayers())
		{
			g2d.drawString(player.getName(), x, y);

			x += 50;
			List<Integer> scoresRound = player.getScoresRound();
			for (int i: scoresRound)
			{
				x += 30;
				g2d.drawString(Integer.toString(i), x, y);

			}
			x = 100;
			y += 30;

		}

		g2d.drawString("Total score", 100, y);
		x = 150;


		for (int i = 0; i < nbRound; i++)
		{
			x+=30;

			g2d.drawString(Integer.toString(i), x, y);
		}

	}

	public static void main(String[] args)
	{
		JFrame frame = new JFrame();

		List<Player> ps = new ArrayList<>();
		AbstractBoard rb = new RectangleBoard();
		ps.add(new RealPlayer("Jacques", rb));
//		ps.add(new RealPlayer("Théo", rb));
//		ps.add(new RealPlayer("Th1", rb));
//		ps.add(new RealPlayer("Aaa", rb));
		ScoreCalculatorVisitor visitor = new ScoreCalculatorVisitor();

		ps.add(new VirtualPlayer("ord1", rb, new RandomStrategy()));
//        ps.add(new VirtualPlayer("ord2", rb, new RandomStrategy()));
		AbstractShapeUpGame model = new ShapeUpGame(visitor, ps, rb);

		for (Player p: model.getPlayers())
		{
			p.addRoundScore(5);
			p.addRoundScore(5);
			p.addRoundScore(5);
			p.addRoundScore(5);
			p.addRoundScore(5);
		}

		frame.add(new EndGameScorePanel(model));
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
//        comp.fakeUpdate();



		frame.setVisible(true);
	}



}
