package me.lostmatter.fancySK.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.oliver.fancynpcs.api.FancyNpcsPlugin;
import de.oliver.fancynpcs.api.Npc;
import de.oliver.fancynpcs.api.utils.SkinFetcher;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class EffSetNPCSkin extends Effect {

    static {
        Skript.registerEffect(EffSetNPCSkin.class,
                "set [the] npc skin of %string% to %string%",
                "set [the] skin of npc [named] %string% to %string%"
        );
    }

    private Expression<String> targetNpcExpression;
    private Expression<String> skinExpression;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        targetNpcExpression = (Expression<String>) exprs[0];
        skinExpression = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    protected void execute(@NotNull Event event) {
        Npc npc = FancyNpcsPlugin.get().getNpcManager().getNpc(targetNpcExpression.getSingle(event));
        if (npc == null) return;

        String skin = skinExpression.getSingle(event);
        if (skin == null) return;

        SkinFetcher.SkinData skinData = new SkinFetcher.SkinData(skin, null, null);
        npc.getData().setSkin(skinData);

        npc.removeForAll();
        npc.create();
        npc.spawnForAll();
    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "set " + targetNpcExpression.toString(event, b) + "'s skin to " + skinExpression.toString(event, b);
    }

}