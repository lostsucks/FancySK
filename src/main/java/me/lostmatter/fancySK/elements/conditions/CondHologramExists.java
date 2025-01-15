package me.lostmatter.fancySK.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CondHologramExists extends Condition {

    static {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("FancyHolograms")) {
            Skript.registerCondition(CondHologramExists.class,
                    "hologram [named] %string% [does] exists",
                    "hologram [named] %string% does(n't| not) exist"
            );
        }
    }

    private Expression<String> hologramNameExpression;
    private boolean isNegated;

    @Override
    public boolean check(@NotNull Event event) {
        String hologramName = hologramNameExpression.getSingle(event);
        if (hologramName == null) return false;

        List<String> names = new ArrayList<>();
        HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
        manager.getHolograms().forEach(hologram -> names.add(hologram.getName()));
        if (isNegated) {
            return !names.contains(hologramName);
        } else {
            return names.contains(hologramName);
        }
    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "hologram " + hologramNameExpression.toString(event, b) + (isNegated ? " does not exist" : " exists");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, @NotNull SkriptParser.ParseResult parseResult) {
        hologramNameExpression = (Expression<String>) expressions[0];
        isNegated = i == 1;
        return true;
    }

}
