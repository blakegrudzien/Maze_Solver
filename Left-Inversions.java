//Array work using Sedgewicks Inversions.java and adding a pair class




/*Consider insertion sort. For each value A[i] (for i=0, 1, 2, ...), we "insert" it in its correct 
position among the previous values. The inserted A[i] moves L[i] steps to its left, where L[i] is 
the number of larger values to its left. Note those larger values were also to its left in the 
original array.We could compute these L[i] values as a side-effect of insertion sort. However, insertion 
sort can take quadratic time, which is too slow. Your goal is to compute them by a faster method.*/


import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Solution {  
    static class Pair {
        int v;
        int p; 
        Pair(int v, int p) { this.v = v; this.p = p; }
    }
    
    public static int[] computeL(int[] A) {
        int[] answer = new int[A.length];
        Pair[] P = new Pair[A.length];
        for(int i = 0;i<A.length;i++){
            P[i] = new Pair(A[i], i);
        }
        count(P, answer);
        return answer;
    }
    
    public static void count(Pair[] a, int[] answer) {
        Pair[] aux = a.clone();
        count(a, aux, 0, a.length - 1, answer);
        return;
    }
    
    private static void count(Pair[] a, Pair[] aux, int lo, int hi, int[] answer) {
        if (hi <= lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        count(a, aux, lo, mid, answer);
        count(a, aux, mid+1, hi, answer);
        merge(a, aux, lo, mid, hi, answer);
        return;
    }

    private static void merge(Pair[] a, Pair[] aux, int lo, int mid, int hi, int[] answer) {

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
    
        int i = lo;
        int j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if(i > mid){
                a[k] = aux[j++];
            }
            else if (j > hi){
                a[k] = aux[i++];
            }
            else if (aux[j].v < aux[i].v) { 
                a[k] = aux[j++]; 
                answer[a[k].p] += (mid-i+1);       
            }
            else{      
                a[k] = aux[i++];
            }
        }
        return;
    }
    
    
    
    public static void main(String[] args) throws IOException {
        // Read input array A. We avoid java.util.Scanner, for speed.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // first line
        int[] A = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine()); // second line
        for (int i=0; i<N; ++i)
            A[i] = Integer.parseInt(st.nextToken());

        // Solve the problem!
        int[] L = computeL(A);

        // Print the output array L, again buffered for speed.
        PrintWriter out = new PrintWriter(System.out);
        out.print(L[0]);
        for (int i=1; i<N; ++i)
            // System.out.print here would be too slow!
            out.print(" " + L[i]);      
        out.close();
    }
}