package fr.utt.lo02.projet;

import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.board.boardEmptyException;
import fr.utt.lo02.projet.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.game.ShapeUpGame;
import fr.utt.lo02.projet.game.ShapeUpGameAdvanced;
import fr.utt.lo02.projet.strategy.PlayerHandEmptyException;
import fr.utt.lo02.projet.strategy.PlayerStrategy;
import fr.utt.lo02.projet.strategy.RealPlayer;
import fr.utt.lo02.projet.strategy.VirtualPlayer;

import java.util.ArrayList;
import java.util.List;

public class RealPlayers
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		RectangleBoard rb = new RectangleBoard();
		List<PlayerStrategy> ps = new ArrayList<>();
		ps.add(new RealPlayer("Vrai1",rb));
		ps.add(new RealPlayer("Vrai2",rb));


		try
		{
			new ShapeUpGame(new ScoreCalculatorVisitor(), ps, rb).playGame();
		} catch (PlayerHandEmptyException | boardEmptyException e)
		{
			e.printStackTrace();
		}
	}
}
