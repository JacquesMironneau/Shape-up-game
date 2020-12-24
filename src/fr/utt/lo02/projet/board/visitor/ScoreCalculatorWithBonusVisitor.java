package fr.utt.lo02.projet.board.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.CircleBoard;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.board.TriangleBoard;
import fr.utt.lo02.projet.board.Card.Color;
import fr.utt.lo02.projet.board.Card.Filling;
import fr.utt.lo02.projet.board.Card.Shape;

/**
 * Represent one of the different variants to calculate the score for the game.
 * This one have Bonus with Cards.
 * It implements IBoard Visitor to follow the visitor's construction.
 * @author Baptiste, Jacques
 *
 */

public class ScoreCalculatorWithBonusVisitor implements IBoardVisitor {

	public ScoreCalculatorWithBonusVisitor() {
	}

	/**
	 * This method calculate and return score for a circle board, one of the board variants.
	 *
	 * @param board,       the circle board
	 * @param victoryCard, the victory card associated with the score
	 */
	public int visit(CircleBoard board, Card victoryCard)
	{
		Shape victoryShape = victoryCard.getShape();
		Color victoryColor = victoryCard.getColor();
		Filling victoryFilling = victoryCard.getFilling();
		int final_score = 0;
		int width = 4;
		int height = 5;
		boolean isARow;

		// We recover coordinates of the placed Cards.
		Map<Coordinates, Card> placedCardsWithNull = new HashMap<>(board.getPlacedCards());
		final int smallestAbscissa = Coordinates.smallestAbscissa(new ArrayList<>(placedCardsWithNull.keySet()));
		final int biggestAbscissa = Coordinates.biggestAbscissa(new ArrayList<>(placedCardsWithNull.keySet()));
		final int biggestOrdinate = Coordinates.biggestOrdinate(new ArrayList<>(placedCardsWithNull.keySet()));
		final int smallestOrdinate = Coordinates.smallestOrdinate(new ArrayList<>(placedCardsWithNull.keySet()));

		for (int i = smallestAbscissa; i <= biggestAbscissa; i++)
		{
			for (int j = biggestOrdinate; j >= smallestOrdinate; j--)
			{
				if (!placedCardsWithNull.containsKey(new Coordinates(i,j)))
				{
					placedCardsWithNull.put(new Coordinates(i,j), null);
				}
			}
		}

		Iterator<Coordinates> iterator = placedCardsWithNull.keySet().iterator();
		Coordinates topLeftCard = iterator.next();
		//We recover the card which is in the top left corner of the rectangle.
		while (iterator.hasNext())
		{
			Coordinates key = iterator.next();
			if (Coordinates.isOneMoreTopLeftThanTwo(key, topLeftCard))
			{
				topLeftCard = key;
			}
		}
		//We browse each row and add the row score in the final score each time, for each card's attribute.
		for (int i = 0; i < height; i++)
		{
			isARow = true;
			Coordinates nextCard = new Coordinates(topLeftCard.getX(), topLeftCard.getY() - i);
			ArrayList<Card.Color> colorList = colorListOfOneRow(placedCardsWithNull, width, nextCard, isARow);
			final_score += calculateScoreOfColorRow(colorList, width, victoryColor);
			ArrayList<Card.Shape> shapeList = shapeListOfOneRow(placedCardsWithNull, width, nextCard, isARow);
			final_score += calculateScoreOfShapeRow(shapeList, width, victoryShape);
			ArrayList<Card.Filling> fillingList = fillingListOfOneRow(placedCardsWithNull, width, nextCard, isARow);
			final_score += calculateScoreOfFillingRow(fillingList, width, victoryFilling);
		}
		//We browse each column and add the row score in the final score each time, for each card's attribute.
		for (int j = 0; j < width; j++)
		{
			isARow = false;
			Coordinates nextCard = new Coordinates(topLeftCard.getX() + j, topLeftCard.getY());
			ArrayList<Card.Color> colorList = colorListOfOneRow(placedCardsWithNull, height, nextCard, isARow);
			final_score += calculateScoreOfColorRow(colorList, height, victoryColor);
			ArrayList<Card.Shape> shapeList = shapeListOfOneRow(placedCardsWithNull, height, nextCard, isARow);
			final_score += calculateScoreOfShapeRow(shapeList, height, victoryShape);
			ArrayList<Card.Filling> fillingList = fillingListOfOneRow(placedCardsWithNull, height, nextCard, isARow);
			final_score += calculateScoreOfFillingRow(fillingList, height, victoryFilling);
		}
		return final_score;
	}

