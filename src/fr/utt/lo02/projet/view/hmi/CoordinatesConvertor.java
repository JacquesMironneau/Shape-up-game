package fr.utt.lo02.projet.view.hmi;

import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.CircleBoard;
import fr.utt.lo02.projet.model.board.Coordinates;

import static fr.utt.lo02.projet.view.hmi.SwingHmiView.*;

/**
 * Convertor for coordinates
 * Used to convert coordinates from their screen coordinates to their board ones
 */
public class CoordinatesConvertor
{

    /**
     * Circle coefficient for its offset in order to represent a true circle
     */
    public static final double CIRCLE_COEFFICIENT_OFFSET = 0.5;

    /**
     * Store if the board is a circle or not
     */
    private final boolean isCircleBoard;

    public CoordinatesConvertor(AbstractBoard board)
    {
        isCircleBoard = board instanceof CircleBoard;
    }

    /**
     * Convert screen coordinates (x,y) to (i,j) : the associated coordinates in the board
     *
     * @param x           the screen abscissa of the card
     * @param y           the screen ordinate of the card
     * @param minAbscissa the board minimum abscissa
     * @param maxOrdinate the board maximum ordinate
     * @return the matching board coordinated
     */
    public Coordinates screenToGameCoordinates(int x, int y, int minAbscissa, int maxOrdinate)
    {
        int j = maxOrdinate - ((y - TOP_BOARD_OFFSET) / (CARD_HEIGHT + OFFSET_Y));

        // remove the circle offset if the board is a circle
        if (isCircleBoard)
        {
            int spaceIndex = maxOrdinate - j;
            switch (spaceIndex)
            {
                case 0 -> x -= (HOLOGRAM_WIDTH);
                case 1 -> x -= (HOLOGRAM_WIDTH) * CIRCLE_COEFFICIENT_OFFSET;
                case 3 -> x += (HOLOGRAM_WIDTH) * CIRCLE_COEFFICIENT_OFFSET;
                case 4 -> x += (HOLOGRAM_WIDTH);
            }
        }

        int i = ((x - LEFT_BOARD_OFFSET) / (CARD_WIDTH + OFFSET_X)) + minAbscissa;

        if (x < LEFT_BOARD_OFFSET)
        {
            i = minAbscissa - 1;
        } else if (y < TOP_BOARD_OFFSET)
        {
            j = maxOrdinate + 1;

        }
        return new Coordinates(i, j);
    }

    /**
     * Convert board coordinates (i,j) to (x,y) : the associated coordinates in the screen
     *
     * @param i           the board abscissa
     * @param j           the board ordinate
     * @param minAbscissa the board minimum abscissa
     * @param maxOrdinate the board maximum ordinate
     * @return the matching screen coordinated
     */
    public Coordinates gameToScreenCoordinates(int i, int j, int minAbscissa, int maxOrdinate)
    {
        int x = (i - minAbscissa) * (CARD_WIDTH + OFFSET_X);
        x += LEFT_BOARD_OFFSET;
        int y = (maxOrdinate - j) * (CARD_HEIGHT + OFFSET_Y);
        y += TOP_BOARD_OFFSET;
        // add the circle offset if the board is a circle
        if (isCircleBoard)
        {
            int spaceIndex = maxOrdinate - j;
            switch (spaceIndex)
            {
                case 0 -> x += (HOLOGRAM_WIDTH);
                case 4 -> x -= (HOLOGRAM_WIDTH);
                case 1 -> x += HOLOGRAM_WIDTH * CIRCLE_COEFFICIENT_OFFSET;
                case 3 -> x -= HOLOGRAM_WIDTH * CIRCLE_COEFFICIENT_OFFSET;
            }
        }

        return new Coordinates(x, y);
    }
}
