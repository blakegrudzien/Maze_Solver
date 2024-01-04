//Finding a cycle in a Graph




/*Taking courses is hard. Figuring out what courses to take is even harder. 
Especially with all these prerequisites...The CS department at the University 
of Flugeldufel recently found out that their CS205 course had CS201 as a prerequisite, 
which in required MATH160 as a prerequisite, but you could only take MATH160 when you 
had completed CS205. A real catch-22... Recent reports suggest that state of affairs frustrated 
some students, that enrollment in all these four courses plummeted, and almost no 
CS majors have been able to graduate from U. Flugeldufel!As Emory updates its curricula, 
it wishes to avoid the same fate. Given a list of courses with their prerequisites, can you
 figure out if there are some courses that are actually impossible for students to take?
 */





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
 
 class Result {
     private final int V;
     private final List<List<Integer> > adj;
  
     public Result(int V)
     {
         this.V = V;
         adj = new ArrayList<>(V);
  
         for (int i = 0; i < V; i++)
             adj.add(new LinkedList<>());
     }
  
     
     public void addPrereq(int source, int dest)
     {
         adj.get(source).add(dest);
     }
  
     public boolean isCyclic()
     {
         
         boolean[] visited = new boolean[V];
         boolean[] stack = new boolean[V];
  
         
         for (int i = 0; i < V; i++)
             if (Cyclic_Helper(i, visited, stack)){
                 return true;
             }
         
         return false;
     }  
         private boolean Cyclic_Helper(int i, boolean[] visited, boolean[] stack){
     
  
         if (stack[i]){
             return true;
         }
  
         if (visited[i]){
             return false;
         }
  
         visited[i] = true;
  
         stack[i] = true;
         List<Integer> children = adj.get(i);
  
         for (Integer c : children)
             if (Cyclic_Helper(c, visited, stack))
                 return true;
  
         stack[i] = false;
  
         return false;
     }
     // Complete the 'isPossible' function below.
     public boolean isPossible() {
         return !isCyclic();
     }
   
 }
 public class Solution {
     public static void main(String[] args) throws IOException {
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
         String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");
 
         int numCourses = Integer.parseInt(firstMultipleInput[0]);
         int numPrereqs = Integer.parseInt(firstMultipleInput[1]);
 
         Result result = new Result(numCourses);
         IntStream.range(0, numPrereqs).forEach(numPrereqsItr -> {
             try {
                 String[] secondMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");
                 int prereq = Integer.parseInt(secondMultipleInput[0]);
                 int course = Integer.parseInt(secondMultipleInput[1]);
                
                 result.addPrereq(prereq, course);
             } catch (IOException ex) {
                 throw new RuntimeException(ex);
             }
         });
         if (result.isPossible())
             System.out.println("possible");
         else
             System.out.println("IMPOSSIBLE");
 
         bufferedReader.close();
     }
 }