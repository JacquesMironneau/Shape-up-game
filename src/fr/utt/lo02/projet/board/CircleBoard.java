package fr.utt.lo02.projet.board;

import fr.utt.lo02.projet.board.visitor.IBoardVisitor;

import java.util.*;

/**
 * Represent a circle game board, one of the different shapes for the game.
 * It is extends by Abstract Board to follow the board's construction.
 *
 * @author Baptiste, Jacques
 */
public class CircleBoard extends AbstractBoard
{

	private final List<Coordinates> pattern;

	public CircleBoard()
	{
		super();
		pattern = new ArrayList<>();
		initPattern();
	}


	@Override
	public int accept(IBoardVisitor board, Card victoryCard)
	{
		return board.visit(this, victoryCard);
	}

	@Override
	public boolean isCardAdjacent(Coordinates coordinates)
	{
		int x = coordinates.getX();
		int y = coordinates.getY();
		int[] possibleAbscissas = new int[]{x + 1, x - 1, x, x};
		int[] possibleOrdinates = new int[]{y, y, y - 1, y + 1};

		for (int i = 0; i < 4; ++i)
		{
			if (placedCards.containsKey(new Coordinates(possibleAbscissas[i], possibleOrdinates[i])))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isCardInTheLayout(Coordinates coordinates)
	{
		// If no cards has been placed, the card is obligatory in the layout
		if (placedCards.isEmpty()) return true; // maybe exception

		// If one card is already at the given position the card can't me moved or placed here
		if (placedCards.containsKey(coordinates)) return false;

		// Get the top left card, in order to apply the map to the pattern
		List<Coordinates> list = new ArrayList<>(placedCards.keySet());
		list.add(coordinates);

		Iterator<Coordinates> iterator = list.iterator();
		Coordinates topLeftCard = iterator.next();

		while (iterator.hasNext())
		{
			Coordinates key = iterator.next();
			if (Coordinates.isOneMoreTopLeftThanTwo(key, topLeftCard))
			{
				topLeftCard = key;
			}
		}

		for (Coordinates patternCoord : pattern)
		{
			ArrayList<Coordinates> res = new ArrayList<>();
			for (Coordinates coord : list)
			{
				Coordinates co = new Coordinates(coord.getX() - topLeftCard.getX() + patternCoord.getX(), coord.getY() - topLeftCard.getY() + patternCoord.getY());
				res.add(co);
			}
			if (pattern.containsAll(res)) return true;
		}
		return false;
	}

	@Override
	public void display()
	{
		if (placedCards.isEmpty()) return;
		
		// Store every ordinates in different lists.
		Set<Integer> ordinateCoordinates = new HashSet<>();

		for (Coordinates coord : placedCards.keySet())
		{
			ordinateCoordinates.add(coord.getY());
		}

		int minOrdinate = Collections.min(ordinateCoordinates);
		int maxOrdinate = Collections.max(ordinateCoordinates);

		// Je récup la top left card comme dans le isCardInTheLayout
		List<Coordinates> list = new ArrayList<>(placedCards.keySet());
		Iterator<Coordinates> iterator = list.iterator();
		Coordinates topLeftCard = iterator.next();

		while (iterator.hasNext())
			{
				Coordinates key = iterator.next();
			if (Coordinates.isOneMoreTopLeftThanTwo(key, topLeftCard))
				{
					topLeftCard = key;
				}
			}
		
		int spaceNumber = 0;
		String space = "";

		// On parcourt toutes les lignes du jeu
		for (int j = maxOrdinate; j >= minOrdinate; j--)
		{
			//Même chose que t'avais fait sauf que j'ai décrémenté spaceNumber 
			//pour avoir des chiffres correspondants aux ordonnées du pattern
			switch (spaceNumber)
			{
				case 0, -4 -> space = "    ";
				case -1, -3 -> space = "  ";
				case -2 -> space = " ";
			}
			spaceNumber--;
			//booléen qui se réinitialise à vrai à chaque fois qu'on parcourt une nouvelle ligne
			boolean firstCardOnTheRow = true;
			
			//On parcourt mnt toutes les cartes du pattern
			for (Coordinates patternCoord : pattern)
			{
				//Si la carte du pattern parcourue n'est pas sur la ligne qu'on vérifie,
				//on fait rien et on passe à la suivante
				if (patternCoord.getY()==spaceNumber) {
					//Si c'est la première carte de la ligne on print l'espace
					//On met à faux le bool: pas d'espace pour les autres cartes de la ligne
					if (firstCardOnTheRow) {
						System.out.print(space);
						firstCardOnTheRow = false;
					}
					//On projete la carte du pattern parcourue avec des coordonnées correspondantes à notre partie
					//à l'aide de la top left card du board
					Coordinates patternCoordInGame = new Coordinates(topLeftCard.getX() + patternCoord.getX(), topLeftCard.getY() + patternCoord.getY());
					//Si la carte du pattern avec ses coordonnées projetées existe dans le jeu, on la print
					if (placedCards.containsKey(patternCoordInGame))
					{
						Card.printTop(placedCards.get(patternCoordInGame).getColor());
					} else { //Sinon on met un espace
						System.out.print("    ");
					}
				}

			}
			System.out.println();

			for (Coordinates patternCoord : pattern)
			{
				if (patternCoord.getY()==spaceNumber) {
					if (firstCardOnTheRow) {
						System.out.print(space);
						firstCardOnTheRow = false;
					}
					Coordinates patternCoordInGame = new Coordinates(topLeftCard.getX() + patternCoord.getX(), topLeftCard.getY() + patternCoord.getY());
					if (placedCards.containsKey(patternCoordInGame))
					{
						Card.printMiddle(placedCards.get(patternCoordInGame));
					} else {
						System.out.print("    ");
					}
				}
			}
			System.out.println();


			for (Coordinates patternCoord : pattern)
			{
				if (patternCoord.getY()==spaceNumber) {
					if (firstCardOnTheRow) {
						System.out.print(space);
						firstCardOnTheRow = false;
					}
					Coordinates patternCoordInGame = new Coordinates(topLeftCard.getX() + patternCoord.getX(), topLeftCard.getY() + patternCoord.getY());
					if (placedCards.containsKey(patternCoordInGame))
					{
						Card.printBottom(placedCards.get(patternCoordInGame).getColor());
					} else {
						System.out.print("    ");
					}	
					
				}
			}
			System.out.println();

		}
	}

	private void initPattern()
	{
		pattern.add(new Coordinates(0, 0));
		pattern.add(new Coordinates(1, 0));
		pattern.add(new Coordinates(0, -1));
		pattern.add(new Coordinates(1, -1));
		pattern.add(new Coordinates(2, -1));
		pattern.add(new Coordinates(0, -2));
		pattern.add(new Coordinates(1, -2));
		pattern.add(new Coordinates(2, -2));
		pattern.add(new Coordinates(3, -2));
		pattern.add(new Coordinates(1, -3));
		pattern.add(new Coordinates(2, -3));
		pattern.add(new Coordinates(3, -3));
		pattern.add(new Coordinates(2, -4));
		pattern.add(new Coordinates(3, -4));
	}

}
