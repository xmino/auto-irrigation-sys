package onion.domain.types;

public enum SlotStatus {
    PENDING,
    IRRIGATING,
    COMPLETE,
    ERROR,
    UNKNOWN;

    public static SlotStatus safeLookup(String type) {
        try {
            return SlotStatus.valueOf(type);
        } catch (IllegalArgumentException e) {
            return SlotStatus.UNKNOWN;
        }
    }
}
