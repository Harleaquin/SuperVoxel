package de.superdau.voxel;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector3;

import de.superdau.voxel.actor.Actor;
import de.superdau.voxel.octree.OctreeTests;


public class SuperVoxel implements ApplicationListener {

    private OctreeTests octreeModel;
    private Actor actor;
	
	@Override
	public void create() {		
		octreeModel=new OctreeTests();
		octreeModel.removeHiddenMeshes();
		System.out.println(Math.pow(2, octreeModel.getMaxDepth()));
		actor=new Actor(new Vector3(5,16,5), new Vector3(16,16,16));
		Gdx.input.setInputProcessor(actor);
	}

	@Override
	public void dispose() {
		//texture.dispose();
	}

	
	
	@Override
	public void render() {		
	    GL10 gl =Gdx.gl10;
	    Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
	    Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
		
		actor.getCamera().update();
		actor.getCamera().apply(gl);
		actor.render(gl);
	    gl.glPushMatrix();
	    	octreeModel.render(gl);
	    gl.glPopMatrix();
	}
	

	
	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
	}
}
