package me.lostmatter.fancySK.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprHologramText extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprHologramText.class, String.class, ExpressionType.SIMPLE,
                "[the] text (from|of) [the] holo[gram] [named] %string%"
        );
    }

    private Expression<String> targetNpcExpression;

    @Override
    protected @Nullable String[] get(Event event) {
        HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
        Hologram hologram = manager.getHologram(targetNpcExpression.getSingle(event)).orElse(null);

        TextHologramData hologramData = (TextHologramData) hologram.getData();
        return hologramData.getText().toArray(new String[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "text of hologram " + targetNpcExpression.toString(event, b);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        targetNpcExpression = (Expression<String>) expressions[0];
        return false;
    }

}
