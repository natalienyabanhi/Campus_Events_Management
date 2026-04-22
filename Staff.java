/**
 * Staff user has elevated privileges:
 * create, update, cancel events and view participant lists.
 */

public class Staff extends User {

    public Staff(String userId, String name) {
        super(userId, name, "STAFF");
    }

    public void createEvent(EventManager manager, Event event) {
        manager.addEvent(event);
    }

    public void updateEventName(EventManager manager, int eventId, String newName) {
        Event event = manager.findById(eventId);
        if (event != null) {
            event.setEventName(newName);
            FileManager.saveAll(manager.getEvents());
            System.out.println("  [SUCCESS] Event name updated to: " + newName);
        } else {
            System.out.println("  [ERROR] Event not found.");
        }
    }

    public void updateEventTime(EventManager manager, int eventId, String newTime) {
        Event event = manager.findById(eventId);
        if (event != null) {
            event.setEventTime(newTime);
            FileManager.saveAll(manager.getEvents());
            System.out.println("  [SUCCESS] Event time updated to: " + newTime);
        } else {
            System.out.println("  [ERROR] Event not found.");
        }
    }

    public void updateEventLocation(EventManager manager, int eventId, String newLocation) {
        Event event = manager.findById(eventId);
        if (event != null) {
            event.setLocation(newLocation);
            FileManager.saveAll(manager.getEvents());
            System.out.println("  [SUCCESS] Location updated to: " + newLocation);
        } else {
            System.out.println("  [ERROR] Event not found.");
        }
    }

    public void updateEventDate(EventManager manager, int eventId, String newDate) {
        Event event = manager.findById(eventId);
        if (event != null) {
            event.setEventDate(newDate);
            FileManager.saveAll(manager.getEvents());
            System.out.println("  [SUCCESS] Event date updated to: " + newDate);
        } else {
            System.out.println("  [ERROR] Event not found.");
        }
    }

    public void cancelEvent(EventManager manager, int eventId) {
        Event event = manager.findById(eventId);
        if (event != null) {
            manager.getEvents().remove(event);
            FileManager.saveAll(manager.getEvents());
            System.out.println("  [SUCCESS] Event \"" + event.getEventName()
                    + "\" has been cancelled.");
        } else {
            System.out.println("  [ERROR] Event not found.");
        }
    }

    public void viewParticipants(EventManager manager, int eventId) {
        Event event = manager.findById(eventId);
        if (event == null) {
            System.out.println("  [ERROR] Event not found.");
            return;
        }
        System.out.println();
        System.out.println("  +" + "-".repeat(40) + "+");
        System.out.printf("  | Participants for: %-20s |%n", event.getEventName());
        System.out.println("  +" + "-".repeat(40) + "+");
        System.out.printf("  | %-15s | %-20s |%n", "Student ID", "Status");
        System.out.println("  +" + "-".repeat(40) + "+");

        if (event.getRegisteredParticipants().isEmpty()
                && event.getWaitlist().isEmpty()) {
            System.out.println("  | " + String.format("%-38s", "  No registrations yet.") + " |");
        } else {
            for (String id : event.getRegisteredParticipants()) {
                System.out.printf("  | %-15s | %-20s |%n", id, "Registered");
            }
            for (String id : event.getWaitlist()) {
                System.out.printf("  | %-15s | %-20s |%n", id, "Waitlisted");
            }
        }
        System.out.println("  +" + "-".repeat(40) + "+");
        System.out.println();
    }
}
