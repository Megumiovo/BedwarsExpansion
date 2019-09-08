package com.taylorswiftcn.megumi.bedwarsexpansion;

import com.taylorswiftcn.megumi.bedwarsexpansion.commands.MainCommand;
import com.taylorswiftcn.megumi.bedwarsexpansion.file.FileManager;
import com.taylorswiftcn.megumi.bedwarsexpansion.file.sub.Config;
import com.taylorswiftcn.megumi.bedwarsexpansion.file.sub.Message;
import com.taylorswiftcn.megumi.bedwarsexpansion.listener.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BedwarsExpansion extends JavaPlugin {
    @Getter private static BedwarsExpansion instance;

    @Getter private FileManager fileManager;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();

        instance = this;

        fileManager = new FileManager(this);
        fileManager.init();

        Config.init();
        Message.init();

        if (Config.WitherBow.enable) {
            Bukkit.getPluginManager().registerEvents(new WitherBowListener(this), this);
            getLogger().info("Function: 已启用 凋零弓");
        }
        if (Config.LobbyItem.enable) {
            Bukkit.getPluginManager().registerEvents(new LobbyItemListener(), this);
            getLogger().info("Function: 已启用 大厅菜单");
        }
        if (Config.CustomCommand.enable) {
            Bukkit.getPluginManager().registerEvents(new CustomCommandListener(this), this);
            getLogger().info("Function: 已启用 自定义命令");
        }
        if (Config.CustomHealth.enable) {
            Bukkit.getPluginManager().registerEvents(new CustomHealthListener(this), this);
            getLogger().info("Function: 已启用 自定义血量");
        }
        if (Config.Spectator.enable) {
            Bukkit.getPluginManager().registerEvents(new SpectatorListener(this), this);
            getLogger().info("Function: 已启用 观察者");
        }
        if (Config.AttackInfo.enable) {
            if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null) {
                Bukkit.getPluginManager().registerEvents(new AttackInfoListener(this), this);
                getLogger().info("Function: 已启用 战斗信息");
            }
            else {
                getLogger().info("Function: 限制破坏床 启用失败,需要前置 HolographicDisplays 插件");
            }
        }
        if (Config.PositionProtect.enable) {
            Bukkit.getPluginManager().registerEvents(new PositionProtectListener(), this);
            getLogger().info("Function: 已启用 位置保护");
        }
        /*if (Config.BreakHolo.enable) {
            Bukkit.getPluginManager().registerEvents(new BreakHoloListener(this), this);
            getLogger().info("Function: 已启用 床破坏全息图");
        }*/
        if (Config.GameOverMessage.enable) {
            Bukkit.getPluginManager().registerEvents(new GameOverMessageListener(), this);
            getLogger().info("Function: 已启用 结束游戏数据显示");
        }
        if (Config.ItemGift.enable) {
            Bukkit.getPluginManager().registerEvents(new ItemGiftListener(), this);
            getLogger().info("Function: 已启用 物品馈赠");
        }

        getCommand("bwe").setExecutor(new MainCommand());

        long end = System.currentTimeMillis();

        getLogger().info("加载成功! 用时 %time% ms".replace("%time%", String.valueOf(end - start)));
    }

    @Override
    public void onDisable() {
        getLogger().info("卸载成功!");
    }

    public String getVersion() {
        String packet = Bukkit.getServer().getClass().getPackage().getName();
        return packet.substring(packet.lastIndexOf('.') + 1);
    }
}
