package com.matteoveroni;

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

	private final int[][] matrix;

	private final int virtualTop = Integer.MAX_VALUE;
	private final int virtualBottom = Integer.MIN_VALUE;

	/**
	 * Create n-by-n grid, with all sites blocked
	 *
	 * @param n squared grid dimensions
	 */
	public Percolation(int n) {
		matrix = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = -1;
			}
		}
	}

	/**
	 * Open site (row i, column j) if it is not
	 *
	 * @param i number of rows
	 * @param j number of columns
	 */
	public void open(int i, int j) {

	}

	/**
	 * Is site (row i, column j) open?
	 *
	 * @param i row coordinate
	 * @param j column coordinate
	 * @return if the site is open or not
	 */
	public boolean isOpen(int i, int j) {

		// aperto se ci sono altri full intorno
		try {

			boolean atLeastANeighbourIsOpen = false;

			// DOWN
			if (isFull(i, j - 1)) {
				return true;
			}

			// UP
			if (isFull(i, j + 1)) {
				return true;
			}

			// RIGHT
			if (isFull(i + 1, j)) {
				return true;
			}

			// LEFT
			if (isFull(i - 1, j)) {
				return true;
			}

		} catch (Exception ex) {
		}

		return false;
	}

	/**
	 * Is site (row i, column j) full?
	 *
	 * @param i row coordinate
	 * @param j column coordinate
	 * @return if the site is full or not
	 *
	 * @throws IllegalArgumentException if row or column is out of bound
	 */
	public boolean isFull(int i, int j) throws IllegalArgumentException {
		if (i < 0 || j < 0) {
			throw new IllegalArgumentException();
		}
		return matrix[i][j] > 0;
	}

	public static void main(String[] args) {

	}
}
