package fr.utt.lo02.projet.model.board.visitor;

/**
 * Represent one of the different variants to calculate the score for the game.
 * This one have use a scaled score
 * It implements IBoard Visitor to follow the visitor's construction.
 *
 * @author Baptiste, Jacques
 */

public class ScoreCalculatorWithBonusVisitor extends ScoreCalculatorVisitor
{

    /**
     * This method calculate the score according to the number of the alignment's length (cf. statement's table score)
     *
     * @param nb_victory_color, the number of colors which following each other.
     * @return the score of the current alignment
     */
    @Override
    protected int calculateColorScoreAlignment(int nb_victory_color)
    {
        int alignment_score = 0;
        if (nb_victory_color == 3)
        {
            alignment_score = 4;
        } else if (nb_victory_color == 4)
        {
            alignment_score = 10;
        } else if (nb_victory_color == 5)
        {
            alignment_score = 12;
        }
        return alignment_score;
    }

    /**
     * This method calculate the score according to the number of the alignment's length (cf. statement's table score)
     *
     * @param nb_victory_shape, the number of shapes which following each other.
     * @return the score of the current alignment
     */
    @Override
    protected int calculateShapeScoreAlignment(int nb_victory_shape)
    {
        int alignment_score = 0;
        if (nb_victory_shape == 2)
        {
            alignment_score = 1;
        } else if (nb_victory_shape == 3)
        {
            alignment_score = 2;
        } else if (nb_victory_shape == 4)
        {
            alignment_score = 6;
        } else if (nb_victory_shape == 5)
        {
            alignment_score = 8;
        }
        return alignment_score;
    }

    /**
     * This method calculate the score according to the number of the alignment's length (cf. statement's table score)
     *
     * @param nb_victory_filling, the number of filling which following each other.
     * @return the score of the current alignment
     */
    @Override
    protected int calculateFillingScoreAlignment(int nb_victory_filling)
    {
        int alignment_score = 0;
        if (nb_victory_filling == 3)
        {
            alignment_score = 3;
        } else if (nb_victory_filling == 4)
        {
            alignment_score = 8;
        } else if (nb_victory_filling == 5)
        {
            alignment_score = 10;
        }
        return alignment_score;
    }
}
