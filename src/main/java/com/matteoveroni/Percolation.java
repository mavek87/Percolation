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
    private final int[][] incrementalSitesMatrix;

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

        N = n;
        unionFind = new WeightedQuickUnionUF(N * N + 2);
        openSitesGrid = new boolean[N + 1][N + 1];
        incrementalSitesMatrix = new int[N + 1][N + 1];

        virtualTop = N * N + 1;
        virtualBottom = N * n;

        int i = 0;
        for (int y = 1; y < n + 1; y++) {
            for (int x = 1; x < n + 1; x++) {
                openSitesGrid[x][y] = false;
                incrementalSitesMatrix[x][y] = i;
                if (y == 1) {
                    unionFind.union(virtualTop, incrementalSitesMatrix[x][y]);
                } else if (y == n) {
                    unionFind.union(virtualBottom, incrementalSitesMatrix[x][y]);
                }
                i++;
            }
        }
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
                        int neighbour = incrementalSitesMatrix[xNeighbours[index]][yNeighbours[index]];
                        unionFind.union(incrementalSitesMatrix[x][y], neighbour);
                    }
                } catch (Exception ex) {
                }
            }
        }
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
        boolean isSiteFull = false;
        if (isOpen(x, y)) {
            int siteRoot = unionFind.find(incrementalSitesMatrix[x][y]);
            isSiteFull = unionFind.connected(siteRoot, virtualTop);
        }
        return isSiteFull;
    }
    
    /**
     * Check if the system percolates
     * 
     * @return if the system percolates or not 
     */
    public boolean percolates() {
        return unionFind.connected(virtualTop, virtualBottom);
    }


    private void checkIfCoordinatesAreInsideBounds(int x, int y) throws IndexOutOfBoundsException {
        if (x < 1 || y < 1 || x > N || y > N) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(6);
        percolation.printTest();

        WeightedQuickUnionUF unionFind = new WeightedQuickUnionUF(6);

//        percolation.open(1, 1);
    }

    private void printTest() {
        System.out.println("*********** GRAPHICAL TEST ***********");
        printOpenSitesMatrix();
        printIncrementalSitesMatrix();
        System.out.println("N => " + N);
        System.out.println("Virtual top => " + virtualTop);
        System.out.println("Virtual bottom => " + virtualBottom);

        System.out.println("is 1,1 connected with 1,1? => " + unionFind.connected(incrementalSitesMatrix[1][1], incrementalSitesMatrix[1][1]));
        System.out.println("is 1,1 connected with 1,2? => " + unionFind.connected(incrementalSitesMatrix[1][1], incrementalSitesMatrix[1][2]));
        System.out.println("is 1,1 connected with 3,1? => " + unionFind.connected(incrementalSitesMatrix[1][1], incrementalSitesMatrix[3][1]));

        System.out.println("1,3 father => " + unionFind.find(incrementalSitesMatrix[1][3]) + " 2,1 father => " + unionFind.find(incrementalSitesMatrix[2][1]));
        System.out.println("**************************************\n");

    }

    private void printIncrementalSitesMatrix() {
        for (int y = 1; y < N + 1; y++) {
            for (int x = 1; x < N + 1; x++) {
                System.out.print("(x=" + x + ",y=" + y + "):" + incrementalSitesMatrix[x][y] + "\t");
            }
            System.out.println("\n");
        }
        System.out.println("\n");
    }

    private void printOpenSitesMatrix() {
        for (int y = 1; y < N + 1; y++) {
            for (int x = 1; x < N + 1; x++) {
                System.out.print("(x=" + x + ",y=" + y + "):" + openSitesGrid[x][y] + "\t");
            }
            System.out.println("\n");
        }
        System.out.println("\n");
    }
}