	/**
	 * This method calculate and return score for a triangle board, one of the board variants.
	 *
	 * @param board,       the triangle board
	 * @param victoryCard, the victory card associated with the score
	 */
	public int visit(TriangleBoard board, Card victoryCard)
	{
		Shape victoryShape = victoryCard.getShape();
		Color victoryColor = victoryCard.getColor();
		Filling victoryFilling = victoryCard.getFilling();
		int final_score = 0;
		int width = 5;
		int height = 5;
		boolean isARow;

		// We recover coordinates of the placed Cards.

		Map<Coordinates, Card> placedCardsWithNull = new HashMap<>(board.getPlacedCards());
		final int smallestAbscissa = Coordinates.smallestAbscissa(new ArrayList<>(placedCardsWithNull.keySet()));
		final int biggestAbscissa = Coordinates.biggestAbscissa(new ArrayList<>(placedCardsWithNull.keySet()));
		final int biggestOrdinate = Coordinates.biggestOrdinate(new ArrayList<>(placedCardsWithNull.keySet()));
		final int smallestOrdinate = Coordinates.smallestOrdinate(new ArrayList<>(placedCardsWithNull.keySet()));

		for (int i = smallestAbscissa; i <= biggestAbscissa; i++)
		{
			for (int j = biggestOrdinate; j >= smallestOrdinate; j--)
			{
				if (!placedCardsWithNull.containsKey(new Coordinates(i,j)))
				{
					placedCardsWithNull.put(new Coordinates(i,j), null);
				}
			}
		}

		Iterator<Coordinates> iterator = placedCardsWithNull.keySet().iterator();
		Coordinates topLeftCard = iterator.next();
		//We recover the card which is in the top left corner of the rectangle.
		while (iterator.hasNext())
		{
			Coordinates key = iterator.next();
			if (Coordinates.isOneMoreTopLeftThanTwo(key, topLeftCard))
			{
				topLeftCard = key;
			}
		}
		//We browse each row and add the row score in the final score each time, for each card's attribute.
		for (int i = 0; i < height; i++)
		{
			isARow = true;
			Coordinates nextCard = new Coordinates(topLeftCard.getX(), topLeftCard.getY() - i);
			ArrayList<Card.Color> colorList = colorListOfOneRow(placedCardsWithNull, width, nextCard, isARow);
			final_score += calculateScoreOfColorRow(colorList, width, victoryColor);
			ArrayList<Card.Shape> shapeList = shapeListOfOneRow(placedCardsWithNull, width, nextCard, isARow);
			final_score += calculateScoreOfShapeRow(shapeList, width, victoryShape);
			ArrayList<Card.Filling> fillingList = fillingListOfOneRow(placedCardsWithNull, width, nextCard, isARow);
			final_score += calculateScoreOfFillingRow(fillingList, width, victoryFilling);
		}
		//We browse each column and add the row score in the final score each time, for each card's attribute.
		for (int j = 0; j < width; j++)
		{
			isARow = false;
			Coordinates nextCard = new Coordinates(topLeftCard.getX() + j, topLeftCard.getY());
			ArrayList<Card.Color> colorList = colorListOfOneRow(placedCardsWithNull, height, nextCard, isARow);
			final_score += calculateScoreOfColorRow(colorList, height, victoryColor);
			ArrayList<Card.Shape> shapeList = shapeListOfOneRow(placedCardsWithNull, height, nextCard, isARow);
			final_score += calculateScoreOfShapeRow(shapeList, height, victoryShape);
			ArrayList<Card.Filling> fillingList = fillingListOfOneRow(placedCardsWithNull, height, nextCard, isARow);
			final_score += calculateScoreOfFillingRow(fillingList, height, victoryFilling);
		}
		return final_score;
	}

