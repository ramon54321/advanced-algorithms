import java.util.Arrays;

public class ExList {
    private Comparable[] array;

    public ExList(Comparable[] array){
        this.setArray(array);
    }

    public void setArray(Comparable[] array) {
        this.array = array;
    }

    public void sort(){
        Arrays.sort(array);
    }

    int searchLinear(Comparable element){
        for (int i = 0; i < array.length; i++) {
            if(element.compareTo(array[i]) == 0)
                return i;
        }
        return -1;
    }

    int searchBinary(Comparable element){

        int startIndex = 0;
        int endIndex = array.length-1;
        int currentIndex = (endIndex + startIndex) / 2;

        while (true) {
            int comparison = element.compareTo(array[currentIndex]);

            if (comparison == 1) {
                // -- The target is higher than current
                // -- Check to see if start and end are neighbours
                if (Math.abs(startIndex - endIndex) <= 1)
                    break;

                // -- Move start to current
                startIndex = currentIndex;

                // -- Move current to middle of range
                currentIndex = (endIndex + startIndex) / 2;
            } else if (comparison == -1) {
                // -- The target is lower than current
                // -- Check to see if start and end are neighbours
                if (Math.abs(startIndex - endIndex) <= 1)
                    break;

                // -- Move end to current
                endIndex = currentIndex;

                // -- Move current to middle of range
                currentIndex = (endIndex + startIndex) / 2;
            } else {
                // -- The target is the current
                //System.out.println("Got it!");
                return currentIndex;
            }
        }
        //System.out.println("Not found");
        return -1;
    }
}