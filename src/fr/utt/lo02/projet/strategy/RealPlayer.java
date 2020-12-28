package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.BoardEmptyException;

import java.util.Scanner;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.GREEN_TEXT;

/**
 * Represent the strategy for a real player.
 * It implements Player Strategy to follow the player's construction.
 *
 * @author Baptiste, Jacques
 */

public class RealPlayer extends Player
{


	public RealPlayer(String name, AbstractBoard b)
	{
		super(name, b);
	}

	@Override
	public Choice askChoice(ChoiceOrder choiceNumber)
	{
		return Choice.END_THE_TURN;
	}

	@Override
	public PlaceRequest askPlaceCard() throws PlayerHandEmptyException
	{
	return null;
	}

	@Override
	public MoveRequest askMoveCard() throws BoardEmptyException
	{
		return null;
	}
}
