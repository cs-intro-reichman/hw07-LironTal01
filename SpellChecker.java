
public class SpellChecker {

	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
	}

	public static String tail(String str) {
		return str.substring(1, str.length());
	}

	public static int levenshtein(String word1, String word2) {

		int insert = 0;
		int delete = 0;
		int replace = 0;
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();

		if (word1.length() == 0) {
			return word2.length();

		}

		else if (word2.length() == 0) {
			return word1.length();
		}

		if (word1.charAt(0) == word2.charAt(0)) {
			word1 = tail(word1);
			word2 = tail(word2);
			return levenshtein(word1, word2);

		} else {
			insert = levenshtein(tail(word1), word2);
			delete = levenshtein(word1, tail(word2));
			replace = levenshtein(tail(word1), tail(word2));
		}

		return Math.min(Math.min(insert, delete), replace) + 1;

	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];

		In in = new In(fileName);

		for (int i = 0; i < dictionary.length; i++) {
			dictionary[i] = in.readString();

		}

		return dictionary;
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
		int minDistance = threshold + 1;
		String similarString = word;

		for (int i = 0; i < dictionary.length; i++) {
			int distance = levenshtein(word, dictionary[i]);
			if (distance < minDistance) {
				minDistance = distance;
				similarString = dictionary[i];
			}

		}

		if (minDistance <= threshold) {
			return similarString;
		} else {
			return word;
		}
	}

}
