package SG;

/**
 * Sample agent that always attacks the highest valued target, and
 * fully covers the highest valued covered targets.
 * 
 * @author Jesus Molina
 */
public class RelentlessAssault extends Player {
	protected final String newName = "RelentlessAssault";

	/** Your constructor should look just like this */
	public RelentlessAssault() {
		super();
		playerName = newName;
	}

	/**
	 * Obtain the targets that when covered will give highest value and fully
	 * protect them.
	 * 
	 * @param g
	 *            The game your agent will be playing
	 * @return the coverage
	 */
	protected double[] solveGame(GameModel g) {
		double[] coverage = new double[g.getT()];
		int[] targetValues = g.getPayoffs()[2]; // 2 is for defender covered utilities

		int resources = g.getM(); // To know how many targets to protect
		int[] highestValuedTargetIndexes = new int[resources];

		int maxIndex;
		int maxValue;

		/** Obtain the indexes of the highest value targets. */
		for (int i = 0; i < resources; i++) {
			maxIndex = 0;
			maxValue = Integer.MIN_VALUE;

			for (int e = 0; e < targetValues.length; e++) {
				if (!contains(highestValuedTargetIndexes, e)) {
					if (targetValues[e] > maxValue) {
						maxIndex = e;
						maxValue = targetValues[e];
					}
				}
			}

			highestValuedTargetIndexes[i] = maxIndex;
		}

		for (int index : highestValuedTargetIndexes)
			coverage[index] = 1;
		return coverage;
	}

	/**
	 * returns true if val is in arr.
	 * 
	 * @param arr
	 *            the array.
	 * @param val
	 *            the value.
	 * @return true if val is in arr.
	 */
	public boolean contains(int[] arr, int val) {
		for (int v : arr)
			if (v == val)
				return true;
		return false;
	}

	/**
	 * Attack the highest target either covered or not.
	 * 
	 * @param g
	 *            the game
	 * @param coverage
	 *            defender's coverage
	 * @return the target to attack
	 */
	protected int attackTarget(GameModel g, double[] coverage) {
		int[] uncoveredTargetValues = g.getPayoffs()[1]; // 1 is for attacker uncovered utilities

		int[] coveredTargetValues = g.getPayoffs()[0]; // 0 is for attacker covered utilities
		/** Obtain the index of the highest value target. */
		int maxIndex = 0;
		double maxValue = Double.MIN_VALUE;
		for (int i = 0; i < coverage.length; i++) {
			// Check uncovered values if uncovered.
			if (coverage[i] == 0) {
				if (uncoveredTargetValues[i] > maxValue) {
					maxValue = uncoveredTargetValues[i];
					maxIndex = i;
				}
			}
			// Check covered values if covered.
			else{
				double targetValue = coveredTargetValues[i] * coverage[i];
				if(targetValue > maxValue){
					maxValue = targetValue;
					maxIndex = i;
				}
			}
		}
		return maxIndex;
	}
}
