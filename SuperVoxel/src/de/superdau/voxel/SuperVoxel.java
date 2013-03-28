package de.superdau.voxel;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;

import de.superdau.voxel.actor.Actor;
import de.superdau.voxel.octree.OctreeTests;


public class SuperVoxel implements ApplicationListener {

    private OctreeTests octreeModel;
    private Actor actor;
    private ShaderProgram shader;
	
	@Override
	public void create() {		
		octreeModel=new OctreeTests();
		System.out.println("removing hidden meshes");
		octreeModel.removeHiddenMeshes();
		System.out.println("finished removing");
		float hoehe=(float) Math.pow(2, octreeModel.getMaxDepth())/2;
		actor=new Actor(new Vector3(hoehe,hoehe,hoehe), new Vector3(2*hoehe,hoehe,2*hoehe));
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
	    //Gdx.gl10.glBlendFunc(GL10.GL_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	    Gdx.gl10.glEnable(GL10.GL_BLEND);
	    Gdx.gl.glEnable(GL10.GL_ALPHA_TEST);
	    Gdx.gl10.glAlphaFunc(GL10.GL_GREATER, 0);
	    
	    Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
		//gl.glEnable(GL10.GL_CULL_FACE);
		//gl.glCullFace(GL10.GL_BACK);
		
		actor.getCamera().update();
		actor.getCamera().apply(gl);
		actor.render(gl);
	    gl.glPushMatrix();
	    	octreeModel.render(gl,actor);
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
