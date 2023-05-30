/*
1-Name: Yoonus Kizhakkethil | ID: 2142644 | Email: ykizhakkethil@stu.kau.edu.sa
2-Name: Abdulkareem Al-Ghamdi | ID: 2135037 | Email: asalghamdi0015@stu.kau.edu.sa
3-Name: Ammar Bin Madi | ID: 2135146 | Email: aomarbinmadi@stu.kau.edu.sa
4-Name: Omar Badr | ID: 2136480 | Email: oahmedbader@stu.kau.edu.sa
*/
/*
 * @author: yoonus
 */
package cpcs.pkg324.group.project;

import java.io.*;
import java.util.*;

/*
 * basic flow of the program: 
 * 1. read the input from the file and fill the matrix with the values
 * 2. display the matrix
 * 3. prompt the user to enter the source vertex
 * 4. run the algorithm using unordered array
 * 5. run the algorithm using min heap
 * 6. display the shortest path from the source vertex to all other vertices
 * 7. compare the running time of both
 */

public class dijkstra {

    // declare variables
    static int[][] adjacencyMatrix; 
    static int source;
    static Scanner scanner;
    static Scanner input;

    public static void main(String[] args) {
        // initialize input
        input = new Scanner(System.in);

        try { // read from file
            scanner = new Scanner(new File("input2.txt"));
            int vertices = scanner.nextInt();
            int edges = scanner.nextInt();
            adjacencyMatrix = new int[vertices][vertices];

            // fill the matrix with the values
            for (int i = 0; i < edges; i++) {
                int source = scanner.nextInt();
                int target = scanner.nextInt();
                int weight = scanner.nextInt();
                adjacencyMatrix[source][target] = weight; // fill the matrix with the weight
            }

        } catch (FileNotFoundException e) { // catch the exception if the file is not found
            System.out.println("File not found");
        }

        // display the matrix
        displayWeightMatrix(adjacencyMatrix);

        // prompt the user to enter the source vertex
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print("Enter Source vertex: ");

        // check if the source vertex is valid
        source = scanner.nextInt();
        while (source < 0 || source > adjacencyMatrix.length - 1) {
            System.out.println("Invalid source vertex, please enter a valid source vertex");
            source = scanner.nextInt();
        }

        // calculate the time of running the algorithm
        // run the algorithm using unordered array
        long startTime1 = System.nanoTime();
        dijkstraUnorderedArray();
        long endTime1 = System.nanoTime();
        long duration1 = (endTime1 - startTime1); // calculate the time of running the algorithm
        // run the algorithm using min heap
        long startTime2 = System.nanoTime();
        dijkstraMinHeap();
        long endTime2 = System.nanoTime();
        long duration2 = (endTime2 - startTime2);

        System.out.println("Comparison Of the running time : ");
        System.out.println("Running time of Dijkstra using priority queue is: " + duration1 + " nano seconds");
        System.out.println("Running time of Dijkstra using min Heap is: " + duration2 + " nano seconds");
        if (duration1 < duration2) {
            System.out.println("Running time of Dijkstra using using priority is better");
        } else {
            System.out.println("Running time of Dijkstra using min Heap is better");
        }
        // close the scanner
    }

