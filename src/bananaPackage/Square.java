package bananaPackage;
import java.util.Objects;

/**
 * Contains the information about each square
 * Resources, mountains, land
 * Makes up the map class
 *
 * @author williampatmore
 *
 */
public class Square {

	private Republic owner;
	private boolean isLand;
	private boolean isMountain;
	private int fish;
	private int mine;
	private int agr;

	public Square (boolean isMountain, boolean isLand, int mine, int agr, int fish)
	{
		this.isMountain = isMountain;
		this.fish = fish;
		this.agr = agr;
		this.mine = mine;
		this.isLand = isLand;
		owner = Republic.NONE;
	}

	/**
	 * Helper method for fate cards
	 * 
	 * @return The combined total of all resource amounts of this square
	 */
	public int getTotalResources()
	{
		return fish + mine + agr;
	}

	public boolean isLand() {
		return isLand;
	}

	public void setLand(boolean isLand) {
		this.isLand = isLand;
	}

	public boolean isMountain() {
		return isMountain;
	}

	public void setMountain(boolean isMountain) {
		this.isMountain = isMountain;
	}

	public Republic getOwner()
	{
		return owner;
	}

	public void setOwner(Republic owner)
	{
		this.owner = owner;
	}

	public int getFish() {
		return fish;
	}

	public void setFish(int fish) {
		this.fish = fish;
	}

	public int getMine() {
		return mine;
	}

	public void setMine(int mine) {
		this.mine = mine;
	}

	public int getAgr() {
		return agr;
	}

	public void setAgr(int agr) {
		this.agr = agr;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agr, fish, isLand, isMountain, mine);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Square other = (Square) obj;
		return toString().equals(other.toString());
	}

	@Override
	public String toString()
	{
		//Return this Square in map file format
		return "".concat((isMountain ? "t" : "f") + (isLand ? "t" : "f") + mine + agr + fish);
	}
}

