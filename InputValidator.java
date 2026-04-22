import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class for validated user input.
 */
public class InputValidator {

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FMT =
            DateTimeFormatter.ofPattern("HH:mm");

    public static String readNonEmptyString(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("  [ERROR] Input cannot be empty. Please try again.");
        }
    }

    public static int readPositiveInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) return value;
                System.out.println("  [ERROR] Please enter a positive number.");
            } catch (NumberFormatException e) {
                System.out.println("  [ERROR] Invalid number. Please try again.");
            }
        }
    }

    public static String readDate(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                LocalDate.parse(input, DATE_FMT);
                return input;
            } catch (DateTimeParseException e) {
                System.out.println(
                        "  [ERROR] Invalid date. Use dd/MM/yyyy (e.g. 25/12/2026).");
            }
        }
    }

    public static String readTime(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                LocalTime.parse(input, TIME_FMT);
                return input;
            } catch (DateTimeParseException e) {
                System.out.println(
                        "  [ERROR] Invalid time. Use HH:mm format (e.g. 14:30).");
            }
        }
    }

    public static boolean isEventIdUnique(int id, List<Event> events) {
        for (Event e : events) {
            if (e.getEventId() == id) return false;
        }
        return true;
    }
}
