# Campus Event Management System
**Module:** Programming 731


## Overview

A Java console-based Campus Event Management System that allows staff and
students to create, manage, view, and attend events happening on campus.
The system simulates real-world behaviour including role-based access control,
event capacity management, waitlists, and automated background processing.

---

## How to Compile and Run

### Requirements
- Java JDK 8 or higher
- Any Java IDE (IntelliJ IDEA recommended) or command line

### Compile via Command Line
```bash
cd src
javac *.java
java Main
```

### Run via IntelliJ IDEA
1. Open the project in IntelliJ IDEA
2. Ensure all `.java` files are inside the `src` folder
3. Right-click `Main.java` and select **Run 'Main.main()'**

---

## Login Credentials

| Role    | PIN  |
|---------|------|
| Staff   | See source code comment in `Main.java` |
| Student | See source code comment in `Main.java` |

---

## Project Structure

```
Campus_events_management/
│
├── src/
│   ├── Main.java               - Entry point, menus, role routing
│   ├── User.java               - Abstract base class for all users
│   ├── Staff.java              - Staff user with elevated privileges
│   ├── Student.java            - Student user with limited access
│   ├── Event.java              - Event entity with registered list and waitlist
│   ├── EventManager.java       - Manages all event operations
│   ├── WaitlistPromoter.java   - Background thread for waitlist promotion
│   ├── InputValidator.java     - Centralised input validation utility
│   └── FileManager.java        - File I/O for data persistence
│
├── events.txt                  - Auto-generated: saved event data
├── registrations.txt           - Auto-generated: saved registration data
├── waitlists.txt               - Auto-generated: saved waitlist data
│
└── README.md                   - This file
```

---

## Features

### Staff Features (PIN protected)
- Create new events with ID, name, date, time, location, and max participants
- Update event name, time, location, and date
- Cancel events
- View all events in a formatted table
- View participants and waitlist for a specific event
- View full student registration table (staff only)
- Sort events by name or date
- Search events by name or date

### Student Features (PIN protected)
- View all available events
- Register for an event using the Event ID
- Cancel a registration or waitlist entry
- View personal registration status across all events
- Search events by name or date

### Automated Features
- Students are automatically registered if space is available
- Students are automatically waitlisted if the event is full
- When a registered student cancels, the first student on the waitlist
  is automatically promoted using a background thread
- A notification message is displayed confirming every promotion
- All data is saved to files automatically on every change
- All saved data is loaded automatically on program startup

---

## System Design

### OOP Principles Applied

| Principle     | Implementation |
|---------------|----------------|
| Abstraction   | `User` is an abstract class |
| Inheritance   | `Staff` and `Student` both extend `User` |
| Polymorphism  | Each subclass provides its own behaviour |
| Encapsulation | All fields are private with getters/setters |

### Data Structures Used

| Structure       | Used For                          |
|-----------------|-----------------------------------|
| `ArrayList`     | Registered participants per event |
| `LinkedList` / `Queue` | FIFO waitlist per event    |
| `List<Event>`   | Central store of all events       |

### Multithreading
- `WaitlistPromoter` implements `Runnable`
- Launched via `new Thread(new WaitlistPromoter(...))` on cancellation
- Simulates background processing with a short delay
- Prints a formatted notification on completion

### Data Persistence
- `events.txt` — stores all event details (pipe-delimited)
- `registrations.txt` — stores registered student IDs per event
- `waitlists.txt` — stores waitlisted student IDs per event (FIFO order)
- All files are loaded automatically on startup
- All files are saved automatically after every change

---

## Test Data (Preloaded on Startup)

### Events
| ID | Name                | Date       | Time  | Location  | Capacity |
|----|---------------------|------------|-------|-----------|----------|
| 1  | Tech Talk           | 20/04/2026 | 10:00 | Hall A    | 3        |
| 2  | Java Workshop       | 25/04/2026 | 14:00 | Lab 3B    | 2        |
| 3  | Programming Seminar | 28/04/2026 | 09:00 | Room 101  | 3        |
| 4  | Database Bootcamp   | 30/04/2026 | 11:00 | Lab 2A    | 2        |

### Students
| ID  | Name    |
|-----|---------|
| ST1 | Natalie |
| ST2 | John    |
| ST3 | Mike    |
| ST4 | Lisa    |
| ST5 | James   |
| ST6 | Sara    |
| ST7 | Peter   |
| ST8 | Anna    |

---

## Validation Rules

- Event IDs must be unique positive integers
- Dates must follow `dd/MM/yyyy` format
- Times must follow `HH:mm` (24-hour) format
- All text fields must be non-empty
- Max participants must be a positive integer
- Duplicate registrations are prevented
- Invalid inputs are caught and the user is prompted to retry

---

## Error Handling

All errors are handled gracefully with clear messages:
- `[ERROR]` — invalid input or operation failed
- `[INFO]`  — informational message (e.g. already registered)
- `[SUCCESS]` — operation completed successfully
- `[NOTIFICATION]` — background thread promotion alert

---

## Submission Notes

- All source files are in the `src/` folder
- The program is fully console-based with no GUI
- Code is clearly structured, commented, and modular
- Data files are auto-generated in the project root on first run

- Author: Natalie Nyabanhi
