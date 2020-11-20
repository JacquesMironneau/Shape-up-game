package fr.utt.lo02.projet.junit;

import fr.utt.lo02.projet.board.AbstractBoard;
import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;
import fr.utt.lo02.projet.board.RectangleBoard;
import fr.utt.lo02.projet.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.game.AbstractShapeUpGame;
import fr.utt.lo02.projet.game.ShapeUpGame;
import fr.utt.lo02.projet.strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

class ShapeUpGameTest
{

	private AbstractShapeUpGame game;

	private ScoreCalculatorVisitor v;

	private AbstractBoard board;


	private List<PlayerStrategy> list;
	@Mock
	private VirtualPlayer playerVirtual1;

	@Mock
	private VirtualPlayer playerVirtual2;



	@BeforeEach
	void setUp()
	{
		v = Mockito.mock(ScoreCalculatorVisitor.class);

		list = new ArrayList<>();
		board = new RectangleBoard();

		playerVirtual1 = new VirtualPlayer(board);
		playerVirtual2 = new VirtualPlayer(board);

		playerVirtual1 = spy(playerVirtual1);
//		playerVirtual1 = Mockito.mock(VirtualPlayer.class);
		playerVirtual2 = spy(playerVirtual2);
		list.add(playerVirtual1);
		list.add(playerVirtual2);

		game = new ShapeUpGame(v, list, board);

		Card c= new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.HOLLOW);


		try
		{
			Method method = ShapeUpGame.class.getDeclaredMethod("initRound");
			Method method2 = AbstractShapeUpGame.class.getDeclaredMethod("initDeck");
			method.setAccessible(true);
			method2.setAccessible(true);

			method.invoke(game);
			method2.invoke(game);


		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
		{
			e.printStackTrace();
		}

	}

	@Test
	void DrawCardTest()
	{

		game.drawCard(playerVirtual1);
		game.drawCard(playerVirtual2);

		assertEquals(1,playerVirtual1.getPlayerHand().size());
		assertEquals(1,playerVirtual2.getPlayerHand().size());

		game.drawCard(playerVirtual1);
		assertEquals(2,playerVirtual1.getPlayerHand().size());

	}

	@RepeatedTest(100)
	void TestFirstTurnForEachPlayerPlaceCard()
	{
		//List<Set<Card>> cards = game.getPlayerCards();

		game.drawCard(playerVirtual1);
		game.drawCard(playerVirtual2);

		assertEquals(1, playerVirtual1.getPlayerHand().size());
		assertEquals(1, playerVirtual2.getPlayerHand().size());

		final int sizeFirstPlayerHand = playerVirtual1.getPlayerHand().size();
		final int sizeSecondPlayerHand = playerVirtual2.getPlayerHand().size();

		Card ca = null;
		Card ca2 = null;
		//Get a random card from player hand
		for (Card c: playerVirtual1.getPlayerHand())
		{
			ca = c;
		}
		for (Card c: playerVirtual2.getPlayerHand())
		{
			ca2 = c;
		}


		Card card = new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.HOLLOW);
		Card card2 = new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.HOLLOW);
		if (ca.getFilling() == Card.Filling.HOLLOW)
		{
			card = new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED);
		}

		if (ca2.getFilling() == Card.Filling.HOLLOW)
		{
			card2 = new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED);
		}
		int chosenX =new Random().nextInt(2000) - 1000;
		int chosenY = new Random().nextInt(2000) - 1000;

		// A card from the other player hand can't be placed
		assertFalse(game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX, chosenY),  ca), playerVirtual2));

		// A card not possessed by the player can't be placed
		assertFalse(game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX, chosenY),  card), playerVirtual1));
		// This card is possessed and then should be placed at any coordinate in the first turn (no adjacency check)
		assertTrue(game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX, chosenY), ca), playerVirtual1));
		// The player hand is now free from one card (the placed one)
		assertEquals(sizeFirstPlayerHand-1, playerVirtual1.getPlayerHand().size());

		try
		{
			Field f = AbstractShapeUpGame.class.getDeclaredField("isFirstTurn");
			f.setAccessible(true);
			f.set(game, false);
		} catch (NoSuchFieldException | IllegalAccessException e)
		{
			e.printStackTrace();
		}

		// The second player can't place a card on the right+2 of the first player card
		assertFalse(game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX+2, chosenY), ca2), playerVirtual2));

		// The second player can't place a card that he doesn't own
		assertFalse(game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX+2, chosenY), card), playerVirtual2));

		// The second player can place a card on the right of the first player card
		assertTrue(game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX+1, chosenY), ca2), playerVirtual2));

	}

	@RepeatedTest(20)
	void TestPlaceCardDuringGame()
	{
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

		// The player has drawn a card
		Card playerOneCard = new Card(Card.Color.GREEN,Card.Shape.TRIANGLE,Card.Filling.HOLLOW);
		Card playerTwoCard = new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.FILLED);

		// Fake card draw
		playerVirtual1.drawCard(playerOneCard);
		playerVirtual2.drawCard(playerTwoCard);

		final int bound = 5;
		int randomX = new Random().nextInt(bound);
		int randomY = new Random().nextInt(bound)+2;
		System.out.println(randomX);
		assertFalse(game.placeCardRequest(new PlaceRequest(new Coordinates(randomX, randomY), playerOneCard), playerVirtual1));

		assertTrue(game.placeCardRequest(new PlaceRequest(new Coordinates(randomX, -2),playerOneCard), playerVirtual1));


		assertFalse(game.placeCardRequest(new PlaceRequest(new Coordinates(randomX, -2), playerTwoCard), playerVirtual2));
		assertTrue(game.placeCardRequest(new PlaceRequest(new Coordinates((randomX + 1 )% (bound - 1), -2),playerTwoCard), playerVirtual2));

		assertFalse(game.placeCardRequest(new PlaceRequest(new Coordinates(randomX, randomY), playerTwoCard), playerVirtual2));

		// remaining cards
