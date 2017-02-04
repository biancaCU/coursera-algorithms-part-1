

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private int[][] gridOpen;
    private int openSites;
    private WeightedQuickUnionUF qu;
    private WeightedQuickUnionUF quBackwash;
    private int dim; //dimension
    private int pTopVirtualNode;
    private int pBottomVirtualNode;
    
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if(n <= 0) throw new IllegalArgumentException();
        dim = n;
        gridOpen = new int[dim][dim];
        openSites = 0;
        pTopVirtualNode = dim*dim;
        pBottomVirtualNode = dim*dim+1;
        qu = new WeightedQuickUnionUF(dim*dim+2); //dim*dim+1 == top, dim*dim+2 == bottom
        quBackwash = new WeightedQuickUnionUF(dim*dim+1);
    }              
    
    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if(row <= 0 || col <=0 || row > dim || col > dim) throw new IndexOutOfBoundsException();
        if(!isOpen(row,col)){
            gridOpen[row-1][col-1] = 1;
            openSites++;
            int pos = getPosition(row,col);
            //if its in the top row connect to virtual top node
            if(row == 1){
                qu.union(pos, pTopVirtualNode);
                quBackwash.union(pos, pTopVirtualNode);
            }
            //if its in the bottom row connect to virtual bottom node
            if(row == dim){
                qu.union((row-1)*dim+(col-1), pBottomVirtualNode);
            }
            //connect to adjacent open sites
            if(row > 1 && isOpen(row-1,col)){
                qu.union(pos, getPosition(row-1,col));
                quBackwash.union(pos, getPosition(row-1,col));
            }
            if(row < dim && isOpen(row+1,col)){
                qu.union(pos, getPosition(row+1,col));
                quBackwash.union(pos, getPosition(row+1,col));
            }
            if(col > 1 && isOpen(row,col-1)){
                qu.union(pos, getPosition(row,col-1));
                quBackwash.union(pos, getPosition(row,col-1));
            }
            if(col < dim && isOpen(row,col+1)){
                qu.union(pos, getPosition(row,col+1));
                quBackwash.union(pos, getPosition(row,col+1));
            }
        }
    }
    
    private int getPosition(int row,int col){
        if(row <= 0 || col <=0 || row > dim || col > dim) throw new IndexOutOfBoundsException();
        return (row-1)*dim+(col-1);
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if(row <= 0 || col <=0 || row > dim || col > dim) throw new IndexOutOfBoundsException();
        return gridOpen[row-1][col-1] == 1;
    }
    
    public boolean isFull(int row, int col){
        if(row <= 0 || col <=0 || row > dim || col > dim) throw new IndexOutOfBoundsException();
        return quBackwash.connected(getPosition(row,col), pTopVirtualNode);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }      

    // does the system percolate?
    public boolean percolates() {
        //does the virtual bottom site connect to the virtual top site?
        return qu.connected(pTopVirtualNode, pBottomVirtualNode);
    }             

    // test client (optional)
    public static void main(String[] args) {
    }   
}