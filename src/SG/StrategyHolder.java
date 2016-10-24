package SG;
import java.util.*;
/**
 * Auxiliary class for NFG. Keeps the Strategies nice and organized.
 * Updated to work for Security Games.
 * @author Oscar
 */
 
public class StrategyHolder {
	private double[] ms1;
	private double[] ms2;

	/**
	 * Default Constructor
	 */
	public StrategyHolder(){}
	
	/**
	 * Store a strategy for a player
	 * @param ms A strategy
	 * @param playerNum Row Player = 1, Column Player = 2
	 */
	public void addStrategy(double[] ms, int playerNum){
		if(playerNum == 1)
			ms1 = Arrays.copyOf(ms,ms.length);
		else
			ms2 = Arrays.copyOf(ms,ms.length);
	}
	
	/**
	 * Get the stored strategy for a player
	 * @param playerNum Row Player = 1, Column Player = 2
	 * @return coverage vector
	 */
	public double[] getStrategy(int playerNum){
		if(playerNum == 1)
			return ms1;
		return ms2;
	}
}
