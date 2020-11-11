package fr.utt.lo02.projet.board.visitor;

import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Card.Shape;
import fr.utt.lo02.projet.game.ShapeUpGame;
import fr.utt.lo02.projet.board.Card.Color;
import fr.utt.lo02.projet.board.Card.Hollow;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Collection;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.CircleBoard;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.board.TriangleBoard;

/**
 * Represent one of the different variants to calculate the score for the game.
 * This one is the normal version.
 * It implements IBoard Visitor to follow the visitor's construction.
 * @author} Baptiste, Jacques
 *
 */
public class ScoreCalculatorVisitor implements IBoardVisitor {

	
	public ScoreCalculatorVisitor() { 
	}
	
	public int visit(CircleBoard board, Card victoryCard) {
		int score=1;
		return score;
	}
	
	public int visit(TriangleBoard board, Card victoryCard) {
		int score=1;
		return score;
	}
	
	public int visit(RectangleBoard board, Card victoryCard) {
		Shape victoryShape = victoryCard.getShape();
		Color victoryColor = victoryCard.getColor();
		Hollow victoryHollow = victoryCard.getHollow();
		int final_score=0;
		int width=0;
		int height=0;
		boolean isARow;
		if (board.isHorizontal()) {
			width=5;
			height=3;
		}
		if (board.isVertical()) {
			width=3;
			height=5;
		}
		Set<Coordinates> listKeys = board.getPlacedCards().keySet();
		Iterator<Coordinates> iterator = listKeys.iterator();
		Coordinates topLeftCard = (Coordinates)iterator.next();
		while (iterator.hasNext()) {
			Coordinates key = (Coordinates)iterator.next();
			if (Coordinates.isOneMoreTopLeftThanTwo(key,topLeftCard)) {
				topLeftCard = key;
			}
		}

		for (int i=0; i<height; i++) {
			isARow=true;
			Coordinates nextCard = new Coordinates(topLeftCard.getX(), topLeftCard.getY()-i);
			ArrayList<Card.Color> colorList = constructorColorList(board.getPlacedCards(), width, nextCard, isARow);
			final_score += calculColorScoreList(colorList, width, victoryColor);
			ArrayList<Card.Shape> shapeList = constructorShapeList(board.getPlacedCards(), width, nextCard, isARow);
			final_score += calculShapeScoreList(shapeList, width, victoryShape);
			ArrayList<Card.Hollow> hollowList = constructorHollowList(board.getPlacedCards(), width, nextCard, isARow);
			final_score += calculHollowScoreList(hollowList, width, victoryHollow);
		}
		for (int j=0; j<width; j++) {
			isARow=false;
			Coordinates nextCard = new Coordinates(topLeftCard.getX()+j, topLeftCard.getY());
			ArrayList<Card.Color> colorList = constructorColorList(board.getPlacedCards(), height, nextCard, isARow);
			final_score += calculColorScoreList(colorList, height, victoryColor);
			ArrayList<Card.Shape> shapeList = constructorShapeList(board.getPlacedCards(), height, nextCard, isARow);
			final_score += calculShapeScoreList(shapeList, height, victoryShape);
			ArrayList<Card.Hollow> hollowList = constructorHollowList(board.getPlacedCards(), height, nextCard, isARow);
			final_score += calculHollowScoreList(hollowList, height, victoryHollow);

		}
		return final_score;
	}
	
	private ArrayList<Card.Color> constructorColorList(Map<Coordinates, Card> map, int nb_Box, Coordinates nextCard, boolean isARow) {
		ArrayList<Card.Color> colorList = new ArrayList<Card.Color>();
		for (int i=0; i<nb_Box; i++ ) {
			Coordinates coord;
			if (isARow) {
				coord = new Coordinates(nextCard.getX()+i,nextCard.getY());
			} else {
				coord = new Coordinates(nextCard.getX(),nextCard.getY()-i);
			}
			Card.Color colorCard = map.get(coord).getColor();
			colorList.add(i, colorCard);
		}
		return colorList;
	}
	
	private ArrayList<Card.Shape> constructorShapeList(Map<Coordinates, Card> map, int nb_Box, Coordinates nextCard, boolean isARow) {
		ArrayList<Card.Shape> shapeList = new ArrayList<Card.Shape>();
		for (int i=0; i<nb_Box; i++ ) {
			Coordinates coord;
			if (isARow) {
				coord = new Coordinates(nextCard.getX()+i,nextCard.getY());
			} else {
				coord = new Coordinates(nextCard.getX(),nextCard.getY()-i);
			}
			Card.Shape shapeCard = map.get(coord).getShape();
			shapeList.add(i, shapeCard);
		}
		return shapeList;
	}
	
	private ArrayList<Card.Hollow> constructorHollowList(Map<Coordinates, Card> map, int nb_Box, Coordinates nextCard, boolean isARow) {
		ArrayList<Card.Hollow> hollowList = new ArrayList<Card.Hollow>();
		for (int i=0; i<nb_Box; i++ ) {
			Coordinates coord;
			if (isARow) {
				coord = new Coordinates(nextCard.getX()+i,nextCard.getY());
			} else {
				coord = new Coordinates(nextCard.getX(),nextCard.getY()-i);
			}
			Card.Hollow hollowCard = map.get(coord).getHollow();
			hollowList.add(i, hollowCard);
		}
		return hollowList;
	}
	
