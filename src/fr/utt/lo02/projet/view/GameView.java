package fr.utt.lo02.projet.view;

import fr.utt.lo02.projet.controller.GameController;
import fr.utt.lo02.projet.model.game.MoveRequestResult;
import fr.utt.lo02.projet.model.game.PlaceRequestResult;

import java.beans.PropertyChangeListener;

public interface GameView extends PropertyChangeListener
{
	void displayMoveFailed(PlaceRequestResult prr);

	void displayPlaceFailed(MoveRequestResult mrr);

	void displayScoresEndRound();

	void displayBoard();

	void setController(GameController sugc);
}
