package dev.ratas.playercommmandprocessor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class ReloadCommand implements TabExecutor {
    private final PlayerCommandProcessor plugin;

    public ReloadCommand(PlayerCommandProcessor plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("Reloading plugin config");
        plugin.reload();
        return true;
    }

}
