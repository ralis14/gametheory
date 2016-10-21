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
	 * @param M resouces
	 * @param ampl true if using ampl
	 */
	public GameModel(int seed, int maxU, int Targets, int M){
		Random r = new Random(seed);
		T = Targets;
		m = M;
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
		this(seed,10,10,1);
	}

	/**
	 * return the number of resources
	 * @return the number of resources
	 */
	public double getM(){
		return m;
	}
	/**
	 * return the number of targets
	 * @return the number of targets
	 */
	public int getT(){
		return T;
	}
	
	/**
	 * compute the payoffs given a coverage and a target
	 * @param c coverage
	 * @param t target
	 */
	public double[] computePayoffs(double[] c, int t){
		double[] payoffs = new double[2];//defender payoff is zero attacker is one
		payoffs[0] = (c[t])*UAC[t] - (1-c[t])*UAU[t];
		payoffs[1] = (1-c[t])*UDC[t] - (c[t])*UDU[t];
		return payoffs;
	}
}
