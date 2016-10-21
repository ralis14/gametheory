package SG;

/**
 * Sample agent that always defends Target 0 and always attacks Target 0
 * @author Oscar
 * @version 2015.10.04
 */
public class SolidRock extends Player{
	protected final String newName = "SolidRock"; //Overwrite this variable in your player subclass

	/**Your constructor should look just like this*/
	public SolidRock() {
		super();
        playerName = newName;
	}
	
	/**
     * THIS METHOD SHOULD BE OVERRIDDEN 
     * GameMaster will call this to compute your strategy.
     * @param mg The game your agent will be playing
     * @return the coverage
     */
    protected double[] solveGame(GameModel mg){
    	double[] coverage = new double[mg.getT()];
    	coverage[0] = 1;//fully protect target 0
    	return coverage;
    }
    
    /**
     * Attacker logic
     * @param mg the game
     * @param coverage defender's coverage
     */
    protected int attackTarget(GameModel mg, double[] coverage){
		return 0;//always attack target 0
	}
}
