/*
1-Name: Yoonus Kizhakkethil | ID: 2142644 | Email: ykizhakkethil@stu.kau.edu.sa
2-Name: Abdulkareem Al-Ghamdi | ID: 2135037 | Email: asalghamdi0015@stu.kau.edu.sa
3-Name: Ammar Bin Madi | ID: 2135146 | Email: aomarbinmadi@stu.kau.edu.sa
4-Name: Omar Badr | ID: 2136480 | Email: oahmedbader@stu.kau.edu.sa
*/
package cpcs.pkg324.group.project;

import java.io.File;
import java.io.*;
import java.util.*;

// Test
/**
 *
 * @authors yoonus
 *         Abdulakreem Abdullah Al-Ghamdi ($)
 */
public class CPCS324GroupProject {
    static Scanner main_scanner = new Scanner(System.in);
    static int choice = 0;

    public static void main(String[] args) throws IOException, Exception {

        do {
            System.out.println("Menu:");
            System.out.println("1. Comparison between Horspool and Brute force algorithms");
            System.out.println("2. Finding minimum spanning tree using Prim’s algorithm");
            System.out.println("3. Finding minimum spanning tree using Kruskal’s algorithm");
            System.out.println("4. Finding the shortest path using Dijkstra’s algorithm");
            System.out.println("5. Quit");

            System.out.print("Enter your choice: ");

            choice = main_scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Comparison between Horspool and Brute force algorithms");
                    System.out.println("How many lines you want to read from the text file?");
                    int lines = main_scanner.nextInt();
                    System.out.println("How many patterns to be generated?");
                    int patterns = main_scanner.nextInt();
                    System.out.println("What is the length of each pattern?");
                    int length = main_scanner.nextInt();
                    horspool_BruteForce x = new horspool_BruteForce();
                    x.run(lines, patterns, length); // length is a double
                    break;

                case 2:
                    System.out.println("Finding minimum spanning tree using Prim’s algorithm");
                    prims.main(args);
                    break;
                case 3:
                    System.out.println("Finding minimum spanning tree using Kruskal’s algorithm");
                    kruskal.main(args);
                    break;
                case 4:
                    System.out.println("Finding the shortest path using Dijkstra’s algorithm");
                    dijkstra.main(args);
                    break;
                case 5:
                    System.out.println("Quitting program...");
                    main_scanner.close();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    break;
            }
        } while (choice != 5);
    }
}
