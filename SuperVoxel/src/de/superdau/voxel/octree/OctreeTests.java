package de.superdau.voxel.octree;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector3;

import de.superdau.voxel.meshzeugs.CubeMask;
import de.superdau.voxel.textur.TextureHelper;

public class OctreeTests {
	private OctreeNodeInterface rootNode;
	private int MAXDEPTH;

	private static TextureHelper th;
	
	private int maxDepth;
	private double maxAchse;
	private double maxNodes;
	
	
	public OctreeTests(){
		this.MAXDEPTH=5;
		this.rootNode=this.flatlandOctree();
		System.out.println("asdf");
		//treeWalk(rootNode);
		th = new TextureHelper();
		CubeMask.getInstance();
	}
	
	public OctreeTests(int maxdepth){
	 this.MAXDEPTH=maxdepth;
	 rootNode=recOctreeFull(0);
	 maxDepth=getMaxDepth(rootNode);
	 maxNodes=Math.pow(8, maxDepth);
	 maxAchse = Math.pow(2, maxDepth);
	 System.out.println("\nMaximale Tiefe: "+maxDepth+" Maximale Voxel: "+maxNodes+" Max pro Achse: "+maxAchse);
	
	 System.out.println("Treewalk:");
	 treeWalk(rootNode);
	 th = new TextureHelper();
	 CubeMask.getInstance();
	}
	
	public void removeHiddenMeshes(){
		maxDepth=getMaxDepth(rootNode);
		maxNodes=Math.pow(8, maxDepth);
		maxAchse = Math.pow(2, maxDepth);
		this.removeHiddenMeshes(rootNode);
	}
	
	private void removeHiddenMeshes(OctreeNodeInterface node){
		if (node.isLeaf()) {
			if (node.isEmpty()==false) {
				int maske=63;
				
				Vector3 vg=node.globalVector();
				if (vg.x<maxAchse-1 && this.getNodeAt(rootNode, vg.x+1, vg.y, vg.z).isEmpty()==false) maske-=2;
				if (vg.x>0 && this.getNodeAt(rootNode, vg.x-1, vg.y, vg.z).isEmpty()==false) maske-=8;
				
				if (vg.y<maxAchse-1 && this.getNodeAt(rootNode, vg.x, vg.y+1, vg.z).isEmpty()==false) maske-=16;
				if (vg.y>0 && this.getNodeAt(rootNode, vg.x, vg.y-1, vg.z).isEmpty()==false) maske-=32;
				
				if (vg.z<maxAchse-1 && this.getNodeAt(rootNode, vg.x, vg.y, vg.z+1).isEmpty()==false) maske-=4;
				if (vg.z>0 && this.getNodeAt(rootNode, vg.x, vg.y, vg.z-1).isEmpty()==false) maske-=1;
				
				node.setBitmask(maske);
			}
		}
		else {
			for (OctreeNodeInterface child:node.getChildNodes()) {
				removeHiddenMeshes(child);
			}
		}
	}
	
	public void render(GL10 gl){
		renderWalk(rootNode,gl);
	}
	
	public void renderWalk(OctreeNodeInterface node, GL10 gl){
		if (node.isLeaf()) {
			if (node.isEmpty()==false && node.getBitmask()>0) {
				Mesh mesh=CubeMask.getMesh((byte) (node.getBitmask()));
				//System.out.println("bitmask: "+node.getBitmask());
				mesh.bind();
				
				Vector3 vec = node.globalVector();
				
				gl.glTranslatef(vec.x, vec.y, vec.z);
				gl.glPushMatrix();
			     th.getTexture(node.getTextur()).bind();
				 mesh.render(GL10.GL_TRIANGLES); 
				gl.glPopMatrix();
				gl.glTranslatef(-vec.x, -vec.y, -vec.z);
			}
		}
		else {
			for (OctreeNodeInterface child:node.getChildNodes()){
				renderWalk(child,gl);
			}
		}
	}
	
	public void treeWalk(OctreeNodeInterface node){
		System.out.print(".");
		if (node.isLeaf()) {
			if (node.isEmpty()==false) System.out.println(node.toString());
		}
		else {
			for (OctreeNodeInterface child:node.getChildNodes()){
				System.out.print("f");
				treeWalk(child);
			}
		}
	}
	
