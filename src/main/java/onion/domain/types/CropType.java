package onion.domain.types;

public enum CropType {
    FEED,
    FOOD,
    FIBER,
    OIL,
    ORNAMENTAL,
    INDUSTRIAL,
    UNKNOWN;

    public static CropType safeLookup(String type) {
        try {
            return CropType.valueOf(type);
        } catch (IllegalArgumentException e) {
            return CropType.UNKNOWN;
        }
    }
}
