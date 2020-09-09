package dev.ratas.playercommmandprocessor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;

public class Settings {
    private final JavaPlugin plugin;
    private final Set<String> commands = new HashSet<>();
    private boolean ignoreCommands;
    private double lookupRadius;
    private double lookupSquared;

    public Settings(JavaPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        ignoreCommands = plugin.getConfig().getBoolean("ignore-commands", true);
        commands.addAll(plugin.getConfig().getStringList("commands"));
        lookupRadius = plugin.getConfig().getDouble("look-radius", 20);
        lookupSquared = lookupRadius * lookupRadius;
    }

    public void reload() {
        commands.clear();
        load();
    }

    public boolean ignoresListedCommands() {
        return ignoreCommands;
    }

    public Set<String> getListedCommands() {
        return Collections.unmodifiableSet(commands);
    }

    public double getLookupDistance() {
        return lookupRadius;
    }

    public double getLookupDistanceSquared() {
        return lookupSquared;
    }

}
