package SG;
import java.util.*;
import java.io.*;
/****
 * Creates games, formats the game in suitable format for minizinc,
 * parse the output from minizinc and update the game.
 * 
 * @author Oscar Veliz
 */

public class GameModel{
	private int T;
	private int m;
	private int[] UAU;
	private int[] UAC;
	private int[] UDU;
	private int[] UDC;
	/**
	 * Generic constructor. Makes a test game.
	 */
	public GameModel(){
		T = 2;
		m = 1;
		UAC = new int[] {2,2};
		UAU = new int[] {3,4};
		UDC = new int[] {2,2};
		UDU = new int[] {1,0};
	}
	/**
	 * Creates a game based off of parameters
	 * @param seed Random number seed so you can create the same game twice
	 * @param maxU The overall maximum utility of the game.
	 * @param Targets the number of targets
	 * @param ampl true if using ampl
	 */
	public GameModel(int seed, int maxU, int Targets){
		Random r = new Random(seed);
		T = Targets;
		m = r.nextInt(T-1)+1;
		UAC = new int[T];
		UAU = new int[T];
		UDC = new int[T];
		UDU = new int[T];
		for(int i = 0; i < T; i++){
			UDC[i] = r.nextInt(maxU)+1;
			UDU[i] = r.nextInt(UDC[i]);//udc >= udu
			UAU[i] = r.nextInt(maxU)+1;
			UAC[i] = r.nextInt(UAU[i]);//uau >= uac
		}
	}
	/**
	 * Same as the other constructor but has default values of 10 and 10
	 * for maxU and Targets
	 */
	public GameModel(int seed){
		this(seed,10,10);
	}

	/**
	 * return the number of resources
	 * @return the number of resources
	 */
	public int getM(){
		return m;
	}
	/**
	 * set the number of resources
	 * @param M new resources
	 */
	 public void setM(int newM){
		 m = newM;
	 }
	/**
	 * return the number of targets
	 * @return the number of targets
	 */
	public int getT(){
		return T;
	}
	/**
	 * set the number of targets
	 * @param newT
	 */
	 public void setT(int newT){
		 T = newT;
	 }
	/**
	 * compute the payoffs given a coverage and a target
	 * @param c coverage
	 * @param t target
	 */
	public double[] computePayoffs(double[] c, int t){
		double[] payoffs = new double[2];//defender payoff is zero attacker is one
		payoffs[0] = (c[t])*UAC[t] - (1-c[t])*UAU[t];
		payoffs[1] = (c[t])*UDC[t] - (1-c[t])*UDU[t];
		return payoffs;
	}
	/**
	 * Get Payoffs
	 * @return 2d array index 0 is UAC, 1 is UAU, 2 is UDC, 3 is UDU
	 */
	public int[][] getPayoffs(){
		int[][] payoffs = new int[4][T];
		payoffs[0] = UAC;
		payoffs[1] = UAU;
		payoffs[2] = UDC;
		payoffs[3] = UDU;
		return payoffs;
	}
	
	/**
	 * Set payoffs
	 * @param uac new attacker covered payoffs
	 * @param uau new attacer uncovered payoffs
	 * @param udc new defender covered payoffs
	 * @param udu new defender uncovered payoffs
	 */
	 public void setPayoffs(int[] uac, int[] uau, int[] udc, int[] udu){
		 UAC = Arrays.copyOf(uac,uac.length);
		 UAU = Arrays.copyOf(uau,uau.length);
		 UDC = Arrays.copyOf(udc,udc.length);
		 UDU = Arrays.copyOf(udu,udu.length);
	 }
	 /**
	  * clones the current tame
	  * @return a clone of the current game
	  */
	 public GameModel clone(){
		 GameModel g = new GameModel();
		 g.setT(this.getT());
		 g.setM(this.getM());
		 int[][] payoffs = this.getPayoffs();
		 g.setPayoffs(payoffs[0],payoffs[1],payoffs[2],payoffs[3]);
		 return g;
	 }
	 /**
	  * is a valid coverage array
	  * @param C the coverage to check
	  *	@return true if valid, false otherwise
	  */
	 public boolean isValidCoverage(double[] C){
		if(C.length != T)
			return false;
		double sum = 0;
		for(int i = 0; i < T; i++){
			if(C[i] > 1 || C[i] < 0)
				return false;
			sum+=C[i];
		}
		return sum <= m;
	 }
	 /**
	  * is valid target to attack
	  * @param t the target to attack
	  * @return true if valid false otherwise
	  */
	 public boolean isValidTarget(int t){
		 return t >= 0 && t < T;
	 }
}
