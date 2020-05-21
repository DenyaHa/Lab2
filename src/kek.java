import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class kek {private static double calculatedValue = 0; // dont yet
    private static String stringPhrase;
    private static Scanner in;
    private static final String sum = "+";
    private static final String sub = "-";
    private static final String mult = "*";
    private static final String div = "/";
    private static final String bracketLeft = "(";
    private static final String bracketRight = ")";

    public static void main(String[] args) {
        in = new Scanner(System.in);
        System.out.println("Введите выражение:");
        stringPhrase = in.nextLine();
        checkStringEmpty();
    }

    private static void checkStringEmpty() {
        if (stringPhrase.isEmpty()) {
            System.out.println("Ошибка! Введите заново:");
        } else {
            System.out.println("Ответ: " + calculatedString());
            System.out.println("\nЕще разок? Введите выражение:");
            stringPhrase = in.nextLine();
            checkStringEmpty();
        }

    }

    private static String calculatedString() {
        stringPhrase = stringPhrase.trim();
        ArrayList<String> listSymbols = new ArrayList<>();
        Pattern pattern = Pattern.compile("[+]|-|[*]|/|[(][)]|[(]|[)]|[0-9]+|^");
        Matcher matcher = pattern.matcher(stringPhrase);
        for (/*int index = 0*/ ; matcher.find(); /*index++*/) {
            listSymbols.add(matcher.group());
        }
        stringPhrase = searchValue(listSymbols);
        return listSymbols.get(0);
    }

    private static String searchValue(ArrayList<String> listSymbols) {
        for (int x = 0; x < listSymbols.size(); x++) {
            String value = listSymbols.get(x);
            stringPhrase = listSymbols.stream().map(String::valueOf).collect(Collectors.joining());
            if (value.equals(bracketLeft)) {
                if (listSymbols.size() == 1) return listSymbols.get(0);
                int secondBracketRight = listSymbols.indexOf(bracketRight);
                int diff = secondBracketRight - x;
                ArrayList<String> listItems = new ArrayList<>();
                for (int i = x; i < secondBracketRight; i++) {
                    listItems.add(listSymbols.get(i));
                }
                listItems.remove(0);
                String valueS = searchValue(listItems);
                listSymbols.set(x, valueS);
                for (int i = 0; i < diff; i++) listSymbols.remove(x+ 1);
            }
            if (value.equals(mult)) calculateAndReplace(listSymbols, x, mult);
            else if (value.equals(div)) calculateAndReplace(listSymbols, x, div);
            else if (value.equals(sum) && !(listSymbols.contains(mult) || listSymbols.contains(div)))
                calculateAndReplace(listSymbols, x, sum);
            else if (value.equals(sub) && !(listSymbols.contains(mult) || listSymbols.contains(div)))
                calculateAndReplace(listSymbols, x, sub);

        }
        if (listSymbols.size() > 1) searchValue(listSymbols);
        else return stringPhrase;
        return stringPhrase;
    }

    private static void calculateAndReplace(ArrayList<String> listSymbols, int indeXXX, String action) {
        listSymbols.set(indeXXX - 1, String.valueOf(calculateValue(action, listSymbols, indeXXX)));
        listSymbols.remove(indeXXX);
        listSymbols.remove(indeXXX);
        searchValue(listSymbols);
    }

    private static double calculateValue(String operation, ArrayList<String> listSymbol, int index) {
        double valueOne = Double.parseDouble(listSymbol.get(index - 1));
        double valueTwo = Double.parseDouble(listSymbol.get(index + 1));
        switch (operation) {
            case sum:
                return sum(valueOne, valueTwo);
            case sub:
                return sub(valueOne, valueTwo);
            case mult:
                return mult(valueOne, valueTwo);
            case div:
                return div(valueOne, valueTwo);
        }
        return valueOne;
    }

    private static double sum(double valueOne, double valueTwo) {
        return valueOne + valueTwo;
    }

    private static double sub(double valueOne, double valueTwo) {
        return valueOne - valueTwo;
    }

    private static double div(double valueOne, double valueTwo) {
        return valueOne / valueTwo;
    }

    private static double mult(double valueOne, double valueTwo) {
        return valueOne * valueTwo;
    }
}
