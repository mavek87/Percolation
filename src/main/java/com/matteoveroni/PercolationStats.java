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
 */
public class PercolationStats {

	private final int n;
	private final int trials;
	private double[] results;

	public PercolationStats(int n, int trials) throws IllegalArgumentException {
		if (n < 1 || trials < 1) {
			throw new IllegalArgumentException();
		}
		this.trials = trials;
		this.n = n;

		results = new double[trials];

		for (int i = 0; i < trials; i++) {
			Percolation percolation = new Percolation(n);
			results[i] = performTest(percolation);
//			System.out.println("Result [" + i + "]= " + results[i]);
		}
	}

	private double performTest(Percolation percolation) {
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
		double result = ((double) nmbOpen / (double) (n * n));
//		System.out.println("result " + result);
		return result;
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
//			System.out.println("First = " + firstNumericArgument);
//			System.out.println("Second = " + secondNumericArgument);
			PercolationStats stats = new PercolationStats(firstNumericArgument, secondNumericArgument);
			System.out.println("mean\t= " + stats.mean());
			System.out.println("stddev\t= " + stats.stddev());
			System.out.println("95% confidence interval\t= " + stats.confidenceLo() + ", " + stats.confidenceHi());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
