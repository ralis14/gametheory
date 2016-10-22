package SG;

import java.util.*;


/**
 * Security Game Tournament Simulator based of Normal Form Tournament Simulator
 * created by Marcus and Oscar for Fall 2015 of Risk Analysis
 * 
 * @author Oscar Veliz
 * @version 2016.10.20
 */
public class GameMaster {

	private static boolean verbose = false; //Set to false if you do not want the details
	private static int numGames = 10; //test with however many games you want
	private static boolean zeroSum = false; //when true use zero sum games, when false use general sum
	private static ArrayList<GameModel> games = new ArrayList<GameModel>();
	
	/**
	 * Runs the tournament. Add your agent(s) to the list.
	 * 
	 * @param args not using any command line arguments
	 */
	public static void main(String[] args) {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new UniformRandom());
		players.add(new SolidRock());
		//add your agent(s) here
		
		//create games
		for(int i = 0; i < numGames; i++)
			games.add(new GameModel(i));
		computeStrategies(players);
		
		//compute expected payoffs
		//double[][] payoffMatrix = new double[players.size()][players.size()];
		double[] attackerPayoffs = new double[players.size()];
		double[] defenderPayoffs = new double[players.size()];
		double[][] defenderUtilities = new double[players.size()][players.size()];
		double[][] attackerUtilities = new double[players.size()][players.size()];
		//double[] wins = new double[players.size()];
		int numPlayers = players.size();
		for(int d = 0; d < numPlayers; d++) {
			for(int a = 0; a < numPlayers; a++) {
				for (int game = 0; game < numGames; game++) {
					Player player1 = players.get(d);
					Player player2 = players.get(a);
					if(verbose)	System.out.println("Game" + game);
					if(verbose) System.out.println(player1.getName()+" vs "+player2.getName());
					double[] payoffs = match(player1,player2,game);
					defenderPayoffs[d] += payoffs[0];
					attackerPayoffs[a] += payoffs[1];
					defenderUtilities[d][a] += payoffs[0];
					attackerUtilities[d][a] += payoffs[1];
					if(verbose) System.out.println(payoffs[0]);
					if(verbose) System.out.println(payoffs[1]);
				}
			}
		}
		String[] names = new String[numPlayers];
		for(int i = 0; i < numPlayers; i++)
			names[i] = players.get(i).getName();
		System.out.println("Defender Results");
		Analyzer defenderResults = new Analyzer(defenderUtilities, names);
		defenderResults.printResults();
        defenderResults.printAverages();
        defenderResults.printMedians();
        defenderResults.printRegret();
        defenderResults.printMinimizer();
        System.out.println();
        System.out.println("Attacker Results");
        Analyzer attackerResults = new Analyzer(attackerUtilities, names);
        attackerResults.printResults();
        attackerResults.printAverages();
        attackerResults.printMedians();
        attackerResults.printRegret();
		System.exit(0);//just to make sure it exits
	}

	/**
	 * Tries to execute a Player class' method by using threads for protection in case
	 * the Player subclasses crash or time out.
	 * 
	 * @param pDriver The thread that will execute the player
	 */
	private static void tryPlayer(PlayerDriver pDriver){
		int timeLimit = 2000;//2s or 2000ms
		Thread playerThread = new Thread(pDriver);
		playerThread.start();
		for(int sleep = 0; sleep < timeLimit; sleep+=10){
			if(playerThread.isAlive())
				try {Thread.sleep(10);} catch (Exception e) {e.printStackTrace();}
			else
				return;
		}
	}

	/**
	 * Figures out every agent strategy for every game for all players within a game
	 * @param p The agents being run in the tournament
	 */
	private static void computeStrategies(ArrayList<Player> p){
		ArrayList<PlayerDriver> drivers = new ArrayList<PlayerDriver>();
		for(int pd = 0; pd < p.size(); pd++)
			drivers.add(new PlayerDriver(PlayerState.SOLVE,p.get(pd)));
		for(int gameNumber = 0; gameNumber < numGames; gameNumber++){
			GameModel mg = new GameModel(gameNumber);//gives the agent a copy of the game
			for(int playerIndex = 0; playerIndex < p.size(); playerIndex++){
				Player player = p.get(playerIndex);
				//for(int playerNumber = 1; playerNumber <= 2; playerNumber++){
				player.setGame(gameNumber);
				player.setGame(mg);
				//player.setPlayerNumber(playerNumber);
				player.setDefender();
				tryPlayer(new PlayerDriver(PlayerState.SOLVE,player));
				//}
			}				
		}
	}
	
	/**
	 * A single individual match between to players, the first player is row the second is column
	 * 
	 * If a strategy for a player is invalid it will assign a payoff of -1337 to that player
	 * @param p1 row player
	 * @param p2 column player
	 * @param gameNumber game
	 * @return
	 */
	public static double[] match(Player d, Player a, int gameNumber){
		double[] coverage = d.getStrategy(gameNumber);
		a.setAttacker();
		a.setGame(gameNumber);
		a.setC(coverage);
		tryPlayer(new PlayerDriver(PlayerState.SOLVE,a));
		int t = a.getT();
		GameModel g = new GameModel(gameNumber);
		double[] payoffs = g.computePayoffs(coverage,t);

		return payoffs;
	}
	
	/**
	 * Want to visualize the results of a tournament? Call this function.
	 * @param matrix Payoff Matrix
	 * @param players List of Players
	 */
	public static void printMatrix(double[][] matrix, ArrayList<Player> players){
		for(int i = 0; i < players.size(); i++)
			System.out.print("\t"+players.get(i).getName());
		System.out.println();
		for(int i = 0; i < matrix.length; i++){
			System.out.print(players.get(i).getName());
			for(int j = 0; j < matrix[i].length; j++){
				System.out.print("\t"+matrix[i][j]);
			}
			System.out.println();
		}
	}
}
