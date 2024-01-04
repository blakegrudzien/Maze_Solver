//Array work manipulating Sedgewick's MaxPQ

/*A is an array of M integers, initially all zero: A[0], A[1], ..., A[M-1]. 
The input describes a sequence of N assignments operations, each of the form 
"A[i]=v". After each assignment, you report the index j such that A[j] is the 
current maximum array value. In case of a tie, report the smallest such j.
You should produce the kth output before reading the (k+1)th assignment.
Hints: we prefer solutions using O(M) space, where M is the array size.  
Note that M may be much smaller than N, the number of assignments. Refer to 
the Canvas Assignment to figure out how to do this, as well as suggestions on I/O to handle slow runtime. */







import java.io.*;
import java.util.StringTokenizer;
import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayMax
{


public static class IndexMaxPQ{
    public int maxN;        // maximum number of elements on PQ
    public int n;           // number of elements on PQ
    public int[] pq;        // binary heap using 1-based indexing
    public int[] qp;        // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
    public int[] keys;      // keys[i] = priority of i

   
    public IndexMaxPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();
        this.maxN = maxN;
        n = 0;
        keys = new int[maxN + 1];    // make this of length maxN??
        pq   = new int[maxN + 1];
        qp   = new int[maxN + 1];                   // make this of length maxN??
        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }

    public boolean isEmpty() {
        return n == 0;
    }


    public boolean contains(int i) {
        validateIndex(i);
        return qp[i] != -1;
    }

    public int size() {
        return n;
    }


    public void insert(int i, int key) {
        validateIndex(i);
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = key;
        swim(n);
    }

    public int maxIndex() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

  
    public int maxKey() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        return keys[pq[1]];
    }

    public int delMax() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        int max = pq[1];
        exch(1, n--);
        sink(1);
        assert pq[n+1] == max;
        qp[max] = -1;        // delete
        keys[max] = -1;    // to help with garbage collection
        pq[n+1] = -1;        // not needed
        return max;
    }

    public int keyOf(int i) {
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        else return keys[i];
    }

    public void changeKey(int i, int key) {
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }


    @Deprecated
    public void change(int i, int key) {
        validateIndex(i);
        changeKey(i, key);
    }



    // throw an IllegalArgumentException if i is an invalid index
    private void validateIndex(int i) {
        if (i < 0) throw new IllegalArgumentException("index is negative: " + i);
        if (i >= maxN) throw new IllegalArgumentException("index >= capacity: " + i);
    }


    private boolean less(int i, int j) {
        if(keys[pq[i]] == keys[pq[j]]){
            return pq[i]>pq[j];
        }
        return keys[pq[i]] < keys[pq[j]];
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(j, j+1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
    
    public void delete(int i) {
        validateIndex(i);
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        int index = qp[i];
        exch(index, n--);
        swim(index);
        sink(index);
        keys[i] = -1;
        qp[i] = -1;
    }


   
} 
    
  
public static void main(String[] args) throws IOException
{

PrintWriter out = new PrintWriter(System.out);

BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
StringTokenizer st = new StringTokenizer(br.readLine());
int M = Integer.parseInt(st.nextToken()),
N = Integer.parseInt(st.nextToken());
     
IndexMaxPQ total = new IndexMaxPQ(M);    

// Loop through the N assignment lines
for (int n=0; n<N; ++n) {
// read the line, parse i and v
st = new StringTokenizer(br.readLine());
int i = Integer.parseInt(st.nextToken()),
v = Integer.parseInt(st.nextToken());
    if(n == 0){
        total.insert(i,v);
    
    }
    else if (total.maxIndex() == i) {
                total.delMax();
                total.insert(i, v);
    } 
    else {
        if(total.contains(i)){
            total.change(i,v);
        }
        else{
            total.insert(i, v);
        }
                
            }
    
    
    if(total.maxKey() == 0){
        out.println(total.maxKey());
    }
    else{
    out.println(total.maxIndex());
    }

}


    out.close();
}

}