import java.util.Random;

public class Main {

    // -- URL For Chart
    // https://docs.google.com/spreadsheets/d/e/2PACX-1vTPa6xodgm5ck4081H3VtRqwgK1HjhEd043ThWzCFzQpNQnkb0xks-dtUMhzDJ20HWUoN-fblIaQ5an/pubchart?oid=1196809382&format=interactive


    public static void main(String[] args) {
        random = new Random();
        count = (int) Math.pow(2, power);

        // -- Count override for specific tests
        count = 10000;

        System.out.println("Count: " + count);

        runIntegerTest();
        //runStringTest();
    }

    private static void runStringTest(){
        double total = 0;
        int wordLength = 30;

        for (int t = 10; t <= 55; t+=5) {
            count = t * 1000;

            for (int x = 0; x < 4; x++) {
                Comparable[] array = new Comparable[count];
                for (int i = 0; i < count; i++){
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int y = 0; y < wordLength; y++) {
                        char letter = (char) (random.nextInt(122-65) + 65);
                        stringBuilder.append(letter);
                    }
                    array[i] = stringBuilder.toString();
                }

                // -- Populate search array
                for (int i = 0; i < 512; i++) {
                    testEntities[i] = array[i * (count / 513)];
                }

                exList = new ExList(array);
                exList.sort();

                // -- Time of search
                Stopwatch sw = new Stopwatch();

                // ---------------------------------------------- Uncomment the type of search
                sw.measure(() -> testLinearString());
                //sw.measure(() -> testBinaryString());

                //System.out.println(sw.toValue());
                total += sw.toValue();
            }

            System.out.println("Average time: " + total/24);
        }
    }

    private static void runIntegerTest(){
        double total = 0;

        for (int t = 10; t <= 55; t+=5) {
            count = t * 1000;

            for (int x = 0; x < 24; x++) {
                Comparable[] array = new Comparable[count];
                for (int i = 0; i < count; i++) {
                    array[i] = i;//random.nextInt(count);
                }

                exList = new ExList(array);
                exList.sort();

                // -- Time of search
                Stopwatch sw = new Stopwatch();

                sw.measure(() -> testLinearInteger());
                //sw.measure(() -> testBinaryInteger());

                //System.out.println(sw.toValue());
                total += sw.toValue();
            }

            System.out.println("Average time: " + total / 24);
        }
    }

    private static Comparable[] testEntities = new Comparable[512];

    private static int power = 16;
    private static int count;
    private static ExList exList;
    private static Random random;

    private static void testLinearInteger(){
        for (int i = 0; i < 512; i++) {
            exList.searchLinear(random.nextInt(count));
        }
    }

    private static void testBinaryInteger(){
        for (int i = 0; i < 512; i++) {
            exList.searchBinary(random.nextInt(count));
        }
    }

    private static void testLinearString(){
        for (int i = 0; i < 512; i++) {
            exList.searchLinear(testEntities[i]);
        }
    }

    private static void testBinaryString(){
        for (int i = 0; i < 512; i++) {
            exList.searchBinary(testEntities[i]);
        }
    }
}
