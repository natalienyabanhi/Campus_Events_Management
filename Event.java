import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a campus event.
 * Stores registered participants as Strings (student IDs).
 * Uses ArrayList for registered and LinkedList Queue for FIFO waitlist.
 */
public class Event {
    private int    eventId;
    private String eventName;
    private String eventDate;
    private String eventTime;
    private String location;
    private int    maxParticipants;

    private ArrayList<String> registeredParticipants;
    private Queue<String>     waitlist;

    public Event(int eventId, String eventName, String eventDate,
                 String eventTime, String location, int maxParticipants) {
        this.eventId           = eventId;
        this.eventName         = eventName;
        this.eventDate         = eventDate;
        this.eventTime         = eventTime;
        this.location          = location;
        this.maxParticipants   = maxParticipants;
        this.registeredParticipants = new ArrayList<>();
        this.waitlist          = new LinkedList<>();
    }

    // Getters
    public int               getEventId()              { return eventId; }
    public String            getEventName()             { return eventName; }
    public String            getEventDate()             { return eventDate; }
    public String            getEventTime()             { return eventTime; }
    public String            getLocation()              { return location; }
    public int               getMaxParticipants()       { return maxParticipants; }
    public ArrayList<String> getRegisteredParticipants(){ return registeredParticipants; }
    public Queue<String>     getWaitlist()              { return waitlist; }

    // Setters
    public void setEventName(String eventName) { this.eventName = eventName; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }
    public void setEventTime(String eventTime) { this.eventTime = eventTime; }
    public void setLocation(String location)   { this.location  = location;  }

    // Capacity
    public boolean isFull() {
        return registeredParticipants.size() >= maxParticipants;
    }

    // Registration
    public boolean addParticipant(String studentId) {
        if (!registeredParticipants.contains(studentId) && !isFull()) {
            registeredParticipants.add(studentId);
            return true;
        }
        return false;
    }

    public boolean addToWaitlist(String studentId) {
        if (!waitlist.contains(studentId)) {
            waitlist.offer(studentId);
            return true;
        }
        return false;
    }

    public boolean removeParticipant(String studentId) {
        return registeredParticipants.remove(studentId);
    }

    public boolean removeFromWaitlist(String studentId) {
        return waitlist.remove(studentId);
    }

    public String promoteFromWaitlist() {
        String next = waitlist.poll();
        if (next != null) registeredParticipants.add(next);
        return next;
    }

    public boolean isRegistered(String studentId) {
        return registeredParticipants.contains(studentId);
    }

    public boolean isOnWaitlist(String studentId) {
        return waitlist.contains(studentId);
    }

    public String toFileString() {
        return eventId + "|" + eventName + "|" + eventDate + "|"
                + eventTime + "|" + location + "|" + maxParticipants;
    }

    public String toSummaryString() {
        return String.format(
                "ID: %-4d | %-22s | %s %s | %-15s | Registered: %d/%d | Waitlist: %d",
                eventId, eventName, eventDate, eventTime, location,
                registeredParticipants.size(), maxParticipants, waitlist.size());
    }

    @Override
    public String toString() { return toSummaryString(); }
}
