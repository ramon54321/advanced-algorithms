package Lab6;

import java.io.*;
import java.util.ArrayList;
import java.util.BitSet;

public class Lab6a {

    public static void main(String[] args) {

        Knapsack knapsack = getKnapsackFromFile("res/easy20.txt");

        // Part 1 a -> show contents of read knapsack
        for (int i = 0; i < knapsack.items.length; i++) {
            System.out.println(i + " : " + knapsack.items[i].value + " , " + knapsack.items[i].weight);
        }

        /*
        // Prt 1 b -> find best combo by brute force
        ArrayList<KnapsackItem> bestItems = knapsack.getHighestValueCombo();
        System.out.println("Optimal solution found:");
        System.out.println("Value: " + knapsack.getValueOfSubset(bestItems.toArray(new KnapsackItem[bestItems.size()])));
        System.out.println("Weight: " + knapsack.getWeightOfSubset(bestItems.toArray(new KnapsackItem[bestItems.size()])) + " / " + knapsack.capacity);
        */

        /*
        // Prt 2 -> find greedy combo with value density sort
        ArrayList<KnapsackItem> bestItemsGreedy = knapsack.getHighestValueComboGreedy();
        System.out.println("Feasible solution found: (Not always optimal)");
        System.out.println("Value: " + knapsack.getValueOfSubset(bestItemsGreedy.toArray(new KnapsackItem[bestItemsGreedy.size()])));
        System.out.println("Weight: " + knapsack.getWeightOfSubset(bestItemsGreedy.toArray(new KnapsackItem[bestItemsGreedy.size()])) + " / " + knapsack.capacity);
        */

        ArrayList<KnapsackItem> bestItemsGreedyHeuristic = knapsack.getHighestValueHeuristic(10);
        System.out.println("Feasible solution found: (Not always optimal) -> Using Heuristics");
        System.out.println("Value: " + knapsack.getValueOfSubset(bestItemsGreedyHeuristic.toArray(new KnapsackItem[bestItemsGreedyHeuristic.size()])));
        System.out.println("Weight: " + knapsack.getWeightOfSubset(bestItemsGreedyHeuristic.toArray(new KnapsackItem[bestItemsGreedyHeuristic.size()])) + " / " + knapsack.capacity);




    }

    public static Knapsack getKnapsackFromFile(String path) {
        ArrayList<String> easy20 = readLines(path);

        int lineNum = 0;
        int itemCount = 0;
        KnapsackItem[] items = null;
        float capacity = 0;
        while (lineNum < easy20.size()) {
            String line = easy20.get(lineNum);
            System.out.println(line);

            if (lineNum == 0) {
                itemCount = Integer.parseInt(line);
                items = new KnapsackItem[itemCount];
                lineNum++;
                continue;
            }

            if (lineNum < itemCount+1) {
                String[] parts = line.split("[ ]+");
                System.out.println("Parts: " + parts.length);
                items[lineNum-1] = new KnapsackItem(Float.parseFloat(parts[2]), Float.parseFloat(parts[3]));
                lineNum++;
                continue;
            }

            // Only last line gets here
            capacity = Float.parseFloat(line);

            lineNum++;
        }

        return new Knapsack(items, capacity);
    }

    public static ArrayList<String> readLines(String path) {
        try {
            InputStream is = new FileInputStream(new File(path));
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();

            ArrayList<String> lines = new ArrayList<>();
            while (line != null) {
                lines.add(line);
                line = buf.readLine();
            }
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
