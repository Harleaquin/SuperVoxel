package de.superdau.voxel.textur;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class TextureHelper {
	private List<Texture> texturList;
	
	public TextureHelper(){
		texturList=new ArrayList<Texture>();
		texturList.add(new Texture(Gdx.files.internal("data/badlogic.jpg"),true));
		texturList.add(new Texture(Gdx.files.internal("data/pflaster.jpg"),true));
		texturList.add(new Texture(Gdx.files.internal("data/eis.jpg"),true));
		texturList.add(new Texture(Gdx.files.internal("data/frame.jpg"),true));
		texturList.add(new Texture(Gdx.files.internal("data/frame.jpg"),true));
		for (Texture texture:texturList) texture.setFilter(TextureFilter.MipMap, TextureFilter.MipMap);
	}
	
	public Texture getTexture(int color){
		return texturList.get(color);
	}
	
	public void dispose(){
		for (Texture texture:texturList) texture.dispose();
	}

}
