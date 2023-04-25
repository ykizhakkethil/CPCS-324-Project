package cpcs.pkg324.group.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class dijkstra {

    static int[][] adjacencyMatrix;
    static int source;

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("input2.txt"));
            int vertices = scanner.nextInt();
            int edges = scanner.nextInt();
            adjacencyMatrix = new int[vertices][vertices];
            for (int i = 0; i < edges; i++) {
                int source = scanner.nextInt();
                int target = scanner.nextInt();
                int weight = scanner.nextInt();
                adjacencyMatrix[source][target] = weight;
            }
            scanner.close();
            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    System.out.print(adjacencyMatrix[i][j] + " ");
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        displayWeightMatrix(adjacencyMatrix);
        // ---------------------------------------------------
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Source vertex: ");
        source = scanner.nextInt();
        scanner.close();
        long startTime1 = System.nanoTime();
        dijkstraPriorityQueue();
        long endTime1 = System.nanoTime();
        long duration1 = (endTime1 - startTime1);

        long startTime2 = System.nanoTime();
        dijkstraMinHeap();
        long endTime2 = System.nanoTime();
        long duration2 = (endTime2 - startTime2);

        System.out.println("Running time of Dijkstra using priority queue is: " + duration1 + " nano seconds");
        System.out.println("Running time of Dijkstra using min Heap is: " + duration2 + " nano seconds");
        if (duration1 < duration2) {
            System.out.println("Running time of Dijkstra using priority queue is better");
        } else {
            System.out.println("Running time of Dijkstra using min Heap is better");
        }
    }

    public static void displayWeightMatrix(int[][] adjacencyMatrix) {
        int vertices = adjacencyMatrix.length;
        int edges = 0;
        System.out.print("Weight Matrix:\n  ");
        for (int i = 0; i < vertices; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < vertices; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < vertices; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
                if (adjacencyMatrix[i][j] != 0) {
                    edges++;
                }
            }
            System.out.println();
        }
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
    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------

    public static void dijkstraPriorityQueue() {

        int vertices = adjacencyMatrix.length;
        int[] distance = new int[vertices];
        boolean[] visited = new boolean[vertices];

        int[] previous = new int[vertices];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        for (int i = 0; i < vertices - 1; i++) {
            int minVertex = findMinVertex(distance, visited);
            System.out.println(minVertex);
            //-------------
            String[] s = new String[10];
            for (String string : s) {
                string = "";
            }
            s[minVertex] += source + " ";
            visited[minVertex] = true;
            for (int j = 0; j < vertices; j++) {

                if (adjacencyMatrix[minVertex][j] != 0 && !visited[j]) {
                    int newDistance = distance[minVertex] + adjacencyMatrix[minVertex][j];
                    s[minVertex] += j + " ";
                    System.out.println("");
                    System.out.println(s[minVertex]);

                    if (newDistance < distance[j]) {

                        distance[j] = newDistance;
                        previous[j] = minVertex;
                        for (int b : distance) {
                            System.out.println("b " + b);
                        }
                    }
                }
                System.out.print(adjacencyMatrix[minVertex][j] + " ,");

            }
            System.out.println("");
            System.out.println("---------------------");
        }
        System.out.println("Dijkstra using priority queue:");
        displayShortestPaths(source, distance, previous, adjacencyMatrix);
    }

    private static int findMinVertex(int[] distance, boolean[] visited) {
        int minVertex = -1;
        for (int i = 0; i < distance.length; i++) {
            if (!visited[i] && (minVertex == -1 || distance[i] < distance[minVertex])) {
                minVertex = i;
            }
        }

        return minVertex;
    }

    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------
    public static void dijkstraMinHeap() {

        int vertices = adjacencyMatrix.length;
        int[] distance = new int[vertices];
        boolean[] visited = new boolean[vertices];
        int[] previous = new int[vertices];
        PriorityQueue<Pair> minHeap = new PriorityQueue<>();
        for (int i = 0; i < vertices; i++) {
            if (i == source) {
                distance[i] = 0;
                minHeap.add(new Pair(i, distance[i]));
            } else {
                distance[i] = Integer.MAX_VALUE;
                minHeap.add(new Pair(i, distance[i]));
            }
            previous[i] = i;
        }
        while (!minHeap.isEmpty()) {
            Pair pair = minHeap.poll();
            int minVertex = pair.vertex;
            visited[minVertex] = true;
            for (int j = 0; j < vertices; j++) {
                if (adjacencyMatrix[minVertex][j] != 0 && !visited[j]) {
                    int newDistance = distance[minVertex] + adjacencyMatrix[minVertex][j];
                    if (newDistance < distance[j]) {
                        distance[j] = newDistance;
                        previous[j] = minVertex;
                        minHeap.remove(new Pair(j, distance[j]));
                        minHeap.add(new Pair(j, distance[j]));
                    }
                }
            }
        }
        displayShortestPaths(source, distance, previous, adjacencyMatrix);
    }

    private static class Pair implements Comparable<Pair> {

        int vertex;
        int distance;

        public Pair(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(Pair other) {
            return this.distance - other.distance;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pair) {
                Pair other = (Pair) obj;
                return this.vertex == other.vertex;
            }
            return false;
        }
    }

    // --------------------------------------------------------------------------
    public static void displayShortestPaths(int source, int[] distance, int[] previous, int[][] adjacencyMatrix) {
        int vertices = adjacencyMatrix.length;
        for (int i = 0; i < vertices; i++) {
            previous[i] = i;
        }
        System.out.println("Shortest paths from vertex " + source + " are:");
        for (int i = 0; i < vertices; i++) {
            System.out.print("A path from " + source + " to " + i + ": ");
            printPath(previous, i);
            System.out.println(" (Length: " + distance[i] + ")");
        }
    }

    private static void printPath(int[] previous, int target) {
        if (previous[target] != target) {
            printPath(previous, previous[target]);
        }
        System.out.print(target + " ");
    }
}
