package dev.tran.nam.sortalgorithm;

/**
 * Created by NamTran on 4/6/2017.
 */

public class SampleSort {

    public static void main(String[] args) {
        System.out.print("Selection Sort : ");
        int[] a = {5,6,2,2,10,12,9,99,9};
        selectionSort(a);
        for (int result : a){
            System.out.print(result + " ");
        }

        System.out.println();

        System.out.print("Insertion Sort : ");

        int[] b = {5,6,2,-29,2,10,12,9,10,9,3,-8};
        insertionSort(b);
        for (int result : b){
            System.out.print(result + " ");
        }

        System.out.println();

        System.out.print("Bubble Sort : ");

        int[] c = {5,6,2,2,10,12,-26,9,10,9,3,-5};
        bubbleSort(c);
        for (int result : c){
            System.out.print(result + " ");
        }
    }

    public static void selectionSort(int[] a){
        for (int i=0;i<a.length;i++){
            int begin = i;
            for (int j=i+1;j<a.length;j++){
                if (a[begin]>a[j]){
                    begin = j;
                }
            }
            int temp = a[begin];
            a[begin]=a[i];
            a[i] = temp;
        }
    }

    public static void insertionSort(int[] a){
        for (int i=1;i<a.length;i++){
            int j = i;
            while (j > 0 && a[j] < a[j-1]){
                int temp = a[j-1];
                a[j-1] = a[j];
                a[j] = temp;
                j = j-1;
            }
        }
    }

    public static void bubbleSort(int[] a){
        for (int i=0; i < a.length; i++){
            for (int j = a.length-1; j>i; j--){
                if (a[j] < a[j-1]){
                    int temp = a[j-1];
                    a[j-1]=a[j];
                    a[j] = temp;
                }
            }
        }
    }
}
