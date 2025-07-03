package me.lostmatter.fancySK;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class FancySK extends JavaPlugin {

    public static FancySK instance;
    SkriptAddon addon;

    @Override
    public void onEnable() {
        instance = this;
        addon = Skript.registerAddon(this);
        try {
            addon.loadClasses("me.lostmatter.fancySK.elements", "effects", "expressions", "sections");
        } catch (IOException e) {
            getLogger().severe("Failed to load classes: " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("FancySK has been enabled!");

        // check for mineskin api key in FancyNpcs config
        File fancyNpcsConfig = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("FancyNpcs")).getDataFolder(), "config.yml");
        if (!fancyNpcsConfig.exists()) {
            getLogger().warning("FancyNpcs config.yml not found! Please make sure FancyNpcs is installed correctly. (Or the server has started up for the first time)");
        } else {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(fancyNpcsConfig);
            if (!config.isSet("mineskin_api_key") || Objects.requireNonNull(config.getString("mineskin_api_key")).isEmpty()) {
                getLogger().warning("It is suggested to set your server's MineSkin API Key in the FancyNpcs config.yml to avoid rate limits.");
            } else {
                getLogger().info("FancyNpcs config.yml loaded successfully. MineSkin API Key is set.");
            }
        }
    }

    public FancySK getInstance() {
        return instance;
    }

    public SkriptAddon getAddon() {
        return addon;
    }

}