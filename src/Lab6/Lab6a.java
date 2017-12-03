package Lab6;

import java.io.*;
import java.util.ArrayList;
import java.util.BitSet;

public class Lab6a {

    public static void main(String[] args) {

        //Knapsack knapsack = getKnapsackFromFile("res/easy20.txt");
        Knapsack knapsack = getKnapsackFromFile("res/hard33.txt");

        // Part 1 a -> show contents of read knapsack
        /*
        for (int i = 0; i < knapsack.items.length; i++) {
            System.out.println(i + " : " + knapsack.items[i].value + " , " + knapsack.items[i].weight);
        }
        */

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

        /*
        // Prt 3 -> heuristic optimal mixed with greedy
        ArrayList<KnapsackItem> bestItemsGreedyHeuristic = knapsack.getHighestValueHeuristic(10);
        System.out.println("Feasible solution found: (Not always optimal) -> Using Heuristics");
        System.out.println("Value: " + knapsack.getValueOfSubset(bestItemsGreedyHeuristic.toArray(new KnapsackItem[bestItemsGreedyHeuristic.size()])));
        System.out.println("Weight: " + knapsack.getWeightOfSubset(bestItemsGreedyHeuristic.toArray(new KnapsackItem[bestItemsGreedyHeuristic.size()])) + " / " + knapsack.capacity);
        */

        /*
        // Prt 4 -> heuristic greedy using multithreading
        ArrayList<KnapsackItem> bestItemsMultiThreaded = runSplit(knapsack, 20);
        System.out.println("Feasible solution found: (Not always optimal) -> Using Multithreaded Greedy Heuristics");
        System.out.println("Value: " + knapsack.getValueOfSubset(bestItemsMultiThreaded.toArray(new KnapsackItem[bestItemsMultiThreaded.size()])));
        System.out.println("Weight: " + knapsack.getWeightOfSubset(bestItemsMultiThreaded.toArray(new KnapsackItem[bestItemsMultiThreaded.size()])) + " / " + knapsack.capacity);
        */
    }

    public static float value0 = 0;
    public static float value1 = 0;
    public static float value2 = 0;
    public static float value3 = 0;

    public static ArrayList<KnapsackItem> bestItemsGreedyHeuristic0 = null;
    public static ArrayList<KnapsackItem> bestItemsGreedyHeuristic1 = null;
    public static ArrayList<KnapsackItem> bestItemsGreedyHeuristic2 = null;
    public static ArrayList<KnapsackItem> bestItemsGreedyHeuristic3 = null;

    public static ArrayList<KnapsackItem> runSplit(Knapsack knapsack, int numberToOptimize) {

        Thread offset0 = new Thread() {
            @Override
            public void run() {
                bestItemsGreedyHeuristic0 = knapsack.getHighestValueHeuristicSubset(0,numberToOptimize);
                value0 = knapsack.getValueOfSubset(bestItemsGreedyHeuristic0.toArray(new KnapsackItem[bestItemsGreedyHeuristic0.size()]));
            }
        };
        Thread offset1 = new Thread() {
            @Override
            public void run() {
                bestItemsGreedyHeuristic1 = knapsack.getHighestValueHeuristicSubset(1,numberToOptimize);
                value1 = knapsack.getValueOfSubset(bestItemsGreedyHeuristic1.toArray(new KnapsackItem[bestItemsGreedyHeuristic1.size()]));
            }
        };
        Thread offset2 = new Thread() {
            @Override
            public void run() {
                bestItemsGreedyHeuristic2 = knapsack.getHighestValueHeuristicSubset(2,numberToOptimize);
                value2 = knapsack.getValueOfSubset(bestItemsGreedyHeuristic2.toArray(new KnapsackItem[bestItemsGreedyHeuristic2.size()]));
            }
        };
        Thread offset3 = new Thread() {
            @Override
            public void run() {
                bestItemsGreedyHeuristic3 = knapsack.getHighestValueHeuristicSubset(3,numberToOptimize);
                value3 = knapsack.getValueOfSubset(bestItemsGreedyHeuristic3.toArray(new KnapsackItem[bestItemsGreedyHeuristic3.size()]));
            }
        };

        offset0.start();
        offset1.start();
        offset2.start();
        offset3.start();

        try {
            offset0.join();
            offset1.join();
            offset2.join();
            offset3.join();

            if(value0 >= value1 && value0 >= value2 && value0 >= value3) {
                System.out.println("Set 0");
                return bestItemsGreedyHeuristic0;
            }

            if(value1 >= value0 && value1 >= value2 && value1 >= value3) {
                System.out.println("Set 1");
                return bestItemsGreedyHeuristic1;
            }

            if(value2 >= value0 && value2 >= value1 && value2 >= value3) {
                System.out.println("Set 2");
                return bestItemsGreedyHeuristic2;
            }

            if(value3 >= value0 && value3 >= value1 && value3 >= value2) {
                System.out.println("Set 3");
                return bestItemsGreedyHeuristic3;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Knapsack getKnapsackFromFile(String path) {
        ArrayList<String> easy20 = readLines(path);

        int lineNum = 0;
        int itemCount = 0;
        KnapsackItem[] items = null;
        float capacity = 0;
        while (lineNum < easy20.size()) {
            String line = easy20.get(lineNum);
            //System.out.println(line);

            if (lineNum == 0) {
                itemCount = Integer.parseInt(line);
                items = new KnapsackItem[itemCount];
                lineNum++;
                continue;
            }

            if (lineNum < itemCount+1) {
                String[] parts = line.split("[ ]+");
                //System.out.println("Parts: " + parts.length);
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
