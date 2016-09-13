package com.matteoveroni;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 *
 * @author Matteo Veroni
 *
 * API:
 *
 * public Percolation(int n) // create n-by-n grid, with all sites blocked
 *
 * public void open(int i, int j) // open site (row i, column j) if it is not
 * open already
 *
 * public boolean isOpen(int i, int j) // is site (row i, column j) open
 *
 * public boolean isFull(int i, int j) // is site (row i, column j) full?
 *
 * public boolean percolates() // does the system percolate?
 *
 * public static void main(String[] args) // test client (optional)
 *
 */
public class Percolation {

	private final int N;
	private final int[][] sitesMatrix;
	private final int[][] incrementalCoordinates;

	private final WeightedQuickUnionUF unionFind;

	/**
	 * Create n-by-n grid, with all sites blocked
	 *
	 * @param n squared grid dimensions
	 */
	public Percolation(int n) throws IllegalArgumentException {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		N = n;
		sitesMatrix = new int[n + 1][n + 1];
		incrementalCoordinates = new int[n + 1][n + 1];
		int i = 0;
		for (int y = 1; y < n + 1; y++) {
			for (int x = 1; x < n + 1; x++) {
				incrementalCoordinates[x][y] = i;
				sitesMatrix[x][y] = -1;
				System.out.print("(x=" + x + ",y=" + y + "):" + sitesMatrix[x][y] + "\t");
				i++;
			}
			System.out.println("\n");
		}
		System.out.println("\n");
		unionFind = new WeightedQuickUnionUF(n * n);
	}

	/**
	 * Open site (row x, column y) if it is not
	 *
	 * @param x number of rows
	 * @param y number of columns
	 */
	public void open(int x, int y) throws IllegalArgumentException {
		checkIfCoordinatesAreInsideBounds(x, y);
		if (!isOpen(x, y)) {
			sitesMatrix[x][y] = incrementalCoordinates[x][y];

			// TOP => RIGHT => BOTTOM => LEFT
			final int xNeighbours[] = {x, x + 1, x, x - 1};
			final int yNeighbours[] = {y + 1, y, y - 1, y};

			for (int index = 0; index < 4; index++) {
				try {
					if (isOpen(xNeighbours[index], yNeighbours[index])) {
//						int openNeighbourRootValue = unionFind.find();
						System.out.println("a=> " + sitesMatrix[xNeighbours[index]][yNeighbours[index]]);
						unionFind.union(sitesMatrix[x][y], sitesMatrix[xNeighbours[index]][yNeighbours[index]]);
//						sitesMatrix[x][y] = openNeighbourRootValue;
					}
				} catch (Exception ex) {
				}
			}
		}

		printMatrix();
	}

	/**
	 * Is site (row x, column y) open?
	 *
	 * @param x row coordinate
	 * @param y column coordinate
	 * @return if the site is open or not
	 */
	public boolean isOpen(int x, int y) throws IllegalArgumentException {
		checkIfCoordinatesAreInsideBounds(x, y);
		return sitesMatrix[x][y] >= 0;
	}

	/**
	 * Is site (row x, column y) full?
	 *
	 * @param x row coordinate
	 * @param y column coordinate
	 * @return if the site is full or not
	 *
	 * @throws IllegalArgumentException if row or column is out of bound
	 */
	public boolean isFull(int x, int y) throws IllegalArgumentException {
		checkIfCoordinatesAreInsideBounds(x, y);
		throw new UnsupportedOperationException();
	}

	private void checkIfCoordinatesAreInsideBounds(int x, int y) throws IndexOutOfBoundsException {
		if (x < 1 || y < 1 || x > N || y > N) {
			throw new IndexOutOfBoundsException();
		}
	}

	public static void main(String[] args) {
		Percolation percolation = new Percolation(7);
//		WeightedQuickUnionUF unionFind = new WeightedQuickUnionUF(7);
		percolation.open(1, 1);
		percolation.open(1, 2);
		percolation.open(4, 1);
		percolation.open(4, 2);
		percolation.open(4, 3);
		percolation.open(4, 4);
		percolation.open(6, 6);
	}

	private void printMatrix() {
		for (int y = 1; y < N + 1; y++) {
			for (int x = 1; x < N + 1; x++) {
				System.out.print("(x=" + x + ",y=" + y + "):" + sitesMatrix[x][y] + "\t");
			}
			System.out.println("\n");
		}
		System.out.println("\n");
	}
}
