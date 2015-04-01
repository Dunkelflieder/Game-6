package game6.client;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.*;
import java.util.regex.Matcher;

public class LanguageFile {
	private Map<String, String> strings;

	public LanguageFile() {
		strings = new HashMap<String, String>();
	}

	public void load(String filename) {
		try (Scanner scanner = new Scanner(new FileInputStream(filename), "UTF-8")) {

			while (scanner.hasNext()) {
				String line = scanner.nextLine().trim();
				String[] split = line.split("=", 2);

				if (line.isEmpty()) continue;

				if (split.length == 2) {
					strings.put(split[0].trim(), split[1].trim());
				} else {
					System.err.println("Error reading language file, could not parse: \"" + line + "\"");
				}
			}

		} catch (FileNotFoundException e) {
			System.err.println("Could not load language file");
			e.printStackTrace();
			return;
		}

	}

	public String getString(String id) {
		String retString = strings.get(id);
		if (retString != null) {
			return retString;
		} else {
			return id;
		}
	}

	public String getString(String id, String... values) {
		String retString = strings.get(id);
		if (retString != null) {
			for (int i = 0; i < values.length; i++) {
				retString = retString.replaceAll("\\{" + (i + 1) + "\\}", Matcher.quoteReplacement(values[i]));
			}

			return retString;
		} else {
			return id + " " + Arrays.toString(values);
		}
	}
}
