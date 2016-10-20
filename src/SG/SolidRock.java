package SG;

/**
 * Sample agent that always picks Rock (the first action). Remember to put your name in the author field,
 * change the name of the agent, and make your own solveGame(MatrixGame,PlayerNumber) method.
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
     * @param playerNumber Row Player = 1, Column Player = 2
     */
    protected double[] solveGame(GameModel mg, int playerNumber){
    	double[] coverage = new double[mg.getT()];
    	coverage[0] = mg.getM();
    	return coverage;
    }
}
