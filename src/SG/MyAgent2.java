package SG;

/**
 * Created by Raul on 11/9/2016.
 * Some code was modified from Oscar, author of the game.
 */


/**
 * Find the highest payoff and protect, with the rest of the resources spread them evenly between the rest of the targets
 * for a small insurance.
 */
public class MyAgent2 extends Player{
    private final String newName = "MyAgent2";
    public MyAgent2(){
        super();
        playerName = newName;
    }

    public double[] solveGame(GameModel g){
        double[] coverage = new double[g.getT()];
        int[] targetValues = g.getPayoffs()[2];

        int resources = g.getM();
        int[] highestValuedTargetIndex = new int[resources];

        int maxIndex=Integer.MIN_VALUE;
        int maxValue=Integer.MIN_VALUE;

        int secondMaxIndex = Integer.MIN_VALUE;
        int seconfMaxValue = Integer.MIN_VALUE;

        for (int a = 0; a<coverage.length; a++){
            if(targetValues[a] > maxValue){
                maxValue = targetValues[a];
                maxIndex = a;
            }
            else{
                if(targetValues[a] > seconfMaxValue){
                    secondMaxIndex = a;
                    seconfMaxValue = targetValues[a];
                }
            }
        }
        coverage[maxIndex] = 1;
       // coverage[secondMaxIndex] = 1;

        //cover all targets equally with left over resources
        for(int i = 0; i<resources-1; i++){
            //if((coverage[i] != coverage[maxIndex]) || (coverage[i] != coverage[secondMaxIndex])){
            coverage[i] = resources / (double)coverage.length;
            //}
        }


        return coverage;
    }

    /**
     *
     * Find the computed highest payoff and record its index, return index to choose that option to attack
     * @param g game model
     * @param coverage
     * @return
     */
    protected int attackTarget(GameModel g, double[] coverage){

        int maxIndex = 0;

        double maxValue = Double.MIN_VALUE;

        for(int i = 0; i<coverage.length; i++){
            if(coverage[i] < 1){
                double[] one = g.computePayoffs(coverage, i);
                 if (one[1] > maxValue){
                    maxValue = one[1];
                    maxIndex = i;
                 }
            }
        }

        return maxIndex;
    }

}
