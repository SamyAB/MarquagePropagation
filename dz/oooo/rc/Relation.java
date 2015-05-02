package dz.oooo.rc;

public class Relation {
	private static int nbRelation=0;
	private int id;
	private String type;
	private Node debut;
	private Node fin;
	
	public Relation(String type,Node debut,Node fin){
		this.type=type;
		this.debut=debut;
		this.fin=fin;
		this.debut.getSortants().add(this);
		this.fin.getEntrants().add(this);
		this.id=nbRelation;
		nbRelation++;
	}
	
	public Relation(String type){
		this(type,null,null);
	}

	public static int getNbRelation(){
		return nbRelation;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Node getDebut() {
		return debut;
	}

	public void setDebut(Node debut) {
		this.debut = debut;
		debut.getSortants().add(this);
	}

	public Node getFin() {
		return fin;
	}

	public void setFin(Node fin) {
		this.fin = fin;
		debut.getEntrants().add(this);
	}
}
