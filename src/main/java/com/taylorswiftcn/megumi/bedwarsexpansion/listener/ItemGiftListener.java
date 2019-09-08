package com.taylorswiftcn.megumi.bedwarsexpansion.listener;

import com.taylorswiftcn.megumi.bedwarsexpansion.file.sub.Config;
import io.github.bedwarsrel.BedwarsRel;
import io.github.bedwarsrel.events.BedwarsGameStartedEvent;
import io.github.bedwarsrel.game.Game;
import io.github.bedwarsrel.game.Team;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemGiftListener implements Listener {

    @EventHandler
    public void onStarted(BedwarsGameStartedEvent e) {
        Game game = e.getGame();

        for (Player player : game.getPlayers()) {
            addGift(player);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRespawn(PlayerRespawnEvent e) {
        if (!Config.ItemGift.respawnGive) return;

        Player player = e.getPlayer();

        Game game = BedwarsRel.getInstance().getGameManager().getGameOfPlayer(player);
        if (game == null) return;

        if (game.isSpectator(player)) return;

        addGift(player);
    }

    private static void addGift(Player player) {
        player.getInventory().setHelmet(divLeather(player, Config.ItemGift.helmet));
        player.getInventory().setChestplate(divLeather(player, Config.ItemGift.chestplate));
        player.getInventory().setLeggings(divLeather(player, Config.ItemGift.leggings));
        player.getInventory().setBoots(divLeather(player, Config.ItemGift.boots));

        for (ItemStack item : Config.ItemGift.items) {
            player.getInventory().addItem(item);
        }
    }

    private static ItemStack divLeather(Player p, ItemStack item) {
        ItemStack itemStack = item.clone();

        Material material = itemStack.getType();
        if (!(material == Material.LEATHER_BOOTS || material == Material.LEATHER_CHESTPLATE || material == Material.LEATHER_HELMET || material == Material.LEATHER_LEGGINGS)) return itemStack;

        Game game = BedwarsRel.getInstance().getGameManager().getGameOfPlayer(p);
        if (game == null) return itemStack;
        Team team = game.getPlayerTeam(p);
        if (team == null) return itemStack;

        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(team.getColor().getColor());

        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
