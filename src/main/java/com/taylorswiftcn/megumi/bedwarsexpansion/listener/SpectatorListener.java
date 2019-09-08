package com.taylorswiftcn.megumi.bedwarsexpansion.listener;

import com.taylorswiftcn.megumi.bedwarsexpansion.BedwarsExpansion;
import com.taylorswiftcn.megumi.bedwarsexpansion.tool.ActionBarAPI;
import com.taylorswiftcn.megumi.bedwarsexpansion.file.sub.Config;
import com.taylorswiftcn.megumi.bedwarsexpansion.util.ItemUtil;
import com.taylorswiftcn.megumi.bedwarsexpansion.util.WeiUtil;
import io.github.bedwarsrel.BedwarsRel;
import io.github.bedwarsrel.game.Game;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SpectatorListener implements Listener {

    private BedwarsExpansion plugin;

    public SpectatorListener(BedwarsExpansion plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEvent e) {
        if (e.getAction() != Action.LEFT_CLICK_AIR) return;

        Player player = e.getPlayer();
        if (player.getSpectatorTarget() == null || !(player.getSpectatorTarget() instanceof Player)) return;
        Player target = (Player) player.getSpectatorTarget();

        Game game = BedwarsRel.getInstance().getGameManager().getGameOfPlayer(player);
        if (game == null) return;

        if (!(game.isSpectator(player)) || game.isSpectator(target)) return;

        WatchPlayerTask task = new WatchPlayerTask(player);
        task.runTaskTimer(plugin, 0L, 20L);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        giveTool(player);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        giveTool(player);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (e.getInventory().getType() != InventoryType.CRAFTING) return;

        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if (ItemUtil.isEmpty(item)) return;
        if (!item.equals(Config.Spectator.tool_Item)) return;

        WeiUtil.playerCmd(player, Config.Spectator.tool_Command);
    }

    private void giveTool(Player player) {
        if (!Config.Spectator.tool_Enable) return;

        Game game = BedwarsRel.getInstance().getGameManager().getGameOfPlayer(player);
        if (game == null) return;

        if (!game.isSpectator(player)) return;

        player.getInventory().setItem(Config.Spectator.tool_Slot, Config.Spectator.tool_Item);
    }

    public class WatchPlayerTask extends BukkitRunnable {

        Player player;

        private WatchPlayerTask(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            Entity entity = player.getSpectatorTarget();
            if (entity == null) {
                cancel();
                return;
            }

            if (!(entity instanceof Player)) {
                cancel();
                return;
            }

            Player target = (Player) entity;
            ActionBarAPI.sendActionBar(player, Config.Spectator.watchPlayer.replace("%player%", target.getName()));
        }
    }
}
