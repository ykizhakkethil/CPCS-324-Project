package cpcs.pkg324.group.project;
import java.util.*;
import java.io.*;

public class dijkstra {
     private static final int V = 6;
    // Create a 2D array to store the graph
    private static final double[][] graph = {
        {0, 1, 4, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY},
        {1, 0, 2, 7, 5, Double.POSITIVE_INFINITY},
        {4, 2, 0, Double.POSITIVE_INFINITY, 1, Double.POSITIVE_INFINITY},
        {Double.POSITIVE_INFINITY, 7, Double.POSITIVE_INFINITY, 0, 3, 2},
        {Double.POSITIVE_INFINITY, 5, 1, 3, 0, 6},
        {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 2, 6, 0}
    };
    
   
    
    static int E;
    

    public static void main(String[] args) throws IOException {
        readGraphFromFile("input2.txt");
        System.out.println("Weight Matrix:");
        printGraph();
        System.out.println("# of vertices is: " + V + ", # of edges is: " + E);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Source vertex: ");
        int source = scanner.nextInt();
        dijkstraUsingPriorityQueue(source);
        dijkstraUsingMinHeap(source);
    }

    public static void readGraphFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        
        E = Integer.parseInt(br.readLine());
       
        for (int i = 0; i < V; i++) {
            Arrays.fill(graph[i], Double.POSITIVE_INFINITY);
        }
        for (int i = 0; i < E; i++) {
            String[] line = br.readLine().split(" ");
            int src = Integer.parseInt(line[0]);
            int dest = Integer.parseInt(line[1]);
            double weight = Double.parseDouble(line[2]);
            graph[src][dest] = weight;
        }
        br.close();
    }

    public static void printGraph() {
        System.out.print(" ");
        for (int j = 0; j < V; j++) {
            System.out.print(" " + j);
        }
        System.out.println();
        for (int i = 0; i < V; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < V; j++) {
                if (graph[i][j] == Double.POSITIVE_INFINITY) {
                    System.out.print("∞ ");
                } else {
                    System.out.print((int) graph[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public static void dijkstraUsingPriorityQueue(int source) {
        long startTime = System.nanoTime();
        double[] dist = new double[V];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[source] = 0;
        boolean[] visited = new boolean[V];
        PriorityQueue<Integer> pq = new PriorityQueue<>(V, (a, b) -> Double.compare(dist[a], dist[b]));
        pq.add(source);
        while (!pq.isEmpty()) {
            int u = pq.poll();
            if (visited[u]) {
                continue;
            }
            visited[u] = true;
            for (int v = 0; v < V; v++) {
                if (!visited[v] && graph[u][v] != Double.POSITIVE_INFINITY) {
                    if (dist[u] + graph[u][v] < dist[v]) {
                        dist[v] = dist[u] + graph[u][v];
                        pq.add(v);
                    }
                }
            }
        }
        long endTime = System.nanoTime();
        printShortestPaths(source, dist);
        System.out.println("Running time of Dijkstra using priority queue is: " + (endTime - startTime) + " nano seconds");
    }

    public static void dijkstraUsingMinHeap(int source) {
        long startTime = System.nanoTime();
        double[] dist = new double[V];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[source] = 0;
        boolean[] visited = new boolean[V];
        PriorityQueue<Integer> pq = new PriorityQueue<>(V, (a, b) -> Double.compare(dist[a], dist[b]));
        pq.add(source);
        while (!pq.isEmpty()) {
            int u = pq.poll();
            if (visited[u]) {
                continue;
            }
            visited[u] = true;
            for (int v = 0; v < V; v++) {
                if (!visited[v] && graph[u][v] != Double.POSITIVE_INFINITY) {
                    if (dist[u] + graph[u][v] < dist[v]) {
                        dist[v] = dist[u] + graph[u][v];
                        pq.add(v);
                    }
                }
            }
        }
        long endTime = System.nanoTime();
        printShortestPaths(source, dist);
        System.out.println("Running time of Dijkstra using min Heap is: " + (endTime - startTime) + " nano seconds");
    }

  public static void printShortestPaths(int source, double[] dist) {
        System.out.println("Dijkstra using priority queue:");
        System.out.println("Shortest paths from vertex " + source + " are:");
        for (int i = 0; i < V; i++) {
            if (i == source) {
                continue;
            }
            List<Integer> path = new ArrayList<>();
            path.add(i);
            int prev = i;
            while (prev != source) {
                for (int j = 0; j < V; j++) {
                    if (graph[prev][j] != Double.POSITIVE_INFINITY && dist[prev] == dist[j] + graph[prev][j]) {
                        prev = j;
                        path.add(0, j);
                        break;
                    }
                }
            }
            System.out.print("A path from " + source + " to " + i + ": " + source);
            for (int j = 1; j < path.size(); j++) {
                System.out.print(" " + path.get(j));
            }
            System.out.println(" (Length: " + dist[i] + ")");
        }
    }
}
