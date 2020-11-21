package fr.utt.lo02.projet.junit;

import static org.junit.jupiter.api.Assertions.*;

import fr.utt.lo02.projet.board.*;
import fr.utt.lo02.projet.strategy.*;
import org.junit.jupiter.api.Test;

class VirtualPlayerTest
{

	@Test
	void place() throws PlayerHandEmptyException
	{

		AbstractBoard board = new RectangleBoard();
		board.getPlacedCards().put(new Coordinates(0, 0), new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(1, 0), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(2, 0), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(3, 0), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(4, 0), new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(0, -1), new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1, -1), new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(2, -1), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(3, -1), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(4, -1), new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(0, -2), new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(1, -2), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(2, -2), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
		Card victoryCard = new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.HOLLOW);
		Card handCard = new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.FILLED);

		VirtualPlayer joueur1 = new VirtualPlayer("ord1",board);
		joueur1.drawCard(handCard);
		joueur1.addRoundScore(15);
		joueur1.addRoundScore(25);
		joueur1.setVictoryCard(victoryCard);

		System.out.println(joueur1.askChoice(ChoiceOrder.FIRST_CHOICE));
		PlaceRequest request = new PlaceRequest(joueur1.askPlaceCard().getCoordinates(), joueur1.askPlaceCard().getCard());
		System.out.println("Player 1 wants to place " + request.getCard() + " to : " + request.getCoordinates());
		joueur1.displayRoundScore();
		joueur1.displayFinalScore();
		assertNotNull(request);
	}

	@Test
	void move() throws boardEmptyException
	{

		AbstractBoard board = new RectangleBoard();
		board.getPlacedCards().put(new Coordinates(0, 0), new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(1, 0), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(2, 0), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(3, 0), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(4, 0), new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(0, -1), new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1, -1), new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(2, -1), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(3, -1), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(4, -1), new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(0, -2), new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(1, -2), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(2, -2), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.HOLLOW));
		Card victoryCard = new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.HOLLOW);
		Card handCard = new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.FILLED);
		VirtualPlayer joueur1 = new VirtualPlayer("ord1",board);
		joueur1.drawCard(handCard);
		joueur1.drawCard(handCard);
		joueur1.drawCard(handCard);
		joueur1.setVictoryCard(victoryCard);
		joueur1.addRoundScore(15);
		joueur1.addRoundScore(25);

		System.out.println(joueur1.askChoice(ChoiceOrder.FIRST_CHOICE));
		MoveRequest request2 = new MoveRequest(joueur1.askMoveCard().getOrigin(), joueur1.askMoveCard().getDestination());
		System.out.println("Player 1 wants to move card from " + request2.getOrigin() + " to : " + request2.getDestination());
		joueur1.displayRoundScore();
		joueur1.displayFinalScore();
		assertNotNull(request2);
	}

}
