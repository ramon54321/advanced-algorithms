package Lab2;

public class Lab2Main {

    public static void main(String[] args){

        MyDictionary<String, String> myDictionary = new MyDictionary<>(20);

        myDictionary.put("one", "number 1");
        myDictionary.put("two", "number 2");
        myDictionary.put("three", "number 3");
        myDictionary.put("four", "number 4");
        myDictionary.put("five", "number 5");

        System.out.println(myDictionary.get("two"));

        myDictionary.print();
    }

}
