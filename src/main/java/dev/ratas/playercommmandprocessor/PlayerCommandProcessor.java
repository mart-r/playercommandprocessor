package dev.ratas.playercommmandprocessor;

import java.util.Set;
import java.util.regex.Pattern;

import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerCommandProcessor extends JavaPlugin implements Listener {
    private static final String AT_P = "@p(\\[.*?\\])?";
    private static final Pattern AT_P_PATTERN = Pattern.compile(AT_P);
    private Settings settings;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.settings = new Settings(this);
        getServer().getPluginManager().registerEvents(this, this);

        getCommand("pcpreload").setExecutor(new ReloadCommand(this));
    }

    @EventHandler
    public void onPrepocess(ServerCommandEvent event) {
        if (!(event.getSender() instanceof BlockCommandSender)) {
            return;
        }
        BlockCommandSender sender = (BlockCommandSender) event.getSender();
        String msg = event.getCommand();
        if (validMessage(msg) && AT_P_PATTERN.matcher(msg).find()) {
            Player closest = getClosestPlayer(sender);
            if (closest == null) {
                getLogger().warning(
                        "Unable to find closest player within distance of " + settings.getLookupDistance() + ":" + msg);
                return;
            }
            event.setCommand(msg.replaceAll(AT_P, closest.getName()));
        }
    }

    private Player getClosestPlayer(BlockCommandSender sender) {
        Location loc = sender.getBlock().getLocation();
        double dist = settings.getLookupDistance();
        double minDist2 = settings.getLookupDistanceSquared();
        Player closest = null;
        for (Entity player : loc.getWorld().getNearbyEntities(loc, dist, dist, dist, e -> e instanceof Player)) {
            double curDist2 = player.getLocation().distanceSquared(loc);
            if (curDist2 < minDist2) {
                minDist2 = curDist2;
                closest = (Player) player;
            }
        }
        return closest;

    }

    private boolean validMessage(String msg) {
        String cmd = msg.split(" ")[0];
        Set<String> cmds = settings.getListedCommands();
        if (settings.ignoresListedCommands()) {
            return !cmds.contains(cmd);
        } else {
            return cmds.contains(cmd);
        }
    }

    public void reload() {
        reloadConfig();
        settings.reload();
    }

}
