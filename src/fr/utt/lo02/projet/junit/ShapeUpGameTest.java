package fr.utt.lo02.projet.junit;

import fr.utt.lo02.projet.model.board.*;
import fr.utt.lo02.projet.model.board.visitor.ScoreCalculatorVisitor;
import fr.utt.lo02.projet.model.game.AbstractShapeUpGame;
import fr.utt.lo02.projet.model.game.ShapeUpGame;
import fr.utt.lo02.projet.model.strategy.*;
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

import static fr.utt.lo02.projet.model.board.Card.Color.*;
import static fr.utt.lo02.projet.model.board.Card.Filling.FILLED;
import static fr.utt.lo02.projet.model.board.Card.Filling.HOLLOW;
import static fr.utt.lo02.projet.model.board.Card.Shape.*;
import static fr.utt.lo02.projet.model.strategy.MoveRequestResult.MOVE_VALID;
import static fr.utt.lo02.projet.model.strategy.MoveRequestResult.NO_CARD_IN_THE_ORIGIN_COORDINATE;
import static fr.utt.lo02.projet.model.strategy.PlaceRequestResult.CORRECT_PLACEMENT;
import static fr.utt.lo02.projet.model.strategy.PlaceRequestResult.PLAYER_DOESNT_OWN_CARD;
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
		//v = Mockito.mock(ScoreCalculatorVisitor.class);

		v = new ScoreCalculatorVisitor();
		list = new ArrayList<>();
		board = new RectangleBoard();

		playerVirtual1 = new VirtualPlayer("a", board, new RandomStrategy());
		playerVirtual2 = new VirtualPlayer("b", board, new RandomStrategy());

