package hospital.staff;

public abstract class Staff {

    private final String staffId;
    private final String name;

    protected Staff(String staffId, String name) {
        if (staffId == null || staffId.isBlank() || name == null || name.isBlank()) {
            throw new IllegalArgumentException("Staff ID and name are required");
        }
        this.staffId = staffId;
        this.name = name;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getName() {
        return name;
    }

    /** Each kind of staff states its own role. */
    public abstract String getRole();

    /** Audit trail entry, usable only by Staff subclasses. */
    protected void logAccess(String action) {
        System.out.println("  [AUDIT] " + getRole() + " " + name + " (" + staffId + "): " + action);
    }

    @Override
    public String toString() {
        return getRole() + " " + name + " [" + staffId + "]";
    }
}
