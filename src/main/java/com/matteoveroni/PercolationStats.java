package com.matteoveroni;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 *
 * @author Matteo Veroni
 *
 * API:
 *
 * public PercolationStats(int n, int trials) // perform trials independent
 * experiments on an n-by-n grid
 *
 * public double mean() // sample mean of percolation threshold
 *
 * public double stddev() // sample standard deviation of percolation threshold
 *
 * public double confidenceLo() // low endpoint of 95% confidence interval
 *
 * public double confidenceHi() // high endpoint of 95% confidence interval
 *
 *
 */
public class PercolationStats {

    private final int n;
    private final int trials;
    private final double[] results;

    public PercolationStats(int n, int trials) throws IllegalArgumentException {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        this.n = n;

        // da controllare trials o trials+1
        Percolation percolation = new Percolation(n);
        results = new double[trials];

        for (int i = 0; i < trials; i++) {
            results[i] = performTest(percolation, n);
        }
    }

    private double performTest(Percolation percolation, int n) {
        int nmbOpen = 0;
        while (!percolation.percolates()) {
            // Choose a site (row i, column j) uniformly at random among all blocked sites.
            int xRandom = StdRandom.uniform(1, n + 1);
            int yRandom = StdRandom.uniform(1, n + 1);
            // Open the site (row i, column j). 
            if (!percolation.isOpen(xRandom, yRandom)) {
                percolation.open(xRandom, yRandom);
                nmbOpen++;
            }
        }
//        DA ELIMINARE
//        System.out.println("nbm open => " + nmbOpen);
//        System.out.println("n/nmbOpen => " + (double) nmbOpen / (n * n));
        return (double) (nmbOpen / (n * n));
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return StdStats.min(results);
    }

    public double confidenceHi() {
        return StdStats.max(results);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }

        String firstArgument = args[0];
        String secondArgument = args[1];
        try {
            int firstNumericArgument = Integer.parseInt(firstArgument);
            int secondNumericArgument = Integer.parseInt(secondArgument);
            PercolationStats stats = new PercolationStats(firstNumericArgument, secondNumericArgument);
            System.out.println("mean\t= " + stats.mean());
            System.out.println("stddev\t= " + stats.stddev());
            System.out.println("95% confidence interval\t= " + stats.confidenceLo() + ", " + stats.confidenceHi());
        } catch (Exception ex) {
        }
    }
}
