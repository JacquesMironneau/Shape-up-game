package fr.utt.lo02.projet.board;

import fr.utt.lo02.projet.board.visitor.IBoardVisitor;

import java.util.*;

/**
 * Represent a rectangle game board, one of the different shapes for the game.
 * It is extends by Abstract Board to follow the board's construction.
 *
 * @author Baptiste, Jacques
 */
public class  RectangleBoard extends AbstractBoard
{

	/**
	 * Max width of a rectangle board
	 */
	public static final int DISTANCE_MAX_WIDTH = 5;

	/**
	 * Max height of a rectangle board
	 */
	public static final int DISTANCE_MAX_HEIGHT = 3;

	public RectangleBoard()
	{
		super();
	}

	/**
	 * @param board       the visitor
	 * @param victoryCard The victory card associated with the score
	 */
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


		int x = coordinates.getX();
		int y = coordinates.getY();
		// Store every abscissas and ordinates in different lists.
		List<Integer> abscissaCoordinates = new ArrayList<>();
		List<Integer> ordinateCoordinates = new ArrayList<>();

		for (Coordinates coord : placedCards.keySet())
		{
			abscissaCoordinates.add(coord.getX());
			ordinateCoordinates.add(coord.getY());
		}

		if (isHorizontal())
		{
			// if the board is horizontal, check if the new coordinate respects the maximum distance with every others.
			return isCloseEnoughToFarthestCoordinate(x, DISTANCE_MAX_WIDTH, abscissaCoordinates) &&
					isCloseEnoughToFarthestCoordinate(y, DISTANCE_MAX_HEIGHT, ordinateCoordinates);

		} else if (isVertical())
		{
			// if the board is vertical, check if the new coordinate respects the maximum distance with every others.
			return isCloseEnoughToFarthestCoordinate(x, DISTANCE_MAX_HEIGHT, abscissaCoordinates) &&
					isCloseEnoughToFarthestCoordinate(y, DISTANCE_MAX_WIDTH, ordinateCoordinates);

		} else
		{
			// if the board is neither vertical or horizontal
			// Check if the new coordinate respects the maximum distance in a horizontal way or in the vertical way.
			// If the coordinate respects both, it means that it is not in an accepted location.
			return isCloseEnoughToFarthestCoordinate(x, DISTANCE_MAX_HEIGHT, abscissaCoordinates) &&
					isCloseEnoughToFarthestCoordinate(y, DISTANCE_MAX_WIDTH, ordinateCoordinates) ||
					isCloseEnoughToFarthestCoordinate(x, DISTANCE_MAX_WIDTH, abscissaCoordinates) &&
							isCloseEnoughToFarthestCoordinate(y, DISTANCE_MAX_HEIGHT, ordinateCoordinates);
		}
	}

	/**
	 * Return if the board is horizontal or not
	 *
	 * @return true if the board contains 4 or 5 elements on one of its ordinates
	 */
	public boolean isVertical()
	{
		int smallestAbscissa = Coordinates.smallestOrdinate(new ArrayList<>(placedCards.keySet()));
		int biggestAbscissa = Coordinates.biggestOrdinate(new ArrayList<>(placedCards.keySet()));

		return Math.abs(smallestAbscissa - biggestAbscissa) >= 3;
	}

	/**
	 * Return if the board is vertical or not
	 *
	 * @return true if the board contains 4 or 5 element on one of its abscissas
	 */
	public boolean isHorizontal()
	{
		int smallestAbscissa = Coordinates.smallestAbscissa(new ArrayList<>(placedCards.keySet()));
		int biggestAbscissa = Coordinates.biggestAbscissa(new ArrayList<>(placedCards.keySet()));

		return Math.abs(smallestAbscissa - biggestAbscissa) >= 3;
	}


	/**
	 * @param coordinateField  A field of a coordinate (eg: x or y)
	 * @param distanceMax      the max distance, see DISTANCE_MAX_HEIGHT a,d DISTANCE_MAX_WIDTH
	 * @param coordinatesField A list of coordinates field (e.g.: a list of abscissa if the coordinate field is an ordinate).
	 * @return false if the given coordinate exceed the maximum distance between itself and one of the coordinate.
	 */
	private boolean isCloseEnoughToFarthestCoordinate(int coordinateField, int distanceMax, List<Integer> coordinatesField)
	{
		for (int coordinate : coordinatesField)
		{
			if (Math.abs(coordinate - coordinateField) >= distanceMax)
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public void display()
	{
		if (placedCards.isEmpty()) return;
		// Store every abscissas and ordinates in different lists.
		Set<Integer> abscissaCoordinates = new HashSet<>();
		Set<Integer> ordinateCoordinates = new HashSet<>();

		for (Coordinates coord : placedCards.keySet())
		{
			abscissaCoordinates.add(coord.getX());
			ordinateCoordinates.add(coord.getY());
		}

		int maxAbscissa = Collections.max(abscissaCoordinates);
		int minAbscissa = Collections.min(abscissaCoordinates);
		int minOrdinate = Collections.min(ordinateCoordinates);
		int maxOrdinate = Collections.max(ordinateCoordinates);
		final int sizeOfChar = Math.max(String.valueOf(maxAbscissa).length(), String.valueOf(minAbscissa).length());

		for (int j = maxOrdinate; j >= minOrdinate; j--)
		{
			for (int k = 0; k <= sizeOfChar; k++)
			{
				System.out.print(' ');
			}
			for (int i = minAbscissa; i <= maxAbscissa; i++)
			{
				Card card = placedCards.get(new Coordinates(i, j));
				if (card != null)
				{
					Card.printTop(card.getColor());
				} else
					System.out.print("   ");

			}
			System.out.println();

			int sizeOfCurrentChar = String.valueOf(j).length();
			System.out.print(j);

			for (int k = 0; k <= sizeOfChar - sizeOfCurrentChar; k++)
			{
				System.out.print(' ');

			}
			for (int i = minAbscissa; i <= maxAbscissa; i++)
			{
				Card card = placedCards.get(new Coordinates(i, j));
				if (card != null)
				{
					Card.printMiddle(card);
				} else
					System.out.print("   ");

			}
			System.out.println();

			for (int k = 0; k <= sizeOfChar; k++)
			{
				System.out.print(' ');
			}
			for (int i = minAbscissa; i <= maxAbscissa; i++)
			{
				Card card = placedCards.get(new Coordinates(i, j));

				if (card != null)
				{
					Card.printBottom(card.getColor());
				} else
					System.out.print("   ");

			}

			System.out.println();

		}

		System.out.print("  ");
		for (int n = minAbscissa; n <= maxAbscissa; n++)
		{
			int nbChar = String.valueOf(n).length();

			switch (nbChar)
			{
				case 1 -> {
					System.out.print(' ');
					System.out.print(n);
					System.out.print(' ');
					System.out.print(' ');
				}
				case 2 -> {
					System.out.print(' ');
					System.out.print(n);
					System.out.print(' ');
				}
				case 3 -> {
					System.out.print(n);
					System.out.print(' ');
				}
				case 4 -> System.out.print(n);
			}
		}
		System.out.println();
		System.out.println();
	}

}
