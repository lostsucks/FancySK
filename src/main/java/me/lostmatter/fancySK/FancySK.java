package me.lostmatter.fancySK;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class FancySK extends JavaPlugin {

    public static FancySK instance;
    SkriptAddon addon;

    @Override
    public void onEnable() {
        instance = this;
        addon = Skript.registerAddon(this);
        try {
            addon.loadClasses("me.lostmatter.fancySK.elements", "effects", "expressions", "conditions");
        } catch (IOException e) {
            e.printStackTrace();
        }

        getLogger().info("FancySK has been enabled!");
    }

    public FancySK getInstance() {
        return instance;
    }

    public SkriptAddon getAddon() {
        return addon;
    }

}