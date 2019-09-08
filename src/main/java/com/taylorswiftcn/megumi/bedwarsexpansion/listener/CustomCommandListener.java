package com.taylorswiftcn.megumi.bedwarsexpansion.listener;

import com.taylorswiftcn.megumi.bedwarsexpansion.BedwarsExpansion;
import com.taylorswiftcn.megumi.bedwarsexpansion.file.sub.Config;
import com.taylorswiftcn.megumi.bedwarsexpansion.util.WeiUtil;
import io.github.bedwarsrel.events.BedwarsGameOverEvent;
import io.github.bedwarsrel.events.BedwarsGameStartEvent;
import io.github.bedwarsrel.game.Game;
import io.github.bedwarsrel.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class CustomCommandListener implements Listener {

    private BedwarsExpansion plugin;

    public CustomCommandListener(BedwarsExpansion plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onStart(BedwarsGameStartEvent e) {
        Game game = e.getGame();

        TimingCommandTask task = new TimingCommandTask(game);
        task.runTaskTimer(plugin, 0L, 20L);

        for (Player player : game.getPlayers()) {
            WeiUtil.executionCmd(player, Config.CustomCommand.start);
        }
    }

    @EventHandler
    public void onEnd(BedwarsGameOverEvent e) {
        Game game = e.getGame();

        for (Player player : game.getPlayers()) {
            WeiUtil.executionCmd(player, Config.CustomCommand.end);
        }
    }

    public class TimingCommandTask extends BukkitRunnable {

        private Game game;

        private TimingCommandTask(Game game) {
            this.game = game;
        }

        @Override
        public void run() {
            if (game.getState() != GameState.RUNNING) {
                cancel();
                return;
            }

            int timeLeft = game.getTimeLeft();

            if (!Config.CustomCommand.timingCommand.containsKey(timeLeft)) return;

            List<String> commands = Config.CustomCommand.timingCommand.get(timeLeft);
            for (Player player : game.getPlayers()) {
                if (game.isSpectator(player)) continue;

                WeiUtil.executionCmd(player, commands);
            }
        }
    }
}