//		board.getPlacedCards().put(new Coordinates(2,-2), new Card(Card.Color.BLUE,Card.Shape.CIRCLE,Card.Filling.HOLLOW));
//		board.getPlacedCards().put(new Coordinates(3,-2), new Card(Card.Color.BLUE,Card.Shape.SQUARE,Card.Filling.HOLLOW));
//		board.getPlacedCards().put(new Coordinates(4,-2), new Card(Card.Color.GREEN,Card.Shape.SQUARE,Card.Filling.FILLED));
	}

	@Test
	void TestMoveCardDuringGame()
	{
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


		// Non existent card && non full destination
		assertFalse(game.moveCardRequest(new MoveRequest(new Coordinates(-1,-1), new Coordinates(1,0))));
		// Non existent card && free destination
		assertFalse(game.moveCardRequest(new MoveRequest(new Coordinates(-1,-1), new Coordinates(1,-2))));

		// Non existent card && dest out of the layout
		assertFalse(game.moveCardRequest(new MoveRequest(new Coordinates(-50,-50), new Coordinates(-51,-51))));

		// Existent card , full dest
		assertFalse(game.moveCardRequest(new MoveRequest(new Coordinates(0,0), new Coordinates(1,0))));

		//Existent card, dest out of the layout
		assertFalse(game.moveCardRequest(new MoveRequest(new Coordinates(0,0), new Coordinates(-3,-3))));

		assertEquals(board.getPlacedCards().get(new Coordinates(0, 0)), new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));

		//Correct move
		assertTrue(game.moveCardRequest(new MoveRequest(new Coordinates(0,0), new Coordinates(1,-2))));
		// here the card (0,0) has been moved to (1,-2)
		// The coordinate 0,0 is now free
		assertFalse(board.getPlacedCards().containsKey(new Coordinates(0,0)));
		// The coordinates (1,-2) are now occupied
		assertTrue(board.getPlacedCards().containsKey(new Coordinates(1,-2)));

		//And at (1,-2) a card is existing
		assertEquals(new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW), board.getPlacedCards().get(new Coordinates(1, -2)));
	}

	// Hypothetical case
	//@Test
	void TestLastTurnAfterSecondPlayerPlays()
	{
		// TODO

	}

	// Common case for 2 player
	//@Test
	void TestLastTurnFirstPlayerEndIt()
	{
		// TODO


	}

	// TODO
	void TestCaseFirstTurn()
	{
		Choice c;
		when(playerVirtual1.askChoice()).thenReturn(Choice.END_THE_TURN);
		c = playerVirtual1.askChoice();
		assertSame(c, Choice.END_THE_TURN);


		when(playerVirtual1.askPlaceCard())
				.thenReturn(new PlaceRequest(new Coordinates(0, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED)))
				.thenReturn(new PlaceRequest(new Coordinates(1, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED)));
		when(playerVirtual2.askChoice()).thenReturn(Choice.PLACE_A_CARD);

		when(playerVirtual2.askPlaceCard())
				.thenReturn(new PlaceRequest(new Coordinates(1, 0), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED)))
				.thenReturn(new PlaceRequest(new Coordinates(1, 1), new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED)));


	//	game.playTurn(playerVirtual1);
		// Should turn indefinitely
		//game.playTurn(playerVirtual1);
//		AbstractShapeUpGame spyGame = spy(game);
//
//		InOrder inOrder = inOrder(game);
//		inOrder.verify(game, times(10)).moveCardRequest(any());
//		inOrder.verify(game, times(10)).placeCardRequest(any(), playerVirtual1);
//		inOrder.verify(game, times(10)).placeCardRequest(any(), playerVirtual2);

	}


//	@Test
//	void test()
//	{
////		ps = mock(VirtualPlayer.class);
////		ps1 = mock(VirtualPlayer.class);
////
////		when(ps.askChoice()).thenReturn(Choice.PLACE_A_CARD);
////		when(ps1.askChoice()).thenReturn(Choice.PLACE_A_CARD);
//
//		//MockitoAnnotations.initMocks(this);
//
//		List<PlayerStrategy> list = new ArrayList<>();
//		list.add(ps);
//		list.add(ps1);
//
//		game = new ShapeUpGame(v, list, b);
//	}

}