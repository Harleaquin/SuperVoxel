package de.superdau.voxel.meshzeugs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class CubeMask {
	public static final float QubeSize=1;
	private static Mesh[] mesh;
		
	private static final float[] vertices_south = new float[]{
	   -QubeSize/2f, -QubeSize/2f, QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 0,1,
	    QubeSize/2f, -QubeSize/2f, QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 1,1,
	   -QubeSize/2f,  QubeSize/2f, QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 0,0,
	    QubeSize/2f,  QubeSize/2f, QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 1,0
	};

	private static final float[] vertices_east = new float[]{
		QubeSize/2f, -QubeSize/2f,  QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 0,1,
        QubeSize/2f, -QubeSize/2f, -QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 1,1,       
        QubeSize/2f,  QubeSize/2f,  QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 0,0,
        QubeSize/2f,  QubeSize/2f, -QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 1,0
	};

	private static final float[] vertices_north = new float[]{
		QubeSize/2f, -QubeSize/2f, -QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 0,1,
	   -QubeSize/2f, -QubeSize/2f, -QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 1,1,          
	    QubeSize/2f,  QubeSize/2f, -QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 0,0,
	   -QubeSize/2f,  QubeSize/2f, -QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 1,0
	};
	
	private static final float[] vertices_west = new float[]{
		-QubeSize/2f, -QubeSize/2f, -QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 0,1,
	    -QubeSize/2f, -QubeSize/2f,  QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 1,1,       
	    -QubeSize/2f,  QubeSize/2f, -QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 0,0,
	    -QubeSize/2f,  QubeSize/2f,  QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 1,0
	};
	
	private static final float[] vertices_bottom = new float[]{
		-QubeSize/2f, -QubeSize/2f, -QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 0,0,
         QubeSize/2f, -QubeSize/2f, -QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 0,1,       
        -QubeSize/2f, -QubeSize/2f,  QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 1,0,
         QubeSize/2f, -QubeSize/2f,  QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 1,1
	};
	
	private static final float[] vertices_top = new float[]{
		-QubeSize/2f,  QubeSize/2f,  QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 0,0,
         QubeSize/2f,  QubeSize/2f,  QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 0,1,         
        -QubeSize/2f,  QubeSize/2f, -QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 1,0,
         QubeSize/2f,  QubeSize/2f, -QubeSize/2f, Color.toFloatBits(255, 255, 255, 255), 1,1
	};
	
	private static final short[] indices_all = new short[] { 
		0, 1, 3,  0, 3, 2
	};
	
	private static CubeMask instance = new CubeMask();
		
	public static final Mesh getNorth(){
		Mesh mesh = new Mesh(true, vertices_north.length, indices_all.length, new VertexAttribute(Usage.Position,	3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4,	"a_color"),new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
		mesh.setVertices(vertices_north);
		mesh.setIndices(indices_all);
		return mesh;
	}
	
	public static final Mesh getEast(){
		Mesh mesh = new Mesh(true, vertices_east.length, indices_all.length, new VertexAttribute(Usage.Position,	3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4,	"a_color"),new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
		mesh.setVertices(vertices_east);
		mesh.setIndices(indices_all);
		return mesh;
	}
	
	public static final Mesh getSouth(){
		Mesh mesh = new Mesh(true, vertices_south.length, indices_all.length, new VertexAttribute(Usage.Position,	3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4,	"a_color"),new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
		mesh.setVertices(vertices_south);
		mesh.setIndices(indices_all);
		return mesh;
	}
	
	public static final Mesh getWest(){
		Mesh mesh = new Mesh(true, vertices_west.length, indices_all.length, new VertexAttribute(Usage.Position,	3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4,	"a_color"),new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
		mesh.setVertices(vertices_west);
		mesh.setIndices(indices_all);
		return mesh;
	}
	
	public static final Mesh getTop(){
		Mesh mesh = new Mesh(true, vertices_top.length, indices_all.length, new VertexAttribute(Usage.Position,	3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4,	"a_color"),new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
		mesh.setVertices(vertices_top);
		mesh.setIndices(indices_all);
		return mesh;
	}
	
	public static final Mesh getBottom(){
		Mesh mesh = new Mesh(true, vertices_north.length, indices_all.length, new VertexAttribute(Usage.Position,	3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4,	"a_color"),new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
		mesh.setVertices(vertices_bottom);
		mesh.setIndices(indices_all);
		return mesh;
	}
	
	private CubeMask(){
		mesh=new Mesh[64];
		for (byte i=0; i<64; i++) {
			mesh[i]=makeMesh(i);
		}
	}
	
	public static final Mesh getMesh(byte maske){
		return mesh[maske];
	}
	
	private static final Mesh makeMesh(byte maske){
		Mesh mesh=null;
		if ((maske & 1) !=0) mesh=MeshHelper.combineMeshes(mesh, getNorth());
		if ((maske & 2) !=0) mesh=MeshHelper.combineMeshes(mesh, getEast());
		if ((maske & 4) !=0) mesh=MeshHelper.combineMeshes(mesh, getSouth());
		if ((maske & 8) !=0) mesh=MeshHelper.combineMeshes(mesh, getWest());
		if ((maske & 16) !=0) mesh=MeshHelper.combineMeshes(mesh, getTop());
		if ((maske & 32) !=0) mesh=MeshHelper.combineMeshes(mesh, getBottom());
		return mesh;
	}

	public static CubeMask getInstance() {
		return instance;
	}

	public static void setInstance(CubeMask instance) {
		CubeMask.instance = instance;
	}
}
