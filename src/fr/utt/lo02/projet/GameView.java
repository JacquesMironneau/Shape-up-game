package fr.utt.lo02.projet;

import fr.utt.lo02.projet.game.GameController;
import fr.utt.lo02.projet.strategy.MoveRequestResult;
import fr.utt.lo02.projet.strategy.PlaceRequestResult;

import java.beans.PropertyChangeListener;

public interface GameView extends PropertyChangeListener
{
	void displayMoveFailed(PlaceRequestResult prr);

	void displayPlaceFailed(MoveRequestResult mrr);

	void displayScoresEndRound();

	void displayBoard();

	void setController(GameController sugc);
}
