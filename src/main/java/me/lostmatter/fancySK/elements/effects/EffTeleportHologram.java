package me.lostmatter.fancySK.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class EffTeleportHologram extends Effect {

    static {
        Skript.registerEffect(EffTeleportHologram.class,
                "teleport [the] holo[gram] [named] %string% to %location%"
        );
    }

    private Expression<String> hologramExpression;
    private Expression<Location> locationExpression;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        hologramExpression = (Expression<String>) exprs[0];
        locationExpression = (Expression<Location>) exprs[1];
        return true;
    }

    @Override
    protected void execute(@NotNull Event event) {
        HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
        Hologram hologram = manager.getHologram(hologramExpression.getSingle(event)).orElse(null);
        if (hologram == null) return;

        TextHologramData hologramData = (TextHologramData) hologram.getData();
        hologramData.setLocation(locationExpression.getSingle(event));

        hologram.forceUpdate();
    }

    @Override
    public @NotNull String toString(@NotNull Event event, boolean b) {
        return "teleport hologram " + hologramExpression.toString(event, b) + " to " + locationExpression.toString(event, b);
    }

}
