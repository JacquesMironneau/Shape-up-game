package fr.utt.lo02.projet.board;

import fr.utt.lo02.projet.board.visitor.IBoardVisitor;

import java.util.*;

/**
 * Represent a rectangle game board, one of the different shapes for the game.
 * It is extends by Abstract Board to follow the board's construction.
 *
 * @author Baptiste, Jacques
 */
public class RectangleBoard extends AbstractBoard
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
	public void accept(IBoardVisitor board, Card victoryCard)
	{
		board.visit(this, victoryCard);
	}

	@Override
	public boolean isCardAdjacent(int x, int y)
	{
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
	public boolean isCardInTheLayout(int x, int y)
	{
		// If one card is already at the given position the card can't me moved or placed here
		if (placedCards.containsKey(new Coordinates(x, y))) return false;
 
		//TODO: might not be needed
		// Prevent errors on future get(), thus this function shouldn't be called when the map is empty.
		if (placedCards.isEmpty()) return true; // maybe exception

		// Store every abscissas and ordinates in different lists.
		List<Integer> abscissaCoordinates = new ArrayList<>();
		List<Integer> ordinateCoordinates = new ArrayList<>();

		for (Coordinates coordinates : placedCards.keySet())
		{
			abscissaCoordinates.add(coordinates.getX());
			ordinateCoordinates.add(coordinates.getY());
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
	 * @return true if the board contains 4 or 5 elements on one of its ordinates
	 */
	public boolean isHorizontal()
	{
		List<Integer> ordinateCoordinates = new ArrayList<>();

		for (Coordinates coordinates : placedCards.keySet())
		{
			ordinateCoordinates.add(coordinates.getY());
		}
		List<Integer> sortedOrdinatesOccurrencesList = getSortedOccurrences(ordinateCoordinates);

		return sortedOrdinatesOccurrencesList.get(sortedOrdinatesOccurrencesList.size() - 1) >= 4;
	}

	/**
	 * Return if the board is vertical or not
	 * @return true if the board contains 4 or 5 element on one of its abscissas
	 */
	public boolean isVertical()
	{
		List<Integer> abscissasCordinates = new ArrayList<>();

		for (Coordinates coordinates : placedCards.keySet())
		{
			abscissasCordinates.add(coordinates.getX());
		}
		List<Integer> sortedAbscissasOccurrencesList = getSortedOccurrences(abscissasCordinates);

		return sortedAbscissasOccurrencesList.get(sortedAbscissasOccurrencesList.size() - 1) >= 4;
	}




	/**
	 * Create a list of occurrences for a coordinates list sorted in ascending order.
	 *
	 * @param coordinatesList : List of unique coordinate (For instance: a list of abscissas)
	 * @return a list of occurrences of the given list sorted in ascending order
	 */
	private List<Integer> getSortedOccurrences(List<Integer> coordinatesList)
	{
		Map<Integer, Integer> coordinateFieldOccurrence = new HashMap<>();
		for (int coordinate : coordinatesList)
		{
			Integer nbOccurrence = coordinateFieldOccurrence.get(coordinate);
			if (nbOccurrence == null)
			{
				coordinateFieldOccurrence.put(coordinate, 1);
			} else
			{
				coordinateFieldOccurrence.put(coordinate, nbOccurrence + 1);
			}
		}

		List<Integer> sortedCoordinateFieldOccurrencesList = new ArrayList<Integer>(coordinateFieldOccurrence.values());
		Collections.sort(sortedCoordinateFieldOccurrencesList);
		return sortedCoordinateFieldOccurrencesList;
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


}
