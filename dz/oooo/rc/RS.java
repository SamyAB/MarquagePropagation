package dz.oooo.rc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class RS {
	private Hashtable<String, Node> nodes;
	private Hashtable<String, Node> parcouruM1;
	private Hashtable<String, Node> parcouruM2;
	private String reponse;

	//Constructeur
	public RS()
	{
		this.nodes=new Hashtable<String, Node>();
		this.parcouruM1=new Hashtable<String, Node>();
		this.parcouruM2=new Hashtable<String, Node>();
		this.reponse="";
	}

	//Getters et setters
	public Hashtable<String,Node> getNodes(){
		return nodes;
	}
	public String getReponse(){
		return this.reponse;
	}

	//Méthode de marquage d'un noeud par le marquage M1
	public void marquageM1(String node){
		Node tmp=nodes.get(node);
		tmp.setMarqueur(tmp.getMarqueur()+"M1");
	}

	//Méthode de marqauge d'un noeud par le marquage M2
	public void marquageM2(String node){
		Node tmp=nodes.get(node);
		tmp.setMarqueur(tmp.getMarqueur()+"M2");
	}

	//Méthode d'initialisation de recherche
	public void init(String nodeM1,String nodeM2,String relation,boolean unique){
		marquageM1(nodeM1);
		marquageM2(nodeM2);
		this.parcouruM1.put(nodeM1, nodes.get(nodeM1));
		this.parcouruM2.put(nodeM2, nodes.get(nodeM2));
		propagation(nodes.get(nodeM1).getPredIsA(),nodes.get(nodeM2).getPredIsA(),relation,unique);
	}

	//Méthode de propagation
	public void propagation(LinkedList<Node> nodesM1,LinkedList<Node> nodesM2,String relation,boolean unique){
		if(unique){
			reponse=rechercheUnique(relation);

		}
		else{
			recherche(relation);
		}

		if(unique&&(!reponse.equals(""))){
			System.out.println("Trouvé: "+reponse);
		}
		else{
			LinkedList<Node> futureM1=new LinkedList<Node>();
			if(!nodesM1.isEmpty()){
				Iterator<Node> m1=nodesM1.iterator();
				while(m1.hasNext()){
					Node tmpM1=m1.next();
					futureM1.addAll(suppressDouble(tmpM1.getPredIsA(),"M1"));
				}
			}
			LinkedList<Node> futureM2=new LinkedList<Node>();
			if(!nodesM2.isEmpty()){
				Iterator<Node> m2=nodesM2.iterator();
				while(m2.hasNext()){
					Node tmpM2=m2.next();
					futureM2.addAll(suppressDouble(tmpM2.getPredIsA(),"M2"));
				}
			}
			if(!(futureM1.isEmpty()&&futureM2.isEmpty())){
				propagation(futureM1, futureM2, relation, unique);
			}
			else{
				if(unique){
					reponse=rechercheUnique(relation);
				}
				else{
					recherche(relation);
				}
				if(reponse.equals("")){
					System.out.println("pas de réponse");
				}
				else{
					System.out.println("Réponse(s) trouvée(s): "+reponse);
				}
			}
		}
	}

	//Méthode de suppression des doublons
	public LinkedList<Node> suppressDouble(LinkedList<Node> nodes,String marque){
		LinkedList<Node> liste=new LinkedList<Node>();
		Iterator<Node> i=nodes.iterator();
		while(i.hasNext()){
			Node node=i.next();
			if(marque.equals("M1")){	
				if(!this.parcouruM1.containsKey(node.getName())){
					liste.add(node);
					this.parcouruM1.put(node.getName(), node);
					node.setMarqueur(node.getMarqueur()+"M1");
				}
			}
			else{
				if(!this.parcouruM2.containsKey(node.getName())){
					liste.add(node);
					this.parcouruM2.put(node.getName(), node);
					node.setMarqueur(node.getMarqueur()+"M2");
				}
			}
		}
		return liste;
	}

	//Méthode de recherche multiple
	public void recherche(String relation){
		Enumeration<Node> nodes=this.nodes.elements();
		while(nodes.hasMoreElements()){
			Node tmpNode=nodes.nextElement();
			if(tmpNode.getMarqueur().contains("M1")){
				Iterator<Relation> i=tmpNode.getSortants().listIterator();
				while(i.hasNext()){
					Relation tmpRelation=i.next();
					Node tmpNode2=this.nodes.get(tmpRelation.getFin().getName());
					if(tmpRelation.getType().equals(relation)&&tmpNode2.getMarqueur().contains("M2")){
						String r=tmpNode.getName()+" "+relation+" "+tmpNode2.getName()+"\n";
						if(!reponse.contains(r)){
							reponse+=r;
						}
					}
				}
			}
		}
	}

	//Méthode de recherche de réponse unique
	public String rechercheUnique(String relation){
		Enumeration<Node> nodes=this.nodes.elements();
		while(nodes.hasMoreElements()){
			Node tmpNode=nodes.nextElement();
			if(tmpNode.getMarqueur().contains("M1")){
				Iterator<Relation> i=tmpNode.getSortants().listIterator();
				while(i.hasNext()){
					Relation tmpRelation=i.next();
					Node tmpNode2=this.nodes.get(tmpRelation.getFin().getName());
					if(tmpRelation.getType().equals(relation)&&tmpNode2.getMarqueur().contains("M2")){
						return tmpNode.getName()+" "+relation+" "+tmpNode2.getName();
					}
				}
			}
		}
		return "";
	}

	//Méthode qui remet les marqueur de tout les noeuds du réseaux sématantique à vide
	public void resetMarquage(){
		Enumeration<Node> nodes=this.nodes.elements();
		while(nodes.hasMoreElements()){
			nodes.nextElement().setMarqueur("");
		}
		this.reponse="";
	}

	//Méthode affichant marqueage de chaque noeud du RS
	public void afficherMarquage(){
		Enumeration<Node> nodes=this.nodes.elements();
		while(nodes.hasMoreElements()){
			Node tmp=nodes.nextElement();
			System.out.println(tmp.getName()+" -> "+tmp.getMarqueur());
		}
	}
	
	//Méthode de création manuelle de réseaux sémantiques
	public void manual(){
		//Creátion de nodes
		Scanner sc=new Scanner(System.in);
		System.out.println("Veuillez entres les noms des noeuds de votre réseau sémantique, et terminez par 0");
		String node=sc.nextLine();
		while(!node.equals("0")){
			this.nodes.put(node, new Node(node));
			System.out.println("Noeud créé");
			node=sc.nextLine();
		}
		if(this.nodes.isEmpty()){
			System.err.println("Le RS est vide!");
		}
		
		//Création des relations
		System.out.println("Donnez les relations, en terminant par le type 0");
		System.out.println("Donnez le type de la relation");
		String typeR=sc.nextLine();
		while(!typeR.equals("0")){
			System.out.println("Donnez le nom du noeud début de la relation");
			String debut=sc.nextLine();
			System.out.println("Donnez le nom du neuod fin de la relation");
			String fin=sc.nextLine();
			new Relation(typeR,this.nodes.get(debut),this.nodes.get(fin));
			System.out.println("Relation créée");
			System.out.println("Donnez le type de la relation suivante (pour terminer enrez 0)");
			typeR=sc.nextLine();
		}
		
		sc.close();
	}

	//Lecture à partir d'un fichier d'un RS
	public void fichier(String nomFichier) {
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("nomFichier")));
			String ligne=br.readLine();
			while(ligne!=null){
				String[] mots=ligne.split(",");
				if(mots[0].equals("node")){
					this.nodes.put(mots[1], new Node(mots[1]));
				}
				else if(mots[0].equals("relation")){
					new Relation(mots[1],this.nodes.get(mots[2]),this.nodes.get(mots[3]));
				}
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}