	public OctreeNodeInterface getNodeAt(OctreeNodeInterface node, double x, double y, double z){
		int dx=1,dy=1,dz=1;
		double maxDepth=Math.pow(2, getMaxDepth(node)-node.getDepth());
		if (x<maxDepth/2) dx=0;
		if (y<maxDepth/2) dy=0;
		if (z<maxDepth/2) dz=0;
		OctreeNodeInterface nextNode=node.getChild(dx, dy, dz);
		if (nextNode.isLeaf()) return nextNode;
		else return getNodeAt(nextNode,x-dx*maxDepth/2,y-dy*maxDepth/2,z-dz*maxDepth/2);
	}
	
	public OctreeNodeInterface getRootNode(){
		return rootNode;
	}
	
	public int getMaxDepth(){
		return getMaxDepth(rootNode);
	}
	
	private int getMaxDepth(OctreeNodeInterface node){
		OctreeNodeInterface tempNode=node;
		int depth;
		if (tempNode.isLeaf()) return tempNode.getDepth();
		else {
			depth=tempNode.getDepth();
			for (OctreeNodeInterface childNode:node.getChildNodes()){
				int testDepth=getMaxDepth(childNode);
				if (testDepth>depth) depth=testDepth;
			}
		}
		return depth;
	}
	
	public OctreeNodeInterface recOctreeFull(int depth){
		OctreeNodeInterface tempNode = new OctreeNode(depth);
		if (depth>=MAXDEPTH) {
		 tempNode.setBitmask(63);
		 tempNode.setTextur((int) (Math.random()*4));
		 System.out.print("-");
		}
		else {
		 List<OctreeNodeInterface> children=new LinkedList<OctreeNodeInterface>();	
		 children.add(recOctreeFull(depth+1));
		 children.add(recOctreeFull(depth+1));
		 children.add(recOctreeFull(depth+1));
		 children.add(recOctreeFull(depth+1));
		 children.add(recOctreeFull(depth+1));
		 children.add(recOctreeFull(depth+1));
		 children.add(recOctreeFull(depth+1));
		 children.add(recOctreeFull(depth+1));
		 
		 tempNode.setChildNodes(children);
		}
		 return tempNode;
	}
	
	public OctreeNodeInterface recOctreeRandom(int depth){
		OctreeNodeInterface tempNode = new OctreeNode(depth);
		List<OctreeNodeInterface> children=new LinkedList<OctreeNodeInterface>();	
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 tempNode.setChildNodes(children);
		return tempNode;
	}
	
	public OctreeNodeInterface recOctreeRandom2(int depth){
		OctreeNodeInterface tempNode = new OctreeNode(depth);
		if ((Math.random()*100)>50) {
		 tempNode.setEmpty(true);
		 tempNode.setLeaf(true);
		}
		else if (depth>=MAXDEPTH) {
			tempNode.setBitmask(63);	
		}
		else {
		 List<OctreeNodeInterface> children=new LinkedList<OctreeNodeInterface>();	
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 tempNode.setChildNodes(children);
		}
		 return tempNode;
	}
	
	public OctreeNodeInterface emptyNode(int depth){
		OctreeNodeInterface tempNode = new OctreeNode(0);
		tempNode.setEmpty(true);
		tempNode.setDepth(depth);
		tempNode.setLeaf(true);
		return tempNode;
	}
	
	public OctreeNodeInterface flatlandOctree(){
		OctreeNodeInterface tempNode = new OctreeNode(0);
		List<OctreeNodeInterface> children=new LinkedList<OctreeNodeInterface>();	
		
		children.add(recOctreeFull(1));
		children.add(recOctreeFull(1));
		children.add(emptyNode(1));
		children.add(emptyNode(1));
		children.add(recOctreeFull(1));
		children.add(recOctreeFull(1));
		children.add(emptyNode(1));
		children.add(emptyNode(1));
		 
		 tempNode.setChildNodes(children);
		return tempNode;
	}
	
}