	/**
	 * This method calculate and return score for a rectangle board, basic board in the game.
	 *
	 * @param board,       the rectangle board
	 * @param victoryCard, the victory card associated with the score
	 */
	public int visit(RectangleBoard board, Card victoryCard)
	{
		Shape victoryShape = victoryCard.getShape();
		Color victoryColor = victoryCard.getColor();
		Filling victoryFilling = victoryCard.getFilling();
		int width;
		int height;
		// if the board is Horizontal, we fix the width and the height of the board to 5 and 3.
		// if the board is Vertical, we fix the width and the height of the board to 3 and 5.
		// if the board is not vertical and not horizontal, we fix width and height to 3.
		if (board.isHorizontal())
		{
			width = 5;
			height = 3;
		} else if (board.isVertical())
		{
			width = 3;
			height = 5;
		} else
		{
			width = 3;
			height = 3;
		}

		Map<Coordinates, Card> placedCardsWithNull = new HashMap<>(board.getPlacedCards());
		final int smallestAbscissa = Coordinates.smallestAbscissa(new ArrayList<>(placedCardsWithNull.keySet()));
		final int biggestAbscissa = Coordinates.biggestAbscissa(new ArrayList<>(placedCardsWithNull.keySet()));
		final int biggestOrdinate = Coordinates.biggestOrdinate(new ArrayList<>(placedCardsWithNull.keySet()));
		final int smallestOrdinate = Coordinates.smallestOrdinate(new ArrayList<>(placedCardsWithNull.keySet()));

		for (int i = smallestAbscissa; i <= biggestAbscissa; i++)
		{
			for (int j = biggestOrdinate; j >= smallestOrdinate; j--)
			{
				if (!placedCardsWithNull.containsKey(new Coordinates(i,j)))
				{
					placedCardsWithNull.put(new Coordinates(i,j), null);
				}
			}
		}
		// We recover coordinates of the placed Cards.
		Iterator<Coordinates> iterator = placedCardsWithNull.keySet().iterator();

		Coordinates topLeftCard = iterator.next();


		//We recover the card which is in the top left corner of the rectangle.
		while (iterator.hasNext())
		{
			Coordinates key = iterator.next();
			if (Coordinates.isOneMoreTopLeftThanTwo(key, topLeftCard))
			{
				topLeftCard = key;
			}
		}
		//We browse each row and add the row score in the final score each time, for each card's attribute.
		boolean isARow;
		int final_score = 0;
		for (int i = 0; i < height; i++)
		{
			isARow = true;
			Coordinates nextCard = new Coordinates(topLeftCard.getX(), topLeftCard.getY() - i);
			ArrayList<Card.Color> colorList = colorListOfOneRow(placedCardsWithNull, width, nextCard, isARow);
			final_score += calculateScoreOfColorRow(colorList, width, victoryColor);
			ArrayList<Card.Shape> shapeList = shapeListOfOneRow(placedCardsWithNull, width, nextCard, isARow);
			final_score += calculateScoreOfShapeRow(shapeList, width, victoryShape);
			ArrayList<Card.Filling> fillingList = fillingListOfOneRow(placedCardsWithNull, width, nextCard, isARow);
			final_score += calculateScoreOfFillingRow(fillingList, width, victoryFilling);
		}
		//We browse each column and add the row score in the final score each time, for each card's attribute.
		for (int j = 0; j < width; j++)
		{
			isARow = false;
			Coordinates nextCard = new Coordinates(topLeftCard.getX() + j, topLeftCard.getY());
			ArrayList<Card.Color> colorList = colorListOfOneRow(placedCardsWithNull, height, nextCard, isARow);
			final_score += calculateScoreOfColorRow(colorList, height, victoryColor);
			ArrayList<Card.Shape> shapeList = shapeListOfOneRow(placedCardsWithNull, height, nextCard, isARow);
			final_score += calculateScoreOfShapeRow(shapeList, height, victoryShape);
			ArrayList<Card.Filling> fillingList = fillingListOfOneRow(placedCardsWithNull, height, nextCard, isARow);
			final_score += calculateScoreOfFillingRow(fillingList, height, victoryFilling);
		}
		return final_score;
	}

