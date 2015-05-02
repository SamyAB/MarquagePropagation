package dz.oooo.rc;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

public class Node {
	private String name;
	private LinkedList<Relation> entrants;
	private LinkedList<Relation> sortants;
	private Hashtable<String, Node> isNot;
	private LinkedList<Chemin> cheminsM1;
	private LinkedList<Chemin> cheminsM2;
	private String marqueur;
	
	public Node(String name){
		this.name=name;
		this.entrants=new LinkedList<Relation>();
		this.sortants=new LinkedList<Relation>();
		this.cheminsM1=new LinkedList<Chemin>();
		this.cheminsM2=new LinkedList<Chemin>();
		this.isNot=new Hashtable<String, Node>();
		this.marqueur="";
	}
	
	public Node(String name,LinkedList<Relation> entrants,LinkedList<Relation> sortants){
		this.name=name;
		this.entrants=entrants;
		this.sortants=sortants;
		this.cheminsM1=new LinkedList<Chemin>();
		this.cheminsM2=new LinkedList<Chemin>();
		this.isNot=new Hashtable<String, Node>();
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
	public Hashtable<String, Node> getIsNot(){
		return this.isNot;
	}
	public LinkedList<Chemin> getCheminsM1(){
		return this.cheminsM1;
	}
	public LinkedList<Chemin> getCheminsM2(){
		return this.cheminsM2;
	}
	
	//Méthodes d'ajout d'un chemin a une des listes de chemain selon le marquage
	public void addChemin(Chemin chemin,String marquage){
		if(marquage.contains("M1")){
			this.cheminsM1.add(chemin);
		}
		if(marquage.contains("M2")){
			this.cheminsM2.add(chemin);
		}
	}
	
	//Méthode d'ajout d'un noed à la liste des noeuds is not
	public void addIsNot(Node node){
		this.isNot.put(node.getName(), node);
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
