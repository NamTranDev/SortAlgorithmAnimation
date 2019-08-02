package dev.tran.nam.sortalgorithm;

/**
 * Created by NamTran on 4/6/2017.
 */

public class BinSort {

    public static void main(String[] args) {
        int[] a = {5,6,2,2,10,12,9,99,9};
        binSort(a,maxValue(a));
        for (int result : a){
            System.out.print(result + " ");
        }
    }

    public static int maxValue(int[] a){
        int maxValue = 0;
        for (int i = 0;i<a.length;i++){
            if (a[i] > maxValue)
                maxValue = a[i];
        }
        return maxValue;
    }

    public static void binSort(int[] a,int maxValue){
        int[] temp = new int[maxValue+1];
        for (int i=0;i<a.length;i++){
            temp[a[i]]++;
        }

        int outpost = 0;
        for (int i = 0; i< temp.length;i++){
            for (int j = 0;j<temp[i];j++){
                a[outpost++]=i;
            }
        }
    }
}
