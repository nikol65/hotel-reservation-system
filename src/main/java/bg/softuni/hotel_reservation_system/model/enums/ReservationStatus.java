package bg.softuni.hotel_reservation_system.model.enums;

public enum ReservationStatus {

    PENDING("Pending Payment"),
    CONFIRMED("Confirmed"),
    CANCELLED("Cancelled"),
    AUTO_CANCELLED("Automatically Cancelled");

    private final String displayName;

    ReservationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
