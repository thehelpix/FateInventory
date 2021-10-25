package ru.thehelpix.vortexinventory.task;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import ru.thehelpix.vortexinventory.VortexInv;
import ru.thehelpix.vortexinventory.manager.ItemManager;
import ru.thehelpix.vortexinventory.object.VortexInventoryPlayer;
import ru.thehelpix.vortexinventory.object.InvPerm;
import ru.thehelpix.vortexinventory.object.SlotsType;

public class InventoryCheckTask implements Runnable {
    private final VortexInv vortexInv;
    private final ItemManager itemManager;

    public InventoryCheckTask(VortexInv vortexInv) {
        this.vortexInv = vortexInv;
        this.itemManager = vortexInv.getItemManager();
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            VortexInventoryPlayer user = new VortexInventoryPlayer(p, vortexInv);
            Inventory inventory = user.getPlayer().getInventory();
            Inventory enderChest = user.getEnderChest();

            if (user.isPerm(InvPerm.ALL_SLOTS)) {
                enderChest.forEach(is -> {
                    if (itemManager.isBarrier(is, SlotsType.THREE_ENDER_LINE)) {
                        for (int i = 18; i <= 26; i++) {
                            inventory.clear(i);
                        }
                    }
                });
                inventory.forEach(is -> {
                    if (itemManager.isBarrier(is, SlotsType.ONE_LINE)) {
                        for (int i = 5; i <= 8; i++) {
                            inventory.clear(i);
                        }
                    }
                    if (itemManager.isBarrier(is, SlotsType.TWO_LINE)) {
                        for (int i = 27; i <= 35; i++) {
                            inventory.clear(i);
                        }
                    }
                    if (itemManager.isBarrier(is, SlotsType.BLOCKED)) {
                        for (int i = 9; i <= 26; i++) {
                            inventory.clear(i);
                        }
                    }
                });
                return;
            }

            if (!user.isPerm(InvPerm.THREE_ENDER_SLOTS)) {
                for (int i = 18; i <= 26; i++) {
                    enderChest.setItem(i, itemManager.getBarrier(SlotsType.THREE_ENDER_LINE));
                }
            } else {
                inventory.forEach(is -> {
                    if (itemManager.isBarrier(is, SlotsType.THREE_ENDER_LINE)) {
                        for (int i = 18; i <= 26; i++) {
                            inventory.clear(i);
                        }
                    }
                });
            }

            if (!user.isPerm(InvPerm.BLOCKED_SLOTS)) {
                for (int i = 9; i <= 26; i++) {
                    inventory.setItem(i, itemManager.getBarrier(SlotsType.BLOCKED));
                }
            } else {
                inventory.forEach(is -> {
                    if (itemManager.isBarrier(is, SlotsType.BLOCKED)) {
                        for (int i = 9; i <= 26; i++) {
                            inventory.clear(i);
                        }
                    }
                });
            }

            if (!user.isPerm(InvPerm.TWO_LINE_SLOTS)) {
                for (int i = 27; i <= 35; i++) {
                    inventory.setItem(i, itemManager.getBarrier(SlotsType.TWO_LINE));
                }
            } else {
                inventory.forEach(is -> {
                    if (itemManager.isBarrier(is, SlotsType.TWO_LINE)) {
                        for (int i = 27; i <= 35; i++) {
                            inventory.clear(i);
                        }
                    }
                });
            }

            if (!user.isPerm(InvPerm.ONE_LINE_SLOTS)) {
                for (int i = 5; i <= 8; i++) {
                    inventory.setItem(i, itemManager.getBarrier(SlotsType.ONE_LINE));
                }
            } else {
                inventory.forEach(is -> {
                    if (itemManager.isBarrier(is, SlotsType.ONE_LINE)) {
                        for (int i = 5; i <= 8; i++) {
                            inventory.clear(i);
                        }
                    }
                });
            }
        });
    }

    public void start() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(vortexInv, this, 10L, 10L);
    }

    public void stop() {
        Bukkit.getScheduler().cancelTasks(vortexInv);
    }
}
