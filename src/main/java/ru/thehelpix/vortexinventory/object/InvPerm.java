package ru.thehelpix.vortexinventory.object;

public enum InvPerm {
    ALL_SLOTS("permissions.all_slots"),
    ONE_LINE_SLOTS("permissions.one_line_slots"),
    TWO_LINE_SLOTS("permissions.two_line_slots"),
    BLOCKED_SLOTS("permissions.blocked_slots"),
    THREE_ENDER_SLOTS("permissions.three_slots_ender");

    private final String configArg;

    InvPerm(String configArg) {
        this.configArg = configArg;
    }

    public String getConfigArg() {
        return configArg;
    }
}
