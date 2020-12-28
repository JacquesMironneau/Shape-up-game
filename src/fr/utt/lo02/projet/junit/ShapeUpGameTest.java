package fr.utt.lo02.projet.junit;

import fr.utt.lo02.projet.board.*;
import fr.utt.lo02.projet.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.game.AbstractShapeUpGame;
import fr.utt.lo02.projet.game.ShapeUpGame;
import fr.utt.lo02.projet.strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ShapeUpGameTest
{

	private AbstractShapeUpGame game;

	private ScoreCalculatorVisitor v;

	private AbstractBoard board;


	private List<Player> list;

	private VirtualPlayer playerVirtual1;

	private VirtualPlayer playerVirtual2;


	@BeforeEach
	void setUp()
	{
		v = new ScoreCalculatorVisitor();

		list = new ArrayList<>();
		board = new RectangleBoard();

		playerVirtual1 = new VirtualPlayer("a",board, new RandomStrategy());
		playerVirtual2 = new VirtualPlayer("b",board, new RandomStrategy());

		
//		playerVirtual1 = Mockito.mock(VirtualPlayer.class);
		
		list.add(playerVirtual1);
		list.add(playerVirtual2);

		game = new ShapeUpGame(v, list, board);


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

		assertEquals(1, playerVirtual1.getPlayerHand().size());
		assertEquals(1, playerVirtual2.getPlayerHand().size());

		game.drawCard(playerVirtual1);
		assertEquals(2, playerVirtual1.getPlayerHand().size());

	}

	@RepeatedTest(100)
	void TestFirstTurnForEachPlayerPlaceCard() throws boardEmptyException
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
		for (Card c : playerVirtual1.getPlayerHand())
		{
			ca = c;
		}
		for (Card c : playerVirtual2.getPlayerHand())
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
		int chosenX = new Random().nextInt(2000) - 1000;
		int chosenY = new Random().nextInt(2000) - 1000;

		// A card from the other player hand can't be placed
		assertFalse(game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX, chosenY), ca), playerVirtual2));

		// A card not possessed by the player can't be placed
		assertFalse(game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX, chosenY), card), playerVirtual1));
		// This card is possessed and then should be placed at any coordinate in the first turn (no adjacency check)
		assertTrue(game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX, chosenY), ca), playerVirtual1));
		// The player hand is now free from one card (the placed one)
		assertEquals(sizeFirstPlayerHand - 1, playerVirtual1.getPlayerHand().size());

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
		assertFalse(game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX + 2, chosenY), ca2), playerVirtual2));

		// The second player can't place a card that he doesn't own
		assertFalse(game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX + 2, chosenY), card), playerVirtual2));

		// The second player can place a card on the right of the first player card
		assertTrue(game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX + 1, chosenY), ca2), playerVirtual2));

	}

	@RepeatedTest(20)
	void TestPlaceCardDuringGame()
	{
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

		// The player has drawn a card
		Card playerOneCard = new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.HOLLOW);
		Card playerTwoCard = new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED);

		// Fake card draw
		playerVirtual1.drawCard(playerOneCard);
		playerVirtual2.drawCard(playerTwoCard);

		final int bound = 5;
		int randomX = new Random().nextInt(bound);
		int randomY = new Random().nextInt(bound) + 2;
		System.out.println(randomX);
		assertFalse(game.placeCardRequest(new PlaceRequest(new Coordinates(randomX, randomY), playerOneCard), playerVirtual1));

		assertTrue(game.placeCardRequest(new PlaceRequest(new Coordinates(randomX, -2), playerOneCard), playerVirtual1));


		assertFalse(game.placeCardRequest(new PlaceRequest(new Coordinates(randomX, -2), playerTwoCard), playerVirtual2));
		assertTrue(game.placeCardRequest(new PlaceRequest(new Coordinates((randomX + 1) % (bound - 1), -2), playerTwoCard), playerVirtual2));

		assertFalse(game.placeCardRequest(new PlaceRequest(new Coordinates(randomX, randomY), playerTwoCard), playerVirtual2));

	}

	@Test
	void TestMoveCardDuringGame()
	{
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


		// Non existent card && non full destination
		assertFalse(game.moveCardRequest(new MoveRequest(new Coordinates(-1, -1), new Coordinates(1, 0)), playerVirtual2));
		// Non existent card && free destination
		assertFalse(game.moveCardRequest(new MoveRequest(new Coordinates(-1, -1), new Coordinates(1, -2)), playerVirtual2));

		// Non existent card && dest out of the layout
		assertFalse(game.moveCardRequest(new MoveRequest(new Coordinates(-50, -50), new Coordinates(-51, -51)), playerVirtual2));

		// Existent card , full dest
		assertFalse(game.moveCardRequest(new MoveRequest(new Coordinates(0, 0), new Coordinates(1, 0)), playerVirtual2));

		//Existent card, dest out of the layout
		assertFalse(game.moveCardRequest(new MoveRequest(new Coordinates(0, 0), new Coordinates(-3, -3)), playerVirtual2));

		assertEquals(board.getPlacedCards().get(new Coordinates(0, 0)), new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));

		//Correct move
		assertTrue(game.moveCardRequest(new MoveRequest(new Coordinates(0, 0), new Coordinates(1, -2)), playerVirtual2));
		// here the card (0,0) has been moved to (1,-2)
		// The coordinate 0,0 is now free
		assertFalse(board.getPlacedCards().containsKey(new Coordinates(0, 0)));
		// The coordinates (1,-2) are now occupied
		assertTrue(board.getPlacedCards().containsKey(new Coordinates(1, -2)));

		//And at (1,-2) a card is existing
		assertEquals(new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW), board.getPlacedCards().get(new Coordinates(1, -2)));
	}

	// Hypothetical case
	@Test
	void TestLastTurnAfterSecondPlayerPlays()
	{
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

		board.getPlacedCards().put(new Coordinates(0, -2), new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1, -2), new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(2, -2), new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW));
		board.getPlacedCards().put(new Coordinates(3, -2), new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.FILLED));

		Field f;
		Queue<Card> q = null;
		try
		{
			f = AbstractShapeUpGame.class.getDeclaredField("deck");
			f.setAccessible(true);

			Method method2 = AbstractShapeUpGame.class.getDeclaredMethod("initDeck");
			method2.setAccessible(true);
			method2.invoke(game);
			q = (Queue<Card>) f.get(game);

			for (int i = 0; i < 14; i++)
			{
				q.poll();
			}
		} catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e)
		{
			e.printStackTrace();
		}

		assertEquals(1,q.size());

		Card c = q.poll();
		playerVirtual1.drawCard(c);
		assertTrue(game.placeCardRequest(new PlaceRequest(new Coordinates(4, -2), c), playerVirtual1));
		Method m;
		try
		{
			m = AbstractShapeUpGame.class.getDeclaredMethod("isRoundFinished");
			m.setAccessible(true);
			boolean a = (boolean) m.invoke(game);

			assertTrue(playerVirtual1.getPlayerHand().isEmpty());
			assertTrue(playerVirtual2.getPlayerHand().isEmpty());
			assertTrue(q.isEmpty());
			assertTrue(a);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}

	// Common case for 2 player
	@Test
	void TestLastTurnFirstPlayerEndIt()
	{
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

		board.getPlacedCards().put(new Coordinates(0, -2), new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.FILLED));
		board.getPlacedCards().put(new Coordinates(1, -2), new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.FILLED));

		Field f;
		Queue<Card> q = null;
		try
		{
			f = AbstractShapeUpGame.class.getDeclaredField("deck");
			f.setAccessible(true);

			Method method2 = AbstractShapeUpGame.class.getDeclaredMethod("initDeck");
			method2.setAccessible(true);
			method2.invoke(game);
			q = (Queue<Card>) f.get(game);

			for (int i = 0; i < 12; i++)
			{
				q.poll();
			}
		} catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e)
		{
			e.printStackTrace();
		}

		assertEquals(3,q.size());

		Card c = q.poll();
		playerVirtual1.drawCard(c);
		playerVirtual1.drawCard(q.poll());

		assertTrue(game.placeCardRequest(new PlaceRequest(new Coordinates(4, -2), c), playerVirtual1));
		Method m;
		try
		{
			m = AbstractShapeUpGame.class.getDeclaredMethod("isRoundFinished");
			m.setAccessible(true);
			boolean a = (boolean) m.invoke(game);

			assertFalse(playerVirtual1.getPlayerHand().isEmpty());
			assertTrue(playerVirtual2.getPlayerHand().isEmpty());
			assertFalse(q.isEmpty());
			assertFalse(a);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
		{
			e.printStackTrace();
		}


	}

}