	/**
	 * This method build a color List from Cards of a row or a column of the board.
	 *
	 * @param map,      the placed Cards' map with their coordinates
	 * @param nb_Box,   the number of boxes to browse
	 * @param nextCard, coordinates of the first Card to put in the list
	 * @param isARow,   which tell if the list is a Row or a Column
	 * @return a color List which match with the cards of the row/column.
	 */
	private ArrayList<Card.Color> colorListOfOneRow(Map<Coordinates, Card> map, int nb_Box, Coordinates nextCard, boolean isARow)
	{
		ArrayList<Card.Color> colorList = new ArrayList<Card.Color>();
		for (int i = 0; i < nb_Box; i++)
		{
			Coordinates coord;
			if (isARow)
			{
				coord = new Coordinates(nextCard.getX() + i, nextCard.getY());
			} else
			{
				coord = new Coordinates(nextCard.getX(), nextCard.getY() - i);
			}
			Card Card = map.get(coord);
			//If there is a hole in the map (with 3 players version), there is no Card in the map for one coordinates.
			//So we have to add a null element in the list to take into account the hole.
			if (Card == null)
			{
				colorList.add(null);
			} else
			{
				colorList.add(i, Card.getColor());
			}
		}
		return colorList;
	}

	/**
	 * This method build a shape List from Cards of a row or a column of the board.
	 *
	 * @param map,      the placed Cards' map with their coordinates
	 * @param nb_Box,   the number of boxes to browse
	 * @param nextCard, coordinates of the first Card to put in the list
	 * @param isARow,   which tell if the list is a Row or a Column
	 * @return a shape List which match with the cards of the row/column.
	 */
	private ArrayList<Card.Shape> shapeListOfOneRow(Map<Coordinates, Card> map, int nb_Box, Coordinates nextCard, boolean isARow)
	{
		ArrayList<Card.Shape> shapeList = new ArrayList<Card.Shape>();
		for (int i = 0; i < nb_Box; i++)
		{
			Coordinates coord;
			if (isARow)
			{
				coord = new Coordinates(nextCard.getX() + i, nextCard.getY());
			} else
			{
				coord = new Coordinates(nextCard.getX(), nextCard.getY() - i);
			}
			Card Card = map.get(coord);
			//If there is a hole in the map (with 3 players version), there is no Card in the map for one coordinates.
			//So we have to add a null element in the list to take into account the hole.
			if (Card == null)
			{
				shapeList.add(null);
			} else
			{
				shapeList.add(i, Card.getShape());
			}
		}
		return shapeList;
	}

	/**
	 * This method build a filling List from Cards of a row or a column of the board.
	 *
	 * @param map,      the placed Cards' map with their coordinates
	 * @param nb_Box,   the number of boxes to browse
	 * @param nextCard, coordinates of the first Card to put in the list
	 * @param isARow,   which tell if the list is a Row or a Column
	 * @return a filling List which match with the cards of the row/column.
	 */
	private ArrayList<Card.Filling> fillingListOfOneRow(Map<Coordinates, Card> map, int nb_Box, Coordinates nextCard, boolean isARow)
	{
		ArrayList<Card.Filling> fillingList = new ArrayList<Card.Filling>();
		for (int i = 0; i < nb_Box; i++)
		{
			Coordinates coord;
			if (isARow)
			{
				coord = new Coordinates(nextCard.getX() + i, nextCard.getY());
			} else
			{
				coord = new Coordinates(nextCard.getX(), nextCard.getY() - i);
			}
			Card Card = map.get(coord);
			//If there is a hole in the map (with 3 players version), there is no Card in the map for one coordinates.
			//So we have to add a null element in the list to take into account the hole.
			if (Card == null)
			{
				fillingList.add(null);
			} else
			{
				fillingList.add(i, Card.getFilling());
			}
		}
		return fillingList;
	}

