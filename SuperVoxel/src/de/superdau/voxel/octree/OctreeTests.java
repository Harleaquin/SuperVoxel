package de.superdau.voxel.octree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector3;

import de.superdau.voxel.actor.Actor;
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
		this.MAXDEPTH=8;
		System.out.println("filling");
		this.rootNode=this.flatlandOctree();
		System.out.println("\nfinished filling");
		//treeWalk(rootNode);
		th = new TextureHelper();
		CubeMask.getInstance();
		getNodeAt(rootNode,16,15,16).setEmpty(true);
	}
	
	public OctreeTests(int maxdepth){
	 this.MAXDEPTH=maxdepth;
	 rootNode=recOctreeFull(0);
	 maxDepth=getMaxDepth();
	 maxNodes=Math.pow(8, maxDepth);
	 maxAchse = Math.pow(2, maxDepth);
	 System.out.println("\nMaximale Tiefe: "+maxDepth+" Maximale Voxel: "+maxNodes+" Max pro Achse: "+maxAchse);
	
	 System.out.println("Treewalk:");
	 treeWalk(rootNode);
	 th = new TextureHelper();
	 CubeMask.getInstance();
	}
	
	public void removeHiddenMeshes(){
		maxDepth=getMaxDepth();
		maxNodes=Math.pow(8, maxDepth);
		maxAchse = Math.pow(2, maxDepth);
		this.removeHiddenMeshes(rootNode);
	}
	
	public void removeHiddenMeshesSimple(){
		removeHiddenMeshesSimple(rootNode);
	}
	
	private void removeHiddenMeshesSimple(OctreeNodeInterface node){
		if (node.isLeaf()) {
			if (node.isEmpty()==false) {
				Vector3 v=node.localVector();
				int maske=63;
				if (v.x==0 && node.getSister(1, (int) v.y, (int) v.z).isEmpty()==false) maske-=2;
				if (v.x==1 && node.getSister(0, (int) v.y, (int) v.z).isEmpty()==false) maske-=8;
				if (v.z==0 && node.getSister((int) v.x, (int) v.y, 1).isEmpty()==false) maske-=4;
				if (v.z==1 && node.getSister((int) v.x, (int) v.y, 0).isEmpty()==false) maske-=1;
				if (v.y==0 && node.getSister((int) v.x, 1, (int) v.z).isEmpty()==false) maske-=16;
				if (v.y==1 && node.getSister((int) v.x, 0, (int) v.z).isEmpty()==false) maske-=32;
				node.setBitmask(maske);
			}
			else {
				for (OctreeNodeInterface child:node.getChildNodes()) {
					removeHiddenMeshesSimple(child);
				}
			}
		}
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
	
	public void render3Depth(GL10 gl, Actor actor){
		
	}
	
	public void renderFaster(GL10 gl, Actor actor){
		Vector3 v = actor.getPosition();
		OctreeNodeInterface startingnode=this.getNodeAt(rootNode, v.x, v.y-1, v.z);
		
		Vector3 pivot = new Vector3(v.x, v.y-1, v.z);
	while(actor.getFrustum().sphereInFrustum(pivot, 1));
		renderNeighbours(startingnode,gl,actor,pivot);
		OctreeNodeInterface mother= startingnode.getMother();
		Vector3 m = mother.localVector();
		///baustelle
	}
	
	public void renderNeighbours(OctreeNodeInterface node, GL10 gl, Actor actor, Vector3 pivot){
		Vector3 local=node.localVector();
		for (int x=0;x<=1;x++)
			for (int y=0;y<=1;y++)
				for (int z=0;z<=1;z++) {
					OctreeNodeInterface sister=node.getSister(x, y, z);
					Vector3 r = new Vector3(pivot.x+x-local.x,pivot.y+y-local.y,pivot.z+z-local.z);
					
					if (sister.isEmpty()==false && sister.getBitmask()>0) {
					Mesh mesh=CubeMask.getMesh((byte) (sister.getBitmask()));
					gl.glTranslatef(r.x,r.y,r.z);
					gl.glPushMatrix();
						th.getTexture(sister.getTextur()).bind();
						mesh.render(GL10.GL_TRIANGLES); 
					gl.glPopMatrix();
					gl.glTranslatef(-r.x,-r.y,-r.z);
					System.out.println("rendering noda at: "+r.toString());
					}
				}
		
	}
	
	public void render(GL10 gl, Actor actor){
		renderWalk(rootNode,gl,actor);
	}
	
	public void renderWalk(OctreeNodeInterface node, GL10 gl, Actor actor){
		if (node.isLeaf()) {
			if (node.isEmpty()==false && node.getBitmask()>0) {
				Vector3 vec = new Vector3(node.globalVector());
				if (actor.getFrustum().sphereInFrustum(vec, 1)) {
					Mesh mesh=CubeMask.getMesh((byte) (node.getBitmask()));
					mesh.bind();
					gl.glTranslatef(vec.x, vec.y, vec.z);
					gl.glPushMatrix();
						th.getTexture(node.getTextur()).bind();
						mesh.render(GL10.GL_TRIANGLES); 
					gl.glPopMatrix();
					gl.glTranslatef(-vec.x, -vec.y, -vec.z);
				}
			}
		}
		else {
			for (OctreeNodeInterface child:node.getChildNodes()){
				renderWalk(child,gl,actor);
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
		double maxDepth=Math.pow(2, getMaxDepth()-node.getDepth());
		System.out.println("lala: "+maxDepth);
		if (x<maxDepth/2) dx=0;
		if (y<maxDepth/2) dy=0;
		if (z<maxDepth/2) dz=0;
		OctreeNodeInterface nextNode=node.getChild(dx, dy, dz);
		System.out.println("lala: "+nextNode.toString());
		if (nextNode.isLeaf()) return nextNode;
		else return getNodeAt(nextNode,x-dx*maxDepth/2,y-dy*maxDepth/2,z-dz*maxDepth/2);
	}
	
	
	public OctreeNodeInterface getRootNode(){
		return rootNode;
	}
	
	public int getMaxDepth(){
		return rootNode.getMaxDepth();
	}
	

	
	public OctreeNodeInterface recOctreeFull(int depth){
		OctreeNodeInterface tempNode = new OctreeNode(depth);
		if (depth>=MAXDEPTH) {
		 tempNode.setBitmask(63);
		 tempNode.setTextur((int) (Math.random()*3)+1);
		 //System.out.print("-");
		}
		else {
		 List<OctreeNodeInterface> children=new ArrayList<OctreeNodeInterface>();	
		 children.add(recOctreeFull(depth+1));
		 children.add(recOctreeFull(depth+1));
		 children.add(recOctreeFull(depth+1));
		 children.add(recOctreeFull(depth+1));
		 children.add(recOctreeFull(depth+1));
		 children.add(recOctreeFull(depth+1));
		 children.add(recOctreeFull(depth+1));
		 children.add(recOctreeFull(depth+1));
		 ((ArrayList<OctreeNodeInterface>) children).trimToSize();
		 tempNode.setChildNodes(children);
		}
		 return tempNode;
	}
	
	public OctreeNodeInterface recOctreeRandom(int depth){
		OctreeNodeInterface tempNode = new OctreeNode(depth);
		List<OctreeNodeInterface> children=new ArrayList<OctreeNodeInterface>();	
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 ((ArrayList<OctreeNodeInterface>) children).trimToSize();
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
		 List<OctreeNodeInterface> children=new ArrayList<OctreeNodeInterface>();	
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 children.add(recOctreeRandom2(depth+1));
		 ((ArrayList<OctreeNodeInterface>) children).trimToSize();
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
		List<OctreeNodeInterface> children=new ArrayList<OctreeNodeInterface>();		
		
		children.add(recOctreeFull(1));
		children.add(recOctreeFull(1));
		children.add(emptyNode(1));
		children.add(emptyNode(1));
		children.add(recOctreeFull(1));
		children.add(recOctreeFull(1));
		children.add(emptyNode(1));
		children.add(emptyNode(1));
		((ArrayList<OctreeNodeInterface>) children).trimToSize();
		tempNode.setChildNodes(children);
		return tempNode;
	}
	
}