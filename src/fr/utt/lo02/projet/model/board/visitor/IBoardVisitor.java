package fr.utt.lo02.projet.model.board.visitor;

import fr.utt.lo02.projet.model.board.Card;
import fr.utt.lo02.projet.model.board.CircleBoard;
import fr.utt.lo02.projet.model.board.RectangleBoard;
import fr.utt.lo02.projet.model.board.TriangleBoard;

/**
 * Represent the score calculator in which we start a method based on the shape of the game and the Victory Card.
 * It is an interface because we have several ways to calculate a score.
 * @author Baptiste, Jacques
 *
 */

public interface IBoardVisitor {
	
	/**
	 * Calculate the score for a CircleBoard
	 * @param board the CircleBoard
	 * @param victoryCard The victory card of a player
	 * @return the value of the score
	 */
	int visit(CircleBoard board, Card victoryCard);
	
	/**
	 * Calculate the score for a TriangleBoard
	 * @param board the TriangleBoard
	 * @param victoryCard The victory card of a player
	 * @return the value of the score
	 */
	int visit(TriangleBoard board, Card victoryCard);
	
	/**
	 * Calculate the score for a RectangleBoard
	 * @param board the RectangleBoard
	 * @param victoryCard The victory card of a player
	 * @return the value of the score
	 */
	int visit(RectangleBoard board, Card victoryCard);
	
}
	