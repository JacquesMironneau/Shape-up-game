package fr.utt.lo02.projet.model.strategy;

import fr.utt.lo02.projet.model.board.AbstractBoard;
import fr.utt.lo02.projet.model.board.Card;
import fr.utt.lo02.projet.model.board.Coordinates;
import fr.utt.lo02.projet.model.board.visitor.IBoardVisitor;
import fr.utt.lo02.projet.model.player.Choice;
import fr.utt.lo02.projet.model.player.MoveRequest;
import fr.utt.lo02.projet.model.player.PlaceRequest;

import java.util.ArrayList;
import java.util.List;

public class DifficultStrategy implements PlayerStrategy {
	
	private final IBoardVisitor visitor;
	Choice choice = null;
	Card victoryCard = null;
	
	public DifficultStrategy(IBoardVisitor v) {
		visitor = v; 
	}


	@Override
	public Choice makeChoice(AbstractBoard board, Card vC, List<Card> playerHand)
	{
		AbstractBoard testBoard = null;
        try
        {
        	testBoard = (AbstractBoard) board.clone();
        } catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
		if (testBoard.getPlacedCards().isEmpty()||testBoard.getPlacedCards().size()==1) {
			return Choice.PLACE_A_CARD;
		}
		if (choice==Choice.PLACE_A_CARD) {
			choice=null;
			return Choice.PLACE_A_CARD;
		} else if (choice==Choice.MOVE_A_CARD) {
			choice=null;
			return Choice.MOVE_A_CARD;
		} else if (choice==Choice.END_THE_TURN) {
			choice=null;
			return Choice.END_THE_TURN;
		}
		victoryCard = vC;
		int scorePlaceThenMove;
		int scoreMoveThenPlace;
		int scoreJustPlace;
		PlaceRequest bestPlaceCoord;
		MoveRequest bestMoveCoords;
		Card bestMoveCard;	
		
		// JUST PLACE
		bestPlaceCoord = this.makePlaceRequest(testBoard, victoryCard, playerHand);
		testBoard.getPlacedCards().put(bestPlaceCoord.getCoordinates(), bestPlaceCoord.getCard());	
		scoreJustPlace = testBoard.accept(visitor, victoryCard);
		
		// PLACE THEN MOVE
		bestMoveCoords = this.makeMoveRequest(testBoard, victoryCard, playerHand);
		bestMoveCard = testBoard.getPlacedCards().get(bestMoveCoords.getOrigin());
		testBoard.getPlacedCards().remove(bestMoveCoords.getOrigin(), bestMoveCard);
		testBoard.getPlacedCards().put(bestMoveCoords.getDestination(), bestMoveCard);	
		scorePlaceThenMove = testBoard.accept(visitor, victoryCard);
		
		AbstractBoard testBoard2 = null;
        try
        {
        	testBoard2 = (AbstractBoard) board.clone();
        } catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
		
		//MOVE THEN PLACE
		bestMoveCoords = this.makeMoveRequest(testBoard2, victoryCard, playerHand);
		bestMoveCard = testBoard2.getPlacedCards().get(bestMoveCoords.getOrigin());
		testBoard2.getPlacedCards().remove(bestMoveCoords.getOrigin(), bestMoveCard);
		testBoard2.getPlacedCards().put(bestMoveCoords.getDestination(), bestMoveCard);
		bestPlaceCoord = this.makePlaceRequest(testBoard2, victoryCard, playerHand);
		testBoard2.getPlacedCards().put(bestPlaceCoord.getCoordinates(), bestPlaceCoord.getCard());
		scoreMoveThenPlace = testBoard2.accept(visitor, victoryCard);
		
		if (scoreJustPlace >= scorePlaceThenMove && scoreJustPlace >= scoreMoveThenPlace) {
			//Just Place
			choice=Choice.END_THE_TURN;
			return Choice.PLACE_A_CARD;
		} else if (scorePlaceThenMove >= scoreJustPlace && scorePlaceThenMove >= scoreMoveThenPlace) {
			//Place then Move
			choice=Choice.MOVE_A_CARD;
			return Choice.PLACE_A_CARD;
		} else {
			choice=Choice.PLACE_A_CARD;
			return Choice.MOVE_A_CARD;
			//Move then Place
		} 
	}

