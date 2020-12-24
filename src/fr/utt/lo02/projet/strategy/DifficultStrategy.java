package fr.utt.lo02.projet.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.visitor.IBoardVisitor;

public class DifficultStrategy implements PlayerStrategy {
	
	private final IBoardVisitor visitor;
	Choice choice = null;
	
	public DifficultStrategy(IBoardVisitor v) {
		visitor = v;
	}


	@Override
	public Choice makeChoice(AbstractBoard board, Card victoryCard, List<Card> playerHand)
	{
		if (board.getPlacedCards().isEmpty()) {
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
		
		int scorePlaceThenMove;
		int scoreMoveThenPlace;
		int scoreJustPlace;
		PlaceRequest bestPlaceCoord;
		MoveRequest bestMoveCoords;
		Card bestMoveCard;	
		
		if (victoryCard==null) {
			for (int i=0; i<playerHand.size(); i++) {
				
			}
			
		}
		bestPlaceCoord = this.makePlaceRequest(board, victoryCard, playerHand);
		board.getPlacedCards().put(bestPlaceCoord.getCoordinates(), bestPlaceCoord.getCard());	
		scoreJustPlace = board.accept(visitor, victoryCard);
		bestMoveCoords = this.makeMoveRequest(board, victoryCard);
		bestMoveCard = board.getPlacedCards().get(bestMoveCoords.getOrigin());
		board.getPlacedCards().remove(bestMoveCoords.getOrigin(), bestMoveCard);
		board.getPlacedCards().put(bestMoveCoords.getDestination(), bestMoveCard);	
		scorePlaceThenMove = board.accept(visitor, victoryCard);
		board.getPlacedCards().put(bestMoveCoords.getOrigin(), bestMoveCard);
		board.getPlacedCards().remove(bestMoveCoords.getDestination(), bestMoveCard);
		board.getPlacedCards().remove(bestPlaceCoord.getCoordinates(), bestPlaceCoord.getCard());
		
		
		bestMoveCoords = this.makeMoveRequest(board, victoryCard);
		bestMoveCard = board.getPlacedCards().get(bestMoveCoords.getOrigin());
		board.getPlacedCards().remove(bestMoveCoords.getOrigin(), bestMoveCard);
		board.getPlacedCards().put(bestMoveCoords.getDestination(), bestMoveCard);
		bestPlaceCoord = this.makePlaceRequest(board, victoryCard, playerHand);
		board.getPlacedCards().put(bestPlaceCoord.getCoordinates(), bestPlaceCoord.getCard());
		scoreMoveThenPlace = board.accept(visitor, victoryCard);
		board.getPlacedCards().remove(bestMoveCoords.getDestination(), bestMoveCard);
		board.getPlacedCards().put(bestMoveCoords.getOrigin(), bestMoveCard);
		board.getPlacedCards().remove(bestPlaceCoord.getCoordinates(), bestPlaceCoord.getCard());
		
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
	public PlaceRequest makePlaceRequest(AbstractBoard board, Card victoryCard, List<Card> playerHand) {
		if (board.getPlacedCards().isEmpty()) {
			
			return new PlaceRequest(new Coordinates(0,0), playerHand.get(0));
		}
		int bestScore = board.accept(visitor, victoryCard);
		int score;
		Card cardToPlace = null;
		Coordinates bestCoord = new Coordinates(0,0);
		List<Coordinates> goodRequests = new ArrayList<Coordinates>();
		List<Coordinates> coordsMap = new ArrayList<Coordinates>(board.getPlacedCards().keySet());
		
		int testX=Coordinates.smallestAbscissa(coordsMap)-1;
		int testY=Coordinates.smallestOrdinate(coordsMap)-1;
		Coordinates testCoord = new Coordinates(testX, testY);
		
		while (testY <= Coordinates.biggestOrdinate(coordsMap) + 1) {
			testCoord.setY(testY);
			testX=Coordinates.smallestAbscissa(coordsMap)-1;
			while (testX <= Coordinates.biggestAbscissa(coordsMap) + 1) {
				testCoord.setX(testX);
				if (!board.getPlacedCards().containsKey(testCoord)) {
					if (board.isCardAdjacent(testCoord) && board.isCardInTheLayout(testCoord)) {
						goodRequests.add(new Coordinates(testCoord.getX(), testCoord.getY()));
					}
				}
				testX+=1;
			}
			testY+=1;
		}
		if (playerHand.size() == 1)
		{
			cardToPlace = playerHand.get(0);
			for (int i=0; i<goodRequests.size()-1; i++) {
				board.getPlacedCards().put(goodRequests.get(i), cardToPlace);
				score = board.accept(visitor, victoryCard);
				board.getPlacedCards().remove(goodRequests.get(i), cardToPlace);
				if (score >= bestScore) {
					bestScore = score;
					bestCoord = goodRequests.get(i);
				}
			}
		} else
		{
			Iterator<Card> it = playerHand.iterator();
			while (it.hasNext()) {
				
				for (int i=0; i<goodRequests.size(); i++) {
					board.getPlacedCards().put(goodRequests.get(i), it.next());
					score = board.accept(visitor, victoryCard);
					board.getPlacedCards().remove(goodRequests.get(i), it.next());
				
					if (score >= bestScore) {
						bestScore = score;
						bestCoord = goodRequests.get(i);
						cardToPlace = it.next();
					}
				}
			}
		}
		return new PlaceRequest(bestCoord, cardToPlace);
	}

	
	@Override
	public MoveRequest makeMoveRequest(AbstractBoard board, Card victoryCard)
	{
		
		int bestScore = board.accept(visitor, victoryCard);
		int score;
		Coordinates bestCoordToMove = new Coordinates(0,0);
		Coordinates bestNewCoord = new Coordinates(0,0);
		List<Coordinates> goodRequests = new ArrayList<Coordinates>();
		List<Coordinates> coordsMap = new ArrayList<Coordinates>(board.getPlacedCards().keySet());
		int testX=Coordinates.smallestAbscissa(coordsMap)-1;
		int testY=Coordinates.smallestOrdinate(coordsMap)-1;
		Coordinates testCoord = new Coordinates(testX, testY);
		
		while (testY <= Coordinates.biggestOrdinate(coordsMap) + 1) {
			testCoord.setY(testY);
			testX=Coordinates.smallestAbscissa(coordsMap)-1;
			while (testX <= Coordinates.biggestAbscissa(coordsMap) + 1) {
				testCoord.setX(testX);
				if (!board.getPlacedCards().containsKey(testCoord)) {
					if (board.isCardAdjacent(testCoord) && board.isCardInTheLayout(testCoord)) {
						goodRequests.add(new Coordinates(testCoord.getX(), testCoord.getY()));
					}
				}
				testX+=1;
			}
			testY+=1;
		}
		for (int i=0; i<coordsMap.size(); i++) {
			for (int j=0; j<goodRequests.size(); j++) {
				Card cardMoving = board.getPlacedCards().get(coordsMap.get(i));
				board.getPlacedCards().remove(coordsMap.get(i), cardMoving);
				if (board.isCardAdjacent(goodRequests.get(j)) && board.isCardInTheLayout(goodRequests.get(j))) {
					board.getPlacedCards().put(goodRequests.get(j), cardMoving);
					score = board.accept(visitor, victoryCard);
					if (score >= bestScore) {
						bestScore = score;
						bestCoordToMove = coordsMap.get(i);
						bestNewCoord = goodRequests.get(j);
					}
					board.getPlacedCards().remove(goodRequests.get(j), cardMoving);
				}
				board.getPlacedCards().put(coordsMap.get(i), cardMoving);	
			}
		}		
		return new MoveRequest(bestCoordToMove, bestNewCoord);
	}

}
