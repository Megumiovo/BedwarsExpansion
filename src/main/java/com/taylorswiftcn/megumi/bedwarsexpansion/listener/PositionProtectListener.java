package com.taylorswiftcn.megumi.bedwarsexpansion.listener;

import com.taylorswiftcn.megumi.bedwarsexpansion.file.sub.Config;
import io.github.bedwarsrel.BedwarsRel;
import io.github.bedwarsrel.game.Game;
import io.github.bedwarsrel.game.GameState;
import io.github.bedwarsrel.game.ResourceSpawner;
import io.github.bedwarsrel.game.Team;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PositionProtectListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Game game = BedwarsRel.getInstance().getGameManager().getGameOfPlayer(player);
        if (game == null) return;
        if (game.getState() != GameState.RUNNING) return;

        Location location = e.getBlock().getLocation();

        for (Team team : game.getTeams().values()) {
            if (team.getSpawnLocation().distance(location) <= Config.PositionProtect.spawnRange) {
                e.setCancelled(true);
                player.sendMessage(Config.Prefix + Config.PositionProtect.message);
                return;
            }
        }

        for (ResourceSpawner spawner : game.getResourceSpawners()) {
            if (spawner.getLocation().distance(location) <= Config.PositionProtect.resourceRange) {
                e.setCancelled(true);
                player.sendMessage(Config.Prefix + Config.PositionProtect.message);
                return;
            }
        }
    }
}
