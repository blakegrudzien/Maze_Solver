//A form of Dijkstra's that finds  and quantifies which nodes in a graph are more central than others




/*There is something fundamental about how some nodes are more central 
than others in a graph. We can quantify how different vertices influence 
each node differently based on their proximity by defining a closeness centrality 
measure. This specific way of measuring centrality has a real-world analog. 
For example, consider a subway network, where each subway station is considered 
to be a vertex and the railway connecting different subway stations is considered 
as an edge. Then a central subway station typically has short distances from the other subway stations, on average. 
In this homework, we will rank the importance of vertices in terms of closeness centrality. 
Specifically, in an edge-weighted directed graph, the closeness centrality of vertex v is defined as:
C(v) = (SIGMA)(u!=v)  of (n-1)/distance(u,v) where  denotes the length of the shortest path from vertex u
 to vertex v. Here n means the number of vertices. (Note that this is a sum of reciprocals, unlike some other definitions). */





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
    
    
    public class Node{
        int num; 
        HashMap<Node, Integer> map = new HashMap<>();
        int num_Nodes;
        double dist;
        
        
        Node(int num,int total ){
            this.num = num;
            map.put(this,0);
            this.num_Nodes = total;  
            this.dist = Double.POSITIVE_INFINITY;
        }
        
   
        public double centrality(){
            
            double total = 0;
            
            HashMap<Node, Double> shortest = new HashMap<>();
            PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n->n.dist));
            pq.add(this);
            this.dist = 0;
            while(!pq.isEmpty()){
                Node temp = pq.poll();
                if(shortest.containsKey(temp)){
                    continue;
                }
                shortest.put(temp,temp.dist);
                
                for (Map.Entry<Node,Integer> entry : temp.map.entrySet()){
                    Node neighbor = entry.getKey();
                    int weight = entry.getValue();
                    if(!shortest.containsKey(neighbor)){
                        double alt = temp.dist+weight;
                        if(alt<neighbor.dist){
                            neighbor.dist = alt;
                            pq.add(neighbor);
                        }
                    }        
                }           
            }
            for (Node key : shortest.keySet()) {
                    if(shortest.get(key)!=0 && shortest.get(key) != Double.POSITIVE_INFINITY){
                       total += (1.0)/shortest.get(key); 
                    } 
                }
            
            return total;
        }
        
    }
  
    int num_Nodes;
    HashMap<Integer, Node> total_map = new HashMap<>();
    
    Result(int numvert){
        this.num_Nodes = numvert;
        for(int i = 0;i<numvert;i++){
           total_map.put(i,new Node(i, this.num_Nodes));
        }
        
    }

    public void addWeightedEdge(int curOrigin, int curDestination, int curWeight) {  
        this.total_map.get(curDestination).map.put(this.total_map.get(curOrigin), curWeight);
 
    }

    public int mostInfluentialVertex() {
        int answer = 0;
        double current = 0;
        double max = total_map.get(0).centrality();
        
        for(int i = 1; i< this.num_Nodes;i++){
            current = this.total_map.get(i).centrality();
            if(max<current){
                answer = i;
                max = current;  
            }
            for(int j = 0;j<this.num_Nodes;j++){
                this.total_map.get(j).dist = Double.POSITIVE_INFINITY;
            }
        }
        return answer;

    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int numVertices = Integer.parseInt(firstMultipleInput[0]);
        int numEdges = Integer.parseInt(firstMultipleInput[1]);
      
        Result result = new Result(numVertices);

        IntStream.range(0, numEdges).forEach(numEdgesItr -> {
            try {
                String[] secondMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");
                int curOrigin = Integer.parseInt(secondMultipleInput[0]);
                int curDestination = Integer.parseInt(secondMultipleInput[1]);
                int curWeight = Integer.parseInt(secondMultipleInput[2]);

                result.addWeightedEdge(curOrigin, curDestination, curWeight);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        System.out.println(result.mostInfluentialVertex());

        bufferedReader.close();
    }
}
