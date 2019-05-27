package com.alexlew.gameapi.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.alexlew.gameapi.types.Game;
import org.bukkit.event.Event;

@Name("Game started?")
@Description("Check if a game has already started or not.")
@Examples({
		"command started:\n" +
				"\ttrigger:\n" +
				"\t\tif game \"test\" has not started:\n" +
				"\t\t\tbroadcast \"Nope this game didn't start\""
})
@Since("2.0.4")

public class CondGameStart extends Condition {

	static {
		Skript.registerCondition(CondGameStart.class,
				"%game% has [already] started",
				"%game% has(n't| not) [already] exist"
		);
	}

	private Expression<Game> game;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		game = (Expression<Game>) expr[0];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public boolean check(Event e) {
		return isNegated() != (game.getSingle(e).getState() == 2);
	}

	@Override
	public String toString(Event e, boolean debug) {
		String gameName = game.getSingle(e) != null ? game.getSingle(e).getName() : "null";
		return "Game \"" + gameName + "\" started";
	}
}
