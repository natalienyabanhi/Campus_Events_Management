/**
 * StudentUser extends User with limited privileges:
 * view events, register, cancel registration, view own status.
 */

public class Student extends User {

    public Student(String userId, String name) {
        super(userId, name, "STUDENT");
    }

    public void registerEvent(EventManager manager, int eventId) {
        Event event = manager.findById(eventId);
        if (event == null) {
            System.out.println("  [ERROR] Event ID " + eventId + " not found.");
            return;
        }
        if (event.isRegistered(userId)) {
            System.out.println("  [INFO] " + name
                    + " is already registered for \"" + event.getEventName() + "\".");
            return;
        }
        if (event.isOnWaitlist(userId)) {
            System.out.println("  [INFO] " + name
                    + " is already on the waitlist for \"" + event.getEventName() + "\".");
            return;
        }
        if (!event.isFull()) {
            event.addParticipant(userId);
            FileManager.saveAll(manager.getEvents());
            System.out.println("  [REGISTERED] " + name
                    + " successfully registered for \"" + event.getEventName() + "\".");
        } else {
            event.addToWaitlist(userId);
            FileManager.saveAll(manager.getEvents());
            System.out.println("  [WAITLISTED] " + name
                    + " placed on waitlist for \"" + event.getEventName()
                    + "\" (position " + event.getWaitlist().size() + ").");
        }
    }

    public void cancelRegistration(EventManager manager, int eventId) {
        Event event = manager.findById(eventId);
        if (event == null) {
            System.out.println("  [ERROR] Event ID " + eventId + " not found.");
            return;
        }
        if (event.isRegistered(userId)) {
            event.removeParticipant(userId);
            FileManager.saveAll(manager.getEvents());

            Thread promotionThread = new Thread(new WaitlistPromoter(event, userId));
            promotionThread.setDaemon(true);
            promotionThread.start();
            try { promotionThread.join(1500); } catch (InterruptedException ignored) {}
            FileManager.saveAll(manager.getEvents());

        } else if (event.isOnWaitlist(userId)) {
            event.removeFromWaitlist(userId);
            FileManager.saveAll(manager.getEvents());
            System.out.println("  [CANCELLED] " + name
                    + " removed from waitlist for \"" + event.getEventName() + "\".");
        } else {
            System.out.println("  [INFO] " + name
                    + " is not registered or waitlisted for \""
                    + event.getEventName() + "\".");
        }
    }

    public void viewStatus(EventManager manager) {
        System.out.println();
        System.out.println("  +" + "-".repeat(50) + "+");
        System.out.printf("  | Registration Status for: %-23s |%n", name + " (" + userId + ")");
        System.out.println("  +" + "-".repeat(50) + "+");
        System.out.printf("  | %-28s | %-18s |%n", "Event Name", "Status");
        System.out.println("  +" + "-".repeat(50) + "+");

        boolean found = false;
        for (Event e : manager.getEvents()) {
            if (e.isRegistered(userId)) {
                System.out.printf("  | %-28s | %-18s |%n",
                        e.getEventName(), "Registered");
                found = true;
            } else if (e.isOnWaitlist(userId)) {
                int pos = 1;
                for (String w : e.getWaitlist()) {
                    if (w.equals(userId)) break;
                    pos++;
                }
                System.out.printf("  | %-28s | %-18s |%n",
                        e.getEventName(), "Waitlisted (#" + pos + ")");
                found = true;
            }
        }
        if (!found) {
            System.out.println("  | "
                    + String.format("%-48s", "  Not registered for any events.") + " |");
        }
        System.out.println("  +" + "-".repeat(50) + "+");
        System.out.println();
    }
}
