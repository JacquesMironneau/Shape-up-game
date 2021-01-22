package fr.utt.lo02.projet.view;

import fr.utt.lo02.projet.controller.GameController;
import fr.utt.lo02.projet.model.game.MoveRequestResult;
import fr.utt.lo02.projet.model.game.PlaceRequestResult;

import java.beans.PropertyChangeListener;

/**
 * Represents a view of the game (View of the MVC pattern)
 * <p>
 * The view is here a propertyChangeListener and thus use the Observer pattern.
 */
public interface GameView extends PropertyChangeListener
{
    /**
     * Displays a failed move
     *
     * @param moveRequestResult fail reason
     */
    void displayMoveFailed(MoveRequestResult moveRequestResult);

    /**
     * Displays a failed place
     *
     * @param placeRequestResult fail reason
     */
    void displayPlaceFailed(PlaceRequestResult placeRequestResult);

    /**
     * Displays the scores of the just ended round
     */
    void displayScoresEndRound();

    /**
     * Displays the game board
     */
    void displayBoard();

    /**
     * Sets a controller to the view
     *
     * @param controller the controller
     * @see fr.utt.lo02.projet.controller.GameController
     */
    void setController(GameController controller);
}
