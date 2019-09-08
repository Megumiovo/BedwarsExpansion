package com.taylorswiftcn.megumi.bedwarsexpansion.listener;

import com.taylorswiftcn.megumi.bedwarsexpansion.BedwarsExpansion;
import com.taylorswiftcn.megumi.bedwarsexpansion.file.sub.Config;
import io.github.bedwarsrel.events.BedwarsGameStartEvent;
import io.github.bedwarsrel.game.Game;
import io.github.bedwarsrel.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomHealthListener implements Listener {

    private BedwarsExpansion plugin;

    public CustomHealthListener(BedwarsExpansion plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onStart(BedwarsGameStartEvent e) {
        Game game = e.getGame();

        TimingHealthTask task = new TimingHealthTask(game);
        task.runTaskTimer(plugin, 0L, 20L);

        for (Player player : game.getPlayers()) {
            player.setMaxHealth(Config.CustomHealth.default_Health);
        }
    }

    public class TimingHealthTask extends BukkitRunnable {

        private Game game;

        private TimingHealthTask(Game game) {
            this.game = game;
        }

        @Override
        public void run() {
            if (game.getState() != GameState.RUNNING) {
                cancel();
                return;
            }

            int timeLeft = game.getTimeLeft();

            if (!Config.CustomHealth.timingHealth.containsKey(timeLeft)) return;

            int changeHealth = Config.CustomHealth.timingHealth.get(timeLeft);

            for (Player player : game.getPlayers()) {
                if (game.isSpectator(player)) continue;

                player.setMaxHealth(changeHealth);
                Config.CustomHealth.message.forEach(s -> player.sendMessage(s.replace("%max%", String.valueOf(changeHealth))));
                player.sendTitle(
                        Config.CustomHealth.title_Main.replace("%max%", String.valueOf(changeHealth)),
                        Config.CustomHealth.title_Sub.replace("%max%", String.valueOf(changeHealth)),
                        Config.CustomHealth.fadeIn * 20,
                        Config.CustomHealth.stay * 20,
                        Config.CustomHealth.fadeOut * 20
                );
            }
        }
    }
}