	/**
	 * This method calculate the score from a color list by comparing each color with the victory card's color
	 *
	 * @param list, the color list to browse
	 * @param MAX,  the number of values to compare
	 * @param c,    the color of the victory card
	 * @return the score of the color list
	 */
	private int calculateScoreOfColorRow(ArrayList<Card.Color> list, int MAX, Card.Color c)
	{

		int list_score = 0;
		int nb_victory_color = 0;
		for (int i = 0; i < MAX; i++)
		{
			if (list.get(i) == c)
			{
				nb_victory_color++;
			} else
			{
				list_score += calculateColorScoreAlignment(nb_victory_color);
				nb_victory_color = 0;
			}
		}
		//If it's the last row's element, we have to count the score if we were in a alignment of the victory shape.
		if (nb_victory_color != 0) list_score += calculateColorScoreAlignment(nb_victory_color);

		return list_score;
	}

	/**
	 * This method calculate the score according to the number of the alignment's length (cf. statement's table score)
	 *
	 * @param nb_victory_color, the number of colors which following each other.
	 * @return the score of the current alignment
	 */
	private int calculateColorScoreAlignment(int nb_victory_color)
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
	 * This method calculate the score from a shape list by comparing each shape with the victory card's shape
	 *
	 * @param list, the shape list to browse
	 * @param MAX,  the number of values to compare
	 * @param s,    the shape of the victory card
	 * @return the score of the shape list
	 */
	private int calculateScoreOfShapeRow(ArrayList<Card.Shape> list, int MAX, Card.Shape s)
	{

		int list_score = 0;
		int nb_victory_shape = 0;
		for (int i = 0; i < MAX; i++)
		{

			if (list.get(i) == s)
			{
				nb_victory_shape++;
			} else
			{

				list_score += calculateShapeScoreAlignment(nb_victory_shape);
				nb_victory_shape = 0;
			}
		}
		//If it's the last row's element, we have to count the score if we were in a alignment of the victory shape.
		if (nb_victory_shape != 0) list_score += calculateShapeScoreAlignment(nb_victory_shape);
		return list_score;
	}

	/**
	 * This method calculate the score according to the number of the alignment's length (cf. statement's table score)
	 *
	 * @param nb_victory_shape, the number of shapes which following each other.
	 * @return the score of the current alignment
	 */
	private int calculateShapeScoreAlignment(int nb_victory_shape)
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
	 * This method calculate the score from a filling list by comparing each filling with the victory card's filling
	 *
	 * @param list, the filling list to browse
	 * @param MAX,  the number of values to compare
	 * @param h,    the filling of the victory card
	 * @return the score of the filling list
	 */
	private int calculateScoreOfFillingRow(ArrayList<Card.Filling> list, int MAX, Card.Filling h)
	{

		int list_score = 0;
		int nb_victory_filling = 0;
		for (int i = 0; i < MAX; i++)
		{

			if (list.get(i) == h)
			{
				nb_victory_filling++;
			} else
			{
				list_score += calculateFillingScoreAlignment(nb_victory_filling);
				nb_victory_filling = 0;
			}
		}
		//If it's the last row's element, we have to count the score if we were in a alignment of the victory filling.
		if (nb_victory_filling != 0) list_score += calculateFillingScoreAlignment(nb_victory_filling);
		return list_score;
	}

	/**
	 * This method calculate the score according to the number of the alignment's length (cf. statement's table score)
	 *
	 * @param nb_victory_filling, the number of filling which following each other.
	 * @return the score of the current alignment
	 */
	private int calculateFillingScoreAlignment(int nb_victory_filling)
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
