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
	 * Corresponds to the first choice which a player can do.
	 */
	FIRST_CHOICE,
	/**
	 * Corresponds to the second choice which a player can do.
	 * The player can only move or end his turn.
	 */
	SECOND_CHOICE
}
