/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpcs.pkg324.group.project;

import java.util.*;
import java.io.*;

public class kruskal {
    static class Edge implements Comparable<Edge> {
        int u, v;
        double weight;
        
        public Edge(int u, int v, double weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
        
        public int compareTo(Edge other) {
            return Double.compare(this.weight, other.weight);
        }
    }
    
    public static void main(String[] args) throws Exception {
        int n;
        ArrayList<Edge> edges;
        try (Scanner inputFile = new Scanner(new File("input1.txt"))) {
            n = inputFile.nextInt();
            int m = inputFile.nextInt();
            edges = new ArrayList<Edge>();
            for (int i = 0; i < m; i++) {
                int u = inputFile.nextInt();
                int v = inputFile.nextInt();
                double weight = inputFile.nextDouble();
                edges.add(new Edge(u, v, weight));
            }
        }
        long startTime = System.nanoTime();
        ArrayList<Edge> mst = kruskal(edges, n);
        long endTime = System.nanoTime();
        double totalTime = (endTime - startTime) / 1e6;
        System.out.println("Total weight of MST by Kruskal's algorithm: " + getTotalWeight(mst));
        System.out.println("The edges in the MST are:");
        for (Edge e : mst) {
            System.out.println("Edge from " + e.u + " to " + e.v + " has weight " + e.weight);
        }
        System.out.println("Running Time of Kruskal’s algorithm using Union-Find approach is " + totalTime + " Nano seconds.");
    }
    
    public static ArrayList<Edge> kruskal(ArrayList<Edge> edges, int n) {
        ArrayList<Edge> mst = new ArrayList<Edge>();
        Collections.sort(edges);
        int[] id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
        Stack<Double> weights = new Stack<Double>();
        for (Edge e : edges) {
            int u = e.u;
            int v = e.v;
            double weight = e.weight;
            if (!findSet(u, v, id)) {
                union(u, v, id);
                mst.add(e);
                weights.push(weight);
            }
        }
        return mst;
    }
    
    public static boolean findSet(int p, int q, int[] id) {
        return id[p] == id[q];
    }
    
    public static void union(int p, int q, int[] id) {
        int pid = id[p];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = id[q];
            }
        }
    }
    
    public static double getTotalWeight(ArrayList<Edge> edges) {
        double totalWeight = 0;
        for (Edge e : edges) {
            totalWeight += e.weight;
        }
        return totalWeight;
    }
}
