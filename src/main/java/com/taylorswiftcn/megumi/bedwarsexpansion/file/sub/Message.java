package com.taylorswiftcn.megumi.bedwarsexpansion.file.sub;

import com.taylorswiftcn.megumi.bedwarsexpansion.BedwarsExpansion;
import com.taylorswiftcn.megumi.bedwarsexpansion.util.WeiUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class Message {
    private static YamlConfiguration message;

    public static List<String> AdminHelp;

    public static String NoPermission;

    public static void init() {
        message = BedwarsExpansion.getInstance().getFileManager().getMessage();

        AdminHelp = getStringList("AdminHelp");

        NoPermission = getString("Message.NoPermission");
    }

    private static String getString(String path) {
        return WeiUtil.onReplace(message.getString(path));
    }

    private static List<String> getStringList(String path) {
        return WeiUtil.onReplace(message.getStringList(path));
    }


}
