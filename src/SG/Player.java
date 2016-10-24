package SG;
import java.util.*;

/**
 * Player agent.
 *
 * NOTE TO STUDENTS: The game master will only give player a copy of the game and run two specific methods for defending and attacking.
 * Your role is figure out your strategy.
 *
 * @author Marcus Gutierrez and Oscar Veliz
 * @version 04/15/2015
 */
public abstract class Player{
    protected String playerName = "defaultPlayer"; //Overwrite this variable in your player subclass
    protected GameModel game;
    private int gameNumber;
    protected int playerNumber;
    private ArrayList<double[]> strategies;//interal saved for later use by GameMaster
    private int T;
    private double[] C;
    
    /**
     * Default Constructor
     */
    public Player(){
        strategies = new ArrayList<double[]>();
    }

    /**
     * Set game
     * @param game the game
     */
    public void setGame(GameModel game){
    	this.game = game;
    }
    
    /**
     * Sets the number of the game
     * @param gameNumber number of the game
     */
    public void setGame(int gameNumber){
    	this.gameNumber = gameNumber;
    }
    
    /**
     * Standard accessor
     * @return the game number
     */
    public int getGameNumber(){
    	return gameNumber;
    }
    
    /**
     * Set player number
     * @param playerNumber 1 is defender otherwise attacker
     */
    public void setPlayerNumber(int playerNumber){
    	this.playerNumber = playerNumber;
    }
    
    /**
     * Standard accessor get current player number
     * @return the player number
     */
    public int getPlayerNumber(){
    	return playerNumber;
    }

    /**
     * Get Agent Name used by GameMaster.
     * @return Name of player
     */
    public String getName(){
		return playerName;
	}

    /**
     * Player logic goes here in extended super agent. Do not try to edit this agent
     * @param g the game
     * @return the defender coverage vector c, sum(c) <= m, forall t in T c(t) >=0 & c(t) <=1
     */
    protected double[] solveGame(GameModel g){
    	this.setGame(g);
    	return this.solveGame();
    }
    
    /**
     * Wrapper for the solveGame function
     * @return the mixed strategy developed by the player. vector c, sum(c) <= m, forall t in T c(t) >=0 & c(t) <=1
     */
    protected double[] solveGame(){
    	return this.solveGame(this.game);
    }
    
    /**
     * Game Master stores a copy of the player strategies inside the player.
     * @param index Game number
     * @param coverage Agent's strategy in the game when playing as playerNum
     * @param playerNum defender = 1, anything else is not used anymore
     */
    public void addStrategy(int index, double[] coverage, int playerNum){
    	strategies.add(coverage);
    }
    
    /**
     * Standard accessor
     * @param index Game Number
     * @return the coverage
     */
    public double[] getStrategy(int index){
    	if(index > strategies.size())
    		return null;
    	return strategies.get(index);
    }
    
    /**
     * wrapper for attackTarget
     * @return the target to attack target 0, target 1, ...
     */
    protected int attackTarget(){
		return this.attackTarget(game,C);
	}
	
	/**
     * wrapper for attackTarget
     * @param g game model
     * @param c coverage
     * @return the target to attack such as 0, 1, 2 ....
     */
    protected int attackTarget(GameModel g, double[] c){
		this.setGame(g);
		this.setC(c);
		return this.attackTarget();
	}
	
	/**
	 * set as defender
	 */
	public void setDefender(){
		playerNumber = 1;
	}
	
	/**
	 * set as attacker
	 */
	public void setAttacker(){
		playerNumber = 2;
	}
	
	/**
	 * set number of targets
	 * @param newT the number of targets
	 */
	public void setT(int newT){
		T = newT;
	}
    
    /**
     * get the number of targets
     * @return the number of targets
     */
    public int getT(){
		return T;
	}
	
	/**
	 * set the coverage
	 * @param coverage array assigning resources to each target index. Sum(coverage) &lt;= m all individual coverages are between zero and one. If you are unsure if your coverage is valid call the isValidCoverage() in GameModel
	 */
	public void setC(double[] coverage){
		C = Arrays.copyOf(coverage,coverage.length);
	}
}
