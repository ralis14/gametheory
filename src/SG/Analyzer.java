package SG;

import java.util.Arrays;

/**
 * Calculates stats and performance of agemts
 * @author Oscar Veliz
 * @version 2016/10/22
 */
public class Analyzer
{
    double[][] points;
    String[] names;
    int numAgents;
    double[] totals;
    double[] mean;
    double[] median;
    double[] std;
    double[] regret;
    double[] bestOf;

    /**
     * Calculates important values for comparing attackers and defenders
     * @param results point value 2dArray with row as agents and columns as opponents
     * @param Names the names of the defenders as they are in the results
     */
    public Analyzer(double[][] results, String[] Names)
    {
        points = results;
        names = Names;
        numAgents = names.length;

        totals = new double[numAgents];
        //calculate totals
        for(int i = 0; i < points.length; i++)
            for(int j = 0; j < points[i].length; j++)
                totals[i] += points[i][j];

        //calculate averages
        //assumes arrays may not be the same length
        mean = new double[totals.length];
        for(int a = 0; a < numAgents; a++)
            mean[a] = (double)totals[a] / numAgents;
        
        //calculate medians
        median = new double[numAgents];
        for(int i = 0; i < median.length; i++)
            median[i] = median(points[i]);

        //calculate standard deviations
        std = new double[numAgents];
        for(int d = 0; d < numAgents; d++)
            std[d] = std(points[d],mean[d]);      

        //calculate regrets and best of
        regret = new double[numAgents];
        bestOf = new double[numAgents];
        for(int a = 0; a < numAgents; a++)
        {
            double[] p = new double[numAgents];
            for(int i = 0; i < numAgents; i++)
                p[i] = points[i][a];
            double min = minimum(p);
            for(int d = 0; d < numAgents; d++)
            {
                regret[d] += (p[d] - min) / (double) numAgents;
                if(p[d] == min)
                    bestOf[d]++;
            }
        }
    }

    /**
     * Prints out the contents of array a and the names in array s
     * @param a general array of values
     * @param s names that correspond to array a
     */
    public void print(double[] a, String[] s)
    {
        double[] ac = Arrays.copyOf(a,a.length);
        String[] sc = Arrays.copyOf(s,s.length);
        sort(ac,sc);
        System.out.println(Arrays.toString(sc));
        System.out.println(Arrays.toString(ac));
    }

    /**
     * Outputs in a 2D way the contents of the results
     */
    public void printResults()
    {
        for(int i = 0; i < names.length; i++){
            System.out.print(names[i]);
            for(int j = 0; j < points[i].length; j++)
				System.out.print("\t"+points[i][j]);
			System.out.println();
		}
        System.out.println();
    }

    /**
     * Outputs Averages
     */
    public void printAverages()
    {
        System.out.println("Average Points");
        print(mean,names);
    }

    /**
     * Outputs Medians
     */
    public void printMedians()
    {
        System.out.println("Medians");
        print(median, names);
    }

    /**
     * Outputs Standard Deviations
     */
    public void printStandardDev()
    {
        System.out.println("Standard Deviations");
        print(std,names);
    }

    /**
     * Outputs Regrets
     */
    public void printRegret()
    {
        System.out.println("Average Regret");
        print(regret,names);
    }

    /**
     * Outputs the number of times an agent did the best
     */
    public void printBestOf()
    {
        System.out.println("Instances Where Agent Was The Best");
        print(bestOf,names);
    }

    /**
     * Sorts array a and s based on array a
     * @param a general array of values
     * @param s names for the elements in a
     */
    public void sort(double[] a, String[] s)
    {
        for(int i = 0; i < a.length; i++)
        {
            for(int j = 0; j < a.length; j++)
            {
                if(a[j] < a[i])
                {
                    double tempA = a[j];
                    String tempS = s[j];
                    a[j] = a[i];
                    s[j] = s[i];
                    a[i] = tempA;
                    s[i] = tempS;
                }
            }
        }
    }

    /**
     * Computes the median of array a
     * @param a general array of values
     * @return median of a
     */
    public double median(double[] a)
    {
        double[] m = Arrays.copyOf(a,a.length);
        Arrays.sort(m);
        if(m.length%2==0)//even
            return (m[m.length/2-1]+m[m.length/2])/2.0;
        else//odd
            return m[m.length/2];
    }

    /**
     * Computes the standard deviation given an array a and an average
     * @param a general array of values
     * @param avg average of a
     * @return standard deviation of a
     */
    public double std(double[] a, double avg)
    {
        double[] m = Arrays.copyOf(a,a.length);
        double var = 0;
        for (double aM : m)
            var += Math.pow((double) aM - avg, 2);
        return Math.sqrt(var/m.length);
    }

    /**
     * Find the maximum value of a
     * @param a general array of values
     * @return maximum value in a
     */
    public double maximum(double[] a)
    {
        double max = a[0];
        for(int i = 1; i < a.length; i++)
            if(max < a[i])
                max = a[i];
        return max;
    }

    /**
     * Finds the minimum value of a
     * @param a general array of values
     * @return minimum value of a
     */
    public double minimum(double[] a)
    {
        double min = a[0];
        for(int i = 1; i < a.length; i++)
            if(min > a[i])
                min = a[i];
        return  min;
    }

    /*public static void main(String[] args){
        String[] aS = {"Larry","Curly","Moe"};
        String[] dS = {"Bonnie","Clyde",};
        int[][] results = new int[2][3];
        results[0][0] = 9;results[0][1]=7;results[0][2]=6;
        results[1][0] = 2;results[1][1]=3;results[1][2]=6;
        new Analyzer(results,aS,dS);
    }*/
}
