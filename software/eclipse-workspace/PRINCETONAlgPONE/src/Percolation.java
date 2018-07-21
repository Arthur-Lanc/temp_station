//import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF wquuf;
    private WeightedQuickUnionUF wquuf2;
    private int n;
    private int[] openStatusArr;
    private int openSiteNum;
    
   public Percolation(int n) {
       if (n <= 0) {
           throw new IllegalArgumentException();
       }
       
       wquuf = new WeightedQuickUnionUF(n*n+2);
       wquuf2 = new WeightedQuickUnionUF(n*n+2);
       this.n = n;
       openStatusArr = new int[n*n+2];
       for (int i=0; i<n*n+2; i++) {
           openStatusArr[i] = 0;
       }
       
       openStatusArr[n*n] = 1;
       openStatusArr[n*n+1] = 1;
       openSiteNum = 0;
   }
   
   public void open(int row, int col) {
       if (row < 1 || row > n) {
           throw new IllegalArgumentException();
       }
       
       if (col < 1 || col > n) {
           throw new IllegalArgumentException();
       }
       
       if (!isOpen(row,col)){
           int curr = (row-1)*n+col-1;
           openStatusArr[curr] = 1;
           openSiteNum += 1;
           
           if (curr >= 0 && curr < n) {
               wquuf.union(n*n, curr);
               wquuf2.union(n*n, curr);
           }
           
           if (curr >= (n-1)*n && curr < (n-1)*n+n) {
               wquuf2.union(n*n+1, curr);
           }
           
           int a = (row-2)*n+col-1;
           int b= (row)*n+col-1;
           int c = (row-1)*n+col-2;
           int d= (row-1)*n+col;
           if (row > 1 && a >= 0 && a <= n*n-1 && openStatusArr[a] == 1){
               wquuf.union(a, curr);
               wquuf2.union(a, curr);
           }
           if (row < n && b >= 0 && b <= n*n-1 && openStatusArr[b] == 1){
               wquuf.union(b, curr);
               wquuf2.union(b, curr);
           }
           if (col > 1 && c >= 0 && c <= n*n-1 && openStatusArr[c] == 1){
               wquuf.union(c, curr);
               wquuf2.union(c, curr);
           }
           if (col < n && d >= 0 && d <= n*n-1 && openStatusArr[d] == 1){
               wquuf.union(d, curr);
               wquuf2.union(d, curr);
           }
           
       }
   }
   
   public boolean isOpen(int row, int col) {
       if (row < 1 || row > n) {
           throw new IllegalArgumentException();
       }
       
       if (col < 1 || col > n) {
           throw new IllegalArgumentException();
       }
       
       if (openStatusArr[(row-1)*n+col-1] == 1){
           return true;
       }
       else {
           return false;
       }
   }
   
   public boolean isFull(int row, int col) {
       if (row < 1 || row > n) {
           throw new IllegalArgumentException();
       }
       
       if (col < 1 || col > n) {
           throw new IllegalArgumentException();
       }
       
       int curr = (row-1)*n+col-1;
       
       if(wquuf.connected(curr, n*n)) {
            return true;
       }
       else {
            return false;
       }
   }
   
   public int numberOfOpenSites() {
       return openSiteNum;
   }
   
   public boolean percolates() {
        if(wquuf2.connected(n*n+1, n*n)) {
            return true;
        }
        else {
            return false;
        }
       // for(int i = (n-1)*n; i < (n-1)*n+n; i++) {
       //     if (wquuf.connected(i, n*n)) {
       //         return true;
       //     }
       // }
       // return false;
   }
   
   private void allFind() {
       for (int i=0; i<n*n+2; i++) {
           if (i%n == 0) {
               System.out.println();
           }
           System.out.print(wquuf.find(i)+" ");
       }
   }
   
   public static void main(String[] args) {
//     System.out.println("start");
//     int n = 200;
//     Percolation pcl = new Percolation(n);
////       System.out.println(pcl.openStatusArr);
////       System.out.println(pcl.openStatusArr.size());
////       pcl.open(1, 1);
////       pcl.open(2, 1);
////       System.out.println();
////       pcl.allFind();
////       System.out.println(pcl.openStatusArr);
////       System.out.println(pcl.percolates());
//     
//     while(true) {
//         int row = StdRandom.uniform(1,n+1);
//         int col = StdRandom.uniform(1,n+1);
////           System.out.println("row: " + row+"col: " + col);
//         pcl.open(row, col);
//         if (pcl.percolates()){
//             break;
//         }
//     }
//     System.out.println(pcl.numberOfOpenSites());
//     System.out.println(n*n);
//     System.out.println(((double)pcl.numberOfOpenSites())/(n*n));
//     
//     System.out.println("end");
       
       int n = 6;
       Percolation pcl = new Percolation(n);
//   System.out.println(Arrays.toString(pcl.openStatusArr));
   System.out.println(pcl.openStatusArr.length);
//   pcl.open(1, 3);
//   pcl.open(2, 3);
//   pcl.open(3, 3);
//   pcl.open(3, 1);
   
   pcl.open(1, 6);
   pcl.open(2, 6);
   pcl.open(3, 6);
   pcl.open(4, 6);
   pcl.open(5, 6);
   pcl.open(5, 5);
   pcl.open(4, 4);
   pcl.open(3, 4);
   pcl.open(2, 4);
   pcl.open(2, 3);
   pcl.open(2, 2);
   pcl.open(2, 1);
   pcl.open(3, 1);
   pcl.open(4, 1);
   pcl.open(5, 1);
   pcl.open(5, 2);
   pcl.open(6, 2);
   pcl.open(5, 4);
   System.out.println(pcl.isFull(5, 4));
   pcl.allFind();
   System.out.println();
   System.out.println();
   System.out.println(pcl.percolates());
   }
}
