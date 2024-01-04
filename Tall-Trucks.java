//Manipulation of Dijkstra's finding path to node with the greatest min path cost


/*There are N cities, and you want to send trucks from your factory (in city 1) to the other cities, 
the taller the better. There are M two-way roads connecting pairs of cities. Each road has a maximum 
truck height, determined by bridges and tunnels along the way. For each city V , you want to work out 
the maximum height of a truck that can travel from city 1 to city V. You do not care about the length of the route, just the best height. */

import java.util.Scanner;
import java.util.HashMap;

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
    public static class Node {
        private HashMap<Node, Integer> neighbors = new HashMap<>();
        private int height;

        Node() {
            this.height = 0; 
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] line = sc.nextLine().split(" ");
        int N = Integer.parseInt(line[0]);
        int M = Integer.parseInt(line[1]);
        HashMap<Integer, Node> map = new HashMap<>();

   
        for (int i = 1; i <= N; i++) {
            Node t = new Node();
            map.put(i, t);
        }

       
        for (int m = 0; m < M; ++m) {
            line = sc.nextLine().split(" ");
            int U = Integer.parseInt(line[0]);
            int V = Integer.parseInt(line[1]);
            int H = Integer.parseInt(line[2]);
            // Notice road between U and V with height limit H
            Node first = map.get(U);
            Node second = map.get(V);

            
            if (first.neighbors.containsKey(second)) {
                int existingHeight = first.neighbors.get(second);
                if (H > existingHeight) {
                    first.neighbors.put(second, H);
                    second.neighbors.put(first, H);
                }
            } else {
                first.neighbors.put(second, H);
                second.neighbors.put(first, H);
            }
        }

       
        calculateMaxHeight(map.get(1), map);

       
        for (int i = 2; i <= N; i++) {
            System.out.print(map.get(i).height + " ");
        }

        sc.close();
    }

   
    public static void calculateMaxHeight(Node start, HashMap<Integer, Node> map) {
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> b.height - a.height); 

        start.height = Integer.MAX_VALUE; 
        pq.offer(start);

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            for (Map.Entry<Node, Integer> entry : current.neighbors.entrySet()) {
                Node neighbor = entry.getKey();
                int weight = entry.getValue();

                int minHeight = Math.min(current.height, weight);

                if (minHeight > neighbor.height) {
                    neighbor.height = minHeight;
                    pq.offer(neighbor);
                }
            }
        }
    }
}