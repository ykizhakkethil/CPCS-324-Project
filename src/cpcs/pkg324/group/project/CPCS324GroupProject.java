/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpcs.pkg324.group.project;

import java.io.File;
import java.io.*;
import java.util.*;

/**
 *
 * @author yoonus
 */
public class CPCS324GroupProject {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, Exception {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. Comparison between Horspool and Brute force algorithms");
            System.out.println("2. Finding minimum spanning tree using Prim’s algorithm");
            System.out.println("3. Finding minimum spanning tree using Kruskal’s algorithm");
            System.out.println("4. Finding the shortest path using Dijkstra’s algorithm");
            System.out.println("5. Quit");
            System.out.println("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Comparison between Horspool and Brute force algorithms");
                    System.out.println("How many lines you want to read from the text file?");
                    int lines = scanner.nextInt();
                    System.out.println("How many patterns to be generated?");
                    int patterns = scanner.nextInt();
                    System.out.println("What is the length of each pattern?");
                    int length = scanner.nextInt();
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
                    scanner.close();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    break;
            }
        } while (choice != 5);

    }

    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------

}
