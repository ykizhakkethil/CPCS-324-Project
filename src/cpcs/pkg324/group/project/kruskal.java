/*
1-Name: Yoonus Kizhakkethil | ID: 2142644 | Email: ykizhakkethil@stu.kau.edu.sa
2-Name: Abdulkareem Al-Ghamdi | ID: 2135037 | Email: asalghamdi0015@stu.kau.edu.sa
3-Name: Ammar Bin Madi | ID: 2135146 | Email: aomarbinmadi@stu.kau.edu.sa
4-Name: Omar Badr | ID: 2136480 | Email: oahmedbader@stu.kau.edu.sa
 */
package cpcs.pkg324.group.project;

import java.util.*;
import java.io.*;

public class kruskal {

    /**
     * The Edge class  represents an edge.
     * Every edge has a Source & Destination(target) vertices, and a weight.
     * Source and Destination vertices are represented as an integers.
     * The weight of the edge is represented as a double.
    **/
    static class Edge implements Comparable<Edge> {
        int sourceVertex, targetVertex;
        double weight;

        //Constructor
        public Edge(int sourceVertex, int targetVertex, double weight) {
            this.sourceVertex = sourceVertex;
            this.targetVertex = targetVertex;
            this.weight = weight;
        }
        //Method to compare two edges by their weight.
        public int compareTo(Edge other) {
            return Double.compare(this.weight, other.weight);
        }
    }
    
    public static void main(String[] args) throws Exception {
        int n;
        ArrayList<Edge> edges;
        try (Scanner inputFile = new Scanner(new File("input1.txt"))) { //Checking if the file exists before starting...
            //Number of vertices
            n = inputFile.nextInt();
            //Number of edges
            int m = inputFile.nextInt();
            //Arraylist to store all edges
            edges = new ArrayList<>();
            //Looping tell we add all edges
            for (int i = 0; i < m; i++) { // m = number of edges
                //source vertex
                int sourceVertex = inputFile.nextInt();
                //Destination or target vertex
                int targetVertex = inputFile.nextInt();
                //Weight of the edge
                double weight = inputFile.nextDouble();
                //Creating the new Edge and adding them to the array
                edges.add(new Edge(sourceVertex, targetVertex, weight));
            }
        }
        long startTime = System.nanoTime(); //To calculate total time

        Stack<Double> weights = new Stack<>(); //Stack to store the weight of the edges
        ArrayList<Edge> mst = kruskal(edges, weights, n); // Calling kruskal() method to construct the mst(minimum spanning tree).

        long endTime = System.nanoTime();     //To calculate total time
        double totalTime = (endTime - startTime) / 1e6; // 1e6 = (1 * 10)^6

        //Printing the results
        System.out.println("Total weight of MST by Kruskal's algorithm: " + getTotalWeight(weights));
        System.out.println("The edges in the MST are:");
        for (Edge e : mst)
            System.out.println("Edge from " + e.sourceVertex + " to " + e.targetVertex + " has weight " + e.weight);

        System.out.println("Running Time of Kruskal’s algorithm using Union-Find approach is " + totalTime + " Nano seconds.");
    }
    //Returns the minimum spanning tree.
    public static ArrayList<Edge> kruskal(ArrayList<Edge> edges, Stack<Double> weights, int n) { // n = number of vertices
        ArrayList<Edge> mst = new ArrayList<>(); // Minimum spanning tree array.
        Collections.sort(edges); // Sorting edges by their weights.

        int[] id = makeSet(n); //Make a set for every vertex.

        /*
         * One thing we must note is that the edges already sorted in ascending order.
         *
         * For every edge we have, we will check the two vertices that are connected through that edge.
         * Whether they are part of the same subset or not.
         * If they are part of the same subset then we will do nothing.
         * Otherwise, we will union those vertices to the same subset. Then, add them to the minimum spanning tree array.
         * And the weight of the two unioned vertices will be pushed to the stack.
         *
        */
        for (Edge e : edges) {
            int sourceVertex = e.sourceVertex;
            int targetVertex = e.targetVertex;
            double weight = e.weight;
            if (!findSet(sourceVertex, targetVertex, id)) {
                union(sourceVertex, targetVertex, id);
                mst.add(e);
                weights.push(weight);
            }
        }
        return mst;
    }

    //Method that creates one-element set for every vertex.
    public static int[] makeSet(int n) { // n = number of vertices
        int[] id = new int[n];
        for (int i = 0; i < n; i++)
            id[i] = i;

        return id;
    }
    //Checks if two vertices are from the same set.
    public static boolean findSet(int p, int q, int[] id) { // p and q are vertices.
        return id[p] == id[q]; // True = from the same set.
    }

    //This method constructs a union of disjoint subsets.
    public static void union(int p, int q, int[] id) { // p and q are vertices.
        int pid = id[p];
        for (int i = 0; i < id.length; i++)
            if (id[i] == pid)
                id[i] = id[q];

    }
    //It returns the total weight of all the edges in the mst.
    public static double getTotalWeight(Stack<Double> weights) {
        double totalWeight = 0;
        for (double weight: weights)
            totalWeight += weight;

        return totalWeight;
    }
}
