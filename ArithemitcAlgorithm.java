import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ArithemitcAlgorithm {

    public static List<Character> individualCharactersOf(String line) {

        List<Character> finalString = new ArrayList<>();
        for(int i = 0; i < line.length(); i++) {
            if(!finalString.contains(line.charAt(i))) {
                finalString.add(line.charAt(i));
            }
        }

        return finalString;
    }

    // funckija za presmetuvanje na momentalniot pomal del od opsegot
    public static float getCurrentLowFor(float low, float value, float range) {
        return low + range * value;
    }

    // go presmetuva opsegot za momentalnata bukva
    public static List<Float> valuesOfNextString(Map<Character, Float> sortedValues, List<Float> values, float currentLow, float range){

        System.out.print(currentLow + " - ");
        for(char character : sortedValues.keySet()) {
            System.out.print(" - ");
            currentLow = getCurrentLowFor(currentLow, sortedValues.get(character), range);
            values.add(currentLow);
            String printLine = character + " - " + currentLow;
            System.out.print(printLine);
        }

        System.out.println();
        return values;
    }

    public static int getCurrentLowIndex(char character, Map<Character, Float> sortedValues) {

        int counter = 0;

        for(char c : sortedValues.keySet()) {
            if(character == c) {
                break;
            }
            else {
                counter ++;
            }
        }

        return counter;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Arithemtic Alrogirthm");
        System.out.println("Enter a string that you'd like to encode: "); // Primer MULTI

        String inputString = input.nextLine();

        System.out.println("Enter the probabilities for the following characters: ");
        //  M=0.1, U=0.3, L=0.3, Т=0.2 и I=0.1

        // kreira lista so unikatni bukvi od vlezniot string
        List<Character> tempString = individualCharactersOf(inputString);
        Map<Character, Float> sortedValues = new LinkedHashMap<>();

        // za sekoja edinecna bukva se dodava negovata soodvetna verojatnost vo mapa
        for(char character : tempString) {
            System.out.print(character + ": ");
            sortedValues.put(character, input.nextFloat());
        }

        input.close();


        // gi sortirame elementite od mapata vo opagacki redosled spored verojatnostite
        sortedValues =
                sortedValues.entrySet().stream()
                        .sorted(Collections.reverseOrder(HashMap.Entry.comparingByValue()))
                        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> a, LinkedHashMap::new));


        // go definirame inicijalniot opseg
        // 0 — U — 0.3 — L — 0.6 — T — 0.8 — M - 0.9 — I — 1.0
        float currentLow = 0;
        float currentHigh = 1;

        float range = currentHigh - currentLow;
        List<Float> values = new ArrayList<>();
        values.add(currentLow);

        values = valuesOfNextString(sortedValues, values, currentLow, range);

        // Arithmetic Algorithm
        for(int i = 0; i < inputString.length() - 1; i++) {

            Collections.sort(values);
            int index = getCurrentLowIndex(inputString.charAt(i), sortedValues);
            currentLow = values.get(index);
            currentHigh = values.get(index + 1);
            range = currentHigh - currentLow;

            values.clear();
            values.add(currentLow);
            values = valuesOfNextString(sortedValues, values, currentLow, range);

        }

        Collections.sort(values);
        int index = getCurrentLowIndex(inputString.charAt(inputString.length()-1), sortedValues);
        currentLow = values.get(index);
        currentHigh = values.get(index + 1);
        String finalMsg = "The encoded word is between ["+ currentLow + ", " + currentHigh + ")";
        System.out.println(finalMsg);

    }

}
