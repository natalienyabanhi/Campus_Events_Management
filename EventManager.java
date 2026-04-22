import java.util.*;

/**
 * Central manager for all events.
 */
public class EventManager {

    private List<Event> events;

    public EventManager() {
        this.events = FileManager.loadEvents();
        FileManager.loadRegistrations(events);
        FileManager.loadWaitlists(events);
    }

    public List<Event> getEvents() { return events; }

    // Add event with duplicate ID check
    public void addEvent(Event event) {
        for (Event e : events) {
            if (e.getEventId() == event.getEventId()) {
                System.out.println("  [ERROR] Event ID "
                        + event.getEventId() + " already exists.");
                return;
            }
        }
        events.add(event);
        FileManager.saveAll(events);
        System.out.println("  [SUCCESS] Event \"" + event.getEventName()
                + "\" created successfully.");
    }

    // Display all events in a neat table
    public void displayEvents() {
        System.out.println();
        System.out.println("  +" + "-".repeat(76) + "+");
        System.out.printf("  | %-6s | %-22s | %-10s | %-7s | %-10s | %-6s |%n",
                "ID", "Event Name", "Date", "Time", "Capacity", "Wait");
        System.out.println("  +" + "-".repeat(76) + "+");
        if (events.isEmpty()) {
            System.out.println("  | "
                    + String.format("%-74s", "  No events available.") + " |");
        } else {
            for (Event e : events) {
                String cap = e.getRegisteredParticipants().size()
                        + "/" + e.getMaxParticipants();
                System.out.printf(
                        "  | %-6d | %-22s | %-10s | %-7s | %-10s | %-6d |%n",
                        e.getEventId(),
                        e.getEventName(),
                        e.getEventDate(),
                        e.getEventTime(),
                        cap,
                        e.getWaitlist().size());
            }
        }
        System.out.println("  +" + "-".repeat(76) + "+");
        System.out.println();
    }

    // Display student registration table
    // Shows all students, their event, and registration status
    public void displayStudentTable() {
        System.out.println();
        System.out.println("  +" + "-".repeat(60) + "+");
        System.out.printf("  | %-12s | %-10s | %-22s | %-10s |%n",
                "Student ID", "Name", "Event", "Status");
        System.out.println("  +" + "-".repeat(60) + "+");

        boolean hasData = false;

        for (Event e : events) {
            // Registered participants - lists hold String IDs
            for (String studentId : e.getRegisteredParticipants()) {
                System.out.printf("  | %-12s | %-10s | %-22s | %-10s |%n",
                        studentId,
                        getNameFromId(studentId),
                        e.getEventName(),
                        "Registered");
                hasData = true;
            }
            // Waitlisted students
            for (String studentId : e.getWaitlist()) {
                System.out.printf("  | %-12s | %-10s | %-22s | %-10s |%n",
                        studentId,
                        getNameFromId(studentId),
                        e.getEventName(),
                        "Waitlisted");
                hasData = true;
            }
        }

        if (!hasData) {
            System.out.println("  | "
                    + String.format("%-58s", "  No student registrations found.") + " |");
        }
        System.out.println("  +" + "-".repeat(60) + "+");
        System.out.println();
    }

    // Maps known student IDs to names for the table display
    private String getNameFromId(String id) {
        switch (id) {
            case "ST1": return "Natalie";
            case "ST2": return "John";
            case "ST3": return "Mike";
            case "ST4": return "Lisa";
            case "ST5": return "James";
            case "ST6": return "Sara";
            case "ST7": return "Peter";
            case "ST8": return "Anna";
            default:    return id;
        }
    }

    public Event findById(int eventId) {
        for (Event e : events) {
            if (e.getEventId() == eventId) return e;
        }
        return null;
    }

    public void searchByName(String term) {
        System.out.println();
        System.out.println("  Search results for \"" + term + "\":");
        System.out.println("  +" + "-".repeat(76) + "+");
        boolean found = false;
        for (Event e : events) {
            if (e.getEventName().toLowerCase().contains(term.toLowerCase())) {
                String cap = e.getRegisteredParticipants().size()
                        + "/" + e.getMaxParticipants();
                System.out.printf(
                        "  | %-6d | %-22s | %-10s | %-7s | %-10s | %-6d |%n",
                        e.getEventId(), e.getEventName(), e.getEventDate(),
                        e.getEventTime(), cap, e.getWaitlist().size());
                found = true;
            }
        }
        if (!found) {
            System.out.println("  | "
                    + String.format("%-74s", "  No events matched \"" + term + "\".") + " |");
        }
        System.out.println("  +" + "-".repeat(76) + "+");
        System.out.println();
    }

    public void searchByDate(String date) {
        System.out.println();
        System.out.println("  Search results for date: " + date);
        System.out.println("  +" + "-".repeat(76) + "+");
        boolean found = false;
        for (Event e : events) {
            if (e.getEventDate().equals(date)) {
                String cap = e.getRegisteredParticipants().size()
                        + "/" + e.getMaxParticipants();
                System.out.printf(
                        "  | %-6d | %-22s | %-10s | %-7s | %-10s | %-6d |%n",
                        e.getEventId(), e.getEventName(), e.getEventDate(),
                        e.getEventTime(), cap, e.getWaitlist().size());
                found = true;
            }
        }
        if (!found) {
            System.out.println("  | "
                    + String.format("%-74s", "  No events found on " + date + ".") + " |");
        }
        System.out.println("  +" + "-".repeat(76) + "+");
        System.out.println();
    }

    public void sortByName() {
        events.sort(Comparator.comparing(Event::getEventName,
                String.CASE_INSENSITIVE_ORDER));
        System.out.println("  [INFO] Events sorted by name.");
    }

    public void sortByDate() {
        events.sort(Comparator.comparing(e -> {
            String[] p = e.getEventDate().split("/");
            return p[2] + p[1] + p[0];
        }));
        System.out.println("  [INFO] Events sorted by date.");
    }
}
