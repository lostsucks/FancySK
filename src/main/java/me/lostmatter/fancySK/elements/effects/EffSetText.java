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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EffSetText extends Effect {

    static {
        Skript.registerEffect(EffSetText.class,
                "set text of holo[gram] [named] %string% to %string%"
        );
    }

    private Expression<String> targetNpcExpression;
    private Expression<String> textExpression;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        targetNpcExpression = (Expression<String>) exprs[0];
        textExpression = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    protected void execute(@NotNull Event event) {
        HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
        Hologram hologram = manager.getHologram(targetNpcExpression.getSingle(event)).orElse(null);
        if (hologram == null) return;

        String text = textExpression.getSingle(event);
        if (text == null) return;

        TextHologramData hologramData = (TextHologramData) hologram.getData();
        hologramData.setText(List.of(text));

        hologram.forceUpdate();
        for (Player player : Bukkit.getOnlinePlayers()) {
            hologram.refreshHologram(player);
        }
    }

    @Override
    public @NotNull String toString(@NotNull Event event, boolean b) {
        return "set text of hologram " + targetNpcExpression.toString(event, b) + " to " + textExpression.toString(event, b);
    }
}
