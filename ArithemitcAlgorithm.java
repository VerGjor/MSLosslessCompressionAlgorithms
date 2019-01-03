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
	
	public static float getCurrentLowFor(float low, float value, float range) {
		return low + range * value;
	}
	
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
		System.out.println("Enter a string that you'd like to encode: ");
		
		String inputString = input.nextLine();
		
		System.out.println("Enter the probabilities for the following characters: ");
		
		List<Character> tempString = individualCharactersOf(inputString);
		Map<Character, Float> probabilitiesOfCharacters = new LinkedHashMap<>();
		
		for(char character : tempString) {
			System.out.print(character + ": ");
			probabilitiesOfCharacters.put(character, input.nextFloat());
		}
		
		input.close();
		

		Map<Character, Float> sortedValues = 
				probabilitiesOfCharacters.entrySet().stream()
										.sorted(Collections.reverseOrder(HashMap.Entry.comparingByValue()))
										.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> a, LinkedHashMap::new));
		
		float currentLow = 0;
		float currentHigh = 1;
		float range = currentHigh - currentLow;
		List<Float> values = new ArrayList<>();
		values.add(currentLow);
		
		values = valuesOfNextString(sortedValues, values, currentLow, range);
		
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
