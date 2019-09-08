package com.taylorswiftcn.megumi.bedwarsexpansion.listener;

import com.taylorswiftcn.megumi.bedwarsexpansion.BedwarsExpansion;
import com.taylorswiftcn.megumi.bedwarsexpansion.file.sub.Config;
import io.github.bedwarsrel.BedwarsRel;
import io.github.bedwarsrel.events.BedwarsGameStartEvent;
import io.github.bedwarsrel.game.Game;
import io.github.bedwarsrel.game.GameState;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class WitherBowListener implements Listener {

    private BedwarsExpansion plugin;

    public WitherBowListener(BedwarsExpansion plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onStart(BedwarsGameStartEvent e) {
        Game game = e.getGame();
        WitherBowTask task = new WitherBowTask(game);
        task.runTaskTimer(plugin, 0L, 20L);
    }

    @EventHandler
    public void onShootBow(EntityShootBowEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getEntity();
        Game game = BedwarsRel.getInstance().getGameManager().getGameOfPlayer(player);
        if (game == null) return;

        int min = (int) Math.ceil((double) game.getTimeLeft() / 60);

        if (min > Config.WitherBow.time) return;

        WitherSkull skull = player.launchProjectile(WitherSkull.class);
        skull.setVelocity(e.getProjectile().getVelocity());
        skull.setShooter(player);
        player.getWorld().playSound(player.getLocation(), Sound.WEATHER_RAIN, 1.0F, 0.0F);
        player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 5);

        e.setCancelled(true);
    }

    public class WitherBowTask extends BukkitRunnable {

        private Game game;

        WitherBowTask(Game game) {
            this.game = game;
        }

        @Override
        public void run() {
            if (game.getState() != GameState.RUNNING) {
                cancel();
                return;
            }

            int timeLeft = game.getTimeLeft();

            int min = timeLeft / 60;
            int sec = timeLeft % 60;

            if (sec == 0 && min == Config.WitherBow.time) {
                for (Player player : game.getPlayers()) {
                    Config.WitherBow.message.forEach(player::sendMessage);
                    Config._T(player, Config.WitherBow.title);
                }
            }
        }
    }
}
