package com.taylorswiftcn.megumi.bedwarsexpansion.listener;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.taylorswiftcn.megumi.bedwarsexpansion.BedwarsExpansion;
import com.taylorswiftcn.megumi.bedwarsexpansion.file.sub.Config;
import io.github.bedwarsrel.BedwarsRel;
import io.github.bedwarsrel.game.Game;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.Random;

public class AttackInfoListener implements Listener {

    private BedwarsExpansion plugin;
    private Random random;

    public AttackInfoListener(BedwarsExpansion plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMeleeDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player && e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getDamager();

        Game game = BedwarsRel.getInstance().getGameManager().getGameOfPlayer(player);
        if (game == null) return;

        Entity entity = e.getEntity();

        String value = new DecimalFormat("0.00").format(e.getDamage());
        String message = Config.AttackInfo.meleeHolo.replace("%damage%", value);

        MeleeHoloTask task = new MeleeHoloTask(player, entity.getLocation().add(random.nextDouble() * 0.5, 1.5d, random.nextDouble() * 0.5), message);
        task.runTaskTimer(plugin, 0, 3);
    }

    @EventHandler
    public void onBowDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Arrow)) return;

        Arrow arrow = (Arrow) e.getDamager();

        if (!(arrow.getShooter() instanceof Player && e.getEntity() instanceof Player)) return;

        Player player = (Player) arrow.getShooter();

        String value = new DecimalFormat("0.00").format(e.getDamage());
        player.sendTitle(
                Config.AttackInfo.bowTitle_Main.replace("%damage%", value),
                Config.AttackInfo.bowTitle_Sub.replace("%damage%", value),
                Config.AttackInfo.bowFadeIn * 20,
                Config.AttackInfo.bowStay * 20,
                Config.AttackInfo.bowFadeOut * 20
        );
    }

    public class MeleeHoloTask extends BukkitRunnable {

        private Location location;
        private Hologram hologram;
        private int i;

        private MeleeHoloTask(Player player, Location location, String message) {
            this.location = location;

            hologram = HologramsAPI.createHologram(plugin, location);
            hologram.getVisibilityManager().showTo(player);
            hologram.getVisibilityManager().setVisibleByDefault(false);
            hologram.appendTextLine(message);
            i = 0;
        }

        @Override
        public void run() {
            hologram.teleport(location.add(0d, 0.1, 0d));
            i++;
            if (i == 10) {
                hologram.delete();
                cancel();
            }
        }
    }
}
