package fr.utt.lo02.projet.board.visitor;

import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Card.Shape;
import fr.utt.lo02.projet.game.ShapeUpGame;
import fr.utt.lo02.projet.board.Card.Color;
import fr.utt.lo02.projet.board.Card.Hollow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
		int score=0;
		for (Entry<Coordinates, Card> entry : board.placedCards.entrySet()) { //using map.entrySet() for iteration   
			if((((Card) entry).getShape() == victoryShape)) {
				int x=((Coordinates) entry).getX();
				int y=((Coordinates) entry).getY();
				boolean checkLeft=true;
				boolean checkRight=true;
				boolean checkDown=true;
				boolean checkUp=true;
				int horizontalAlignment=1;
				int verticalAlignment=1;
				while ((checkLeft) && ((Card) entry).isNotCountedHorizontally){
					Coordinates left = new Coordinates (x-1,y);
					if (board.placedCards.containsKey(left))
					{
						Card leftCard = board.placedCards.get(new Coordinates ((x-1), y));
						if ((leftCard.getShape()==victoryShape) && (leftCard.isNotCountedHorizontally)) {
							horizontalAlignment=horizontalAlignment+1;
							leftCard.isNotCountedHorizontally=false;
								left = new Coordinates (x-2,y);
							if (board.placedCards.containsKey(left))
							{
								leftCard = board.placedCards.get(new Coordinates ((x-2), y));
								if ((leftCard.getShape()==victoryShape) && (leftCard.isNotCountedHorizontally)) {
									horizontalAlignment=horizontalAlignment+2;
									leftCard.isNotCountedHorizontally=false;
									left = new Coordinates (x-3,y);
									if (board.placedCards.containsKey(left))
									{
										leftCard = board.placedCards.get(new Coordinates ((x-3), y));
										if ((leftCard.getShape()==victoryShape) && (leftCard.isNotCountedHorizontally)) {
											horizontalAlignment=horizontalAlignment+3;
											leftCard.isNotCountedHorizontally=false;
											left = new Coordinates (x-4,y);
											if (board.placedCards.containsKey(left))
											{
												leftCard = board.placedCards.get(new Coordinates ((x-4), y));
												if ((leftCard.getShape()==victoryShape) && (leftCard.isNotCountedHorizontally)) {
													horizontalAlignment=horizontalAlignment+4;
													leftCard.isNotCountedHorizontally=false;
												} else {
														checkLeft=false;
														break;
													}
											}
										} else {
												checkLeft=false;
												break;
											}
									}
								} else {
									checkLeft=false;
									break;
								}
							}
						} else {
							checkLeft=false;
							break;
						}
					}
				
					
				}
				while ((checkRight) && ((Card) entry).isNotCountedHorizontally) {
					Coordinates right = new Coordinates (x+1,y);
					if (board.placedCards.containsKey(right))
					{
						Card rightCard = board.placedCards.get(new Coordinates ((x+1), y));
						if ((rightCard.getShape()==victoryShape) && (rightCard.isNotCountedHorizontally)) {
							horizontalAlignment=horizontalAlignment+1;
							rightCard.isNotCountedHorizontally=false;
							right = new Coordinates (x+2,y);
							if (board.placedCards.containsKey(right))
							{
								rightCard = board.placedCards.get(new Coordinates ((x+2), y));
								if ((rightCard.getShape()==victoryShape) && (rightCard.isNotCountedHorizontally)) {
									horizontalAlignment=horizontalAlignment+2;
									rightCard.isNotCountedHorizontally=false;
									right = new Coordinates (x+3,y);
									if (board.placedCards.containsKey(right))
									{
										rightCard = board.placedCards.get(new Coordinates ((x+3), y));
										if ((rightCard.getShape()==victoryShape) && (rightCard.isNotCountedHorizontally)) {
											horizontalAlignment=horizontalAlignment+3;
											rightCard.isNotCountedHorizontally=false;
											right = new Coordinates (x+4,y);
												if (board.placedCards.containsKey(right))
												{
												rightCard = board.placedCards.get(new Coordinates ((x+4), y));
												if ((rightCard.getShape()==victoryShape) && (rightCard.isNotCountedHorizontally)) {
													horizontalAlignment=horizontalAlignment+4;
													rightCard.isNotCountedHorizontally=false;
												} else {
														checkRight=false;
														break;
													}
												}
										} else {
												checkRight=false;
												break;
											}
									}
								} else {
									checkRight=false;
									break;
								}
							}
						} else {
							checkRight=false;
							break;
						}
					}
			}
			if (horizontalAlignment==2) {
				score=score+1;
			} else if (horizontalAlignment==3) {
				score=score+2;
			} else if (horizontalAlignment==4) {
				score=score+3;
			} else if (horizontalAlignment==5){
				score=score+4;
			}
			(((Card) entry).isNotCountedHorizontally)=false;
			while ((checkDown) && ((Card) entry).isNotCountedVertically) {
				Coordinates down = new Coordinates (x,(y-1));
				if (board.placedCards.containsKey(down))
				{
					Card downCard = board.placedCards.get(new Coordinates (x, (y-1)));
					if ((downCard.getShape()==victoryShape) && (downCard.isNotCountedVertically))  {
						verticalAlignment=verticalAlignment+1;
						downCard.isNotCountedVertically=false;
						down = new Coordinates (x,(y-2));
						if (board.placedCards.containsKey(down))
						{
							downCard = board.placedCards.get(new Coordinates (x, (y-2)));
							if ((downCard.getShape()==victoryShape) && (downCard.isNotCountedVertically))  {
								verticalAlignment=verticalAlignment+2;
								downCard.isNotCountedVertically=false;
								down = new Coordinates (x,(y-3));
								if (board.placedCards.containsKey(down))
								{
									downCard = board.placedCards.get(new Coordinates (x, (y-3)));
									if ((downCard.getShape()==victoryShape) && (downCard.isNotCountedVertically))  {
										verticalAlignment=verticalAlignment+3;
										downCard.isNotCountedVertically=false;
										down = new Coordinates (x,(y-4));
										if (board.placedCards.containsKey(down))
										{
											downCard = board.placedCards.get(new Coordinates (x, (y-4)));
											if ((downCard.getShape()==victoryShape) && (downCard.isNotCountedVertically))  {
												verticalAlignment=verticalAlignment+4;
												downCard.isNotCountedVertically=false;
											} else {
													checkDown=false;
													break;
												}
									} else {
											checkDown=false;
											break;
										}
								}
							} else {
								checkDown=false;
								break;
							}
						}
					} else {
						checkDown=false;
						break;
					}
				}
			}
			while ((checkUp) && ((Card) entry).isNotCountedVertically) {
				Card upCard = board.placedCards.get(new Coordinates ((x+1), y));
				if ((upCard.getShape()==victoryShape) && (upCard.isNotCountedVertically)) {
					verticalAlignment=verticalAlignment+1;
					upCard.isNotCountedVertically=false;
					upCard = board.placedCards.get(new Coordinates ((x+2), y));
					if ((upCard.getShape()==victoryShape) && (upCard.isNotCountedVertically)) {
						verticalAlignment=verticalAlignment+2;
						upCard.isNotCountedVertically=false;
						upCard = board.placedCards.get(new Coordinates ((x+3), y));
						if ((upCard.getShape()==victoryShape) && (upCard.isNotCountedVertically)) {
							verticalAlignment=verticalAlignment+3;
							upCard.isNotCountedVertically=false;
							upCard = board.placedCards.get(new Coordinates ((x+4), y));
							if ((upCard.getShape()==victoryShape) && (upCard.isNotCountedVertically)) {
								verticalAlignment=verticalAlignment+4;
								upCard.isNotCountedVertically=false;
							} else {
									checkUp=false;
									break;
								}
						} else {
								checkUp=false;
								break;
							}
					} else {
						checkUp=false;
						break;
					}
				} else {
					checkUp=false;
					break;
				}
		}
		if (verticalAlignment==2) {
			score=score+1;
		} else if (verticalAlignment==3) {
			score=score+2;
		} else if (verticalAlignment==4) {
			score=score+3;
		} else if (verticalAlignment==5){
			score=score+4;
		}
		(((Card) entry).isNotCountedVertically)=false;
		}
	}
	}
		return score;
	}
	
	public static void main(String[] args)
	{
		RectangleBoard board;
		
	}
}
