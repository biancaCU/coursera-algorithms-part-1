import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.lang.Math;

public class PercolationStats {
    private double[] thresholds;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        thresholds = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!p.isOpen(row, col)) p.open(row, col);
            }
            thresholds[i] = (double) p.numberOfOpenSites() / (double) (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddevp(thresholds);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - (1.96 * this.stddev()) / Math.sqrt(thresholds.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (1.96 * this.stddev()) / Math.sqrt(thresholds.length);
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean\t\t\t= " + ps.mean());
        System.out.println("stddev\t\t\t= " + ps.stddev());
        System.out.println("95% confidence interval\t= [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
