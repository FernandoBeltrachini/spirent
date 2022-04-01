package service;

import java.util.TreeSet;

public class PhoneNumberService implements LineProcessor {

    private static final String DEFAULT_COUNTRY_CODE = "7";
    private static final String DEFAULT_CITY_CODE = "812";
    private static final String REPLACEMENTS = " |\\+|-|\\(|\\)|";
    private static final String NUMBER_REG_EX = "[+-]?\\d*(\\.\\d+)?";
    private TreeSet<Long> mappedValues = new TreeSet<>();

    private static Long parseLine(String line) {
        String number = line.replaceAll(REPLACEMENTS, "");
        if (number.length() < 7 || number.length() > 11 || !number.matches(NUMBER_REG_EX)) {
            System.out.println("invalid line:  " + number);
            return 0L;
        }
        StringBuffer sb = new StringBuffer();
        if (number.length() == 7) {
            sb.append(DEFAULT_COUNTRY_CODE).append(DEFAULT_CITY_CODE);
        }
        if (number.length() == 10) {
            sb.append(DEFAULT_COUNTRY_CODE);
        }
        sb.append(number);
        return Long.valueOf(sb.toString());
    }

    public void processResponse() {
        System.out.println("Printing result set");
        mappedValues.descendingSet().forEach(s -> System.out.println(unifiedFormat(s)));
        System.out.println("End of result set");
    }

    // +7 (812) 123-4567
    private String unifiedFormat(Long number) {
        String stringNumber = String.valueOf(number);
        if (stringNumber.length() == 11) {
            StringBuffer sb = new StringBuffer();
            sb.append("+")
                    .append(stringNumber.charAt(0))
                    .append(" (")
                    .append(stringNumber, 1, 4)
                    .append(") ")
                    .append(stringNumber, 4, 7)
                    .append("-")
                    .append(stringNumber, 7, 11);
            return sb.toString();
        }
        return "";
    }

    @Override
    public void processLine(String line) {
        Long parsedLine = parseLine(line);
        if (parsedLine != 0L) {
            mappedValues.add(parsedLine);
        }
    }

}
