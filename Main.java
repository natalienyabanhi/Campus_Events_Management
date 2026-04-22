import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        printBanner();

        EventManager manager = new EventManager();

        // Clear old saved data so fresh test data loads cleanly
        manager.getEvents().clear();

        // ── 4 Test Events ────────────────────────────────────────────────
        Staff admin = new Staff("S1", "Admin");
        admin.createEvent(manager, new Event(1, "Tech Talk",           "20/04/2026", "10:00", "Hall A",   3));
        admin.createEvent(manager, new Event(2, "Java Workshop",       "25/04/2026", "14:00", "Lab 3B",   2));
        admin.createEvent(manager, new Event(3, "Programming Seminar", "28/04/2026", "09:00", "Room 101", 3));
        admin.createEvent(manager, new Event(4, "Database Bootcamp",   "30/04/2026", "11:00", "Lab 2A",   2));

        // ── 8 Test Students ──────────────────────────────────────────────
        Student s1 = new Student("ST1", "Natalie");
        Student s2 = new Student("ST2", "John");
        Student s3 = new Student("ST3", "Mike");
        Student s4 = new Student("ST4", "Lisa");
        Student s5 = new Student("ST5", "James");
        Student s6 = new Student("ST6", "Sara");
        Student s7 = new Student("ST7", "Peter");
        Student s8 = new Student("ST8", "Anna");

        // Event 1 - max 3: 3 registered, 1 waitlisted
        s1.registerEvent(manager, 1);
        s2.registerEvent(manager, 1);
        s3.registerEvent(manager, 1);
        s4.registerEvent(manager, 1); // waitlisted

        // Event 2 - max 2: 2 registered, 1 waitlisted
        s5.registerEvent(manager, 2);
        s6.registerEvent(manager, 2);
        s7.registerEvent(manager, 2); // waitlisted

        // Event 3 - max 3: 3 registered
        s1.registerEvent(manager, 3);
        s8.registerEvent(manager, 3);
        s4.registerEvent(manager, 3);

        // Event 4 - max 2: 2 registered, 1 waitlisted
        s2.registerEvent(manager, 4);
        s3.registerEvent(manager, 4);
        s5.registerEvent(manager, 4); // waitlisted

        // ── Show only events table on startup (NO student table) ─────────
        printSectionHeader("CAMPUS EVENTS");
        manager.displayEvents();

        // ── Demo: cancellation triggers waitlist promotion thread ─────────
        printSectionHeader("DEMO - CANCELLATION AND WAITLIST PROMOTION");
        System.out.println("  Cancelling ST1 (Natalie) from Event 1 (Tech Talk)...");
        s1.cancelRegistration(manager, 1);
        System.out.println();

        // Show updated events after demo
        printSectionHeader("UPDATED EVENTS AFTER DEMO");
        manager.displayEvents();

        // ── Interactive menu ─────────────────────────────────────────────
        boolean running = true;
        while (running) {
            printMainMenu();
            String roleChoice = sc.nextLine().trim();

            switch (roleChoice) {
                case "1":
                    // Staff login with PIN
                    System.out.print("\n  Enter Staff PIN: ");
                    String staffPin = sc.nextLine().trim();
                    if (!staffPin.equals("1234")) {
                        System.out.println("\n  [ERROR] Incorrect PIN. Access denied.\n");
                        break;
                    }
                    String staffId   = InputValidator.readNonEmptyString(sc,
                        "  Enter Staff ID : ");
                    String staffName = InputValidator.readNonEmptyString(sc,
                        "  Enter Name     : ");
                    Staff  s         = new Staff(staffId, staffName);
                    System.out.println("\n  [SYSTEM] Welcome, Staff Member "
                        + staffName + "!\n");
                    running = staffMenu(sc, manager, s);
                    break;

                case "2":
                    // Student login with PIN
                    System.out.print("\n  Enter Student PIN: ");
                    String studentPin = sc.nextLine().trim();
                    if (!studentPin.equals("5678")) {
                        System.out.println("\n  [ERROR] Incorrect PIN. Access denied.\n");
                        break;
                    }
                    String studentId   = InputValidator.readNonEmptyString(sc,
                        "  Enter Student ID : ");
                    String studentName = InputValidator.readNonEmptyString(sc,
                        "  Enter Name       : ");
                    Student st = new Student(studentId, studentName);
                    System.out.println("\n  [SYSTEM] Welcome, Student "
                        + studentName + "!\n");
                    running = studentMenu(sc, manager, st);
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println(
                        "\n  [ERROR] Invalid option. Please enter 1, 2, or 0.\n");
            }
        }

        printBanner();
        System.out.println("  Thank you for using the system. Goodbye!");
        System.out.println("=".repeat(60));
        sc.close();
    }

    // ── Staff menu ────────────────────────────────────────────────────────
    private static boolean staffMenu(Scanner sc, EventManager manager, Staff staff) {
        boolean loggedIn = true;
        while (loggedIn) {
            printStaffMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println();
                    int    id  = InputValidator.readPositiveInt(sc,
                        "  Event ID          : ");
                    String nm  = InputValidator.readNonEmptyString(sc,
                        "  Event Name        : ");
                    String dt  = InputValidator.readDate(sc,
                        "  Event Date        : ");
                    String tm  = InputValidator.readTime(sc,
                        "  Event Time        : ");
                    String loc = InputValidator.readNonEmptyString(sc,
                        "  Location          : ");
                    int    max = InputValidator.readPositiveInt(sc,
                        "  Max Participants  : ");
                    staff.createEvent(manager, new Event(id, nm, dt, tm, loc, max));
                    break;

                case "2":
                    System.out.println();
                    int eid2     = InputValidator.readPositiveInt(sc,
                        "  Event ID  : ");
                    String newNm = InputValidator.readNonEmptyString(sc,
                        "  New Name  : ");
                    staff.updateEventName(manager, eid2, newNm);
                    break;

                case "3":
                    System.out.println();
                    int eid3     = InputValidator.readPositiveInt(sc,
                        "  Event ID  : ");
                    String newTm = InputValidator.readTime(sc,
                        "  New Time  : ");
                    staff.updateEventTime(manager, eid3, newTm);
                    break;

                case "4":
                    System.out.println();
                    int eid4      = InputValidator.readPositiveInt(sc,
                        "  Event ID      : ");
                    String newLoc = InputValidator.readNonEmptyString(sc,
                        "  New Location  : ");
                    staff.updateEventLocation(manager, eid4, newLoc);
                    break;

                case "5":
                    System.out.println();
                    int eid5     = InputValidator.readPositiveInt(sc,
                        "  Event ID  : ");
                    String newDt = InputValidator.readDate(sc,
                        "  New Date  : ");
                    staff.updateEventDate(manager, eid5, newDt);
                    break;

                case "6":
                    System.out.println();
                    int eid6 = InputValidator.readPositiveInt(sc,
                        "  Event ID to cancel: ");
                    staff.cancelEvent(manager, eid6);
                    break;

                case "7":
                    // View all events
                    printSectionHeader("ALL EVENTS");
                    manager.displayEvents();
                    break;

                case "8":
                    // View participants and waitlist for specific event
                    System.out.println();
                    int eid8 = InputValidator.readPositiveInt(sc, "  Event ID: ");
                    staff.viewParticipants(manager, eid8);
                    break;

                case "9":
                    // Staff-only: full student registration table
                    printSectionHeader("STUDENT REGISTRATION TABLE");
                    manager.displayStudentTable();
                    break;

                case "10":
                    System.out.println("\n  1. Sort by Name   2. Sort by Date");
                    System.out.print("  Select: ");
                    String sortChoice = sc.nextLine().trim();
                    if (sortChoice.equals("1"))      manager.sortByName();
                    else if (sortChoice.equals("2")) manager.sortByDate();
                    else System.out.println("  [ERROR] Invalid option.");
                    manager.displayEvents();
                    break;

                case "11":
                    System.out.println("\n  1. Search by Name   2. Search by Date");
                    System.out.print("  Select: ");
                    String srchChoice = sc.nextLine().trim();
                    if (srchChoice.equals("1")) {
                        String term = InputValidator.readNonEmptyString(sc,
                            "  Search term: ");
                        manager.searchByName(term);
                    } else if (srchChoice.equals("2")) {
                        String date = InputValidator.readDate(sc,
                            "  Date (dd/MM/yyyy): ");
                        manager.searchByDate(date);
                    } else {
                        System.out.println("  [ERROR] Invalid option.");
                    }
                    break;

                case "0":
                    loggedIn = false;
                    System.out.println("\n  [SYSTEM] Logged out successfully.\n");
                    break;

                default:
                    System.out.println(
                        "\n  [ERROR] Invalid option. Please try again.\n");
            }
        }
        return true;
    }

    // ── Student menu ──────────────────────────────────────────────────────
    private static boolean studentMenu(Scanner sc, EventManager manager,
            Student student) {
        boolean loggedIn = true;
        while (loggedIn) {
            printStudentMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    // Students see events table only - no student table
                    printSectionHeader("AVAILABLE EVENTS");
                    manager.displayEvents();
                    break;

                case "2":
                    // Register - show events first then ask for ID
                    System.out.println();
                    printSectionHeader("AVAILABLE EVENTS");
                    manager.displayEvents();
                    int regId = InputValidator.readPositiveInt(sc,
                        "  Enter Event ID to register: ");
                    student.registerEvent(manager, regId);
                    break;

                case "3":
                    // Cancel registration
                    System.out.println();
                    int canId = InputValidator.readPositiveInt(sc,
                        "  Enter Event ID to cancel: ");
                    student.cancelRegistration(manager, canId);
                    break;

                case "4":
                    // Student sees only their own status
                    printSectionHeader("MY REGISTRATION STATUS");
                    student.viewStatus(manager);
                    break;

                case "5":
                    System.out.println("\n  1. Search by Name   2. Search by Date");
                    System.out.print("  Select: ");
                    String srchChoice = sc.nextLine().trim();
                    if (srchChoice.equals("1")) {
                        String term = InputValidator.readNonEmptyString(sc,
                            "  Search term: ");
                        manager.searchByName(term);
                    } else if (srchChoice.equals("2")) {
                        String date = InputValidator.readDate(sc,
                            "  Date (dd/MM/yyyy): ");
                        manager.searchByDate(date);
                    } else {
                        System.out.println("  [ERROR] Invalid option.");
                    }
                    break;

                case "0":
                    loggedIn = false;
                    System.out.println("\n  [SYSTEM] Logged out successfully.\n");
                    break;

                default:
                    System.out.println(
                        "\n  [ERROR] Invalid option. Please try again.\n");
            }
        }
        return true;
    }

    // ── Display helpers ───────────────────────────────────────────────────

    private static void printBanner() {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("       RICHFIELD INSTITUTE OF TECHNOLOGY");
        System.out.println("        Campus Event Management System");
        System.out.println("=".repeat(60));
        System.out.println();
    }

    private static void printSectionHeader(String title) {
        System.out.println();
        System.out.println("  +" + "-".repeat(46) + "+");
        System.out.printf("  | %-46s |%n", "  " + title);
        System.out.println("  +" + "-".repeat(46) + "+");
    }

    private static void printMainMenu() {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("                 MAIN MENU");
        System.out.println("=".repeat(60));
        System.out.println("  1.  Login as Staff   (PIN: 1234)");
        System.out.println("  2.  Login as Student (PIN: 5678)");
        System.out.println("  0.  Exit");
        System.out.println("=".repeat(60));
        System.out.print("  Select role: ");
    }

    private static void printStaffMenu() {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("                STAFF MENU");
        System.out.println("=".repeat(60));
        System.out.println("  1.   Create Event");
        System.out.println("  2.   Update Event Name");
        System.out.println("  3.   Update Event Time");
        System.out.println("  4.   Update Event Location");
        System.out.println("  5.   Update Event Date");
        System.out.println("  6.   Cancel Event");
        System.out.println("  7.   View All Events");
        System.out.println("  8.   View Participants & Waitlist");
        System.out.println("  9.   View Student Registration Table");
        System.out.println("  10.  Sort Events");
        System.out.println("  11.  Search Events");
        System.out.println("  0.   Logout");
        System.out.println("=".repeat(60));
        System.out.print("  Select option: ");
    }

    private static void printStudentMenu() {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("               STUDENT MENU");
        System.out.println("=".repeat(60));
        System.out.println("  1.  View Available Events");
        System.out.println("  2.  Register for Event");
        System.out.println("  3.  Cancel Registration");
        System.out.println("  4.  View My Registration Status");
        System.out.println("  5.  Search Events");
        System.out.println("  0.  Logout");
        System.out.println("=".repeat(60));
        System.out.print("  Select option: ");
    }
}