    public static void displayWeightMatrix(int[][] adjacencyMatrix) {
        // this method is used to display the weight matrix in a good format
        int vertices = adjacencyMatrix.length;
        int edges = 0;
        System.out.print("Weight Matrix:\n  ");
        for (int i = 0; i < vertices; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        // loop through the matrix
        for (int i = 0; i < vertices; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < vertices; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
                if (adjacencyMatrix[i][j] != 0) {
                    edges++;// if there is an edge increment the edges
                }
            }
            System.out.println();
        }
        System.out.println();

        // display the number of vertices and edges
        System.out.println("# of vertices is: " + vertices + ", # of edges is: " + edges);
        for (int i = 0; i < vertices; i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < vertices; j++) {
                if (adjacencyMatrix[i][j] != 0) {
                    System.out.print(i + "-" + j + " " + adjacencyMatrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public static void dijkstraUnorderedArray() {
        // this method is used to run the dijkstra algorithm using unordered array

        // declare variables
        int vertices = adjacencyMatrix.length;
        int[] distance = new int[vertices];
        boolean[] visited = new boolean[vertices];
        int[] previous = new int[vertices];

        // initialize distance array with infinity
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        // loop through the vertices
        for (int i = 0; i < vertices - 1; i++) {
            int minVertex = findMinVertex(distance, visited);

            visited[minVertex] = true; // mark the vertex as visited
            previous[minVertex] = source; // update previous

            // update the distance array
            for (int j = 0; j < vertices; j++) { // loop through the vertices
                if (adjacencyMatrix[minVertex][j] != 0 && !visited[j]) {// check if the vertex is not visited and there
                                                                        // is an edge between them
                    int newDistance = distance[minVertex] + adjacencyMatrix[minVertex][j];
                    if (newDistance < distance[j]) {
                        // update the distance array
                        distance[j] = newDistance;
                        previous[j] = minVertex;
                    }
                }
            }
        }

        System.out.println();
        System.out.println("Dijkstra using priority queue:");
        displayShortestPaths(source, distance, previous, adjacencyMatrix);
        System.out.println();
    }

    public static void dijkstraMinHeap() {
        // this method is used to run the dijkstra algorithm using min heap

        // declare variables
        int vertices = adjacencyMatrix.length;
        int[] distance = new int[vertices];
        boolean[] visited = new boolean[vertices];
        int[] previous = new int[vertices];

        // initialize distance array with infinity
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        // create a min heap using priority queue to store the vertices
        PriorityQueue<Pair> minHeap = new PriorityQueue<>(vertices, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return o1.distance - o2.distance;// return the difference between the distances
            }
        });

        // add source vertex to the min heap
        minHeap.add(new Pair(source, 0));

        // loop until the min heap is empty
        while (!minHeap.isEmpty()) {
            Pair currentPair = minHeap.poll();// remove the vertex with the minimum distance
            int currentVertex = currentPair.vertex; // get the vertex

            if (!visited[currentVertex]) {// check if the vertex is not visited
                visited[currentVertex] = true;
                previous[currentVertex] = currentPair.previous;

                for (int j = 0; j < vertices; j++) { // loop through the vertices
                    if (adjacencyMatrix[currentVertex][j] != 0 && !visited[j]) {
                        int newDistance = distance[currentVertex] + adjacencyMatrix[currentVertex][j];
                        if (newDistance < distance[j]) {
                            // update the distance array and previous array
                            distance[j] = newDistance;
                            previous[j] = currentVertex;
                            minHeap.add(new Pair(j, newDistance, currentVertex));// add the new distance to the min heap
                        }
                    }
                }
            }
        }

        System.out.println("Dijkstra using min heap:");
        displayShortestPaths(source, distance, previous, adjacencyMatrix);
        System.out.println();
    }

    public static int findMinVertex(int[] distance, boolean[] visited) {
        // this method is used to find the vertex with the minimum distance
        int minVertex = -1;
        for (int i = 0; i < distance.length; i++) {
            if (!visited[i] && (minVertex == -1 || distance[i] < distance[minVertex])) { // check if the vertex is not
                                                                                         // visited and the distance is
                                                                                         // less than the minimum
                                                                                         // distance
                minVertex = i;
            }
        }
        return minVertex;
    }

    public static void displayShortestPaths(int source, int[] distance, int[] previous, int[][] adjacencyMatrix) {
        // this method is used to display the shortest paths
        int vertices = adjacencyMatrix.length;
        System.out.println("Shortest paths from vertex " + source + " are:");
        for (int i = 0; i < vertices; i++) {
            System.out.print("A path from " + source + " to " + i + ": ");
            if (distance[i] == Integer.MAX_VALUE) {
                System.out.println("No path exists");
            } else {
                // print the path and its length from source to vertex i
                printPath(previous, i);
                System.out.println(" (Length: " + (double) (distance[i]) + ")");
            }
        }
    }

    public static void printPath(int[] previous, int vertex) {
        // this method is used to print the path from source to vertex
        if (vertex != source) {
            printPath(previous, previous[vertex]);// recursive call to printPath
        }
        System.out.print(vertex + " ");
    }

    static class Pair {
        // this class is used to store the vertex and its distance of type int

        // inicializ atributes
        int vertex;
        int distance;
        int previous;

        // constructor
        public Pair(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
            this.previous = -1;
        }

        // constructor
        public Pair(int vertex, int distance, int previous) {
            this.vertex = vertex;
            this.distance = distance;
            this.previous = previous;
        }
    }
}