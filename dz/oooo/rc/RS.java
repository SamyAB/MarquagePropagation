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
	private String reponse;

	//Constructeur
	public RS()
	{
		this.nodes=new Hashtable<String, Node>();
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
		tmp.addChemin(new Chemin(tmp),"M1");
		tmp.setMarqueur(tmp.getMarqueur()+"M1");
	}

	//Méthode de marqauge d'un noeud par le marquage M2
	public void marquageM2(String node){
		Node tmp=nodes.get(node);
		tmp.addChemin(new Chemin(tmp),"M2");
		tmp.setMarqueur(tmp.getMarqueur()+"M2");
	}

	//Méthode d'initialisation de recherche
	public void init(String nodeM1,String nodeM2,String relation,boolean unique){
		marquageM1(nodeM1);
		marquageM2(nodeM2);
		LinkedList<Node> listeM1=new LinkedList<Node>();
		listeM1.add(nodes.get(nodeM1));
		LinkedList<Node> listeM2=new LinkedList<Node>();
		listeM1.add(nodes.get(nodeM2));
		propagation(listeM1,listeM2,relation,unique);
	}

	//Méthode de propagation de marquage
	public void propagation(LinkedList<Node> m1EtapePrec,LinkedList<Node> m2EtapePrec,String relation,boolean unique){
		if(unique){
			reponse=rechercheUnique(relation);
		}
		else{
			recherche(relation);
		}
		if(unique && !reponse.equals("")){
			System.out.println("Réponse trouvée : "+reponse);
		}
		else{
			LinkedList<Node> m1EtapeCour=new LinkedList<Node>();
			if(!m1EtapePrec.isEmpty()){
				Iterator<Node> nodesM1=m1EtapePrec.iterator();
				while(nodesM1.hasNext()){
					//Pour chacun des noeuds marqués M1 dans l'étape précédante
					Node tmp=nodesM1.next();
					Iterator<Node> preds=tmp.getPredIsA().iterator();
					while(preds.hasNext()){
						//Pour chacun des prédecesseurs is-a des noeuds marqués M1 dans l'étape précédante
						Node aMarque=preds.next();
						Iterator<Chemin> chemins=tmp.getCheminsM1().iterator();
						while(chemins.hasNext()){
							//Et pour chacun des cheminsM1 qui ont menés au marquage du noeud tmp
							Chemin tmpChemin=chemins.next();
							if(tmpChemin.propagable(aMarque.getIsNot())){
								
								if(!tmpChemin.getNodes().containsKey(aMarque.getName())){
									//Pour éviter les boucles réccurcives infinies
									Chemin newChemin=new Chemin();
									newChemin.getNodes().putAll(tmpChemin.getNodes());
									newChemin.addNode(aMarque);
									aMarque.addChemin(newChemin, "M1");
									if(!aMarque.getMarqueur().contains("M1")){
										aMarque.setMarqueur(aMarque.getMarqueur()+"M1");
									}
									m1EtapeCour.add(aMarque);
								}
							}
						}
					}
				}
			}
			LinkedList<Node> m2EtapeCour=new LinkedList<Node>();
			if(!m2EtapePrec.isEmpty()){
				Iterator<Node> nodesM2=m2EtapePrec.iterator();
				while(nodesM2.hasNext()){
					//Pour chacun des noeuds marqués M2 dans l'étape précédante
					Node tmp=nodesM2.next();
					Iterator<Node> preds=tmp.getPredIsA().iterator();
					while(preds.hasNext()){
						//Pour chacun des prédecesseurs is-a des noeuds marqués M2 dans l'étape précédante
						Node aMarque=preds.next();
						Iterator<Chemin> chemins=tmp.getCheminsM2().iterator();
						while(chemins.hasNext()){
							//Et pour chacun des cheminsM2 qui ont menés au marquage du noeud tmp
							Chemin tmpChemin=chemins.next();
							if(tmpChemin.propagable(aMarque.getIsNot())){
								if(!tmpChemin.getNodes().containsKey(aMarque.getName())){
									//Pour éviter les boucles réccurcives infinies
									Chemin newChemin=new Chemin();
									newChemin.getNodes().putAll(tmpChemin.getNodes());
									newChemin.addNode(aMarque);
									aMarque.addChemin(newChemin, "M2");
									if(!aMarque.getMarqueur().contains("M2")){
										aMarque.setMarqueur(aMarque.getMarqueur()+"M2");
									}
									m2EtapeCour.add(aMarque);
								}
							}
						}
					}
				}
			}
			if(m1EtapeCour.isEmpty()&&m2EtapeCour.isEmpty()){
				if(this.reponse.equals("")){
					System.out.println("Aucune solution trouvée");
				}
				else{
					System.out.println("Les réponses sont : "+this.reponse);
				}
			}
			else{
				propagation(m1EtapeCour, m2EtapeCour, relation, unique);
			}
		}
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
	
	//Méthode d'ajout d'excpetions
	public void addExceptionLink(Node depart,String typeRelationCible,Node departDeRelationCible,Node finDeRelationCible){
		if(typeRelationCible.equals("is-a")){
			depart.addIsNot(finDeRelationCible);
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
		
		//Création des liens d'exceptions
		System.out.println("Donnez les relations d'exception du RS et terminez par noeud de départ 0");
		System.out.println("Donnes le noued de départ de la relation d'exception");
		String exNode=sc.nextLine();
		while(!exNode.equals("0")){
			System.out.println("Donnez le type de la relation cible de la relation d'exception");
			String relationCible=sc.nextLine();
			System.out.println("Donnez le nom du noeud source de la relation cible");
			String debut=sc.nextLine();
			System.out.println("Donnez le nom du noeud fin de la relation cible");
			String fin=sc.nextLine();
			addExceptionLink(this.nodes.get(exNode), relationCible, this.nodes.get(debut), this.nodes.get(fin));
			System.out.println("relation crée");
			System.out.println("Donnes le noued de départ de la relation d'exception");
			exNode=sc.nextLine();
		}
		
		sc.close();
	}

	//Lecture à partir d'un fichier d'un RS
	public void fichier(String nomFichier) {
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(nomFichier)));
			String ligne=br.readLine();
			while(ligne!=null){
				String[] mots=ligne.split(",");
				if(mots[0].equals("node")){
					this.nodes.put(mots[1], new Node(mots[1]));
				}
				else if(mots[0].equals("relation")){
					new Relation(mots[1],this.nodes.get(mots[2]),this.nodes.get(mots[3]));
				}
				else if(mots[0].equals("exception")){
					addExceptionLink(this.nodes.get(mots[1]), mots[2], this.nodes.get(mots[3]), this.nodes.get(mots[4]));
				}
				ligne=br.readLine();
			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}

