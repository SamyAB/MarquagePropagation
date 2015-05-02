package dz.oooo.rc;

import java.util.Iterator;
import java.util.LinkedList;

public class Node {
	private String name;
	private LinkedList<Relation> entrants;
	private LinkedList<Relation> sortants;
	private String marqueur;
	
	public Node(String name){
		this.name=name;
		this.entrants=new LinkedList<Relation>();
		this.sortants=new LinkedList<Relation>();
		this.marqueur="";
	}
	
	public Node(String name,LinkedList<Relation> entrants,LinkedList<Relation> sortants){
		this.name=name;
		this.entrants=entrants;
		this.sortants=sortants;
		this.marqueur="";
	}
	
	public void setMarqueur(String marqueur){
		this.marqueur=marqueur;
	}
	public String getMarqueur(){
		return this.marqueur;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LinkedList<Relation> getEntrants() {
		return entrants;
	}
	public void setEntrants(LinkedList<Relation> entrants) {
		this.entrants = entrants;
	}
	public LinkedList<Relation> getSortants() {
		return sortants;
	}
	public void setSortants(LinkedList<Relation> sortants) {
		this.sortants = sortants;
	}
	
	//Méthode qui retourne les noeuds disposant d'une relation is-a entrante
	public LinkedList<Node> getPredIsA(){
		LinkedList<Node> pred=new LinkedList<Node>();
		Iterator<Relation> entrant=this.entrants.iterator();
		while(entrant.hasNext()){
			Relation tmp=entrant.next();
			if(tmp.getType().equals("is-a")){
				pred.add(tmp.getDebut());
			}
		}
		return pred;
	}
	
}
