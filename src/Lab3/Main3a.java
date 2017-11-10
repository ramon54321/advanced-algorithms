package Lab3;

import Lab1.Stopwatch;

import java.util.Arrays;
import java.util.Random;

public class Main3a {

    private static String[] arrayToSort;
    private static String[][] arrayMap;

    private static final int AVERAGING_ITERATIONS = 10;
    private static final int INTERNAL_ITERATIONS = 10;

    public static void main(String[] args) {
        envSortInsertionToQuickSortC();
    }

    // -- Sorting
    public static void sortInsertion(Comparable[] a) {
        int size = a.length;
        for (int i = 0; i < size; i++) {
            for (int j = i; j > 0 && a[j-1].compareTo(a[j]) > 0; j--) {
                Comparable tmp = a[j];
                a[j] = a[j-1];
                a[j-1] = tmp;
            }
        }
    }

    public static void sortBubble(Comparable[] a) {
        int size = a.length;
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = 1; i < size; i++) {
                if(a[i-1].compareTo(a[i]) > 0) {
                    Comparable tmp = a[i];
                    a[i] = a[i-1];
                    a[i-1] = tmp;
                    swapped = true;
                }
            }
        }
    }

    // -- Timing
    private static void envSortInsertionToQuickSortC() {
        for (int i = 100; i <= 1000; i += 100) {
            buildArrayMap(i);
            Stopwatch sw = new Stopwatch();
            sw.start();
            for (int j = 0; j < AVERAGING_ITERATIONS; j++) {
                testSortInsertionB();
            }
            sw.stop();
            System.out.println("IS " + i + " - " + sw.toValue() / 1000);
        }

        for (int i = 100; i <= 1000; i += 100) {
            buildArrayMap(i);
            Stopwatch sw2 = new Stopwatch();
            sw2.start();
            for (int j = 0; j < AVERAGING_ITERATIONS; j++) {
                testSortQuickB();
            }
            sw2.stop();
            System.out.println("QS " + i + " - " + sw2.toValue() / 1000);
        }

        for (int i = 100; i <= 1000; i += 100) {
            buildArrayMap(i);
            Stopwatch sw3 = new Stopwatch();
            sw3.start();
            for (int j = 0; j < AVERAGING_ITERATIONS; j++) {
                testSortBubbleB();
            }
            sw3.stop();
            System.out.println("BS " + i + " - " + sw3.toValue() / 1000);
        }
    }

    private static void envSortInsertionToQuickSortB() {
        for (int i = 1000; i <= 10000; i += 1000) {
            buildArrayMap(i);
            Stopwatch sw = new Stopwatch();
            sw.start();
            testSortInsertionB();
            sw.stop();
            System.out.println("IS " + i + " - " + sw.toValue() / 1000);
        }

        for (int i = 1000; i <= 10000; i += 1000) {
            buildArrayMap(i);
            Stopwatch sw2 = new Stopwatch();
            sw2.start();
            testSortQuickB();
            sw2.stop();
            System.out.println("QS " + i + " - " + sw2.toValue() / 1000);
        }

        for (int i = 1000; i <= 10000; i += 1000) {
            buildArrayMap(i);
            Stopwatch sw3 = new Stopwatch();
            sw3.start();
            testSortBubbleB();
            sw3.stop();
            System.out.println("BS " + i + " - " + sw3.toValue() / 1000);
        }
    }

    private static void testSortInsertionB() {
        for (int j = 0; j < INTERNAL_ITERATIONS; j++) {
            sortInsertion(arrayMap[j]);
        }
    }

    private static void testSortBubbleB() {
        for (int j = 0; j < INTERNAL_ITERATIONS; j++) {
            sortBubble(arrayMap[j]);
        }
    }

    private static void testSortQuickB() {
        for (int j = 0; j < INTERNAL_ITERATIONS; j++) {
            Arrays.sort(arrayMap[j]);
        }
    }

    private static void envSortInsertionToQuickSort() {
        for (int i = 1000; i <= 10000; i += 1000) {
            long timeTotal = 0;
            for (int j = 0; j < AVERAGING_ITERATIONS; j++) {
                //arrayToSort = getStringArray(i, 32);
                buildArrayMap(i);

                Stopwatch sw = new Stopwatch();
                sw.measure(() -> testSortInsertion());
                timeTotal += sw.toValue();
            }
            timeTotal /= AVERAGING_ITERATIONS;
            System.out.println("IS - Time for " + i + ": " + timeTotal);
        }

        for (int i = 1000; i <= 10000; i += 1000) {
            long timeTotal = 0;
            for (int j = 0; j < AVERAGING_ITERATIONS; j++) {
                //arrayToSort = getStringArray(i, 32);
                buildArrayMap(i);

                Stopwatch sw = new Stopwatch();
                sw.measure(() -> testSortQuick());
                //System.out.println(sw.toValue());
                timeTotal += sw.toValue();
            }
            timeTotal /= AVERAGING_ITERATIONS;
            System.out.println("QS - Time for " + i + ": " + timeTotal);
        }

        for (int i = 1000; i <= 10000; i += 1000) {
            long timeTotal = 0;
            for (int j = 0; j < AVERAGING_ITERATIONS; j++) {
                //arrayToSort = getStringArray(i, 32);
                buildArrayMap(i);

                Stopwatch sw = new Stopwatch();
                sw.measure(() -> testSortBubble());
                //System.out.println(sw.toValue());
                timeTotal += sw.toValue();
            }
            timeTotal /= AVERAGING_ITERATIONS;
            System.out.println("BS - Time for " + i + ": " + timeTotal);
        }
    }

    private static void testSortInsertion() {
        for (int i = 0; i < INTERNAL_ITERATIONS; i++) {
            sortInsertion(arrayMap[i]);
        }
    }

    private static void testSortBubble() {
        for (int i = 0; i < INTERNAL_ITERATIONS; i++) {
            sortBubble(arrayMap[i]);
        }
    }

    private static void testSortQuick() {
        for (int i = 0; i < INTERNAL_ITERATIONS; i++) {
            Arrays.sort(arrayMap[i]);
        }
    }

    // -- Utilities
    private static Random random = new Random();
    private static String[] getStringArray(int length, int width) {
        String[] a = new String[length];
        for (int i = 0; i < length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < width; j++) {
                stringBuilder.append(getRandomChar());
            }
            a[i] = stringBuilder.toString();
        }
        return a;
    }

    private static void buildArrayMap(int length) {
        arrayMap = new String[length][INTERNAL_ITERATIONS];

        for (int i = 0; i < INTERNAL_ITERATIONS; i++) {
            arrayMap[i] = getStringArray(length, 32);
        }
    }

    private static char getRandomChar() {
        return (char) (97 + random.nextInt(26));
    }

    private static void printArray(Object[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

}
