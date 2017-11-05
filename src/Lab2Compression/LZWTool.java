package Lab2Compression;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class LZWTool {

    public static void main(String[] args) {

        String source = readFile("res/source");

        ArrayList<Integer> codesBefore = _compress(source);

        //_printArray(codesBefore);

        _putCodeListToFile(codesBefore, "res/compressed");

        ArrayList<Integer> codesAfter = _getCodeListFromFile("res/compressed");

        //System.out.println("------------");
        //_printArray(codesAfter);

        System.out.println(_decompress(codesAfter));
    }

    public static void compressStringToFile(String string, String filepath) {
        ArrayList<Integer> codesBefore = _compress(string);
        _putCodeListToFile(codesBefore, filepath);
    }

    public static String decompressFileToString(String filepath) {
        ArrayList<Integer> codesAfter = _getCodeListFromFile(filepath);
        return _decompress(codesAfter);
    }

    public static String readFile(String path) {
        try {
            InputStream is = new FileInputStream(path);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            String fileAsString = sb.toString();
            return fileAsString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // -- Private

    private static ArrayList<Integer> _compress(String source) {
        // -- Create dictionary
        HashMap<String, Integer> dictionary = new HashMap<>();
        _initDictionary(dictionary);

        String combo = source.substring(0, 1);
        int code = 256;
        ArrayList<Integer> outputCodes = new ArrayList<>();

        // -- Scan loop
        for (int i = 1; i < source.length(); i++) {
            char c = source.charAt(i);

            if(dictionary.containsKey(combo + c)) {
                combo = combo + c;
            } else {
                outputCodes.add(dictionary.get(combo));

                dictionary.put(combo + c, code);
                code++;

                combo = String.valueOf(c);
            }
        }

        return outputCodes;
    }

    private static String _decompress(ArrayList<Integer> codes) {
        // -- Create dictionary
        HashMap<Integer, String> dictionary = new HashMap<>();
        _initDictionaryReverse(dictionary);

        int code = 256;
        ArrayList<String> outputStrings = new ArrayList<>();

        int ocode = codes.get(0);
        outputStrings.add(dictionary.get(ocode));

        String string = " ";
        char ch;

        // -- Scan loop
        for (int i = 1; i < codes.size(); i++) {

            int ncode = codes.get(i);

            if(ncode == 4095)
                break;

            if(dictionary.containsKey(ncode)) {
                string = dictionary.get(ncode);
            } else {

            }

            outputStrings.add(string);
            ch = string.charAt(0);
            dictionary.put(code, dictionary.get(ocode) + ch);
            code++;
            ocode = ncode;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < outputStrings.size(); i++) {
            sb.append(outputStrings.get(i));
            //System.out.print(outputStrings.get(i));
        }

        return sb.toString();
    }

    private static void _initDictionary(HashMap<String, Integer> dictionary) {
        for (int i = 0; i < 255; i++) {
            dictionary.put(String.valueOf((char) i), i);
        }
    }

    private static void _initDictionaryReverse(HashMap<Integer, String> dictionary) {
        for (int i = 0; i < 255; i++) {
            dictionary.put(i, String.valueOf((char) i));
        }
    }

    private static void _putCodeListToFile(ArrayList<Integer> codes, String path) {
        ArrayList<Byte> bytes = new ArrayList<>();

        for (int i = 0; i < codes.size(); i+=2) {
            if(i+1 >= codes.size()) {
                codes.add(4095);
            }

            int[] codeSubset = new int[] { codes.get(i), codes.get(i+1) };

            char a = (char) (codeSubset[0] >>> 4);
            char b = (char) (((codeSubset[0] & ~(0xff << 8)) << 4) + (codeSubset[1] >>> 8));
            char c = (char) (codeSubset[1] & ~(0xf << 8));

            bytes.add((byte) a);
            bytes.add((byte) b);
            bytes.add((byte) c);
        }

        byte[] byteArray = new byte[bytes.size()];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = bytes.get(i);
        }

        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(byteArray);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Integer> _getCodeListFromFile(String path) {

        Path path1 = Paths.get(path);
        byte[] data = null;
        try {
            data = Files.readAllBytes(path1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(data == null)
            return null;

        ArrayList<Integer> codes = new ArrayList<>();

        for (int i = 0; i < data.length; i+=3) {
            if(i+2 >= data.length) {
                try {
                    throw new Exception("Compressed file cannot be split into 3 byte chunks.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            char[] dataSubset = new char[] { (char) (data[i] & 0xff), (char) (data[i+1] & 0xff), (char) (data[i+2] & 0xff) };

            int a = (dataSubset[0] << 4) + (dataSubset[1] >>> 4);
            int b = ((dataSubset[1] & ~(0xf << 4)) << 8) + dataSubset[2];

            codes.add(a);
            codes.add(b);
        }

        return codes;
    }

    private static void _printArray(ArrayList<Integer> array) {
        for (int i = 0; i < array.size(); i++) {
            System.out.println(array.get(i));
        }
    }
}