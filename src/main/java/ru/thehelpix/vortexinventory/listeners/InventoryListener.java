package ru.thehelpix.vortexinventory.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import ru.thehelpix.vortexinventory.VortexInv;
import ru.thehelpix.vortexinventory.manager.ItemManager;
import ru.thehelpix.vortexinventory.manager.MessagesManager;
import ru.thehelpix.vortexinventory.object.VortexInventoryPlayer;
import ru.thehelpix.vortexinventory.object.InvPerm;
import ru.thehelpix.vortexinventory.object.SlotsType;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class InventoryListener implements Listener {
    private final VortexInv vortexInv;
    private final MessagesManager messagesManager;
    private final ItemManager itemManager;

    public InventoryListener(VortexInv vortexInv) {
        this.vortexInv = vortexInv;
        this.messagesManager = vortexInv.getMessagesManager();
        this.itemManager = vortexInv.getItemManager();
    }

    @EventHandler
    public void playerClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        VortexInventoryPlayer user = new VortexInventoryPlayer((Player) event.getWhoClicked(), vortexInv);
        ItemStack item = event.getCurrentItem();

        if (user.isPerm(InvPerm.ALL_SLOTS)) return;
        
        if (itemManager.isBarrier(item, SlotsType.THREE_ENDER_LINE)) {
            if (user.isPerm(InvPerm.THREE_ENDER_SLOTS)) {
                user.getEnderChest().clear(event.getSlot());
                return;
            }
            user.sendMessage(messagesManager.getMoveSlotMessage(SlotsType.THREE_ENDER_LINE));
            itemManager.voidCommands(user, SlotsType.THREE_ENDER_LINE);
            event.setCancelled(true);
            return;
        }

        if (itemManager.isBarrier(item, SlotsType.BLOCKED)) {
            if (user.isPerm(InvPerm.BLOCKED_SLOTS)) {
                user.getPlayer().getInventory().clear(event.getSlot());
                return;
            }
            user.sendMessage(messagesManager.getMoveSlotMessage(SlotsType.BLOCKED));
            itemManager.voidCommands(user, SlotsType.BLOCKED);
            event.setCancelled(true);
            return;
        }

        if (itemManager.isBarrier(item, SlotsType.TWO_LINE)) {
            if (user.isPerm(InvPerm.TWO_LINE_SLOTS)) {
                user.getPlayer().getInventory().clear(event.getSlot());
                return;
            }
            itemManager.voidCommands(user, SlotsType.TWO_LINE);
            user.sendMessage(messagesManager.getMoveSlotMessage(SlotsType.TWO_LINE));
            event.setCancelled(true);
            return;
        }


        if (itemManager.isBarrier(item, SlotsType.ONE_LINE)) {
            if (user.isPerm(InvPerm.ONE_LINE_SLOTS)) {
                user.getPlayer().getInventory().clear(event.getSlot());
                return;
            }
            user.sendMessage(messagesManager.getMoveSlotMessage(SlotsType.ONE_LINE));
            itemManager.voidCommands(user, SlotsType.ONE_LINE);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent event) {
        VortexInventoryPlayer user = new VortexInventoryPlayer(event.getPlayer(), vortexInv);

        if (user.isPerm(InvPerm.ALL_SLOTS)) return;

        if (itemManager.isBarrier(event.getItemDrop().getItemStack(), SlotsType.THREE_ENDER_LINE)) {
            event.setCancelled(true);
            user.sendMessage(messagesManager.getDropSlotMessage(SlotsType.THREE_ENDER_LINE));
            return;
        }

        if (itemManager.isBarrier(event.getItemDrop().getItemStack(), SlotsType.BLOCKED)) {
            event.setCancelled(true);
            user.sendMessage(messagesManager.getDropSlotMessage(SlotsType.BLOCKED));
            return;
        }

        if (itemManager.isBarrier(event.getItemDrop().getItemStack(), SlotsType.TWO_LINE)) {
            if (user.isPerm(InvPerm.TWO_LINE_SLOTS)) return;
            event.setCancelled(true);
            user.sendMessage(messagesManager.getDropSlotMessage(SlotsType.TWO_LINE));
            return;
        }

        if (itemManager.isBarrier(event.getItemDrop().getItemStack(), SlotsType.ONE_LINE)) {
            if (user.isPerm(InvPerm.ONE_LINE_SLOTS)) return;
            event.setCancelled(true);
            user.sendMessage(messagesManager.getDropSlotMessage(SlotsType.ONE_LINE));
        }
    }

    @EventHandler
    public void itemSelect(PlayerItemHeldEvent event) {
        VortexInventoryPlayer user = new VortexInventoryPlayer(event.getPlayer(), vortexInv);

        if (itemManager.isBackPack(user.getPlayer().getInventory().getItem(event.getNewSlot()))) {
            user.sendActionBart(messagesManager.getBackPackActionBarMessage());
        }
    }

    @EventHandler
    public void keyF(PlayerSwapHandItemsEvent event) {
        VortexInventoryPlayer user = new VortexInventoryPlayer(event.getPlayer(), vortexInv);

        if (user.isPerm(InvPerm.ALL_SLOTS)) return;

        if (itemManager.isBarrier(event.getMainHandItem(), SlotsType.THREE_ENDER_LINE) || itemManager.isBarrier(event.getOffHandItem(), SlotsType.THREE_ENDER_LINE)) {
            event.setCancelled(true);
            user.sendMessage(messagesManager.getSwapMessage(SlotsType.THREE_ENDER_LINE));
            return;
        }

        if (itemManager.isBarrier(event.getMainHandItem(), SlotsType.BLOCKED) || itemManager.isBarrier(event.getOffHandItem(), SlotsType.BLOCKED)) {
            event.setCancelled(true);
            user.sendMessage(messagesManager.getSwapMessage(SlotsType.BLOCKED));
            return;
        }

        if (itemManager.isBarrier(event.getMainHandItem(), SlotsType.TWO_LINE) || itemManager.isBarrier(event.getOffHandItem(), SlotsType.TWO_LINE)) {
            if (user.isPerm(InvPerm.TWO_LINE_SLOTS)) return;
            event.setCancelled(true);
            user.sendMessage(messagesManager.getSwapMessage(SlotsType.TWO_LINE));
            return;
        }

        if (itemManager.isBarrier(event.getMainHandItem(), SlotsType.ONE_LINE) || itemManager.isBarrier(event.getOffHandItem(), SlotsType.ONE_LINE)) {
            if (user.isPerm(InvPerm.ONE_LINE_SLOTS)) return;
            event.setCancelled(true);
            user.sendMessage(messagesManager.getSwapMessage(SlotsType.ONE_LINE));
        }
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        VortexInventoryPlayer user = new VortexInventoryPlayer(event.getPlayer(), vortexInv);
        if (itemManager.isBackPack(event.getItem())) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                event.setCancelled(true);
                user.openBackPack();
            }
            if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                user.getPlayer().getInventory().setChestplate(event.getItem());
                user.getPlayer().getInventory().remove(Objects.requireNonNull(event.getItem()));
            }
        }
    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        VortexInventoryPlayer user = new VortexInventoryPlayer((Player)event.getPlayer(), vortexInv);

        if (event.getInventory().equals(user.getEnderChest())) {
            user.sendMessage(messagesManager.getBackPackCloseMessage());
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        VortexInventoryPlayer user = new VortexInventoryPlayer(event.getPlayer(), vortexInv);
        if (itemManager.isBackPack(event.getItemInHand())) {
            user.openBackPack();
            return;
        }

        if (user.isPerm(InvPerm.ALL_SLOTS)) return;

        if (itemManager.isBarrier(event.getItemInHand(), SlotsType.THREE_ENDER_LINE)) {
            event.setCancelled(true);
            user.sendMessage(messagesManager.getPlaceMessage(SlotsType.THREE_ENDER_LINE));
            return;
        }

        if (itemManager.isBarrier(event.getItemInHand(), SlotsType.BLOCKED)) {
            event.setCancelled(true);
            user.sendMessage(messagesManager.getPlaceMessage(SlotsType.BLOCKED));
            return;
        }

        if (itemManager.isBarrier(event.getItemInHand(), SlotsType.TWO_LINE)) {
            if (user.isPerm(InvPerm.TWO_LINE_SLOTS)) return;
            event.setCancelled(true);
            user.sendMessage(messagesManager.getPlaceMessage(SlotsType.TWO_LINE));
            return;
        }

        if (itemManager.isBarrier(event.getItemInHand(), SlotsType.ONE_LINE)) {
            if (user.isPerm(InvPerm.ONE_LINE_SLOTS)) return;
            event.setCancelled(true);
            user.sendMessage(messagesManager.getPlaceMessage(SlotsType.ONE_LINE));
        }
    }

    @EventHandler
    public void deathPlayer(PlayerDeathEvent event) {
        List<ItemStack> entlist = event.getDrops();
        Iterator<ItemStack> i = entlist.iterator();

        while(i.hasNext()) {
            ItemStack item = i.next();

            if (itemManager.isBarrier(item, SlotsType.THREE_ENDER_LINE)) {
                i.remove();
            }

            if (itemManager.isBarrier(item, SlotsType.BLOCKED)) {
                i.remove();
            }

            if (itemManager.isBarrier(item, SlotsType.ONE_LINE)) {
                i.remove();
            }

            if (itemManager.isBarrier(item, SlotsType.TWO_LINE)) {
                i.remove();
            }
        }
    }
}
