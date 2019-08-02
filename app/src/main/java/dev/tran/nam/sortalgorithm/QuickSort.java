package dev.tran.nam.sortalgorithm;

/**
 * Created by NamTran on 4/6/2017.
 */

public class QuickSort {

    public static void main(String[] args) {
        int[] a = {-1,-2,8,2,10,5,12,8,1,15,4,-2,-10,-7,20};
        quickSort(a,0,a.length - 1);
        for (int result : a)
            System.out.print(result + " ");
    }

    public static void quickSort(int[] a ,int i,int j){
        int k;
        int pivotIndex = findPivot(a,i,j);
        if (pivotIndex != -1){
            k = partition(a,i,j,a[pivotIndex]);
            quickSort(a,i,k-1);
            quickSort(a,k,j);
        }
    }

    public static int findPivot(int[] a , int i,int j){
        int k = i+1;
        int FirstKey = a[i];
        while(k<=j && (a[k] == FirstKey)){
            k = k+1;
        }

        if(k>j){
            return -1;
        }else if (a[k] > FirstKey){
            return k;
        }else{
            return i;
        }
    }

    public static int partition(int[] a,int i,int j,int pivot){
        int L,R;
        L=i;
        R=j;
        while(L<R){
            while(a[L] < pivot){
                L=L+1;
            }
            while(a[R] >= pivot){
                R=R-1;
            }

            if(L<R){
                int temp = a[R];
                a[R]= a[L];
                a[L]=temp;
            }
        }
        return L;
    }
}
