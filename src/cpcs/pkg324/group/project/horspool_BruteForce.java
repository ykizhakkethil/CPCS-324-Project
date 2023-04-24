package cpcs.pkg324.group.project;
// test
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

class horspool_BruteForce {
    public static void run(int lines, int patterns, int length) {
        // Read lines from input file
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get("input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] textLines = text.split("\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines && i < textLines.length; i++) {
            sb.append(textLines[i]);
        }
        text = sb.toString().toLowerCase();

        // Generate random patterns
        Random rand = new Random();
        String[] patternArray = new String[patterns];
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
                if (shiftTables[i][j] != length) {
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
    }
}