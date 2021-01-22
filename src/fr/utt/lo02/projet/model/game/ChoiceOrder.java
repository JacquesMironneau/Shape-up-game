package fr.utt.lo02.projet.model.game;

/**
 * Lists the 2 steps when a player plays one turn.
 * If it's his first choice, he can only place or move. And if he moves first, he doesn't have any second choice.
 * If it's his second choice, he can only move or end his turn.
 * @author Baptiste, Jacques
 *
 */
public enum ChoiceOrder
{
	/**
	 * The player can only place or move
	 */
	FIRST_CHOICE,
	/**
	 * The player can only move or end his turn
	 */
	SECOND_CHOICE
}
