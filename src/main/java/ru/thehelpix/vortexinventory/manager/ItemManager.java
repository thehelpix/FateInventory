package ru.thehelpix.vortexinventory.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.thehelpix.vortexinventory.VortexInv;
import ru.thehelpix.vortexinventory.object.VortexInventoryPlayer;
import ru.thehelpix.vortexinventory.object.SlotsType;
import ru.thehelpix.vortexinventory.utils.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemManager {
    private final MessagesManager messagesManager;

    public ItemManager(VortexInv vortexInv) {
        this.messagesManager = vortexInv.getMessagesManager();
    }

    public ItemStack getBarrier(SlotsType slotsType) {
        ItemStack itemStack = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;
        itemMeta.setDisplayName(Log.parse(messagesManager.getItemName(slotsType)));
        itemMeta.setLocalizedName(Log.parse(messagesManager.getItemName(slotsType)));
        if (messagesManager.getItemLore(slotsType).size() != 0) {
            List<String> lores = new ArrayList<>();
            messagesManager.getItemLore(slotsType).forEach(lore ->  lores.add(Log.parse(lore)));
            itemMeta.setLore(lores);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    
    public boolean isBackPack(ItemStack itemStack) {
        if (itemStack == null || itemStack.equals(new ItemStack(Material.AIR))) return false;
        return itemStack.getType().equals(Material.LEATHER_CHESTPLATE);
    }

    public boolean isBarrier(ItemStack itemStack, SlotsType slotsType) {
        if (itemStack == null) return false;
        if (itemStack.equals(new ItemStack(Material.AIR))) return false;
        if (itemStack.equals(getBarrier(slotsType))) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta == null) return false;
            return itemMeta.getDisplayName().equals(Objects.requireNonNull(getBarrier(slotsType).getItemMeta()).getDisplayName()) && Objects.equals(itemMeta.getLore(), Objects.requireNonNull(getBarrier(slotsType).getItemMeta()).getLore());
        }
        return false;
    }

    public void voidCommands(VortexInventoryPlayer user, SlotsType slotsType) {
        messagesManager.getCommandsLore(slotsType).forEach(command -> {
            if (command.startsWith("console:")) {
                String cmd = Log.parse(command.substring(9).replace("$nick", user.getPlayer().getName()));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            } else if (command.startsWith("sender:")) {
                String cmd = Log.parse(command.substring(8).replace("$nick", user.getPlayer().getName()));
                user.getPlayer().performCommand(cmd);
                //Bukkit.dispatchCommand(user.getPlayer().co, cmd);
            }
        });
    }
}
