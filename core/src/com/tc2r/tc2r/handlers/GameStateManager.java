package com.tc2r.tc2r.handlers;

import com.tc2r.tc2r.TheGame;
import com.tc2r.tc2r.states.GameState;
import com.tc2r.tc2r.states.Play;

import java.util.Stack;

/**
 * Created by Tc2r on 11/7/2017.
 * <p>
 * Description: Handles all of the Game States.
 */
public class GameStateManager {

	private TheGame game;

	private Stack<GameState> gameStates;

	public static final int PLAY = 90250;

	public GameStateManager(TheGame game) {
		this.game = game;
		gameStates = new Stack<GameState>();
		pushState(PLAY);
	}

	public TheGame getGame() {
		return game;
	}

	public GameState getState(int state) {
		if(state == PLAY){
			return new Play(this);
		}
		return null;
	}

	public void setState(int state){
		popState();
		pushState(state);
	}

	private void popState() {
		GameState g = gameStates.pop();
		g.dispose();
	}

	private void pushState(int state) {
		gameStates.push(getState(state));
	}

	public void update(float delta){
		gameStates.peek().update(delta);


	}

	public void render(){

		gameStates.peek().render();

	}


}
