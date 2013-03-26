package de.superdau.voxel.octree;

import java.util.List;

import com.badlogic.gdx.math.Vector3;

public interface OctreeNodeInterface {
	public boolean isEmpty();
	public void setEmpty(boolean empty);
	public boolean isLeaf();
	public void setLeaf(boolean leaf);
	public List<OctreeNodeInterface> getChildNodes();
	public List<OctreeNodeInterface> getSisterNodes();
	public void setChildNodes(List<OctreeNodeInterface> children);
	public void setSisterNodes(List<OctreeNodeInterface> sisters);
	public OctreeNodeInterface getMother();
	public void setMother(OctreeNodeInterface mother);
	public OctreeNodeInterface getChild(int x, int y, int z);
	public OctreeNodeInterface getSister(int x, int y, int z);
	public Vector3 localVector();
	public Vector3 globalVector();
	
	public int getDepth();
	public void setDepth(int depth);
	public int getBitmask();
	public void setBitmask(int value);
	
	public int getTextur();
	public void setTextur(int value);
	
}
