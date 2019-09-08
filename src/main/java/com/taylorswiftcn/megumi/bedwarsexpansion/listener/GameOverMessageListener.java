package com.taylorswiftcn.megumi.bedwarsexpansion.listener;

import com.taylorswiftcn.megumi.bedwarsexpansion.file.sub.Config;
import io.github.bedwarsrel.events.BedwarsGameOverEvent;
import io.github.bedwarsrel.game.Game;
import io.github.bedwarsrel.game.Team;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameOverMessageListener implements Listener {

    @EventHandler
    public void onGameOver(BedwarsGameOverEvent e) {
        Game game = e.getGame();
        Team win = e.getWinner();

        List<String> message = new ArrayList<>();
        for (String s : Config.GameOverMessage.list) {
            if (s.equals("%kill_rank%")) {
                List<Map.Entry<Player, Integer>> list = getRank(game);
                if (list.size() == 0) continue;
                int i = 1;
                for (Map.Entry<Player, Integer> map : list) {
                    Player player = map.getKey();
                    int kill = map.getValue();
                    Team team = game.getPlayerTeam(player);
                    message.add(Config.GameOverMessage.format_Rank
                            .replace("%i%", String.valueOf(i))
                            .replace("%team%", team.getChatColor() + team.getName())
                            .replace("%player%", player.getName())
                            .replace("%kill%", String.valueOf(kill))
                            .replace("&", "§")
                    );
                    i++;
                }
                continue;
            }

            List<String> playerName = new ArrayList<>();
            if (win == null) return;
            for (Player player : win.getPlayers()) {
                playerName.add(player.getName());
            }

            message.add(s
                    .replace("%win_team%", win.getChatColor() + win.getName())
                    .replace("%team_players%", StringUtils.join(playerName, ','))
                    .replace("%team_kill%", String.valueOf(game.getTeamKill().get(win.getName())))
                    .replace("&", "§")
            );
        }

        for (Player p : game.getPlayers()) {
            message.forEach(p::sendMessage);
        }
    }

    private List<Map.Entry<Player, Integer>> getRank(Game game) {
        List<Map.Entry<Player, Integer>> list = new ArrayList<>(game.getPlayerKill().entrySet());

        if (list.size() == 0) return new ArrayList<>();

        list.sort((o1, o2) -> o2.getValue() - o1.getValue());

        return list.size() >= Config.GameOverMessage.amount_Rank ? list.subList(0, Config.GameOverMessage.amount_Rank) : list;
    }
}