//		playerVirtual1 = spy(playerVirtual1);
////		playerVirtual1 = Mockito.mock(VirtualPlayer.class);
//		playerVirtual2 = spy(playerVirtual2);
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

		game.drawCard();
		assertEquals(1, playerVirtual1.getPlayerHand().size());

		game.nextPlayer();
		game.drawCard();

		assertEquals(1, playerVirtual1.getPlayerHand().size());
		assertEquals(1, playerVirtual2.getPlayerHand().size());
		game.nextPlayer();

		game.drawCard();
		assertEquals(2, playerVirtual1.getPlayerHand().size());

	}

	@RepeatedTest(100)
	void TestFirstTurnForEachPlayerPlaceCard()
	{
		//List<Set<Card>> cards = game.getPlayerCards();

		game.drawCard();
		game.nextPlayer();
		game.drawCard();

		assertEquals(1, playerVirtual1.getPlayerHand().size());
		assertEquals(1, playerVirtual2.getPlayerHand().size());

		final int sizeFirstPlayerHand = playerVirtual1.getPlayerHand().size();
		final int sizeSecondPlayerHand = playerVirtual2.getPlayerHand().size();

		Card ca = null;
		Card ca2 = null;
		//Get a random card from player hand
		Card card = new Card(BLUE, CIRCLE, HOLLOW);

		for (Card c : playerVirtual1.getPlayerHand())
		{
			ca = c;
		}
		for (Card c : playerVirtual2.getPlayerHand())
		{
			ca2 = c;
		}

		Card card1 = new Card(GREEN, CIRCLE, HOLLOW);

		if (ca.getFilling() == HOLLOW)
		{
			System.out.println("ca");
		}

		if (ca2.getColor() == GREEN)
		{
			System.out.println("ca2");

			card1 = new Card(BLUE, CIRCLE, FILLED);
		}
		int chosenX = new Random().nextInt(2000) - 1000;
		int chosenY = new Random().nextInt(2000) - 1000;

		// A card from the other player hand can't be placed
		assertSame(PLAYER_DOESNT_OWN_CARD, game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX, chosenY), ca)));

		// A card not possessed by the player can't be placed
		assertSame(PLAYER_DOESNT_OWN_CARD, game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX, chosenY), card1)));
		// This card is possessed and then should be placed at any coordinate in the first turn (no adjacency check)
		game.nextPlayer();
		assertSame(CORRECT_PLACEMENT, game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX, chosenY), ca)));
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

		game.nextPlayer();

		// The second player can't place a card on the right+2 of the first player card
		assertSame(PlaceRequestResult.CARD_NOT_ADJACENT, game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX + 2, chosenY), ca2)));

		// The second player can't place a card that he doesn't own
		assertSame(PLAYER_DOESNT_OWN_CARD, game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX + 2, chosenY), card1)));

		// The second player can place a card on the right of the first player card
		assertSame(CORRECT_PLACEMENT, game.placeCardRequest(new PlaceRequest(new Coordinates(chosenX + 1, chosenY), ca2)));


	}

	@RepeatedTest(20)
	void TestPlaceCardDuringGame()
	{
		board.getPlacedCards().put(new Coordinates(0, 0), new Card(RED, TRIANGLE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(1, 0), new Card(RED, CIRCLE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(2, 0), new Card(RED, SQUARE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(3, 0), new Card(RED, SQUARE, FILLED));
		board.getPlacedCards().put(new Coordinates(4, 0), new Card(BLUE, TRIANGLE, FILLED));
		board.getPlacedCards().put(new Coordinates(0, -1), new Card(GREEN, TRIANGLE, FILLED));
		board.getPlacedCards().put(new Coordinates(1, -1), new Card(GREEN, CIRCLE, FILLED));
		board.getPlacedCards().put(new Coordinates(2, -1), new Card(GREEN, SQUARE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(3, -1), new Card(RED, CIRCLE, FILLED));
		board.getPlacedCards().put(new Coordinates(4, -1), new Card(BLUE, SQUARE, FILLED));

		// The player has drawn a card
		Card playerOneCard = new Card(GREEN, TRIANGLE, HOLLOW);
		Card playerTwoCard = new Card(BLUE, CIRCLE, FILLED);

		// Fake card draw
		playerVirtual1.drawCard(playerOneCard);
		playerVirtual2.drawCard(playerTwoCard);

		final int bound = 5;
		int randomX = new Random().nextInt(bound);
		int randomY = new Random().nextInt(bound) + 2;
		System.out.println(randomX);
		assertSame(PlaceRequestResult.CARD_NOT_IN_THE_LAYOUT, game.placeCardRequest(new PlaceRequest(new Coordinates(randomX, randomY), playerOneCard)));

		assertSame(CORRECT_PLACEMENT, game.placeCardRequest(new PlaceRequest(new Coordinates(randomX, -2), playerOneCard)));

		game.nextPlayer();

		assertSame(PlaceRequestResult.CARD_NOT_IN_THE_LAYOUT, game.placeCardRequest(new PlaceRequest(new Coordinates(randomX, -2), playerTwoCard)));

		assertSame(CORRECT_PLACEMENT, game.placeCardRequest(new PlaceRequest(new Coordinates((randomX + 1) % (bound - 1), -2), playerTwoCard)));

		assertSame(PLAYER_DOESNT_OWN_CARD, game.placeCardRequest(new PlaceRequest(new Coordinates(randomX, randomY), playerTwoCard)));

	}

	@Test
	void TestMoveCardDuringGame()
	{
		board.getPlacedCards().put(new Coordinates(0, 0), new Card(RED, TRIANGLE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(1, 0), new Card(RED, CIRCLE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(2, 0), new Card(RED, SQUARE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(3, 0), new Card(RED, SQUARE, FILLED));
		board.getPlacedCards().put(new Coordinates(4, 0), new Card(BLUE, TRIANGLE, FILLED));
		board.getPlacedCards().put(new Coordinates(0, -1), new Card(GREEN, TRIANGLE, FILLED));
		board.getPlacedCards().put(new Coordinates(1, -1), new Card(GREEN, CIRCLE, FILLED));
		board.getPlacedCards().put(new Coordinates(2, -1), new Card(GREEN, SQUARE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(3, -1), new Card(RED, CIRCLE, FILLED));
		board.getPlacedCards().put(new Coordinates(4, -1), new Card(BLUE, SQUARE, FILLED));


		game.nextPlayer();
		// Non existent card && non full destination
		assertSame(NO_CARD_IN_THE_ORIGIN_COORDINATE, game.moveCardRequest(new MoveRequest(new Coordinates(-1, -1), new Coordinates(1, 0))));
		// Non existent card && free destination
		assertSame(NO_CARD_IN_THE_ORIGIN_COORDINATE, game.moveCardRequest(new MoveRequest(new Coordinates(-1, -1), new Coordinates(1, -2))));

		// Non existent card && dest out of the layout
		assertSame(NO_CARD_IN_THE_ORIGIN_COORDINATE, game.moveCardRequest(new MoveRequest(new Coordinates(-50, -50), new Coordinates(-51, -51))));

		// Existent card , full dest
		assertSame(MoveRequestResult.CARD_NOT_IN_THE_LAYOUT, game.moveCardRequest(new MoveRequest(new Coordinates(0, 0), new Coordinates(1, 0))));

		//Existent card, dest out of the layout
		assertSame(MoveRequestResult.CARD_NOT_ADJACENT, game.moveCardRequest(new MoveRequest(new Coordinates(0, 0), new Coordinates(-3, -3))));

		assertEquals(board.getPlacedCards().get(new Coordinates(0, 0)), new Card(RED, TRIANGLE, HOLLOW));

		//Correct move
		assertSame(MOVE_VALID, game.moveCardRequest(new MoveRequest(new Coordinates(0, 0), new Coordinates(1, -2))));
		// here the card (0,0) has been moved to (1,-2)
		// The coordinate 0,0 is now free
		assertFalse(board.getPlacedCards().containsKey(new Coordinates(0, 0)));
		// The coordinates (1,-2) are now occupied
		assertTrue(board.getPlacedCards().containsKey(new Coordinates(1, -2)));

		//And at (1,-2) a card is existing
		assertEquals(new Card(RED, TRIANGLE, HOLLOW), board.getPlacedCards().get(new Coordinates(1, -2)));
	}

	// Hypothetical case
	@Test
	void TestLastTurnAfterSecondPlayerPlays()
	{
		board.getPlacedCards().put(new Coordinates(0, 0), new Card(RED, TRIANGLE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(1, 0), new Card(RED, CIRCLE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(2, 0), new Card(RED, SQUARE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(3, 0), new Card(RED, SQUARE, FILLED));
		board.getPlacedCards().put(new Coordinates(4, 0), new Card(BLUE, TRIANGLE, FILLED));
		board.getPlacedCards().put(new Coordinates(0, -1), new Card(GREEN, TRIANGLE, FILLED));
		board.getPlacedCards().put(new Coordinates(1, -1), new Card(GREEN, CIRCLE, FILLED));
		board.getPlacedCards().put(new Coordinates(2, -1), new Card(GREEN, SQUARE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(3, -1), new Card(RED, CIRCLE, FILLED));
		board.getPlacedCards().put(new Coordinates(4, -1), new Card(BLUE, SQUARE, FILLED));

		board.getPlacedCards().put(new Coordinates(0, -2), new Card(GREEN, TRIANGLE, FILLED));
		board.getPlacedCards().put(new Coordinates(1, -2), new Card(GREEN, CIRCLE, FILLED));
		board.getPlacedCards().put(new Coordinates(2, -2), new Card(GREEN, SQUARE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(3, -2), new Card(RED, CIRCLE, FILLED));

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

		assertEquals(1, q.size());

		Card c = q.poll();
		playerVirtual1.drawCard(c);
		assertSame(game.placeCardRequest(new PlaceRequest(new Coordinates(4, -2), c)), CORRECT_PLACEMENT);
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
		board.getPlacedCards().put(new Coordinates(0, 0), new Card(RED, TRIANGLE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(1, 0), new Card(RED, CIRCLE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(2, 0), new Card(RED, SQUARE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(3, 0), new Card(RED, SQUARE, FILLED));
		board.getPlacedCards().put(new Coordinates(4, 0), new Card(BLUE, TRIANGLE, FILLED));
		board.getPlacedCards().put(new Coordinates(0, -1), new Card(GREEN, TRIANGLE, FILLED));
		board.getPlacedCards().put(new Coordinates(1, -1), new Card(GREEN, CIRCLE, FILLED));
		board.getPlacedCards().put(new Coordinates(2, -1), new Card(GREEN, SQUARE, HOLLOW));
		board.getPlacedCards().put(new Coordinates(3, -1), new Card(RED, CIRCLE, FILLED));
		board.getPlacedCards().put(new Coordinates(4, -1), new Card(BLUE, SQUARE, FILLED));

		board.getPlacedCards().put(new Coordinates(0, -2), new Card(GREEN, TRIANGLE, FILLED));
		board.getPlacedCards().put(new Coordinates(1, -2), new Card(GREEN, CIRCLE, FILLED));

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

		assertEquals(3, q.size());

		Card c = q.poll();
		playerVirtual1.drawCard(c);
		playerVirtual1.drawCard(q.poll());

		assertSame(game.placeCardRequest(new PlaceRequest(new Coordinates(4, -2), c)), CORRECT_PLACEMENT);
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