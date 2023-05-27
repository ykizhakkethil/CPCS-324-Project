
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
        readGraphFromFile();
        double[][] mst1 = primUnorderedMinPQ();
        double time1 = measureRunningTime(() -> primUnorderedMinPQ());
        double[][] mst2 = primMinHeap();
        double time2 = measureRunningTime(() -> primMinHeap());
        double weight1 = calculateMSTWeight(mst1);
        double weight2 = calculateMSTWeight(mst2);
        System.out.println("Total weight of MST by Prim's algorithm (Using unordered Min-Priority queue): " + weight1);
        printMST(mst1);
        System.out.println("Running time of Prim’s algorithm using unordered Min-Priority Queue is " + time1 + " Nano seconds");
        System.out.println("Total weight of MST by Prim's algorithm (Using Min-Heap): " + weight2);
        printMST(mst2);
        System.out.println("Running time of Prim’s algorithm using Min-Heap is " + time2 + " Nano seconds");
    }

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

    private static long measureRunningTime(Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

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
