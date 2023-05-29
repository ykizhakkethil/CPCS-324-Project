/*
1-Name: Yoonus Kizhakkethil | ID: 2142644 | Email: ykizhakkethil@stu.kau.edu.sa
2-Name: Abdulkareem Al-Ghamdi | ID: 2135037 | Email: asalghamdi0015@stu.kau.edu.sa
3-Name: Ammar Bin Madi | ID: 2135146 | Email: aomarbinmadi@stu.kau.edu.sa
4-Name: Omar Badr | ID: 2136480 | Email: oahmedbader@stu.kau.edu.sa
*/
package cpcs.pkg324.group.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;


public class prims {
    
    private static int numVertices;
    private static int numEdges;
    private static double[][] adjacencyMatrix;

    public static void main(String[] args) {

        readGraphFromFile(); // Read data from file.

        /*
        Defining variables to facilitate the calculations as per Prim's algorithm.
        As noticed, variable values are evaluated throughout various methods 
        which will be further explained below.
        */
        double[][] mst1 = primUnorderedMinPQ();
        double time1 = measureRunningTime(() -> primUnorderedMinPQ());
        double[][] mst2 = primMinHeap();
        double time2 = measureRunningTime(() -> primMinHeap());
        double weight1 = calculateMSTWeight(mst1);
        double weight2 = calculateMSTWeight(mst2);

        // Print results 
        System.out.println("Total weight of MST by Prim's algorithm (Using unordered Min-Priority queue): " + weight1);
        printMST(mst1);
        System.out.println("Running time of Prim’s algorithm using unordered Min-Priority Queue is " + time1 + " Nano seconds");
        System.out.println("Total weight of MST by Prim's algorithm (Using Min-Heap): " + weight2);
        printMST(mst2);
        System.out.println("Running time of Prim’s algorithm using Min-Heap is " + time2 + " Nano seconds");
    }
    // Method to read data from file, by using the Scanner class.
    private static void readGraphFromFile() {
        try {
            Scanner scanner = new Scanner(new File("input1.txt"));
            numVertices = scanner.nextInt();
            numEdges = scanner.nextInt();
            adjacencyMatrix = new double[numVertices][numVertices];
            for (int i = 0; i < numVertices; i++) {
                Arrays.fill(adjacencyMatrix[i], Double.POSITIVE_INFINITY);
            }
            for (int i = 0; i < numEdges; i++) {
                int source = scanner.nextInt();
                int target = scanner.nextInt();
                double weight = scanner.nextDouble();
                adjacencyMatrix[source][target] = weight;
                adjacencyMatrix[target][source] = weight;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    // Method to calculate the total weight of MST (Using unordered Min-Priority queue)
    private static double[][] primUnorderedMinPQ() {
        double[][] mst = new double[numVertices][numVertices];
        boolean[] visited = new boolean[numVertices];
        PriorityQueue<Edge> pq = new PriorityQueue<>(numEdges, (e1, e2) -> Double.compare(e1.weight, e2.weight));
        for (int i = 0; i < numVertices; i++) {
            Arrays.fill(mst[i], Double.POSITIVE_INFINITY);
        }
        visited[0] = true;
        for (int i = 0; i < numVertices - 1; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (visited[j]) {
                    for (int k = 0; k < numVertices; k++) {
                        if (!visited[k] && adjacencyMatrix[j][k] < Double.POSITIVE_INFINITY) {
                            pq.add(new Edge(j, k, adjacencyMatrix[j][k]));
                        }
                    }
                }
            }
            Edge minEdge = pq.remove();
            while (visited[minEdge.source] && visited[minEdge.target]) {
                minEdge = pq.remove();
            }
            mst[minEdge.source][minEdge.target] = minEdge.weight;
            mst[minEdge.target][minEdge.source] = minEdge.weight;
            visited[minEdge.target] = true;
        }
        return mst;
    }
    // Method to calculate total weight of MST (Using Min-Heap): 
    private static double[][] primMinHeap() {
        double[][] mst = new double[numVertices][numVertices];
        boolean[] visited = new boolean[numVertices];
        PriorityQueue<Edge> pq = new PriorityQueue<>(numEdges, (e1, e2) -> Double.compare(e1.weight, e2.weight));
        for (int i = 0; i < numVertices; i++) {
            Arrays.fill(mst[i], Double.POSITIVE_INFINITY);
        }
        visited[0] = true;
        for (int i = 0; i < numVertices - 1; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (visited[j]) {
                    for (int k = 0; k < numVertices; k++) {
                        if (!visited[k] && adjacencyMatrix[j][k] < Double.POSITIVE_INFINITY) {
                            pq.add(new Edge(j, k, adjacencyMatrix[j][k]));
                        }
                    }
                }
            }
            Edge minEdge = pq.remove();
            while (visited[minEdge.source] && visited[minEdge.target]) {
                minEdge = pq.remove();
            }
            mst[minEdge.source][minEdge.target] = minEdge.weight;
            mst[minEdge.target][minEdge.source] = minEdge.weight;
            visited[minEdge.target] = true;
        }
        return mst;
    }
    // Method to calculate weight of given MST
    private static double calculateMSTWeight(double[][] mst) {
        double weight = 0.0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (mst[i][j] < Double.POSITIVE_INFINITY) {
                    weight += mst[i][j];
                }
            }
        }
        return weight;
    }

    // Method to print given MST.
    private static void printMST(double[][] mst) {
        System.out.println("The edges in the MST are:");
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (mst[i][j] < Double.POSITIVE_INFINITY) {
                    System.out.println("Edge from " + i + " to " + j + " has weight " + mst[i][j]);
                }
            }
        }
    }

    /*  Method which acts as a system timer (i.e: giving us the running time).
        Note that running time could vary, depending on whether you are calculating
        the weight using Min-Heap or unordered Min-Priority queue.
     */
    private static long measureRunningTime(Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    // Class to create edges.
    private static class Edge {

        int source;
        int target;
        double weight;

        public Edge(int source, int target, double weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }
    }
}
