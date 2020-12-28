package fr.utt.lo02.projet.strategy;

import fr.utt.lo02.projet.board.Card;
import fr.utt.lo02.projet.board.Coordinates;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel
{
	private Window window;

	public static Map<Card, Image> imageList;

	private HashMap<Coordinates, Card> map;

	public GamePanel()
	{
		setMinimumSize(new Dimension(300,300));

		setPreferredSize(new Dimension(800, 600));
		setLayout(null);
		imageList = new HashMap<>();
		map = new HashMap<>();
		GamePanel.initImages();
		map.put(new Coordinates(1, 0), new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
		map.put(new Coordinates(0, 1), new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.FILLED));
		map.put(new Coordinates(1, 1), new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.HOLLOW));
		map.put(new Coordinates(0, 0), new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.FILLED));



	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawString("salut", 10, 20);

		for (Map.Entry<Coordinates, Card> c: map.entrySet())
		{
			g.drawImage(imageList.get(c.getValue()), c.getKey().getX()*150 +120,c.getKey().getY()*100 +150, 100,120,null);
		}

	}

	private static void initImages()
	{
		try
		{
			imageList.put(new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.FILLED), ImageIO.read(new File("../cards/filled_blue_circle.png")));
			imageList.put(new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.FILLED), ImageIO.read(new File("../cards/filled_blue_square.png")));
			imageList.put(new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.FILLED), ImageIO.read(new File("../cards/filled_blue_triangle.png")));

			imageList.put(new Card(Card.Color.BLUE, Card.Shape.CIRCLE, Card.Filling.HOLLOW), ImageIO.read(new File("../cards/hollow_blue_circle.png")));
			imageList.put(new Card(Card.Color.BLUE, Card.Shape.SQUARE, Card.Filling.HOLLOW), ImageIO.read(new File("../cards/hollow_blue_square.png")));
			imageList.put(new Card(Card.Color.BLUE, Card.Shape.TRIANGLE, Card.Filling.HOLLOW), ImageIO.read(new File("../cards/hollow_blue_triangle.png")));

			imageList.put(new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.FILLED), ImageIO.read(new File("../cards/filled_green_circle.png")));
			imageList.put(new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.FILLED), ImageIO.read(new File("../cards/filled_green_square.png")));
			imageList.put(new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.FILLED), ImageIO.read(new File("../cards/filled_green_triangle.png")));

			imageList.put(new Card(Card.Color.GREEN, Card.Shape.CIRCLE, Card.Filling.HOLLOW), ImageIO.read(new File("../cards/hollow_green_circle.png")));
			imageList.put(new Card(Card.Color.GREEN, Card.Shape.SQUARE, Card.Filling.HOLLOW), ImageIO.read(new File("../cards/hollow_green_square.png")));
			imageList.put(new Card(Card.Color.GREEN, Card.Shape.TRIANGLE, Card.Filling.HOLLOW), ImageIO.read(new File("../cards/hollow_green_triangle.png")));

			imageList.put(new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.FILLED), ImageIO.read(new File("../cards/filled_red_circle.png")));
			imageList.put(new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.FILLED), ImageIO.read(new File("../cards/filled_red_square.png")));
			imageList.put(new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.FILLED), ImageIO.read(new File("../cards/filled_red_triangle.png")));

			imageList.put(new Card(Card.Color.RED, Card.Shape.CIRCLE, Card.Filling.HOLLOW), ImageIO.read(new File("../cards/hollow_red_circle.png")));
			imageList.put(new Card(Card.Color.RED, Card.Shape.SQUARE, Card.Filling.HOLLOW), ImageIO.read(new File("../cards/hollow_red_square.png")));
			imageList.put(new Card(Card.Color.RED, Card.Shape.TRIANGLE, Card.Filling.HOLLOW), ImageIO.read(new File("../cards/hollow_red_triangle.png")));




		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
