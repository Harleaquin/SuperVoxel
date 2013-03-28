package de.superdau.voxel.octree;

import java.util.List;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class OctreeNode implements OctreeNodeInterface{

	private boolean empty;
    private boolean leaf;
    private int depth;
    private static int maxDepth;
    
    private List<OctreeNodeInterface> childNodes;
    private List<OctreeNodeInterface> sisterNodes;
    private OctreeNodeInterface mother;
    
    private int bitmask;
    private int textur;
    
    private Vector3 globalOrigin;
    
    public OctreeNode(int depth){
    	this.mother=null;
    	this.empty=true;
    	this.leaf=true;
    	this.depth=depth;
    	if (this.depth>maxDepth) maxDepth=depth;
    }
    
	@Override
	public boolean isEmpty() {
		return empty;
	}
	@Override
	public void setEmpty(boolean empty) {
		this.empty=empty;
	}
	@Override
	public boolean isLeaf() {
		return leaf;
	}
	@Override
	public void setLeaf(boolean leaf) {
		this.leaf=leaf;
	}

	@Override
	public List<OctreeNodeInterface> getChildNodes() {
		return childNodes;
	}

	@Override
	public List<OctreeNodeInterface> getSisterNodes() {
		return sisterNodes;
	}

	@Override
	public void setChildNodes(List<OctreeNodeInterface> children) {
		this.setEmpty(false);
		this.setLeaf(false);
		this.childNodes=children;
		for (OctreeNodeInterface child:childNodes){
			child.setMother(this);
			child.setSisterNodes(childNodes);
		}
	}

	@Override
	public void setSisterNodes(	List<OctreeNodeInterface> sisters) {
		this.sisterNodes=sisters;
	}

	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public void setDepth(int depth) {
		this.depth=depth;
	}

	@Override
	public int getBitmask() {
		return bitmask;
	}

	@Override
	public void setBitmask(int value) {
		this.bitmask=value;
		this.setEmpty(false);
		this.setLeaf(true);
		//System.out.println("Value: "+this.bitmask+" Depth: "+this.depth);
	}

	@Override
	public OctreeNodeInterface getChild(int x,	int y,	int z) {
		int index = x*4+y*2+z*1;
		return this.childNodes.get(index);
	}
	
	@Override
	public OctreeNodeInterface getSister(int x,	int y, int z) {
		int index = x*4+y*2+z*1;
		return this.sisterNodes.get(index);
	}


	@Override
	public OctreeNodeInterface getMother() {
		return mother;
	}

	@Override
	public void setMother(OctreeNodeInterface mother) {
		this.mother=mother;
	}
	
	public int neighborIndex(){
		return this.getSisterNodes().indexOf(this);
	}
	
	@Override
	public Vector3 localVector(){
		Vector3 vec = new Vector3(0,0,0);
		int index = this.neighborIndex();
		if ((index & 1) !=0) vec.z=1;
		if ((index & 2) !=0) vec.y=1;
		if ((index & 4) !=0) vec.x=1;
		return vec;
	}
	
	@Override
	public Vector3 globalVector(){
		return globalVectorIntern();
	}
	
	private Vector3 globalVectorIntern(){
		if (globalOrigin!=null) {
			return this.globalOrigin;
		}
		else {
			OctreeNodeInterface node = this;
			Vector3 vec = new Vector3(0,0,0);
			int multi=1; 
			while (node.getDepth()>0){
				Vector3 newVec = node.localVector().mul(multi);
				vec.add(newVec);
				multi*=2;
				node=node.getMother();
			}
			this.globalOrigin=vec.cpy();
			return this.globalOrigin;
		}
	}
	
	@Override
	public String toString(){
		String info = "Node Info:";
		info+="\n\tisLeaf: "+this.isLeaf();
		info+="\n\tisEmpty: "+this.isEmpty();
		info+="\n\thasValue: "+this.getBitmask();
		info+="\n\tTiefe: "+this.getBitmask();
		info+="\n\tindexOfNeigbours: "+this.neighborIndex();
		info+="\n\tlocalVector3: "+this.localVector();
		info+="\n\tglobalVector3: "+this.globalVector();
		return info;
	}

	@Override
	public int getTextur() {
		return this.textur;
	}

	@Override
	public void setTextur(int value) {
		this.textur=value;
	}

	@Override
	public int getMaxDepth() {
		return OctreeNode.maxDepth;
	}

	@Override
	public BoundingBox getBoundingBox() {
		Vector3 min = globalVector();
		double size = Math.pow(2, (this.maxDepth-this.depth));
		Vector3 max = min.cpy().add(1, 1, 1).mul((float) size);
		return new BoundingBox(min,max);
	}


}
