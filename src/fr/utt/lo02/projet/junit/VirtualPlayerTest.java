package fr.utt.lo02.projet.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.strategy.MoveRequest;
import fr.utt.lo02.projet.strategy.PlaceRequest;
import fr.utt.lo02.projet.strategy.VirtualPlayer;

class VirtualPlayerTest {

	@Test
	void place() {
		
				AbstractBoard board = new RectangleBoard();
				board.getPlacedCards().put(new Coordinates(0,0), new Card(Card.Color.RED,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
				board.getPlacedCards().put(new Coordinates(1,0), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
				board.getPlacedCards().put(new Coordinates(2,0), new Card(Card.Color.RED,Card.Shape.SQUARE,Card.Filling.HOLLOW));
				board.getPlacedCards().put(new Coordinates(3,0), new Card(Card.Color.RED,Card.Shape.SQUARE,Card.Filling.FILLED));
				board.getPlacedCards().put(new Coordinates(4,0), new Card(Card.Color.BLUE,Card.Shape.TRIANGLE,Card.Filling.FILLED));
				board.getPlacedCards().put(new Coordinates(0,-1), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.FILLED));
				board.getPlacedCards().put(new Coordinates(1,-1), new Card(Card.Color.GREEN,Card.Shape.CIRCLE,Card.Filling.FILLED));
				board.getPlacedCards().put(new Coordinates(2,-1), new Card(Card.Color.GREEN,Card.Shape.SQUARE,Card.Filling.HOLLOW));
				board.getPlacedCards().put(new Coordinates(3,-1), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Filling.FILLED));
				board.getPlacedCards().put(new Coordinates(4,-1), new Card(Card.Color.BLUE,Card.Shape.SQUARE,Card.Filling.FILLED));
				board.getPlacedCards().put(new Coordinates(0,-2), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
				board.getPlacedCards().put(new Coordinates(1,-2), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.FILLED));
				board.getPlacedCards().put(new Coordinates(2,-2), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
				Card victoryCard = new Card(Card.Color.GREEN,Card.Shape.CIRCLE,Card.Filling.HOLLOW);
				Card handCard = new Card(Card.Color.RED,Card.Shape.TRIANGLE,Card.Filling.FILLED);
				List<Card> playerHand = new ArrayList<Card>();
				playerHand.add(handCard);
				List<Integer> scoresRound = new ArrayList<Integer>();
				scoresRound.add(15);
				scoresRound.add(25);
				VirtualPlayer joueur1 = new VirtualPlayer(board, victoryCard, scoresRound, playerHand );
				System.out.println(joueur1.askChoice());
				PlaceRequest request = new PlaceRequest(joueur1.askPlaceCard().getCoordinates(), joueur1.askPlaceCard().getCard());
				System.out.println("Player 1 wants to place " + request.getCard() + " to : " + request.getCoordinates());
				joueur1.displayRoundScore(2);
				joueur1.displayFinalScoreForThisRound(2, 1);
				joueur1.displayFinalScore(1);
				assertNotNull(request);
			}
	
	@Test
	void move() {
		
				AbstractBoard board = new RectangleBoard();
				board.getPlacedCards().put(new Coordinates(0,0), new Card(Card.Color.RED,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
				board.getPlacedCards().put(new Coordinates(1,0), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
				board.getPlacedCards().put(new Coordinates(2,0), new Card(Card.Color.RED,Card.Shape.SQUARE,Card.Filling.HOLLOW));
				board.getPlacedCards().put(new Coordinates(3,0), new Card(Card.Color.RED,Card.Shape.SQUARE,Card.Filling.FILLED));
				board.getPlacedCards().put(new Coordinates(4,0), new Card(Card.Color.BLUE,Card.Shape.TRIANGLE,Card.Filling.FILLED));
				board.getPlacedCards().put(new Coordinates(0,-1), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.FILLED));
				board.getPlacedCards().put(new Coordinates(1,-1), new Card(Card.Color.GREEN,Card.Shape.CIRCLE,Card.Filling.FILLED));
				board.getPlacedCards().put(new Coordinates(2,-1), new Card(Card.Color.GREEN,Card.Shape.SQUARE,Card.Filling.HOLLOW));
				board.getPlacedCards().put(new Coordinates(3,-1), new Card(Card.Color.RED,Card.Shape.CIRCLE,Card.Filling.FILLED));
				board.getPlacedCards().put(new Coordinates(4,-1), new Card(Card.Color.BLUE,Card.Shape.SQUARE,Card.Filling.FILLED));
				board.getPlacedCards().put(new Coordinates(0,-2), new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.HOLLOW));
				board.getPlacedCards().put(new Coordinates(1,-2), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.FILLED));
				board.getPlacedCards().put(new Coordinates(2,-2), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
				Card victoryCard = new Card(Card.Color.GREEN,Card.Shape.CIRCLE,Card.Filling.HOLLOW);
				Card handCard = new Card(Card.Color.RED,Card.Shape.TRIANGLE,Card.Filling.FILLED);
				List<Card> playerHand = new ArrayList<Card>();
				playerHand.add(handCard);
				playerHand.add(handCard);
				playerHand.add(handCard);
				List<Integer> scoresRound = new ArrayList<Integer>();
				scoresRound.add(15);
				scoresRound.add(25);
				VirtualPlayer joueur1 = new VirtualPlayer(board, victoryCard, scoresRound, playerHand );
				System.out.println(joueur1.askChoice());
				MoveRequest request2 = new MoveRequest(joueur1.askMoveCard().getOrigin(), joueur1.askMoveCard().getDestination());
				System.out.println("Player 1 wants to move card from " + request2.getOrigin() + " to : " + request2.getDestination());
				joueur1.displayRoundScore(2);
				joueur1.displayFinalScoreForThisRound(2, 1);
				joueur1.displayFinalScore(1);
				assertNotNull(request2);
			}

}
