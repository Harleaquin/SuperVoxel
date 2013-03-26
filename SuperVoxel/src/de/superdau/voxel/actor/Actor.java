package de.superdau.voxel.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import de.superdau.voxel.meshzeugs.CubeMask;

public class Actor implements InputProcessor{
	private Camera camera;
	private Vector3 position;
	private Vector3 lookAt;
	
	
	public Actor(){
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		this.camera =new PerspectiveCamera(67, 2f * (w/h), 2f);
	}
	
	public Actor(Vector3 position, Vector3 lookat){
		this();
		this.setPosition(position);
		this.setLookAt(lookat);
		camera.translate(this.getPosition());
		camera.translate(-2,1,-2);
		camera.lookAt(lookat.x, lookat.y, lookat.z);
	}
	
	public Camera getCamera(){
		return camera;
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public Vector3 getLookAt() {
		return lookAt;
	}

	public void setLookAt(Vector3 lookAt) {
		this.lookAt = lookAt;
	}
	
	private void cameraSchwenk(int winkel, int abstand){
		float dx = MathUtils.sinDeg(winkel)*abstand;
		float dy = MathUtils.cosDeg(winkel)*abstand;
		Vector3 newCamPos=new Vector3(dx,5*MathUtils.cos(winkel/(40*MathUtils.PI)),dy);
		camera.translate(newCamPos.sub(camera.position));
		camera.lookAt(0, 0, 0);
	}
	
	public void render(GL10 gl){
		gl.glPushMatrix();
		 gl.glTranslatef(position.x, position.y, position.z);
		 CubeMask.getMesh((byte) 63).render(GL10.GL_TRIANGLES);
		 gl.glTranslatef(-position.x, -position.y, -position.z); 
		gl.glPopMatrix();
	}

	@Override
	public boolean keyDown(int keycode) {
		System.out.println(keycode);
		Vector3 delta=delta=new Vector3(0,0,0);
		if (keycode==51) delta=new Vector3(0,0,1); //rauf
	    if (keycode==47) delta=new Vector3(0,0,-1); //runter
	    if (keycode==29) delta=new Vector3(1,0,0); //links
	    if (keycode==32) delta=new Vector3(-1,0,0); //rechts
	    position.add(delta);
	    camera.translate(delta);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
