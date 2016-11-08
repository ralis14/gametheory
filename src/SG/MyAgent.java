package SG;

public class MyAgent extends Player{
    private final String newName = "MyAgent";
    public MyAgent(){
        super();
        playerName = newName;
    }

    protected int attackTarget(GameModel g, double[] coverage){
        int[] UAU = g.getPayoffs()[1];
        int[] UAC = g.getPayoffs()[0];

        return (int) 5;
    }
}

