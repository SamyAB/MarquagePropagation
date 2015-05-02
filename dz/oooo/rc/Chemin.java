package dz.oooo.rc;

import java.util.Enumeration;
import java.util.Hashtable;

public class Chemin {
	private Hashtable<String, Node> nodes;
	
	public Chemin(){
		this.nodes=new Hashtable<String, Node>();
	}
	
	public Chemin(Node node){
		this.nodes=new Hashtable<String, Node>();
		this.nodes.put(node.getName(),node);
	}
	
	public Hashtable<String, Node> getNodes(){
		return this.nodes;
	}
	
	public void addNode(Node node){
		this.nodes.put(node.getName(), node);
	}

	public boolean propagable(Hashtable<String, Node> notIn){
		Enumeration<Node> n=this.nodes.elements();
		while(n.hasMoreElements()){
			Node tmp=n.nextElement();
			if(notIn.containsKey(tmp.getName())){
				return false;
			}
		}
		return true;
	}
	
	public String toString(){
		String retour="";
		Enumeration<Node> nodes=this.nodes.elements();
		while(nodes.hasMoreElements()){
			retour+=nodes.nextElement().getName();
		}
		return retour;
	}
	
}
