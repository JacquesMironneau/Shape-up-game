package fr.utt.lo02.projet.model.board;

import fr.utt.lo02.projet.model.board.visitor.IBoardVisitor;

import java.util.*;

/**
 * Represent a circle game board, one of the different shapes for the game.
 * It extends Abstract Board to follow the board's construction.
 *
 * @author Baptiste, Jacques
 */
public class CircleBoard extends AbstractBoard
{

    private static final String SPACE_BETWEEN_CARDS = "    ";

    private static final String SPACE_FIRST_LAST_ROW = "         ";

    private static final String SPACE_SECOND_FOURTH_ROW = "     ";

    private static final String SPACE_MIDDLE_ROW = " ";

    /**
     * Represent the coordinates of the pattern brought down to (0,0)
     * This is mainly used to verify if the layout is well formed, and thus if a placement or a movement
     * is correct
     */
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

        // Try to fit every coordinates of the placedCard in the pattern
        // We browse each pattern coordinate and try to put the top left card of the placedCard and then
        // add the others, if the whole coordinates are fitting in the pattern the layout is therefore correct.
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

    /**
     * Create a Map using the real card and their pattern coordinates
     *
     * @return the placedCoordinates, using the pattern coordinates
     */
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
        // Try to fit every coordinates of the placedCard in the pattern
        // We browse each pattern coordinate and try to put the top left card of the placedCard and then
        // add the others, if the whole coordinates are fitting in the pattern the layout is therefore correct.
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
                return res;
            }
        }
        return null;
    }

    /**
     * Add "null" in card for the empty case of the pattern
     * This is use to draw in an easier way the pattern by replacing "null" value by space (hmi and console)
     * @param coordinates a map using real card and their pattern coordinates
     */
    public void addMissingEmptyCase(Map<Coordinates, Card> coordinates)
    {
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

    /**
     * Instantiate the pattern filling it with coordinates
     */
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


    /**
     * Display the pattern to the terminal in ascii-art
     * @param map the real card with their pattern coordinates and a null card for the empty location
     */
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

        int maxAbscissa;
        int minAbscissa;
        int minOrdinate = Collections.min(ordinateCoordinates);
        int maxOrdinate = Collections.max(ordinateCoordinates);

        int spaceNumber = 0;
        String space = "";

        for (int j = maxOrdinate; j >= minOrdinate; j--)
        {
            switch (spaceNumber)
            {
                case 0, 4 -> space = SPACE_FIRST_LAST_ROW;
                case 1, 3 -> space = SPACE_SECOND_FOURTH_ROW;
                case 2 -> space = SPACE_MIDDLE_ROW;
            }
            spaceNumber++;
            abscissaCoordinates = new HashSet<>();
            for (Coordinates coord : map.keySet())
            {
                if (coord.getY() == j)
                    abscissaCoordinates.add(coord.getX());
            }

            maxAbscissa = Collections.max(abscissaCoordinates);
            minAbscissa = Collections.min(abscissaCoordinates);

            for (int i = minAbscissa; i <= maxAbscissa; i++)
            {
                if (i == minAbscissa)
                    System.out.print(space);
                Card card = map.get(new Coordinates(i, j));
                if (card != null)
                {
                    Card.printTop(card.getColor());
                    System.out.print(SPACE_BETWEEN_CARDS);
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
                    System.out.print(SPACE_BETWEEN_CARDS);

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
                    System.out.print(SPACE_BETWEEN_CARDS);


                } else
                    printNullCard();
            }

            System.out.println();
        }
    }

    /**
     * Print an empty card
     */
    private void printNullCard()
    {
        System.out.print("       ");
    }
}

