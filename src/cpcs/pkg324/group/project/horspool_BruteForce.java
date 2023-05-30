/*
1-Name: Yoonus Kizhakkethil | ID: 2142644 | Email: ykizhakkethil@stu.kau.edu.sa
2-Name: Abdulkareem Al-Ghamdi | ID: 2135037 | Email: asalghamdi0015@stu.kau.edu.sa
3-Name: Ammar Bin Madi | ID: 2135146 | Email: aomarbinmadi@stu.kau.edu.sa
4-Name: Omar Badr | ID: 2136480 | Email: oahmedbader@stu.kau.edu.sa
*/
package cpcs.pkg324.group.project;

// import needed packages
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

// start
class horspool_BruteForce {
    public static void run(int lines, int patterns, int length) {
        // Read lines from input file
        String text = "";
        try {
            // read from input.txt file
            text = new String(Files.readAllBytes(Paths.get("input.txt")));
        } catch (IOException e) { // catch exception if file not found
            System.out.println("File not found!");
        }
        // create a string array by splitting text
        String[] textLines = text.split("\n");

        // create a StringBuilder to append text
        StringBuilder sb = new StringBuilder();

        // for loop to iterate around textLines
        for (int i = 0; i < lines && i < textLines.length; i++) {
            sb.append(textLines[i]);
        }
        // convert sb to lower case
        text = sb.toString().toLowerCase();

        // Generate random patterns
        Random rand = new Random();
        // creat String array
        String[] patternArray = new String[patterns];
        // for loop to iterate around patternArray
        for (int i = 0; i < patterns; i++) {
            int start = rand.nextInt(text.length() - length);// generate random number between 0 and text length -
                                                             // length
            patternArray[i] = text.substring(start, start + length);
        }

        // Write patterns to output file
        try {
            Files.write(Paths.get("patterns.txt"), String.join("\n", patternArray).getBytes());
        } catch (IOException e) { // catch exception if file not found
            System.out.println("File not found!");
        }

        // Create shift tables for all patterns
        int[][] shiftTables = new int[patterns][256];// create 2D array of int with size patterns and 256
        for (int i = 0; i < patterns; i++) {
            for (int j = 0; j < 256; j++) {
                shiftTables[i][j] = length; // fill the array with length
            }
            for (int j = 0; j < length - 1; j++) {
                shiftTables[i][patternArray[i].charAt(j)] = length - j - 1;// fill the array with length - j - 1
            }
        }

        // Display shift tables
        for (int i = 0; i < patterns; i++) {
            System.out.println("Shift table for pattern " + patternArray[i] + " :");
            for (int j = 0; j < 256; j++) {
                if (shiftTables[i][j] != length) { // if a certain char in the table has a shift size diffrent than the
                                                   // length print it
                    System.out.println((char) j + ": " + shiftTables[i][j]);
                }
            }
            System.out.println();
        }

        // Implement brute force algorithm
        long bruteForceTotalTime = 0;
        for (String pattern : patternArray) {
            long startTime = System.nanoTime();
            int n = text.length();
            int m = pattern.length();
            for (int i = 0; i <= n - m; i++) {
                int j;
                for (j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {// if the char at index i + j in text is not equal to
                                                                  // the char at index j in pattern break
                        break;
                    }
                }
                if (j == m) { // Pattern found at index i
                    // Do something with the found index if needed
                }
            }

            // Implement Horspool algorithm and measure running time
            long endTime = System.nanoTime();
            bruteForceTotalTime += endTime - startTime;
        }
        double bruteForceAvgTime = bruteForceTotalTime / (double) patterns;// calculate average time

        // Implement Horspool algorithm
        long horspoolTotalTime = 0;
        for (int p = 0; p < patterns; p++) {

            // declare variables
            String pattern = patternArray[p];
            long startTime = System.nanoTime();
            int n = text.length();
            int m = pattern.length();
            int[] shiftTable = shiftTables[p];
            int i = 0;

            while (i <= n - m) {
                int j = m - 1;
                while (j >= 0 && pattern.charAt(j) == text.charAt(i + j)) {
                    j--;
                }
                if (j < 0) { // Pattern found at index i
                    // Do something with the found index if needed
                    break;
                } else {
                    i += shiftTable[text.charAt(i + m - 1)];
                }
            }
            long endTime = System.nanoTime();
            horspoolTotalTime += endTime - startTime;
        }
        double horspoolAvgTime = horspoolTotalTime / (double) patterns;

        // Display average running times
        System.out.println("Average running time of brute force algorithm: " + bruteForceAvgTime);
        System.out.println("Average running time of Horspool algorithm: " + horspoolAvgTime);

        // deside which algorithm is faster

        if (bruteForceAvgTime < horspoolAvgTime) {
            System.out.println("for this instance Brute force algorithm is better than Horspool algorithm");
        } else if (bruteForceAvgTime > horspoolAvgTime) {
            System.out.println("for this instance Horspool algorithm is better than Brute force algorithm");
        } else {
            System.out.println("Both algorithms have the same running time");
        }
    }
}
