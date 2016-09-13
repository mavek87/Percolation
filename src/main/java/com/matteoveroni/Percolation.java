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
    private final boolean[][] openSitesGrid;
    private final int[][] incrementalSitesCoordinates;

    private final int virtualTop;
    private final int virtualBottom;

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
        unionFind = new WeightedQuickUnionUF(n * n + 2);
        openSitesGrid = new boolean[n + 1][n + 1];
        incrementalSitesCoordinates = new int[n + 1][n + 1];

        virtualTop = n * n + 1;
        virtualBottom = n * n;

        int i = 0;
        for (int y = 1; y < n + 1; y++) {
            for (int x = 1; x < n + 1; x++) {
                openSitesGrid[x][y] = false;
                incrementalSitesCoordinates[x][y] = i;
                if (y == 1) {
                    unionFind.union(virtualTop, incrementalSitesCoordinates[x][y]);
                } else if (y == n) {
                    unionFind.union(virtualBottom, incrementalSitesCoordinates[x][y]);
                }
                System.out.print("(x=" + x + ",y=" + y + "):" + incrementalSitesCoordinates[x][y] + "\t");
                i++;
            }
            System.out.println("\n");
        }
        System.out.println("\n");
        N = n;

        System.out.println("Virtual top => " + virtualTop);
        System.out.println("Virtual bottom => " + virtualBottom);
        
        
        System.out.println("1,1 è connesso con 1,1? => " + unionFind.connected(incrementalSitesCoordinates[1][1], incrementalSitesCoordinates[1][1]));
        System.out.println("1,1 è connesso con 1,2? => " + unionFind.connected(incrementalSitesCoordinates[1][1], incrementalSitesCoordinates[1][2]));
        System.out.println("1,1 è connesso con 3,1? => " + unionFind.connected(incrementalSitesCoordinates[1][1], incrementalSitesCoordinates[3][1]));

        System.out.println("padre di 3,1 è => " + unionFind.find(incrementalSitesCoordinates[1][1]) + " padre di 2,1 è => " + unionFind.find(incrementalSitesCoordinates[2][1]));

//        System.out.println("1 è connesso con n+1? => " + unionFind.connected(1, n+1));
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
            openSitesGrid[x][y] = true;

            // TOP => RIGHT => BOTTOM => LEFT
            final int xNeighbours[] = {x, x + 1, x, x - 1};
            final int yNeighbours[] = {y + 1, y, y - 1, y};

            for (int index = 0; index < 4; index++) {
                try {
                    if (isOpen(xNeighbours[index], yNeighbours[index])) {
                        unionFind.union(incrementalSitesCoordinates[x][y], incrementalSitesCoordinates[xNeighbours[index]][yNeighbours[index]]);
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
        return openSitesGrid[x][y];
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
        if (isOpen(x, y)) {
            if (unionFind.connected(unionFind.find(incrementalSitesCoordinates[x][y]), virtualTop)) {
                return true;
            }
        }
        return false;
    }

    private void checkIfCoordinatesAreInsideBounds(int x, int y) throws IndexOutOfBoundsException {
        if (x < 1 || y < 1 || x > N || y > N) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(6);
        WeightedQuickUnionUF unionFind = new WeightedQuickUnionUF(6);

        percolation.open(1, 1);

    }

    private void printMatrix() {
        for (int y = 1; y < N + 1; y++) {
            for (int x = 1; x < N + 1; x++) {
                System.out.print("(x=" + x + ",y=" + y + "):" + openSitesGrid[x][y] + "\t");
            }
            System.out.println("\n");
        }
        System.out.println("\n");
    }
}
