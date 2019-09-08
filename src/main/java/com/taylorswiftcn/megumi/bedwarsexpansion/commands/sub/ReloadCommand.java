package com.taylorswiftcn.megumi.bedwarsexpansion.commands.sub;

import com.taylorswiftcn.megumi.bedwarsexpansion.BedwarsExpansion;
import com.taylorswiftcn.megumi.bedwarsexpansion.commands.WeiCommand;
import com.taylorswiftcn.megumi.bedwarsexpansion.file.sub.Config;
import com.taylorswiftcn.megumi.bedwarsexpansion.file.sub.Message;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends WeiCommand {

    private BedwarsExpansion plugin = BedwarsExpansion.getInstance();

    @Override
    public void perform(CommandSender CommandSender, String[] Strings) {
        plugin.getFileManager().init();
        Config.init();
        Message.init();
        CommandSender.sendMessage(Config.Prefix + "§a重载成功!");
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "bedwarsexpansion.admin";
    }
}
