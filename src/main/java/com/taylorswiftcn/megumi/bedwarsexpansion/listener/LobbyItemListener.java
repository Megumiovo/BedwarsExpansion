package com.taylorswiftcn.megumi.bedwarsexpansion.listener;

import com.taylorswiftcn.megumi.bedwarsexpansion.file.sub.Config;
import com.taylorswiftcn.megumi.bedwarsexpansion.util.ItemUtil;
import com.taylorswiftcn.megumi.bedwarsexpansion.util.WeiUtil;
import io.github.bedwarsrel.BedwarsRel;
import io.github.bedwarsrel.events.BedwarsPlayerJoinedEvent;
import io.github.bedwarsrel.game.Game;
import io.github.bedwarsrel.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LobbyItemListener implements Listener {

    @EventHandler
    public void onJoin(BedwarsPlayerJoinedEvent e) {
        Player player = e.getPlayer();
        Game game = BedwarsRel.getInstance().getGameManager().getGameOfPlayer(player);
        if (game == null) return;

        if (game.getState() != GameState.WAITING) return;

        player.getInventory().setItem(Config.LobbyItem.slot, Config.LobbyItem.item);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        Player player = e.getPlayer();

        ItemStack item = player.getInventory().getItemInMainHand();
        if (ItemUtil.isEmpty(item)) return;
        if (!item.equals(Config.LobbyItem.item)) return;

        WeiUtil.playerCmd(player, Config.LobbyItem.command);
    }
}
