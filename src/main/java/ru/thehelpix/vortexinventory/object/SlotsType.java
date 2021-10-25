package ru.thehelpix.vortexinventory.object;

public enum SlotsType {
    BLOCKED("type.blocked"),
    ONE_LINE("type.one_line"),
    TWO_LINE("type.two_line"),
    THREE_ENDER_LINE("type.three_ender_line");

    private final String configArg;
    SlotsType(String configArg) {
        this.configArg = configArg;
    }

    public String getConfigArg() {
        return configArg;
    }


    public static Boolean isSlot(SlotsType slotsType, Integer integer) {
        boolean bool = false;

        if (slotsType.equals(SlotsType.BLOCKED)) {
            for (int i = 9; i <= 26; i++) {
                if (i == integer) {
                    bool = true;
                    break;
                }
            }
        }

        if (slotsType.equals(SlotsType.TWO_LINE)) {
            for (int i = 27; i <= 35; i++) {
                if (i == integer) {
                    bool = true;
                    break;
                }
            }
        }

        if (slotsType.equals(SlotsType.ONE_LINE)) {
            for (int i = 5; i <= 8; i++) {
                if (i == integer) {
                    bool = true;
                    break;
                }
            }
        }
        return bool;
    }
}
