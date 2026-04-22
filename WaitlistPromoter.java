/**
 * Runs in a separate thread to promote the first waitlisted
 * student when a registered student cancels.
 * Requirement: promotion must be handled in a separate thread
 * and display a notification message confirming the promotion.
 */
public class WaitlistPromoter implements Runnable {

    private final Event  event;
    private final String cancellingStudentId;

    public WaitlistPromoter(Event event, String cancellingStudentId) {
        this.event               = event;
        this.cancellingStudentId = cancellingStudentId;
    }

    @Override
    public void run() {
        try {
            // Small delay to simulate background processing
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String promoted = event.promoteFromWaitlist();

        System.out.println();
        System.out.println("  +--------------------------------------------------+");
        if (promoted != null) {
            // Assignment-specified notification message format
            System.out.printf("  | NOTIFICATION: Registration cancelled.            |%n");
            System.out.printf("  | Student %-8s has been promoted from the      |%n",
                promoted);
            System.out.printf("  | waitlist to \"%-35s\" |%n",
                event.getEventName() + "\".");
        } else {
            System.out.printf("  | NOTIFICATION: Registration cancelled for %-7s |%n",
                cancellingStudentId + ".");
            System.out.printf("  | No students on the waitlist for               |%n");
            System.out.printf("  | \"%-47s\" |%n", event.getEventName() + "\".");
        }
        System.out.println("  +--------------------------------------------------+");
        System.out.println();
    }
}
