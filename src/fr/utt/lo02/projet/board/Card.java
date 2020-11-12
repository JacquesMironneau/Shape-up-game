package fr.utt.lo02.projet.board;


/**
 * Represents a card from the Game
 * A card is made of a color a shape and if its filled or not.
 * @author Baptiste, Jacques
 *
 */
public class Card
{
	
	/**
	 * The color of the card can be red, green or blue
	 */
	public enum Color {BLUE, GREEN, RED};

	/**
	 * The shape of the card can be a triangle, a circle or a square 
	 */
	public enum Shape {CIRCLE, TRIANGLE, SQUARE};

	/**
	 * A card can be filled or not (hollow).
	 */
	public enum Filling {HOLLOW, FILLED};
	
	
	private Color color;
	private Shape shape;
	private Filling filling;
	
	public Card (Color c, Shape s, Filling f)
	{
		this.color = c;
		this.shape = s;
		this.filling = f;
		
	}

	public Color getColor()
	{
		return color;
	}

	public Shape getShape()
	{
		return shape;
	}

	public Filling getFilling()
	{
		return filling;
	}
	
	
	
}
