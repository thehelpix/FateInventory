package ru.thehelpix.vortexinventory;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.thehelpix.vortexinventory.listeners.InventoryListener;
import ru.thehelpix.vortexinventory.manager.ItemManager;
import ru.thehelpix.vortexinventory.manager.MessagesManager;
import ru.thehelpix.vortexinventory.task.InventoryCheckTask;
import ru.thehelpix.vortexinventory.utils.Log;

public final class VortexInv extends JavaPlugin {
    private MessagesManager messagesManager;
    private ItemManager itemManager;
    private InventoryCheckTask inventoryCheckTask;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.messagesManager = new MessagesManager(this);
        this.itemManager = new ItemManager(this);
        this.inventoryCheckTask = new InventoryCheckTask(this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(this), this);

        inventoryCheckTask.start();
        Log.log("$prefix &aПлагин включён!".replace("$prefix", messagesManager.getPrefix()));
    }

    @Override
    public void onDisable() {
        inventoryCheckTask.stop();
        Log.log("$prefix &aПлагин выключен!".replace("$prefix", messagesManager.getPrefix()));
    }

    public MessagesManager getMessagesManager() {
        return messagesManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }
}