	private int calculColorScoreList(ArrayList<Card.Color> list,int MAX, Card.Color c) {
		
		int list_score=0;	
		int nb_victory_color=0;
		for (int i=0; i<MAX; i++) {
			if (list.get(i)==null) {
				continue;
			} else if (list.get(i)==c){
				nb_victory_color++;
			} else {
				list_score += calculColorScoreAlignment(nb_victory_color);
				nb_victory_color=0;
			}
		}	
		return list_score;
	}
	
	private int calculColorScoreAlignment(int nb_victory_color) {
		int alignment_score=0;
		if (nb_victory_color==3) {
			alignment_score=4;
		} else if (nb_victory_color==4) {
			alignment_score=5;
		} else if (nb_victory_color==5) {
			alignment_score=6;
		}
		return alignment_score;
	}
	
	private int calculShapeScoreList(ArrayList<Card.Shape> list,int MAX, Card.Shape s) {
		
		int list_score=0;	
		int nb_victory_shape=0;
		for (int i=0; i<MAX; i++) {
			if (list.get(i)==null) {
				continue;
			} else if (list.get(i)==s){
				nb_victory_shape++;
			} else {
				list_score += calculShapeScoreAlignment(nb_victory_shape);
				nb_victory_shape=0;
			}
		}	
		return list_score;
	}
	
	private int calculShapeScoreAlignment(int nb_victory_shape) {
		int alignment_score=0;
		if (nb_victory_shape==2) {
			alignment_score=1;
		} else if (nb_victory_shape==3) {
			alignment_score=2;
		} else if (nb_victory_shape==4) {
			alignment_score=3;
		} else if (nb_victory_shape==5) {
			alignment_score=4;
		}
		return alignment_score;
	}
	
	private int calculHollowScoreList(ArrayList<Card.Hollow> list,int MAX, Card.Hollow h) {
		
		int list_score=0;	
		int nb_victory_hollow=0;
		for (int i=0; i<MAX; i++) {
			if (list.get(i)==null) {
				continue;
			} else if (list.get(i)==h){
				nb_victory_hollow++;
			} else {
				list_score += calculHollowScoreAlignment(nb_victory_hollow);
				nb_victory_hollow=0;
			}
		}	
		return list_score;
	}
	
	private int calculHollowScoreAlignment(int nb_victory_hollow) {
		int alignment_score=0;
		if (nb_victory_hollow==3) {
			alignment_score=3;
		} else if (nb_victory_hollow==4) {
			alignment_score=4;
		} else if (nb_victory_hollow==5) {
			alignment_score=5;
		}
		return alignment_score;
	}
	
	public static void main(String[] args)
	{
		RectangleBoard board = new RectangleBoard();
		board.getPlacedCards().put(new Coordinates(0,0), new Card(Card.Color.RED,Card.Shape.TRIANGLE,Card.Hollow.HOLLOW));
		board.getPlacedCards().put(new Coordinates(1,0), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Hollow.HOLLOW));
		board.getPlacedCards().put(new Coordinates(2,0), new Card(Card.Color.RED,Card.Shape.SQUARE,Card.Hollow.HOLLOW));
		board.getPlacedCards().put(new Coordinates(3,0), new Card(Card.Color.RED,Card.Shape.SQUARE,Card.Hollow.FILLED));
		board.getPlacedCards().put(new Coordinates(4,0), new Card(Card.Color.BLUE,Card.Shape.TRIANGLE,Card.Hollow.FILLED));
		board.getPlacedCards().put(new Coordinates(0,-1), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Hollow.FILLED));
		board.getPlacedCards().put(new Coordinates(1,-1), new Card(Card.Color.GREEN,Card.Shape.CIRCLE,Card.Hollow.FILLED));
		board.getPlacedCards().put(new Coordinates(2,-1), new Card(Card.Color.GREEN,Card.Shape.SQUARE,Card.Hollow.HOLLOW));
		board.getPlacedCards().put(new Coordinates(3,-1), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Hollow.FILLED));
		board.getPlacedCards().put(new Coordinates(4,-1), new Card(Card.Color.BLUE,Card.Shape.SQUARE,Card.Hollow.FILLED));
		board.getPlacedCards().put(new Coordinates(0,-2), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Hollow.HOLLOW));
		board.getPlacedCards().put(new Coordinates(1,-2), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Hollow.FILLED));
		board.getPlacedCards().put(new Coordinates(2,-2), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Hollow.HOLLOW));
		board.getPlacedCards().put(new Coordinates(3,-2), new Card(Card.Color.BLUE,Card.Shape.SQUARE,Card.Hollow.HOLLOW));
		board.getPlacedCards().put(new Coordinates(4,-2), new Card(Card.Color.GREEN,Card.Shape.SQUARE,Card.Hollow.FILLED));
		Card victoryCard = new Card(Card.Color.RED,Card.Shape.TRIANGLE,Card.Hollow.FILLED);
		ScoreCalculatorVisitor test = new ScoreCalculatorVisitor();
		int delivrance = test.visit(board, victoryCard);
		System.out.println("Le score final est de "+ delivrance);
		
	}
}
