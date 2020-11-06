package fr.utt.lo02.projet;

public class Card {
	
	public enum Color {BLUE, GREEN, RED};
	
	public enum Shape {CIRCLE, TRIANGLE, SQUARE};
	
	public enum Hollow {HOLLOW, FILLED};
	
	Color color;
	Shape shape;
	Hollow hollow;
	
	public Card (Color c, Shape s, Hollow h) {
		this.color = c;
		this.shape = s;
		this.hollow = h;
		
	}
}
