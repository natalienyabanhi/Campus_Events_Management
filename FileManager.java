import java.io.*;
import java.util.*;

/**
 * Handles saving and loading all data to/from text files.
 * Files: events.txt, registrations.txt, waitlists.txt
 */
public class FileManager {

    private static final String EVENTS_FILE        = "events.txt";
    private static final String REGISTRATIONS_FILE = "registrations.txt";
    private static final String WAITLISTS_FILE     = "waitlists.txt";

    public static void saveAll(List<Event> events) {
        saveEvents(events);
        saveRegistrations(events);
        saveWaitlists(events);
    }

    private static void saveEvents(List<Event> events) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(EVENTS_FILE))) {
            for (Event e : events) pw.println(e.toFileString());
        } catch (IOException ex) {
            System.out.println("  [ERROR] Could not save events: " + ex.getMessage());
        }
    }

    private static void saveRegistrations(List<Event> events) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(REGISTRATIONS_FILE))) {
            for (Event e : events) {
                if (!e.getRegisteredParticipants().isEmpty()) {
                    // getRegisteredParticipants() returns ArrayList<String>
                    pw.println(e.getEventId() + "|"
                            + String.join(",", e.getRegisteredParticipants()));
                }
            }
        } catch (IOException ex) {
            System.out.println("  [ERROR] Could not save registrations: "
                    + ex.getMessage());
        }
    }

    private static void saveWaitlists(List<Event> events) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(WAITLISTS_FILE))) {
            for (Event e : events) {
                if (!e.getWaitlist().isEmpty()) {
                    // Convert Queue<String> to array then join
                    String wl = String.join(",",
                            e.getWaitlist().toArray(new String[0]));
                    pw.println(e.getEventId() + "|" + wl);
                }
            }
        } catch (IOException ex) {
            System.out.println("  [ERROR] Could not save waitlists: "
                    + ex.getMessage());
        }
    }

    public static List<Event> loadEvents() {
        List<Event> events = new ArrayList<>();
        File file = new File(EVENTS_FILE);
        if (!file.exists()) return events;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] p = line.split("\\|");
                if (p.length < 6) continue;
                try {
                    events.add(new Event(
                            Integer.parseInt(p[0].trim()), p[1].trim(),
                            p[2].trim(), p[3].trim(), p[4].trim(),
                            Integer.parseInt(p[5].trim())));
                } catch (NumberFormatException ignored) {}
            }
        } catch (IOException ex) {
            System.out.println("  [ERROR] Could not load events: " + ex.getMessage());
        }
        return events;
    }

    public static void loadRegistrations(List<Event> events) {
        File file = new File(REGISTRATIONS_FILE);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] p = line.split("\\|");
                if (p.length < 2) continue;
                int id = Integer.parseInt(p[0].trim());
                for (Event e : events) {
                    if (e.getEventId() == id) {
                        for (String s : p[1].split(","))
                            e.getRegisteredParticipants().add(s.trim());
                        break;
                    }
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.out.println("  [ERROR] Could not load registrations: "
                    + ex.getMessage());
        }
    }

    public static void loadWaitlists(List<Event> events) {
        File file = new File(WAITLISTS_FILE);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] p = line.split("\\|");
                if (p.length < 2) continue;
                int id = Integer.parseInt(p[0].trim());
                for (Event e : events) {
                    if (e.getEventId() == id) {
                        for (String s : p[1].split(","))
                            e.getWaitlist().offer(s.trim());
                        break;
                    }
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.out.println("  [ERROR] Could not load waitlists: "
                    + ex.getMessage());
        }
    }
}