	@Override
	public PlaceRequest makePlaceRequest(AbstractBoard board, Card vC, List<Card> playerHand) {
		AbstractBoard testBoard = null;
        try
        {
        	testBoard = (AbstractBoard) board.clone();
        } catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
		if (testBoard.getPlacedCards().isEmpty()) {
			
			return new PlaceRequest(new Coordinates(0,0), playerHand.get(0));
		}
		
		victoryCard = vC;
		int score;
		Card cardToPlace = null;
		Coordinates bestCoord = new Coordinates(0,0);
		List<Coordinates> goodRequests = new ArrayList<>();
		List<Coordinates> coordsMap = new ArrayList<>(testBoard.getPlacedCards().keySet());
		
		int testX=Coordinates.smallestAbscissa(coordsMap)-1;
		int testY=Coordinates.smallestOrdinate(coordsMap)-1;
		Coordinates testCoord = new Coordinates(testX, testY);
		
		while (testY <= Coordinates.biggestOrdinate(coordsMap) + 1) {
			testCoord.setY(testY);
			testX=Coordinates.smallestAbscissa(coordsMap)-1;
			while (testX <= Coordinates.biggestAbscissa(coordsMap) + 1) {
				testCoord.setX(testX);
				if (!testBoard.getPlacedCards().containsKey(testCoord)) {
					if (testBoard.isCardAdjacent(testCoord) && testBoard.isCardInTheLayout(testCoord)) {
						goodRequests.add(new Coordinates(testCoord.getX(), testCoord.getY()));
					}
				}
				testX+=1;
			}
			testY+=1;
		}
		
		if (victoryCard==null&&playerHand.size()>1) {
			for (int i=0; i<playerHand.size(); i++) {
				victoryCard = playerHand.get(i);
				int bestScore = testBoard.accept(visitor, victoryCard);
				playerHand.remove(i);
				
				for (int p=0; p<playerHand.size(); p++) {
					for (int j=0; j<goodRequests.size(); j++) {
						testBoard.getPlacedCards().put(goodRequests.get(j), playerHand.get(p));
						score = testBoard.accept(visitor, victoryCard);
						testBoard.getPlacedCards().remove(goodRequests.get(j), playerHand.get(p));
					
						if (score >= bestScore) {
							bestScore = score;
							bestCoord = goodRequests.get(j);
							cardToPlace = playerHand.get(p);
						}
					}
				}
				playerHand.add(i, victoryCard);
			}
		} else if (playerHand.size()==1) {
			int bestScore = testBoard.accept(visitor, victoryCard);
			cardToPlace = playerHand.get(0);
			for (int i=0; i<goodRequests.size(); i++) {
				testBoard.getPlacedCards().put(goodRequests.get(i), cardToPlace);
				score = testBoard.accept(visitor, victoryCard);
				testBoard.getPlacedCards().remove(goodRequests.get(i), cardToPlace);
				if (score >= bestScore) {
					bestScore = score;
					bestCoord = goodRequests.get(i);
				}
			}
		}		
		return new PlaceRequest(bestCoord, cardToPlace);
	}

	
	@Override
	public MoveRequest makeMoveRequest(AbstractBoard board, Card vC, List<Card> playerHand)
	{
		AbstractBoard testBoard = null;
        try
        {
        	testBoard = (AbstractBoard) board.clone();
        } catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
		victoryCard=vC;
		int score; 
		Coordinates bestCoordToMove = new Coordinates(0,0);
		Coordinates bestNewCoord = new Coordinates(0,0);
		List<Coordinates> goodRequests = new ArrayList<>();
		List<Coordinates> coordsMap = new ArrayList<>(testBoard.getPlacedCards().keySet());
		int testX=Coordinates.smallestAbscissa(coordsMap)-1;
		int testY=Coordinates.smallestOrdinate(coordsMap)-1;
		Coordinates testCoord = new Coordinates(testX, testY);
		while (testY <= Coordinates.biggestOrdinate(coordsMap) + 1) {
			testCoord.setY(testY);
			testX=Coordinates.smallestAbscissa(coordsMap)-1;
			while (testX <= Coordinates.biggestAbscissa(coordsMap) + 1) {
				testCoord.setX(testX);
				if (!testBoard.getPlacedCards().containsKey(testCoord)) {
					if (testBoard.isCardAdjacent(testCoord) && testBoard.isCardInTheLayout(testCoord)) {
						goodRequests.add(new Coordinates(testCoord.getX(), testCoord.getY()));
					}
				}
				testX+=1;
			}
			testY+=1;
		}
		
		if (victoryCard==null) {
			for (int p=0; p<playerHand.size(); p++) {
				victoryCard=playerHand.get(p);
				int bestScore = testBoard.accept(visitor, victoryCard);
				for (int i=0; i<coordsMap.size(); i++) {
					for (int j=0; j<goodRequests.size(); j++) {
						Card cardMoving = testBoard.getPlacedCards().get(coordsMap.get(i));
						testBoard.getPlacedCards().remove(coordsMap.get(i), cardMoving);
						if (testBoard.isCardAdjacent(goodRequests.get(j)) && testBoard.isCardInTheLayout(goodRequests.get(j))) {
							testBoard.getPlacedCards().put(goodRequests.get(j), cardMoving);
							score = testBoard.accept(visitor, victoryCard);
							if (score >= bestScore) {
								bestScore = score;
								bestCoordToMove = coordsMap.get(i);
								bestNewCoord = goodRequests.get(j);
							}
							testBoard.getPlacedCards().remove(goodRequests.get(j), cardMoving);
						}
						testBoard.getPlacedCards().put(coordsMap.get(i), cardMoving);	
					}
				}		
			}
		} else {
			int bestScore = testBoard.accept(visitor, victoryCard);
			for (int i=0; i<coordsMap.size(); i++) {
				for (int j=0; j<goodRequests.size(); j++) {
					Card cardMoving = testBoard.getPlacedCards().get(coordsMap.get(i));
					testBoard.getPlacedCards().remove(coordsMap.get(i), cardMoving);
					if (testBoard.isCardAdjacent(goodRequests.get(j)) && testBoard.isCardInTheLayout(goodRequests.get(j))) {
						testBoard.getPlacedCards().put(goodRequests.get(j), cardMoving);
						score = testBoard.accept(visitor, victoryCard);
						if (score >= bestScore) {
							bestScore = score;
							bestCoordToMove = coordsMap.get(i);
							bestNewCoord = goodRequests.get(j);
						}
						testBoard.getPlacedCards().remove(goodRequests.get(j), cardMoving);
					}
					testBoard.getPlacedCards().put(coordsMap.get(i), cardMoving);	
				}
			}
		}
		return new MoveRequest(bestCoordToMove, bestNewCoord);
	}

}
