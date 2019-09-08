package com.taylorswiftcn.megumi.bedwarsexpansion.file.sub;

import com.taylorswiftcn.megumi.bedwarsexpansion.BedwarsExpansion;
import com.taylorswiftcn.megumi.bedwarsexpansion.util.ItemUtil;
import com.taylorswiftcn.megumi.bedwarsexpansion.util.WeiUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config {
    private static YamlConfiguration config;

    public static String Prefix;

    public static class WitherBow {
        public static Boolean enable;
        public static Integer time;
        public static List<String> message;
        public static String title;
    }

    public static class LobbyItem {
        public static Boolean enable;
        public static Integer slot;
        public static String command;
        public static ItemStack item;
    }

    public static class CustomCommand {
        public static Boolean enable;
        public static List<String> start;
        public static List<String> end;
        public static HashMap<Integer, List<String>> timingCommand;
    }

    public static class CustomHealth {
        public static Boolean enable;
        public static Integer default_Health;
        public static List<String> message;
        public static String title_Main;
        public static String title_Sub;
        public static Integer fadeIn;
        public static Integer stay;
        public static Integer fadeOut;
        public static HashMap<Integer, Integer> timingHealth;
    }

    public static class Spectator {
        public static Boolean enable;
        public static String watchPlayer;
        public static Boolean tool_Enable;
        public static Integer tool_Slot;
        public static String tool_Command;
        public static ItemStack tool_Item;
    }

    public static class AttackInfo {
        public static Boolean enable;
        public static String meleeHolo;
        public static String bowTitle_Main;
        public static String bowTitle_Sub;
        public static int bowFadeIn;
        public static int bowStay;
        public static int bowFadeOut;
    }

    public static class PositionProtect {
        public static Boolean enable;
        public static Integer spawnRange;
        public static Integer resourceRange;
        public static String message;
    }

    public static class BreakHolo {
        public static Boolean enable;
        public static String message;
    }

    public static class GameOverMessage {
        public static Boolean enable;
        public static String format_Rank;
        public static Integer amount_Rank;
        public static List<String> list;
    }

    public static class ItemGift {
        public static Boolean enable;
        public static Boolean respawnGive;
        public static ItemStack helmet;
        public static ItemStack chestplate;
        public static ItemStack leggings;
        public static ItemStack boots;
        public static List<ItemStack> items;
    }

    public static void init() {
        config = BedwarsExpansion.getInstance().getFileManager().getConfig();

        Prefix = getString("Prefix");

        WitherBow.enable = config.getBoolean("WitherBow.Enable");
        WitherBow.time = config.getInt("WitherBow.Time");
        WitherBow.message = getStringList("WitherBow.Message");
        WitherBow.title = "WitherBow";

        LobbyItem.enable = config.getBoolean("LobbyItem.Enable");
        LobbyItem.slot = config.getInt("LobbyItem.Slot");
        LobbyItem.command = getString("LobbyItem.Command");
        String lobbyItem_ID = config.getString("LobbyItem.ID");
        short lobbyItem_Date = (short) config.getInt("LobbyItem.Data");
        String lobbyItem_Name = config.getString("LobbyItem.Name");
        List<String> lobbyItem_Lore = config.getStringList("LobbyItem.Lore");
        LobbyItem.item = ItemUtil.createItem(lobbyItem_ID, lobbyItem_Date, 1, lobbyItem_Name, lobbyItem_Lore, null);

        CustomCommand.enable = config.getBoolean("CustomCommand.Enable");
        CustomCommand.start = getStringList("CustomCommand.Start");
        CustomCommand.end = getStringList("CustomCommand.End");
        CustomCommand.timingCommand = new HashMap<>();
        ConfigurationSection timingCommand = config.getConfigurationSection("CustomCommand.Timing");
        for (String s : timingCommand.getKeys(false)) {
            int key = timingCommand.getInt(String.format("%s.Time", s));
            List<String> value = timingCommand.getStringList(String.format("%s.Commands", s));
            CustomCommand.timingCommand.put(key, WeiUtil.onReplace(value));
        }

        CustomHealth.enable = config.getBoolean("CustomHealth.Enable");
        CustomHealth.default_Health = config.getInt("CustomHealth.Default-Health");
        CustomHealth.message = getStringList("CustomHealth.Message");
        CustomHealth.title_Main = getString("CustomHealth.Title-Main");
        CustomHealth.title_Sub = getString("CustomHealth.Title-Sub");
        CustomHealth.fadeIn = config.getInt("CustomHealth.FadeIn");
        CustomHealth.stay = config.getInt("CustomHealth.Stay");
        CustomHealth.fadeOut = config.getInt("CustomHealth.FadeOut");
        CustomHealth.timingHealth = new HashMap<>();
        ConfigurationSection timingHealth = config.getConfigurationSection("CustomHealth.Timing");
        for (String s : timingHealth.getKeys(false)) {
            int key = timingHealth.getInt(String.format("%s.Time", s));
            int value = timingHealth.getInt(String.format("%s.Health", s));
            CustomHealth.timingHealth.put(key, value);
        }

        Spectator.enable = config.getBoolean("Spectator.Enable");
        Spectator.watchPlayer = getString("Spectator.WatchPlayer");
        Spectator.tool_Enable = config.getBoolean("Spectator.Tool.Enable");
        Spectator.tool_Slot = config.getInt("Spectator.Tool.SLot");
        Spectator.tool_Command = getString("Spectator.Tool.Command");
        String id = config.get("Spectator.Tool.ID").toString();
        short data = (short) config.getInt("Spectator.Tool.Data");
        String name = config.getString("Spectator.Tool.Name");
        List<String> lore = config.getStringList("Spectator.Tool.Lore");
        Spectator.tool_Item = ItemUtil.createItem(id, data, 1, name, lore, null);

        AttackInfo.enable = config.getBoolean("AttackInfo.Enable");
        AttackInfo.meleeHolo = getString("AttackInfo.MeleeHolo");
        AttackInfo.bowTitle_Main = getString("AttackInfo.BowTitle.Title-Main");
        AttackInfo.bowTitle_Sub = getString("AttackInfo.BowTitle.Title-Sub");
        AttackInfo.bowFadeIn = config.getInt("AttackInfo.BowTitle.FadeIn");
        AttackInfo.bowStay = config.getInt("AttackInfo.BowTitle.Stay");
        AttackInfo.bowFadeOut = config.getInt("AttackInfo.BowTitle.FadeOut");

        PositionProtect.enable = config.getBoolean("PositionProtect.Enable");
        PositionProtect.spawnRange = config.getInt("PositionProtect.SpawnRange");
        PositionProtect.resourceRange = config.getInt("PositionProtect.ResourceRange");
        PositionProtect.message = getString("PositionProtect.Message");

        BreakHolo.enable = config.getBoolean("BreakHolo.Enable");
        BreakHolo.message = config.getString("BreakHolo.Message");

        GameOverMessage.enable = config.getBoolean("GameOverMessage.Enable");
        GameOverMessage.format_Rank = getString("GameOverMessage.Format-Rank");
        GameOverMessage.amount_Rank = config.getInt("GameOverMessage.Amount-Rank");
        GameOverMessage.list = getStringList("GameOverMessage.List");

        ItemGift.enable = config.getBoolean("ItemGift.Enable");
        ItemGift.respawnGive = config.getBoolean("ItemGift.RespawnGive");
        ItemGift.helmet = _I("ItemGift.Helmet");
        ItemGift.chestplate = _I("ItemGift.Chestplate");
        ItemGift.leggings = _I("ItemGift.Leggings");
        ItemGift.boots = _I("ItemGift.Boots");
        ItemGift.items = new ArrayList<>();
        ConfigurationSection itemSection = config.getConfigurationSection("ItemGift.Item");
        for (String s :itemSection.getKeys(false)) {
            ItemGift.items.add(_I(String.format("ItemGift.Item.%s", s)));
        }
    }

    private static String getString(String path) {
        return WeiUtil.onReplace(config.getString(path));
    }

    private static List<String> getStringList(String path) {
        return WeiUtil.onReplace(config.getStringList(path));
    }

    private static ItemStack _I(String path) {
        String id = config.get(String.format("%s.ID", path)).toString();
        short data = (short) config.getInt(String.format("%s.Data", path));
        Integer amount = config.getInt(String.format("%s.Amount", path));
        String name = config.getString(String.format("%s.Name", path));
        List<String> lore = config.getStringList(String.format("%s.Lore", path));
        return ItemUtil.createItem(id, data, amount, name, lore, null);
    }

    public static void _T(Player player, String path) {
        String title = getString(String.format("%s.Title-Main", path));
        String sub = getString(String.format("%s.Title-Sub", path));
        int fadeIn = config.getInt(String.format("%s.FadeIn", path));
        int stay = config.getInt(String.format("%s.Stay", path));
        int fadeOut = config.getInt(String.format("%s.FadeOut", path));

        player.sendTitle(title, sub, fadeIn * 20, stay * 20, fadeOut * 20);
    }
}
