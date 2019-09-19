import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LZWAlgorithm {

    // funkcijata prima proizvolen string i vrakja lista so unikatnite bukvi od stringot
    private static List<Character> insertDistinctCharactersFrom(String inputString){

        List<Character> list = new ArrayList<>();
        for(int i = 0; i<inputString.length(); i++) {
            if(!list.contains(inputString.charAt(i))) {
                list.add(inputString.charAt(i));
            }
        }

        return list;
    }

    // go vrakja klucot (indeksot) na vlezniot string
    private static int getKeyFrom(String character, Map<Integer, String> dictionary) {

        int key = 0;

        for(int i : dictionary.keySet()) {
            if(dictionary.get(i).equals(character)) {
                key = i;
                break;
            }
        }

        return key;
    }

    // go presmetuva stepenot na kompresija na algoritamot
    private static double getCompressionRate(int input, int output) {
        return input*1.0/output;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("LZW Alrogirthm");
        System.out.println("Enter an input string: ");

        String inputString = input.nextLine();

        System.out.println("Enter the initial dictionary:");

        // prebrojuva kolku unikatni bukvi imame vo vneseniot string
        long countEntries = inputString.chars().distinct().count();

        // kreirame nova lisa koja gi sodrzi samo unikatnite bukvi od string-ot sto go vnesovme
        List<Character> distinctCharacters = insertDistinctCharactersFrom(inputString);

        long counter = 0;
        Map<Integer, String> dictionary = new LinkedHashMap<>();

        int indexOfEntry = 1;

        // so ovoj ciklus ja kreirame tabelata so karakteri sto treba da se izminat vo stringot za da se
        // kreira inicijalniot recnik, a toa go praveme so racno vnesuvanje na unikatnite karakteri
        // eden po eden preku vlez
        while(counter != countEntries) {
            System.out.print("\nindex: " + indexOfEntry);
            System.out.print("\nentry: ");
            String character = input.next();
            if(!distinctCharacters.contains(character.charAt(0))) {
                System.out.println("The character that you've entered does not exist in the input string!");
            }
            else {
                dictionary.put(indexOfEntry, character);
                counter++;
            }
            indexOfEntry += 1;
        }

        input.close();

        int index = 0;
        String current = inputString.charAt(index) + "";
        String next = inputString.charAt(index+1) + "";
        List<Integer> output = new ArrayList<>();
        StringBuilder tempString = new StringBuilder();


        // glavniot ciklus preku koj se odviva LZW enkodiranjeto
        for(int i=1;i<inputString.length();i++) {

            // najprvo gi dodavame momentalnata i slednata bukva od vlezniot string vo
            // privremen string i go pecateme
            tempString.append(current);
            tempString.append(next);
            System.out.println(tempString.toString());

            // proveruvame dali nasiot recnik go sodrzi ovoj privremen string
            // dokolku ne go sodrzi, go dodavame vo recnikot i go prebrisuvame stringot
            // ako go sodrzi ovoj string, togas current za vrednost kje go sodrzi nasiot momentalen privremen string
            // a next kje bide sledbenikot na next
            if(!dictionary.containsValue(tempString.toString())) {
                int sizeOfDictionary = dictionary.keySet().size();
                dictionary.put(sizeOfDictionary + 1, tempString.toString());
                output.add(getKeyFrom(current, dictionary));
                tempString.setLength(0);
                current = inputString.charAt(i) + "";
                if(i+1 != inputString.length())
                    next = inputString.charAt(i + 1) + "";
            }
            else {

                current = tempString.toString();

                if(i+1 != inputString.length())
                    next = inputString.charAt(i+1) + "";

                tempString.setLength(0);

            }
        }

        // ja pravi istata proverka kako vo ciklusot za pretposlednata i poslednata bukva od ciklusot za da nemame
        // NullPointerException
        if(!dictionary.containsValue(current)) {
            int sizeOfDictionary = dictionary.keySet().size();
            dictionary.put(sizeOfDictionary + 1, tempString.toString());
            output.add(getKeyFrom(current, dictionary));
        }
        else {
            output.add(getKeyFrom(current, dictionary));
        }


        // go pecateme ostanatiot del od recnikot (sto go dodadovme so prethodniot ciklus)
        System.out.print("Dictionary: \n");
        for(int i : dictionary.keySet()) {
            System.out.println(i + " " + dictionary.get(i));
        }

        // izlezot od algoritamot e (indeksite na stringovite sto bile povikani za vreme na izminuvanjeto na ciklusot)
        System.out.print("\nOutput: ");
        for(int i : output) {
            System.out.print(i + " ");
        }

        double compressionRate = getCompressionRate(inputString.length(), output.size());
        DecimalFormat numberFormat = new DecimalFormat("#.000");
        String compressionRateString = "\nCompression rate: " + numberFormat.format(compressionRate);
        System.out.println(compressionRateString);

    }

}
