package SG;

import java.util.Random;

/**
 * Sample agent that covers equally all targets
 * and attacks with equal probability a target (random)
 * 
 * @author Jesus Molina
 */
public class Solidarity extends Player {
	protected final String newName = "Solidarity"; 

	public Solidarity() {
		super();
		playerName = newName;
	}

	/**
	 * Divides the coverage equally between the targets.
	 * 
	 * @param g
	 *            The game your agent will be playing
	 * @return the coverage
	 */
	protected double[] solveGame(GameModel g) {
		double[] coverage = new double[g.getT()];
		int resources = g.getM();
		for (int i = 0; i < coverage.length; i++) {
			coverage[i] = resources / coverage.length;
		}
		return coverage;
	}

	/**
	 * Attack targets randomly. (Taken from UniformRandom strategy).
	 * 
	 * @param g
	 *            the game
	 * @param coverage
	 *            defender's coverage
	 * @return the target to attack
	 */
	protected int attackTarget(GameModel g, double[] coverage) {
		Random r = new Random();
		return r.nextInt(g.getT());
	}
}
