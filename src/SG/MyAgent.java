/**
 * My Agent is an Attack strategy that goes for the second highest pay off always, since most people will lean to defend the biggest pay off
 * i believe this strategy will beat an average defender.
 */

package SG;

public class MyAgent extends Player{
    private final String newName = "MyAgent";
    public MyAgent(){
        super();
        playerName = newName;
    }

    public double[] solveGame(GameModel g){
        double[] coverage = new double[g.getT()];
        int[] targetValues = g.getPayoffs()[2];

        int resources = g.getM();
        int[] lowestValuedTargetIndex = new int[resources];

        int minIndex;
        int minValue;

        for(int i = 0; i<resources; i++){
            minIndex = 0;
            minValue = Integer.MAX_VALUE;

            for(int e = 0; e < targetValues.length; e++){
                if(!contains(lowestValuedTargetIndex, e)){
                    if(targetValues[e] < minValue){
                        minIndex = e;
                        minValue = targetValues[e];
                    }
                }
            }

            lowestValuedTargetIndex[i] = minIndex;
        }



        return coverage;
    }

    /**
     *
     * Attack Second Highest
     * @param g game model
     * @param coverage
     * @return
     */
    protected int attackTarget(GameModel g, double[] coverage){
        int[] UAU = g.getPayoffs()[1];
        int[] UAC = g.getPayoffs()[0];

        //int maxIndex = 0;
        int secondMaxIndex = 0;

        double maxValue = Double.MIN_VALUE;
        double secondMax = Double.MIN_VALUE;

        for(int i = 0; i<coverage.length; i++){
            if(coverage[i] == 0){
                if(UAU[i] > maxValue){
                    maxValue = UAU[i];
                    //maxIndex = i;
                }
                else{
                    if(UAU[i] > secondMax){
                        secondMax = UAU[i];
                        secondMaxIndex = i;
                    }
                }
            }
            else{
                double targetValue = UAC[i] * coverage[i];
                if(targetValue > maxValue){
                    maxValue = targetValue;
                }
                else{
                    if(targetValue > secondMax){
                        secondMax = targetValue;
                        secondMaxIndex = i;
                    }
                }
            }
        }

        return secondMaxIndex;
    }

    public boolean contains(int[] a, int value){
        for(int b: a){
            if (b == value)
                return true;
        }
        return false;
    }
}

