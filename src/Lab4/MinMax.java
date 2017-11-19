package Lab4;

public class MinMax {

    private Comparable[] array;
    private int min = 0;
    private int max = 0;

    private int comparisons = 0;

    public MinMax(Comparable[] array) {
        this.array = array;
    }

    public void minmax() {
        if(array.length == 0)
            return;

        min = 0;
        max = 0;
        comparisons = 0;

        MinMaxTuple minMaxTuple = minmaxInternal(0, array.length-1);
        min = minMaxTuple.min;
        max = minMaxTuple.max;
    }

    private MinMaxTuple minmaxInternal(int startIndex, int endIndex) {
        if(startIndex == endIndex) {
            return new MinMaxTuple(startIndex, endIndex);
        } else if (startIndex == endIndex-1) {
            comparisons++;
            if(array[startIndex].compareTo(array[endIndex]) < 0) {
                return new MinMaxTuple(startIndex, endIndex);
            } else {
                return new MinMaxTuple(endIndex, startIndex);
            }
        } else {
            int middleIndex = (startIndex + endIndex) / 2;
            MinMaxTuple a = minmaxInternal(startIndex, middleIndex);
            MinMaxTuple b = minmaxInternal(middleIndex+1, endIndex);
            int combinedMax = 0;
            int combinedMin = 0;
            comparisons+=2;
            if(array[a.max].compareTo(array[b.max]) < 0) {
                combinedMax = b.max;
            } else {
                combinedMax = a.max;
            }
            if(array[a.min].compareTo(array[b.min]) > 0) {
                combinedMin = b.min;
            } else {
                combinedMin = a.min;
            }
            return new MinMaxTuple(combinedMin, combinedMax);
        }
    }

    public void minmax2() {
        if(array.length == 0)
            return;

        min = 0;
        max = 0;
        comparisons = 0;

        for (int i = 1; i < array.length; i++) {
            if(array[i].compareTo(array[min]) < 0) {
                comparisons+=1;
                min = i;
            } else if (array[i].compareTo(array[max]) > 0) {
                comparisons+=2;
                max = i;
            } else {
                comparisons+=2;
            }
        }
    }

    public Comparable getMin() {
        return min;
    }

    public Comparable getMax() {
        return max;
    }

    public int getComparisons() {
        return comparisons;
    }
}
