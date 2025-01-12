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
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class EffAddLine extends Effect {

    static {
        Skript.registerEffect(EffAddLine.class,
                "add[ ]line %string% to holo[gram] [named] %string%"
        );
    }

    private Expression<String> lineExpression;
    private Expression<String> hologramExpression;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        lineExpression = (Expression<String>) exprs[0];
        hologramExpression = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    protected void execute(@NotNull Event event) {
        HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
        Hologram hologram = manager.getHologram(hologramExpression.getSingle(event)).orElse(null);

        TextHologramData hologramData = (TextHologramData) hologram.getData();
        hologramData.addLine(lineExpression.getSingle(event));
    }

    @Override
    public @NotNull String toString(@NotNull Event event, boolean b) {
        return "add line " + lineExpression.toString(event, b) + " to hologram " + hologramExpression.toString(event, b);
    }

}
