package fr.utt.lo02.projet.board;

import java.util.List;
import java.util.Set;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

/**
 * The class represents mere 2D coordinates with an abscissa (x) and an ordinate (y).
 * @author Jacques, Baptiste
 *
 */
public class Coordinates 
{
	/**
	 * The abscissa
	 */
	private int x;
	
	/**
	 * The ordinate
	 */
	private int y;
	
	/**
	 * Create coordinates based on 2 integers
	 * @param coord_x Abscissa
	 * @param coord_y Ordinate
	 */
	public Coordinates(int coord_x, int coord_y) 
	{
		this.x = coord_x;
		this.y = coord_y;
	}

	// Getters, Setters, hashcode and equals
	
	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public static boolean isOneMoreTopLeftThanTwo(Coordinates one, Coordinates two) {
		return (one.x<=two.x) && (one.y>=two.y);
		}
	
	public static int smallestAbscissa(List<Coordinates> coords) {
		int smallestAbscissa = coords.get(0).getX();
		for (Coordinates x: coords) {
			if (x.getX()<smallestAbscissa) {
				smallestAbscissa = x.getX();
			}
		}
		return smallestAbscissa;
	}
	
	public static int biggestAbscissa(List<Coordinates> coords) {
		int smallestAbscissa = coords.get(0).getX();
		for (Coordinates x: coords) {
			if (x.getX()>smallestAbscissa) {
				smallestAbscissa = x.getX();
			}
		}
		return smallestAbscissa;
	}
	
	public static int smallestOrdinate(List<Coordinates> coords) {
		int smallestOrdinate = coords.get(0).getY();
		for (Coordinates y: coords) {
			if (y.getY() < smallestOrdinate) {
				smallestOrdinate = y.getY();
			}
		}
		return smallestOrdinate;
	}
	
	public static int biggestOrdinate(List<Coordinates> coords) {
		int smallestOrdinate = coords.get(0).getY();
		for (Coordinates y: coords) {
			if (y.getY() > smallestOrdinate) {
				smallestOrdinate = y.getY();
			}
		}
		return smallestOrdinate;
	}

	@Override
	public String toString()
	{
		return "Coordinates{" +
				"x=" + x +
				", y=" + y +
				'}';
	}




}
