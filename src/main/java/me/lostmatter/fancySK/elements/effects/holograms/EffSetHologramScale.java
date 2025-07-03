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
import org.joml.Vector3f;

public class EffSetHologramScale extends Effect {

    static {
        Skript.registerEffect(EffSetHologramScale.class,
                "set [the] scale of %hologram% to %vector%"
        );
    }

    private Expression<String> hologramExpression;
    private Expression<Vector3f> vector3fExpression;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        hologramExpression = (Expression<String>) exprs[0];
        vector3fExpression = (Expression<Vector3f>) exprs[1];
        return true;
    }

    @Override
    protected void execute(@NotNull Event event) {
        HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
        Hologram hologram = manager.getHologram(hologramExpression.getSingle(event)).orElse(null);

        TextHologramData hologramData = (TextHologramData) hologram.getData();
        hologramData.setScale(vector3fExpression.getSingle(event));
    }

    @Override
    public @NotNull String toString(@NotNull Event event, boolean b) {
        return "set scale of hologram " + hologramExpression.toString(event, b) + " to " + vector3fExpression.toString(event, b);
    }

}
