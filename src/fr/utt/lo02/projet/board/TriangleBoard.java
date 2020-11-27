package fr.utt.lo02.projet.board;

import fr.utt.lo02.projet.board.visitor.IBoardVisitor;

import java.util.*;

/**
 * Represent a triangle game board, one of the different shapes for the game.
 * It is extends by Abstract Board to follow the board's construction.
 *
 * @author Baptiste, Jacques
 */

public class TriangleBoard extends AbstractBoard
{

	private static final String SPACE_ONE = "   ";

	private static final String SPACE_TWO = "       ";

	private static final String SPACE_THREE = "          ";

	private static final String SPACE_FOUR = "             ";

	private int patternNumber = 1;

	private final List<List<Coordinates>> patterns;

	public TriangleBoard()
	{
		super();
		patterns = new ArrayList<>();
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

		for (List<Coordinates> pattern : patterns)
		{
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
		}
		return false;
	}

	public Map<Coordinates, Card> retrievePattern()
	{
		// Get the top left card, in order to apply the map to the pattern
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
		for (List<Coordinates> pattern : patterns)
		{
			for (Coordinates patternCoord : pattern)
			{
				HashMap<Coordinates, Card> res = new HashMap<>();
				for (Coordinates coord : list)
				{
					Coordinates co = new Coordinates(coord.getX() - topLeftCard.getX() + patternCoord.getX(), coord.getY() - topLeftCard.getY() + patternCoord.getY());
					res.put(co, placedCards.get(new Coordinates(coord.getX(), coord.getY())));
				}
				if (pattern.containsAll(new ArrayList<>(res.keySet())))
				{
					patternNumber = patterns.indexOf(pattern);
					return res;
				}
			}
			patternNumber++;
		}
		return null;
		//	throw new Exception();
	}

	public void addMissingEmptyCase(Map<Coordinates, Card> coordinates)
	{
		List<Coordinates> pattern = patterns.get(patternNumber);
		for (Coordinates coord : pattern)
		{
			if (!coordinates.containsKey(coord))
			{
				coordinates.put(coord, null);
			}
		}

	}

	@Override
	public void display()
	{
		if (placedCards.isEmpty()) return;

		Map<Coordinates, Card> map = retrievePattern();
		addMissingEmptyCase(map);
		printPattern(map);
	}

	private void initPattern()
	{
		List<Coordinates> pattern0 = new ArrayList<Coordinates>();
		List<Coordinates> pattern_1 = new ArrayList<Coordinates>();
		List<Coordinates> pattern_2 = new ArrayList<Coordinates>();
		List<Coordinates> pattern_3 = new ArrayList<Coordinates>();

		pattern0.add(new Coordinates(0, 0));
		pattern0.add(new Coordinates(1, 0));
		pattern0.add(new Coordinates(2, 0));
		pattern0.add(new Coordinates(3, 0));
		pattern0.add(new Coordinates(4, 0));
		pattern0.add(new Coordinates(0, -1));
		pattern0.add(new Coordinates(1, -1));
		pattern0.add(new Coordinates(2, -1));
		pattern0.add(new Coordinates(3, -1));
		pattern0.add(new Coordinates(0, -2));
		pattern0.add(new Coordinates(1, -2));
		pattern0.add(new Coordinates(2, -2));
		pattern0.add(new Coordinates(0, -3));
		pattern0.add(new Coordinates(1, -3));
		pattern0.add(new Coordinates(0, -4));

		pattern_1.add(new Coordinates(0, 0));
		pattern_1.add(new Coordinates(1, 0));
		pattern_1.add(new Coordinates(2, 0));
		pattern_1.add(new Coordinates(3, 0));
		pattern_1.add(new Coordinates(4, 0));
		pattern_1.add(new Coordinates(1, -1));
		pattern_1.add(new Coordinates(2, -1));
		pattern_1.add(new Coordinates(3, -1));
		pattern_1.add(new Coordinates(4, -1));
		pattern_1.add(new Coordinates(2, -2));
		pattern_1.add(new Coordinates(3, -2));
		pattern_1.add(new Coordinates(4, -2));
		pattern_1.add(new Coordinates(3, -3));
		pattern_1.add(new Coordinates(4, -3));
		pattern_1.add(new Coordinates(4, -4));

		pattern_2.add(new Coordinates(0, 0));
		pattern_2.add(new Coordinates(0, -1));
		pattern_2.add(new Coordinates(1, -1));
		pattern_2.add(new Coordinates(0, -2));
		pattern_2.add(new Coordinates(1, -2));
		pattern_2.add(new Coordinates(2, -2));
		pattern_2.add(new Coordinates(0, -3));
		pattern_2.add(new Coordinates(1, -3));
		pattern_2.add(new Coordinates(2, -3));
		pattern_2.add(new Coordinates(3, -3));
		pattern_2.add(new Coordinates(0, -4));
		pattern_2.add(new Coordinates(1, -4));
		pattern_2.add(new Coordinates(2, -4));
		pattern_2.add(new Coordinates(3, -4));
		pattern_2.add(new Coordinates(4, -4));

		pattern_3.add(new Coordinates(4, 0));
		pattern_3.add(new Coordinates(3, -1));
		pattern_3.add(new Coordinates(4, -1));
		pattern_3.add(new Coordinates(2, -2));
		pattern_3.add(new Coordinates(3, -2));
		pattern_3.add(new Coordinates(4, -2));
		pattern_3.add(new Coordinates(1, -3));
		pattern_3.add(new Coordinates(2, -3));
		pattern_3.add(new Coordinates(3, -3));
		pattern_3.add(new Coordinates(4, -3));
		pattern_3.add(new Coordinates(0, -4));
		pattern_3.add(new Coordinates(1, -4));
		pattern_3.add(new Coordinates(2, -4));
		pattern_3.add(new Coordinates(3, -4));
		pattern_3.add(new Coordinates(4, -4));

		patterns.add(pattern0);
		patterns.add(pattern_1);
		patterns.add(pattern_2);
		patterns.add(pattern_3);
	}


