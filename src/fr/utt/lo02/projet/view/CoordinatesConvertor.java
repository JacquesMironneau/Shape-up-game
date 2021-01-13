package fr.utt.lo02.projet.view;

import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.CircleBoard;
import fr.utt.lo02.projet.model.board.Coordinates;

import static fr.utt.lo02.projet.view.SwingHmiView.*;

public class CoordinatesConvertor
{

    public static final double CIRCLE_COEFFICIENT_OFFSET = 0.5;
    private final boolean isCircleBoard;

    public CoordinatesConvertor(AbstractBoard board)
    {
        isCircleBoard = board instanceof CircleBoard;
    }

    public Coordinates screenToGameCoordinates(int x, int y, int minAbscissa, int maxOrdinate)
    {
        int j = maxOrdinate - ((y - TOP_BOARD_OFFSET) / (CARD_HEIGHT + OFFSET_Y));

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


    public Coordinates gameToScreenCoordinates(int i, int j, int minAbscissa, int maxOrdinate)
    {
        int x = (i - minAbscissa) * (CARD_WIDTH + OFFSET_X);
        x += LEFT_BOARD_OFFSET;
        int y = (maxOrdinate - j) * (CARD_HEIGHT + OFFSET_Y);
        y += TOP_BOARD_OFFSET;
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
