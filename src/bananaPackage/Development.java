package bananaPackage;

/**
 * This enum represents the Republic upgrade development status
 * Each enum also has the productivity value associated with it for upgrades
 */

public enum Development {
	UNIMPROVED(0.9),
	BASIC(1),
	ADVANCED(1.5),
	HITECH(2);

	//The productivity value, used as a multiplier
	private double prod;

	private Development(double d) 
	{
		prod = d;
	}

	public double getProductivity()
	{
		return prod;
	}

	public Development nextLevel()
	{
		switch(this)
		{
			case UNIMPROVED:
				return BASIC;
			case BASIC:
				return ADVANCED;
			default:
				return HITECH;
		}
	}

	public int getIntLevel()
	{
		switch(this)
		{
			case UNIMPROVED:
				return 1;
			case BASIC:
				return 2;
			case ADVANCED:
				return 3;
			default:
				return 4;
		}
	}

	public static Development getDevelopmentFromLevel(int level)
	{
		switch(level)
		{
			case 1:
				return UNIMPROVED;
			case 2:
				return BASIC;
			case 3:
				return ADVANCED;
			default:
				return HITECH;
		}
	}

	public int getUpgradeCost()
	{
		switch(this)
		{
			case UNIMPROVED:
				return 0;
			case BASIC:
				return 1;
			case ADVANCED:
				return 5;
			default:
				return 10;
		}
	}
	
	public int getMaxLoan()
	{
		switch(this)
		{
			case UNIMPROVED:
				return 10;
			case BASIC:
				return 35;
			case ADVANCED:
				return 50;
			default:
				return 100;
		}
	}
}
