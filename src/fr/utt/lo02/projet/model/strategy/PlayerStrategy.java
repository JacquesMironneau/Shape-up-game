package fr.utt.lo02.projet.model.strategy;

import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.Card;
import fr.utt.lo02.projet.model.player.Choice;
import fr.utt.lo02.projet.model.player.MoveRequest;
import fr.utt.lo02.projet.model.player.PlaceRequest;

import java.util.List;

/**
 * Represents actions that can be done by a Player and defines virtual player's strategy.
 * @author Baptiste, Jacques
 * It's an interface because there is 2 totally different strategies. They will make same actions but differently.
 */
public interface PlayerStrategy
{	
	/**
	 * Chooses one option to play a turn (place then end turn, place then move, move then place).
	 * 
	 * @param board the board of the round.
	 * @param victoryCard the victory card of the player.
	 * @param playerHand the hand of the player.
	 * @return the choice of the virtual player.
	 */
	Choice makeChoice(AbstractBoard board, Card victoryCard, List<Card> playerHand);

	/**
	 * Makes a place request on the board.
	 * 
	 * @param board the board of the round.
	 * @param victoryCard the victory card of the player.
	 * @param playerHand the hand of the player.
	 * @return return a place request.
	 */
	PlaceRequest makePlaceRequest(AbstractBoard board, Card victoryCard, List<Card> playerHand);

	/**
	 * Makes a move request from an placed card to an empty case on the board
	 * 
	 * @param board the board of the round.
	 * @param victoryCard the victory card of the player.
	 * @param playerHand the hand of the player.
	 * @return return a move request.
	 */
	MoveRequest makeMoveRequest(AbstractBoard board, Card victoryCard, List<Card> playerHand);
}
