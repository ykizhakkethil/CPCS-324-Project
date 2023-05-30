/*
1-Name: Yoonus Kizhakkethil | ID: 2142644 | Email: ykizhakkethil@stu.kau.edu.sa
2-Name: Abdulkareem Al-Ghamdi | ID: 2135037 | Email: asalghamdi0015@stu.kau.edu.sa
3-Name: Ammar Bin Madi | ID: 2135146 | Email: aomarbinmadi@stu.kau.edu.sa
4-Name: Omar Badr | ID: 2136480 | Email: oahmedbader@stu.kau.edu.sa
*/

// Importing the necessary packages.
package cpcs.pkg324.group.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

// basic flow of the program.
/*
1- Read the graph from the file.
2- Calculate the MST using Prim's algorithm with unordered Min-Priority queue.
3- Calculate the MST using Prim's algorithm with Min-Heap.
4- Calculate the total weight of the MST.
5- Print the results.
*/
public class prims {

    // Defining variables to be used in the program.
    private static int numVertices;
    private static int numEdges;
    private static double[][] adjacencyMatrix;

    public static void main(String[] args) {

        readGraphFromFile(); // Read data from file.

        /*
         * Defining variables to facilitate the calculations as per Prim's algorithm.
         * As noticed, variable values are evaluated throughout various methods
         * which will be further explained below.
         */
        double[][] mst1 = primUnorderedMinPQ();
        double time1 = measureRunningTime(() -> primUnorderedMinPQ());// Calculate the running time of the algorithm
        double[][] mst2 = primMinHeap();
        double time2 = measureRunningTime(() -> primMinHeap());

        // Calculating the total weight of MST.
        double weight1 = calculateMSTWeight(mst1);
        double weight2 = calculateMSTWeight(mst2);

        // Print results
        System.out.println("Total weight of MST by Prim's algorithm (Using unordered Min-Priority queue): " + weight1);
        printMST(mst1);
        System.out.println(
                "Running time of Prim’s algorithm using unordered Min-Priority Queue is " + time1 + " Nano seconds");
        System.out.println("Total weight of MST by Prim's algorithm (Using Min-Heap): " + weight2);
        printMST(mst2);
        System.out.println("Running time of Prim’s algorithm using Min-Heap is " + time2 + " Nano seconds");
    }

    // Method to read data from file, by using the Scanner class.
    private static void readGraphFromFile() {
        try {
            Scanner scanner = new Scanner(new File("input1.txt"));
            numVertices = scanner.nextInt(); // Number of vertices
            numEdges = scanner.nextInt(); // Number of edges
            adjacencyMatrix = new double[numVertices][numVertices]; // Create the array to store

            // Below, we will insert the matrix's data into the array.
            for (int i = 0; i < numVertices; i++) {
                Arrays.fill(adjacencyMatrix[i], Double.POSITIVE_INFINITY);
            }
            for (int i = 0; i < numEdges; i++) {// Read the edges from the file and store them in the array
                int source = scanner.nextInt();
                int target = scanner.nextInt();
                double weight = scanner.nextDouble();

                // Add the edges to the array and the adjacency matrix
                adjacencyMatrix[source][target] = weight;
                adjacencyMatrix[target][source] = weight;
            }
            scanner.close(); // Close the scanner object.
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    // Method to calculate the total weight of MST (Using unordered Min-Priority
    // queue)
    private static double[][] primUnorderedMinPQ() {

        // Defining variables to be used in the method.
        double[][] mst = new double[numVertices][numVertices];
        boolean[] visited = new boolean[numVertices];
        PriorityQueue<Edge> pq = new PriorityQueue<>(numEdges, (e1, e2) -> Double.compare(e1.weight, e2.weight));// Create
                                                                                                                 // a
                                                                                                                 // priority
                                                                                                                 // queue
                                                                                                                 // to
                                                                                                                 // store
                                                                                                                 // the
                                                                                                                 // edges
        for (int i = 0; i < numVertices; i++) {
            Arrays.fill(mst[i], Double.POSITIVE_INFINITY); // Fill the array with infinity values.
        }

        // Add the first edge to the MST
        visited[0] = true;

        for (int i = 0; i < numVertices - 1; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (visited[j]) {
                    for (int k = 0; k < numVertices; k++) {
                        if (!visited[k] && adjacencyMatrix[j][k] < Double.POSITIVE_INFINITY) {// Check if the edge is
                                                                                              // not visited and the
                                                                                              // weight is not infinity
                            pq.add(new Edge(j, k, adjacencyMatrix[j][k]));// Add the edge to the priority queue if it
                                                                          // satisfies the condition
                        }
                    }
                }
            }
            Edge minEdge = pq.remove();// Remove the edge with the minimum weight
            while (visited[minEdge.source] && visited[minEdge.target]) {// Check if the edge is visited
                minEdge = pq.remove();
            }

            // Add the edge to the MST
            mst[minEdge.source][minEdge.target] = minEdge.weight;
            mst[minEdge.target][minEdge.source] = minEdge.weight;
            visited[minEdge.target] = true;
        }
        return mst;// Return the MST
    }

    // Method to calculate total weight of MST (Using Min-Heap):
    private static double[][] primMinHeap() {

        // Defining variables to be used in the method.
        double[][] mst = new double[numVertices][numVertices];
        boolean[] visited = new boolean[numVertices];
        PriorityQueue<Edge> pq = new PriorityQueue<>(numEdges, (e1, e2) -> Double.compare(e1.weight, e2.weight));// Create
                                                                                                                 // a
                                                                                                                 // priority
                                                                                                                 // queue
                                                                                                                 // to
                                                                                                                 // store
                                                                                                                 // the
                                                                                                                 // edges
        for (int i = 0; i < numVertices; i++) {
            Arrays.fill(mst[i], Double.POSITIVE_INFINITY);
        }
        visited[0] = true;
        for (int i = 0; i < numVertices - 1; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (visited[j]) {// Check if the edge is visited
                    for (int k = 0; k < numVertices; k++) {
                        if (!visited[k] && adjacencyMatrix[j][k] < Double.POSITIVE_INFINITY) {// Check if the edge is
                                                                                              // not visited and the
                                                                                              // weight is not infinity
                            pq.add(new Edge(j, k, adjacencyMatrix[j][k]));
                        }
                    }
                }
            }
            Edge minEdge = pq.remove();// Remove the edge with the minimum weight
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
        // this method calculates the total weight of the MST
        // Defining variables to be used in the method.
        double weight = 0.0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (mst[i][j] < Double.POSITIVE_INFINITY) {// Check if the weight is not infinity
                    weight += mst[i][j];// Add the weight to the total weight
                }
            }
        }
        return weight;
    }

    // Method to print given MST.
    private static void printMST(double[][] mst) {
        System.out.println("The edges in the MST are:");
        for (int i = 0; i < numVertices; i++) {// Print the edges in the MST
            for (int j = i + 1; j < numVertices; j++) {
                if (mst[i][j] < Double.POSITIVE_INFINITY) {// Check if the weight is not infinity
                    System.out.println("Edge from " + i + " to " + j + " has weight " + mst[i][j]);// Print the edge
                }
            }
        }
    }

    /*
     * Method which acts as a system timer (i.e: giving us the running time).
     * Note that running time could vary, depending on whether you are calculating
     * the weight using Min-Heap or unordered Min-Priority queue.
     */
    private static long measureRunningTime(Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    // Class to create edges.
    private static class Edge {

        // Defining variables to be used in the class.
        int source;
        int target;
        double weight;

        // Constructor to create edges.
        public Edge(int source, int target, double weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }
    }
}
