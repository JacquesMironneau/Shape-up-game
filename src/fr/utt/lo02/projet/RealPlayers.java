package fr.utt.lo02.projet;

import fr.utt.lo02.projet.model.board.RectangleBoard;
import fr.utt.lo02.projet.model.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.model.game.ShapeUpGame;
import fr.utt.lo02.projet.model.strategy.Player;
import fr.utt.lo02.projet.model.strategy.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class RealPlayers
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		RectangleBoard rb = new RectangleBoard();
		List<Player> ps = new ArrayList<>();
		ps.add(new RealPlayer("Vrai1",rb));
		ps.add(new RealPlayer("Vrai2",rb));


		new ShapeUpGame(new ScoreCalculatorVisitor(), ps, rb);
	}
}
