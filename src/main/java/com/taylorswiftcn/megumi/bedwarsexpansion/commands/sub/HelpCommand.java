package com.taylorswiftcn.megumi.bedwarsexpansion.commands.sub;

import com.taylorswiftcn.megumi.bedwarsexpansion.commands.WeiCommand;
import com.taylorswiftcn.megumi.bedwarsexpansion.file.sub.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand extends WeiCommand {
    @Override
    public void perform(CommandSender CommandSender, String[] Strings) {
        Player player = getPlayer();
        player.sendMessage(player.getDisplayName());
        Message.AdminHelp.forEach(CommandSender::sendMessage);
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "bedwarsedxpansion.admin";
    }
}
