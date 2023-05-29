package cpcs.pkg324.group.project;
// import needed packages
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

// this class is used to implement the brute force algorithm and the Horspool algorithm
// to find the patterns in the text
// the class also calculates the average running time of each algorithm
// steps:
// 1. read the text from the input file
// 2. generate random patterns
// 3. write the patterns to the output file
// 4. create shift tables for all patterns
// 5. implement brute force algorithm
// 6. implement Horspool algorithm
// 7. display average running times



<<<<<<< Updated upstream
// start
=======
>>>>>>> Stashed changes
class horspool_BruteForce {
    public static void run(int lines, int patterns, int length) {
        // Read lines from input file
        String text = "";
        try {
            // read from input.txt file
            text = new String(Files.readAllBytes(Paths.get("input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
<<<<<<< Updated upstream
        // create a string array by splitting text
        String[] textLines = text.split("\n");
        // create a StringBuilder
        StringBuilder sb = new StringBuilder();
        // for loop 
        for (int i = 0; i < lines && i < textLines.length; i++) {
            sb.append(textLines[i]);
        }
        // convert sb to lower case
        text = sb.toString().toLowerCase();

        // Generate random patterns
        Random rand = new Random();
        // creat String array 
=======
        String[] textLines = text.split("\n"); // Split text into lines
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines && i < textLines.length; i++) { // Read only the first lines
            sb.append(textLines[i]);
        }
        text = sb.toString().toLowerCase();// Convert to lower case

        // Generate random patterns
        Random rand = new Random(); // Create a random object
>>>>>>> Stashed changes
        String[] patternArray = new String[patterns];
        // for loop to iterate around patternArray
        for (int i = 0; i < patterns; i++) {
            int start = rand.nextInt(text.length() - length);
            patternArray[i] = text.substring(start, start + length);
        }

        
        // Write patterns to output file
        try {
            Files.write(Paths.get("patterns.txt"), String.join("\n", patternArray).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create shift tables for all patterns
        int[][] shiftTables = new int[patterns][256];
        for (int i = 0; i < patterns; i++) {
            for (int j = 0; j < 256; j++) {
                shiftTables[i][j] = length;
            }
            for (int j = 0; j < length - 1; j++) {
                shiftTables[i][patternArray[i].charAt(j)] = length - j - 1;
            }
        }

        // Display shift tables
        for (int i = 0; i < patterns; i++) {
            System.out.println("Shift table for pattern " + patternArray[i] + " :");
            for (int j = 0; j < 256; j++) {
<<<<<<< Updated upstream
                if (shiftTables[i][j] != length) { // if a certain char in the table has a shift size diffrent than the length print it
=======
                if (shiftTables[i][j] != length) {

                    // print the character and its shift value
>>>>>>> Stashed changes
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

            // A loop to slide pattern one by one
            for (int i = 0; i <= n - m; i++) {
                int j;
                for (j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                }
                if (j == m) { // Pattern found at index i
                    // Do something with the found index if needed
                }
            }
            long endTime = System.nanoTime();
            bruteForceTotalTime += endTime - startTime;
        }
        double bruteForceAvgTime = bruteForceTotalTime / (double) patterns;

        // Implement Horspool algorithm
        long horspoolTotalTime = 0;
        for (int p = 0; p < patterns; p++) {
            String pattern = patternArray[p];
            long startTime = System.nanoTime();
            int n = text.length();
            int m = pattern.length();
            int[] shiftTable = shiftTables[p];
            int i = 0;
            
            while (i <= n - m)  {
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
            long endTime = System.nanoTime(); //
            horspoolTotalTime += endTime - startTime;
        }
        double horspoolAvgTime = horspoolTotalTime / (double) patterns; // Average running time of Horspool algorithm

        // Display average running times
        System.out.println("Average running time of brute force algorithm: " + bruteForceAvgTime);
        System.out.println("Average running time of Horspool algorithm: " + horspoolAvgTime);
    }
}
