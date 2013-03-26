package de.superdau.voxel.meshzeugs;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class MeshHelper {
	public static Mesh combineMeshes(Mesh m1, Mesh m2){
		if (m1==null) return m2;
		
		float[] vertices1 = new float[m1.getMaxVertices()]; 
		m1.getVertices(vertices1);
		
		short[] indices1 = new short[m1.getMaxIndices()];
		m1.getIndices(indices1);
		
		float[] vertices2 = new float[m2.getMaxVertices()]; 
		m2.getVertices(vertices2);
		
		short[] indices2 = new short[m2.getMaxIndices()];
		m2.getIndices(indices2);
		
		int newVertexCount = m1.getMaxVertices()+m2.getMaxVertices();
		int newIndexCount = m1.getMaxIndices()+m2.getMaxIndices();
		
		float[] vertices = new float[newVertexCount];
		short[] indices = new short[newIndexCount];
		
		for (int i=0; i<m1.getMaxVertices(); i++) {
			vertices[i]=vertices1[i];
		}
		
		for (int i=0; i<m2.getMaxVertices(); i++) {
			vertices[i+m1.getMaxVertices()]=vertices2[i];
		}
		
		for (int i=0; i<m1.getMaxIndices(); i++) {
			indices[i]=indices1[i];
		}
		
		for (int i=0; i<m2.getMaxIndices(); i++) {
			indices[i+m1.getMaxIndices()]=(short) (((vertices1.length+vertices2.length)/24-1)*4+indices2[i]);
		}
		
		Mesh mesh = new Mesh(true, newVertexCount, newIndexCount, new VertexAttribute(Usage.Position,	3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4,	"a_color"),new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
		mesh.setVertices(vertices);
		mesh.setIndices(indices);

		return mesh;
	}

}
