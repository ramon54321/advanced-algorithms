package Lab6;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Knapsack {
    public KnapsackItem[] items;
    public float capacity;

    public Knapsack(KnapsackItem[] items, float capacity) {
        this.items = items;
        this.capacity = capacity;
    }

    public ArrayList<KnapsackItem> getHighestValueHeuristic(int numberToOptimize) {
        int permutationCount = (int) Math.pow(2, numberToOptimize);

        ArrayList<KnapsackItem> bestItems = new ArrayList<>();
        float bestItemsValue = 0;
        // for each permutation
        for (int i = 0; i < permutationCount; i++) {
            String bitString = String.format("%" + numberToOptimize + "s", Integer.toBinaryString(i)).replace(' ', '0');

            //System.out.println(bitString);

            ArrayList<KnapsackItem> permutationItems = new ArrayList<>();
            ArrayList<KnapsackItem> greedyItems = new ArrayList<>();
            for (int j = 0; j < bitString.length(); j++) {
                char bitVal = bitString.charAt(j);
                if(bitVal == '1'){
                    permutationItems.add(items[j]);
                } else {
                    greedyItems.add(items[j]);
                }
            }
            for (int j = numberToOptimize; j < items.length; j++) {
                greedyItems.add(items[j]);
            }

            //Greedy add
            greedyItems.sort(KnapsackItem.ValueDensityComparator);
            float weightOfPerm = getWeightOfSubset(permutationItems.toArray(new KnapsackItem[permutationItems.size()]));
            for (int j = 0; j < greedyItems.size(); j++) {
                //System.out.println("Greedy add");
                if(weightOfPerm + greedyItems.get(j).weight <= capacity) {
                    permutationItems.add(greedyItems.get(j));
                    weightOfPerm += greedyItems.get(j).weight;
                } else {
                    break;
                }
            }


            // If permutationItems satisfies weight AND has more value than best replace best
            float valueOfPerm = getValueOfSubset(permutationItems.toArray(new KnapsackItem[permutationItems.size()]));
            //float weightOfPerm = getWeightOfSubset(permutationItems.toArray(new KnapsackItem[permutationItems.size()]));
            //if(weightOfPerm <= capacity)
            //  System.out.println(valueOfPerm + " - " + weightOfPerm + "/" + capacity);
            if(weightOfPerm <= capacity &&
                    valueOfPerm > bestItemsValue) {
                bestItems = permutationItems;
                bestItemsValue = valueOfPerm;
            }

            if(i % 10000 == 0) {
                DecimalFormat df = new DecimalFormat("#.00");
                System.out.println(df.format((((float) i) / ((float) permutationCount))*100) + "%");
            }
        }

        return bestItems;
    }

    public ArrayList<KnapsackItem> getHighestValueComboGreedy() {
        ArrayList<KnapsackItem> knapsackItems = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            knapsackItems.add(items[i]);
        }
        knapsackItems.sort(KnapsackItem.ValueDensityComparator);

        for (int i = 0; i < knapsackItems.size(); i++) {
            System.out.println(knapsackItems.get(i).getDensity());
        }

        ArrayList<KnapsackItem> bestItems = new ArrayList<>();

        float weightCurrent = 0;
        for (int i = 0; i < knapsackItems.size(); i++) {
            if(weightCurrent + knapsackItems.get(i).weight <= capacity) {
                weightCurrent += knapsackItems.get(i).weight;
                bestItems.add(knapsackItems.get(i));
            } else {
                break;
            }
        }

        return bestItems;
    }

    public ArrayList<KnapsackItem> getHighestValueCombo() {
        int permutationCount = (int) Math.pow(2, items.length);

        //ArrayList<KnapsackItem> bestItems = new ArrayList<>();
        int bestPermutation = 0;
        float bestItemsValue = 0;
        // for each permutation
        for (int i = 0; i < permutationCount; i++) {
            String bitString = String.format("%" + items.length + "s", Integer.toBinaryString(i)).replace(' ', '0');

            //System.out.println(bitString);

            ArrayList<KnapsackItem> permutationItems = new ArrayList<>();
            for (int j = 0; j < bitString.length(); j++) {
                char bitVal = bitString.charAt(j);
                if(bitVal == '1'){
                    permutationItems.add(items[j]);
                }
            }

            // If permutationItems satisfies weight AND has more value than best replace best
            float valueOfPerm = getValueOfSubset(permutationItems.toArray(new KnapsackItem[permutationItems.size()]));
            float weightOfPerm = getWeightOfSubset(permutationItems.toArray(new KnapsackItem[permutationItems.size()]));
            //if(weightOfPerm <= capacity)
              //  System.out.println(valueOfPerm + " - " + weightOfPerm + "/" + capacity);
            if(weightOfPerm <= capacity &&
                    valueOfPerm > bestItemsValue) {
                //bestItems = new ArrayList<>(permutationItems);
                bestPermutation = i;
                bestItemsValue = valueOfPerm;
            }

            if(i % 10000 == 0) {
                DecimalFormat df = new DecimalFormat("#.00");
                System.out.println(df.format((((float) i) / ((float) permutationCount))*100) + "%");
            }
        }

        String bitString = String.format("%" + items.length + "s", Integer.toBinaryString(bestPermutation)).replace(' ', '0');

        ArrayList<KnapsackItem> permutationItems = new ArrayList<>();
        for (int j = 0; j < bitString.length(); j++) {
            char bitVal = bitString.charAt(j);
            if(bitVal == '1'){
                permutationItems.add(items[j]);
            }
        }

        return permutationItems;
    }

    public float getWeightOfSubset(KnapsackItem[] items) {
        float total = 0;
        for (int i = 0; i < items.length; i++) {
            total += items[i].weight;
        }
        return total;
    }

    public float getValueOfSubset(KnapsackItem[] items) {
        float total = 0;
        for (int i = 0; i < items.length; i++) {
            total += items[i].value;
        }
        return total;
    }

}
