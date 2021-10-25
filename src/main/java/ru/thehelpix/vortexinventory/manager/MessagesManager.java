package ru.thehelpix.vortexinventory.manager;

import ru.thehelpix.vortexinventory.VortexInv;
import ru.thehelpix.vortexinventory.object.SlotsType;

import java.util.List;

public class MessagesManager {
    private final VortexInv vortexInv;

    public MessagesManager(VortexInv vortexInv) {
        this.vortexInv = vortexInv;
    }

    public String getPrefix() {
        return vortexInv.getConfig().getString("prefix");
    }

    public String getBackPackActionBarMessage() {
        return vortexInv.getConfig().getString("backpack.actionbar");
    }

    public String getBackPackCloseMessage() {
        return vortexInv.getConfig().getString("backpack.close");
    }

    public List<String> getItemLore(SlotsType slotsType) {
        return vortexInv.getConfig().getStringList("$args.lore".replace("$args", slotsType.getConfigArg()));
    }

    public List<String> getCommandsLore(SlotsType slotsType) {
        return vortexInv.getConfig().getStringList("$args.commands".replace("$args", slotsType.getConfigArg()));
    }

    public String getItemName(SlotsType slotsType) {
        return vortexInv.getConfig().getString("$args.name".replace("$args", slotsType.getConfigArg()));
    }

    public String getMoveSlotMessage(SlotsType slotsType) {
        return vortexInv.getConfig().getString("$args.messages.move".replace("$args", slotsType.getConfigArg()));
    }

    public String getDropSlotMessage(SlotsType slotsType) {
        return vortexInv.getConfig().getString("$args.messages.drop".replace("$args", slotsType.getConfigArg()));
    }

    public String getSwapMessage(SlotsType slotsType) {
        return vortexInv.getConfig().getString("$args.messages.swap".replace("$args", slotsType.getConfigArg()));
    }

    public String getPlaceMessage(SlotsType slotsType) {
        return vortexInv.getConfig().getString("$args.messages.place".replace("$args", slotsType.getConfigArg()));
    }
}
