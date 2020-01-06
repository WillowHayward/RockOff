package com.willhaycode.rockoff;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.willhaycode.rockoff.core.RockOffStage;

public class RockOff extends ApplicationAdapter {
	private RockOffStage stage;
	
	@Override
	public void create() {
		this.stage = new RockOffStage(new ScreenViewport());
		Gdx.input.setInputProcessor(this.stage);
	}
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}
	
	public void dispose () {
		stage.dispose();
	}
}
