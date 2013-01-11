package at.tugraz.ist.catroid.uitest.formulaeditor;

public class StringUtilShort {

	public static int countMatches(String string, String subString) {
		if (isEmpty(string) || isEmpty(subString)) {
			return 0;
		}
		int count = 0;
		int index = 0;
		while ((index = string.indexOf(subString, index)) != -1) {
			count++;
			index += subString.length();
		}
		return count;
	}

	public static boolean isEmpty(String string) {
		return string == null || string.length() == 0;
	}

}
