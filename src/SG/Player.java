package SG;

//import java.util.ArrayList;
import java.util.*;

/**
 * Player agent.
 *
 * NOTE TO STUDENTS: The game master will only give player a copy of the game and tell you which player you are.
 * Your role is figure out your strategy.
 *
 * @author Marcus Gutierrez and Oscar Veliz
 * @version 04/15/2015
 */
public abstract class Player
{
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
    public Player()
    {
        //strategies = new ArrayList<StrategyHolder>();
        strategies = new ArrayList<double[]>();
    }

    /**
     * Set game
     * @param the game
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
     */
    public void setPlayerNumber(int playerNumber){
    	this.playerNumber = playerNumber;
    }
    /**
     * Standard accessor get current player number
     * @return
     */
    public int getPlayerNumber(){
    	return playerNumber;
    }

    /**
     * Get Agent Name used by GameMaster.
     * @return Name of player
     */
    public String getName(){return playerName;}

    /**
     * Player logic goes here in extended super agent. Do not try to edit this agent
     * 
     */
    protected double[] solveGame(GameModel mg){
    	this.setGame(mg);
    	//this.setPlayerNumber(playerNum);
    	return this.solveGame();
    
    }
    /**
     * Wrapper for the solveGame function
     * @return the mixed strategy developed by the player
     */
    protected double[] solveGame(){
    	return this.solveGame(this.game);
    }
    /**
     * Game Master stores a copy of the player strategies inside the player.
     * @param index Game number
     * @param ms Agent's strategy in the game when playing as playerNum
     * @param playerNum Row Player = 1, Column Player = 2
     */
    public void addStrategy(int index, double[] ms, int playerNum){
    	/*if(strategies.size() == index)
    		strategies.add(new StrategyHolder());
    	strategies.get(index).addStrategy(ms, playerNum);
    	*/
    	strategies.add(ms);
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
     * @return the target to attack
     */
    protected int attackTarget(){
		return this.attackTarget(game,C);
	}
	/**
     * wrapper for attackTarget
     * @param g game model
     * @param c coverage
     * @return the target to attack
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
	 * set recent target
	 * @param newT
	 */
	public void setT(int newT){
		T = newT;
	}
    /**
     * get the recent target
     * @return the recent target
     */
    public int getT(){
		return T;
	}
	/**
	 * set the coverage
	 * @param coverage
	 */
	public void setC(double[] coverage){
		C = Arrays.copyOf(coverage,coverage.length);
	}
}
