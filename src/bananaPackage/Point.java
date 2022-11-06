package bananaPackage;
import java.util.Objects;
/**
 * Stores the x and y coordiantes of a point
 * Also stores the current point that is selected
 *
 * @author williampatmore
 *
 */
public class Point implements Comparable<Point>{
	private static Point currentPoint;

	private int x;
	private int y;

	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
	
	public int[] getArray()
	{
		int[] holder = {x,y};
		return holder;
	}

	public static Point getCurrentPoint()
	{
		return currentPoint;
	}

	public static void setCurrentPoint(int x, int y)
	{
		currentPoint = new Point(x, y);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public int compareTo(Point point) {

		int xdiff = this.x - point.getX();
		int ydiff = this.y - point.getY();

		return xdiff * xdiff + ydiff * ydiff;
	}

	@Override
	public String toString() {
		//Return the point in 1-indexed terms
		return (x + 1) + ", " + (y + 1);
	}
}