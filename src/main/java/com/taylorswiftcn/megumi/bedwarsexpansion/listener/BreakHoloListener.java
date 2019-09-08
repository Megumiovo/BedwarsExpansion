package com.taylorswiftcn.megumi.bedwarsexpansion.listener;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.taylorswiftcn.megumi.bedwarsexpansion.BedwarsExpansion;
import com.taylorswiftcn.megumi.bedwarsexpansion.file.sub.Config;
import io.github.bedwarsrel.events.BedwarsGameEndEvent;
import io.github.bedwarsrel.events.BedwarsGameOverEvent;
import io.github.bedwarsrel.events.BedwarsTargetBlockDestroyedEvent;
import io.github.bedwarsrel.game.Game;
import io.github.bedwarsrel.game.Team;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class BreakHoloListener implements Listener {

    private BedwarsExpansion plugin;

    public BreakHoloListener(BedwarsExpansion plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreakBed(BedwarsTargetBlockDestroyedEvent e) {
        Game game = e.getGame();
        Player player = e.getPlayer();
        Team team = e.getTeam();

        Team playerTeam = game.getPlayerTeam(player);
        Location location = team.getTargetFeetBlock().add(0, 2, 0);

        String message = Config.BreakHolo.message
                .replace("%team%", team.getChatColor() + playerTeam.getName())
                .replace("%player%", player.getName())
                .replace("&", "§");

        Hologram hologram = HologramsAPI.createHologram(plugin, location);
        hologram.appendTextLine(message);
    }

    @EventHandler
    public void onGameOver(BedwarsGameOverEvent e) {
        delete(e.getGame());
    }

    @EventHandler
    public void onGameOver(BedwarsGameEndEvent e) {
        delete(e.getGame());
    }

    private void delete(Game game) {
        for (Hologram hologram : HologramsAPI.getHolograms(plugin)) {
            if (hologram.getWorld().equals(game.getResourceSpawners().listIterator().next().getLocation().getWorld())) {
                hologram.delete();
            }
        }
    }
}
