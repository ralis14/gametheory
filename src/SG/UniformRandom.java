package SG;

import java.util.*;
/**
 * Sample agent that always defends Target 0 and always attacks Target 0
 * @author Oscar
 * @version 2015.10.04
 */
public class UniformRandom extends Player{
	protected final String newName = "UniformRandom"; //Overwrite this variable in your player subclass

	/**Your constructor should look just like this*/
	public UniformRandom() {
		super();
        playerName = newName;
	}
	
	/**
     * THIS METHOD SHOULD BE OVERRIDDEN 
     * GameMaster will call this to compute your strategy.
     * @param g The game your agent will be playing
     * @return the coverage
     */
    protected double[] solveGame(GameModel g){
    	double[] coverage = new double[g.getT()];
    	Random r = new Random();
    	coverage[r.nextInt(g.getT())] = 1;//fully protect random target
    	return coverage;
    }
    
    /**
     * Attacker logic
     * @param mg the game
     * @param coverage defender's coverage
     */
    protected int attackTarget(GameModel g, double[] coverage){
		Random r = new Random();
		return r.nextInt(g.getT());
	}
}
