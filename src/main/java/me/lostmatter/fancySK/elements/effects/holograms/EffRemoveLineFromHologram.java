package me.lostmatter.fancySK.elements.effects.holograms;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class EffRemoveLineFromHologram extends Effect {

    static {
        Skript.registerEffect(EffRemoveLineFromHologram.class,
                "remove line [number] %integer% from %hologram%"
        );
    }

    private Expression<Integer> lineNumberExpression;
    private Expression<String> textExpression;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        lineNumberExpression = (Expression<Integer>) exprs[0];
        textExpression = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    protected void execute(@NotNull Event event) {
        HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
        Hologram hologram = manager.getHologram(textExpression.getSingle(event)).orElse(null);
        if (hologram == null) return;

        TextHologramData hologramData = (TextHologramData) hologram.getData();
        hologramData.removeLine(lineNumberExpression.getSingle(event));

        hologram.forceUpdate();
    }

    @Override
    public @NotNull String toString(@NotNull Event event, boolean b) {
        return "remove line " + lineNumberExpression.toString(event, b) + " from " + textExpression.toString(event, b);
    }

}
