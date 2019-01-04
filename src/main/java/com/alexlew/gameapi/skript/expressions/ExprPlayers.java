package com.alexlew.gameapi.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Players list of team/game")
@Description("Returns the players list of a team or game. Can be set.")
@Examples({
        "command test:",
        "\ttrigger:",
        "\t\tcreate team \"red\" for game \"Test\"",
        "\t\tadd player to last team created",
        "\t\tbroadcast \"All players of team %last team created%: %players of last team created%"
})
@Since("1.0")

public class ExprPlayers extends SimpleExpression<Player> {

    static {
        Skript.registerExpression(ExprPlayers.class, Player.class, ExpressionType.SIMPLE,
                "[the] players (of|in|from) %object%",
                "[the] %object%'s players"
        );
    }

    private Expression<Object> context;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init( Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult ) {
        context = (Expression<Object>) expr[0];
        return true;
    }

    @Override
    protected Player[] get( Event e ) {
        Object o = context.getSingle(e);
        if (o instanceof Team) {
            Team team = (Team) o;
            return team.getPlayers();
        } else if (o instanceof Game) {
            Game game = (Game) o;
            return game.getPlayers();
        } else {
            return null;
        }
    }


    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Player> getReturnType() {
        return Player.class;
    }

    @Override
    public String toString( Event e, boolean debug ) {
        Object o = context.getSingle(e);
        if (o instanceof Game) {
            Game cont = (Game) o;
            return "Players of \"" + cont.getName() + "\"";
        } else if (o instanceof Team) {
            Team cont = (Team) o;
            return "Players of \"" + cont.getName() + "\"";
        } else {
            GameAPI.error("The context is nor a CommandGameSpigot, nor a Team.");
            return null;
        }
    }
}