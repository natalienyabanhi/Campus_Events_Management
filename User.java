/**
 *  * Abstract base class representing a system user.
 *  * Demonstrates inheritance and polymorphism (OOP principle).
 *  */

public abstract class User {
    protected String userId;
    protected String name;
    protected String role;

    public User(String userId, String name, String role) {
        this.userId = userId;
        this.name   = name;
        this.role   = role;
    }

    public String getUserId() { return userId; }
    public String getName()   { return name; }
    public String getRole()   { return role; }

    @Override
    public String toString() {
        return "[" + role + "] " + name + " (ID: " + userId + ")";
    }
}
