package dev.tran.nam.sortalgorithm;

/**
 * Created by NamTran on 4/1/2017.
 */

public class HeapSort {

    public static void main(String[] args) {
        int[] a = {5,6,2,2,10,12,9,10,9,3,-5};
        heapSortIncrease(a);
        //heapSortDecrease(a);
        for (int result : a){
            System.out.print(result + " ");
        }
    }

    public static void PushDown(int[] a, int first, int last) {
        int r = first;
        while (r <= (last / 2)) {
            if ((last/2) == r ) {
                if (a[r-1] > a[last-1]){
                    int temp = a[last - 1];
                    a[last - 1] = a[r-1];
                    a[r-1] = temp;
                }
                r = last;
            } else if (a[r-1] > a[2 * r -1] && a[2 * r - 1] <= a[2 * r]) {
                int temp = a[2 * r -1];
                a[2 * r -1] = a[(r-1)];
                a[(r-1)] = temp;
                r = r * 2;
            } else if (a[(r-1)] > a[r*2] && a[r*2] < a[2 * r -1]) {
                int temp = a[r*2];
                a[r*2] = a[(r-1)];
                a[(r-1)] = temp;
                r = 2 * r + 1;
            } else {
                r = last;
            }
        }
    }

    private static void PushUp(int[] a, int first, int last) {
        int r = first;
        while (r <= (last / 2)) {
            if (last==2*r) {
                if (a[r-1] < a[last-1]){
                    int temp = a[last - 1];
                    a[last - 1] = a[r-1];
                    a[r-1] = temp;
                }
                r = last;
            } else if (a[r-1] < a[2 * r -1] && a[2 * r - 1] >= a[2 * r]) {
                int temp = a[2 * r -1];
                a[2 * r -1] = a[(r-1)];
                a[(r-1)] = temp;
                r = r * 2;
            } else if (a[(r-1)] < a[r*2] && a[r*2] > a[2 * r -1]) {
                int temp = a[r*2];
                a[r*2] = a[(r-1)];
                a[(r-1)] = temp;
                r = 2 * r + 1;
            } else {
                r = last;
            }
        }
    }

    public static void heapSortIncrease(int[] a){
        for (int i = a.length/2; i > 0;i--){
            PushDown(a,i,a.length);
        }

        for (int i = a.length;i>1;i--){
            int temp = a[i-1];
            a[i-1] = a[0];
            a[0] = temp;
            PushDown(a,1,i-1);
        }
    }

    public static void heapSortDecrease(int[] a){
        for (int i = a.length/2; i > 0;i--){
            PushUp(a,i,a.length);
        }

        for (int i = a.length;i>1;i--){
            int temp = a[i-1];
            a[i-1] = a[0];
            a[0] = temp;
            PushUp(a,1,i-1);
        }
    }


}