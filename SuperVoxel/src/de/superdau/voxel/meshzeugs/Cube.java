package de.superdau.voxel.meshzeugs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class Cube{
	
	private static final float[] vertices = new float[]{
	   -1.0f, -1.0f, 1.0f, Color.toFloatBits(255, 255, 255, 255), 0,0,
        1.0f, -1.0f, 1.0f, Color.toFloatBits(255, 255, 255, 255), 0,1,
       -1.0f,  1.0f, 1.0f, Color.toFloatBits(255, 255, 255, 255), 1,0,
        1.0f,  1.0f, 1.0f, Color.toFloatBits(255, 255, 255, 255), 1,1,

        1.0f, -1.0f,  1.0f, Color.toFloatBits(255, 255, 255, 255), 0,0,
        1.0f, -1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255), 0,1,       
        1.0f,  1.0f,  1.0f, Color.toFloatBits(255, 255, 255, 255), 1,0,
        1.0f,  1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255), 1,1,

        1.0f, -1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255), 0,0,
       -1.0f, -1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255), 0,1,          
        1.0f,  1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255), 1,0,
       -1.0f,  1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255), 1,1,

       -1.0f, -1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255), 0,0,
       -1.0f, -1.0f,  1.0f, Color.toFloatBits(255, 255, 255, 255), 0,1,       
       -1.0f,  1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255), 1,0,
       -1.0f,  1.0f,  1.0f, Color.toFloatBits(255, 255, 255, 255), 1,1,

       -1.0f, -1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255), 0,0,
        1.0f, -1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255), 0,1,       
       -1.0f, -1.0f,  1.0f, Color.toFloatBits(255, 255, 255, 255), 1,0,
        1.0f, -1.0f,  1.0f, Color.toFloatBits(255, 255, 255, 255), 1,1,

       -1.0f,  1.0f,  1.0f, Color.toFloatBits(255, 255, 255, 255), 0,0,
        1.0f,  1.0f,  1.0f, Color.toFloatBits(255, 255, 255, 255), 0,1,         
       -1.0f,  1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255), 1,0,
        1.0f,  1.0f, -1.0f, Color.toFloatBits(255, 255, 255, 255), 1,1
	 };
	
	private static final short[] indices = new short[] { 
		0, 1, 3,  0, 3, 2,
		4, 5, 7,  4, 7, 6,
		8, 9,11,  8,11,10,
		12,13,15, 12,15,14,
		16,17,19, 16,19,18,
		20,21,23, 20,23,22
	};
	
	private static Cube instance = new Cube();
	private static Mesh mesh;

	private Cube() {
		mesh = new Mesh(true, 24, 36, new VertexAttribute(Usage.Position,	3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4,	"a_color"),new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
		mesh.setVertices(Cube.vertices);
		mesh.setIndices(Cube.indices);
	}

	public static Mesh getMesh() {
		return mesh;
	}

	public static Cube getInstance() {
		return instance;
	}

}