	public void printPattern(Map<Coordinates, Card> map)
	{
		if (map.isEmpty()) return;
		// Store every abscissas and ordinates in different lists.
		Set<Integer> abscissaCoordinates = new HashSet<>();
		Set<Integer> ordinateCoordinates = new HashSet<>();

		for (Coordinates coord : map.keySet())
		{
			abscissaCoordinates.add(coord.getX());
			ordinateCoordinates.add(coord.getY());
		}

		int minOrdinate = Collections.min(ordinateCoordinates);
		int maxOrdinate = Collections.max(ordinateCoordinates);

		int spaceNumber = 0;
		String space = "";

		for (int j = maxOrdinate; j >= minOrdinate; j--)
		{
			switch (patternNumber)
			{
				case 0:
				case 2:

					switch (spaceNumber)
					{
						case 0, 1, 2, 3, 4 -> space = "";
					}
					break;
				case 1:

					switch (spaceNumber)
					{
						case 0 -> space = "";
						case 1 -> space = SPACE_ONE;
						case 2 -> space = SPACE_TWO;
						case 3 -> space = SPACE_THREE;
						case 4 -> space = SPACE_FOUR;
					}

					break;
				case 3:

					switch (spaceNumber)
					{
						case 4 -> space = "";
						case 3 -> space = SPACE_ONE;
						case 2 -> space = SPACE_TWO;
						case 1 -> space = SPACE_THREE;
						case 0 -> space = SPACE_FOUR;
					}
					break;
			}

			++spaceNumber;
			abscissaCoordinates = new HashSet<>();
			for (Coordinates coord : map.keySet())
			{
				if (coord.getY() == j)
					abscissaCoordinates.add(coord.getX());
			}

			int maxAbscissa = Collections.max(abscissaCoordinates);
			int minAbscissa = Collections.min(abscissaCoordinates);

			for (int i = minAbscissa; i <= maxAbscissa; i++)
			{
				if (i == minAbscissa)
					System.out.print(space);
				Card card = map.get(new Coordinates(i, j));
				if (card != null)
				{
					Card.printTop(card.getColor());
//					System.out.print(" ");
				} else
					printNullCard();

			}
			System.out.println();

			for (int i = minAbscissa; i <= maxAbscissa; i++)
			{
				if (i == minAbscissa)
					System.out.print(space);

				Card card = map.get(new Coordinates(i, j));
				if (card != null)
				{
					Card.printMiddle(card);
					//System.out.print(SPACE_BETWEEN_CARDS);

				} else
					printNullCard();


			}
			System.out.println();


			for (int i = minAbscissa; i <= maxAbscissa; i++)
			{
				if (i == minAbscissa)
					System.out.print(space);

				Card card = map.get(new Coordinates(i, j));

				if (card != null)
				{
					Card.printBottom(card.getColor());
					//	System.out.print(SPACE_BETWEEN_CARDS);


				} else
					printNullCard();
			}

			System.out.println();
		}
	}

	private void printNullCard()
	{
		System.out.print("    ");
	}
}
