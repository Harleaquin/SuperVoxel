package de.superdau.voxel;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import de.superdau.voxel.chunk.OctreeModel;


public class SuperVoxel implements ApplicationListener {
	private PerspectiveCamera camera;
	private Vector3 camPos;
    private float winkel=0;
    private float abstand=3;
    private OctreeModel octreeModel;
	
	@Override
	public void create() {		
		octreeModel=new OctreeModel(2);
		octreeModel.getNodeAt(octreeModel.getRootNode(), 3, 3, 3).setEmpty(true);
		octreeModel.getNodeAt(octreeModel.getRootNode(), 3, 2, 3).setEmpty(true);
		octreeModel.getNodeAt(octreeModel.getRootNode(), 3, 3, 2).setEmpty(true);
		octreeModel.getNodeAt(octreeModel.getRootNode(), 2, 3, 3).setEmpty(true);
		octreeModel.getNodeAt(octreeModel.getRootNode(), 0, 0, 0).setEmpty(true);
		octreeModel.getNodeAt(octreeModel.getRootNode(), 0, 1, 0).setEmpty(true);
		octreeModel.getNodeAt(octreeModel.getRootNode(), 0, 0, 1).setEmpty(true);
		octreeModel.getNodeAt(octreeModel.getRootNode(), 1, 0, 0).setEmpty(true);
		octreeModel.removeHiddenMeshes();
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera =new PerspectiveCamera(67, 2f * (w/h), 2f);
		
		float dx = MathUtils.sinDeg(winkel)*abstand;
		float dy = MathUtils.cosDeg(winkel)*abstand;
		
		camPos=new Vector3(dx,0,dy);
		//System.out.println(camPos.toString());
		camera.translate(camPos);
		camera.lookAt(0, 0, 0);
	}

	@Override
	public void dispose() {
		//texture.dispose();
	}

	private void cameraSchwenk(){
		winkel+=1;
		float dx = MathUtils.sinDeg(winkel)*abstand;
		float dy = MathUtils.cosDeg(winkel)*abstand;
		Vector3 newCamPos=new Vector3(dx,5*MathUtils.cos(winkel/(40*MathUtils.PI)),dy);
		camera.translate(newCamPos.sub(camera.position));
		camera.lookAt(0, 0, 0);
	}
	
	@Override
	public void render() {		
	    GL10 gl =Gdx.gl10;
	    Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
	    Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
		
		cameraSchwenk();

		camera.update();
	    camera.apply(gl);
	    
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
