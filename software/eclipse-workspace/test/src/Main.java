
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    private static int[][] curMap; 
    private static int[][] maxMap; 
    private static int[][] data; 
    private static int n;
    private static int m;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        m = in.nextInt();
        int k = in.nextInt();
        curMap = new int[n][m];
        maxMap = new int[n][m];
        data = new int[k][4];
        for (int i = 0; i < k; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int z = in.nextInt();
            String tStr = in.next();
            int t = TimeStrToLong(tStr);
            data[i][0] = x-1;
            data[i][1] = y-1;
            data[i][2] = z;
            data[i][3] = t;
        }
        in.close();
        Arrays.sort(data, new Comparator<int[]>() {

            @Override
            public int compare(int[] x, int[] y) {
                if(x[3] < y[3]){
                    return -1;
                } else if(x[3] > y[3]){
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        int lightCount = 0;
        int maxCount = 0;
        for (int i = 0; i < data.length; i++) {
            int x = data[i][0];
            int y = data[i][1];
            if(data[i][2]==0) {
                curMap[x][y] = curMap[x][y]+1;
                if(curMap[x][y]==1) {
                    lightCount++;
                    if(maxCount<=lightCount) {
                        maxCount = lightCount;
                        copyList();
                    }
                }
            }else {
                curMap[x][y] = curMap[x][y]-1;
                if(curMap[x][y]==0)lightCount--;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(maxMap[i][j]>0?1:0);
            }
            if(i<n-1) {
            System.out.println("");
            }
        }
    }
    private static void copyList() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                maxMap[i][j] = curMap[i][j]; 
            }
        }
    }
    private static int TimeStrToLong(String str) {
        String []s = str.split(":");
        int result = (int)(Integer.valueOf(s[0])*3600
                +Integer.valueOf(s[1])*60+Double.valueOf(s[2]))*1000;
        return result;
    }
}
