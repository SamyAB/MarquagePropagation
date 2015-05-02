package dz.oooo.rc;

import java.util.Scanner;

public class ClasseMain {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		
		//Choix du type de lecture
		System.out.println("------Menu-----");
		System.out.println("1.Lire le réseau sémantique à partir de fichier");
		System.out.println("2.Entrer le réseau sémantique manuellement");
		System.out.println("3.Démo: Réseau sématantique cliché de nutrition");
		int choix=sc.nextInt();
		sc.nextLine();
		RS net=new RS();
		switch(choix){
			case 1:
				System.out.println("Le fichier doit impérativement respecter:");
				System.out.println("Les noeuds sont déclarés node,nom_du_neud suivi d'un saut de ligne");
				System.out.println("Les relations sont déclarées relation,type_de_relation,nom_du_noeud_début,non_noeud_fin suivi d'un saut de ligne");
				System.out.println("Les relations d'exceptions sont déclarées\n"
						+ "exception,nom_du_noeud_de_départ,type_de_relation_cible,noeud_départ_relation_cible,noeud_arrivé_relation_cible suivi d'un saut de ligne");
				System.out.println("Donnez le chemin du fichier");
				String chemin=sc.nextLine();
				net.fichier(chemin);
				break;
			case 2:
				net.manual();
				break;
			case 3:
			default:
				net=new RS();
				
				net.getNodes().put("humain", new Node("humain"));
				net.getNodes().put("asiatique", new Node("asiatique"));
				net.getNodes().put("européen", new Node("europée"));
				net.getNodes().put("africain", new Node("africain"));
				net.getNodes().put("américain", new Node("américain"));
				net.getNodes().put("chinois", new Node("chinois"));
				net.getNodes().put("viétnamien", new Node("viétnamien"));
				net.getNodes().put("japonais", new Node("japonais"));
				net.getNodes().put("français", new Node("français"));
				net.getNodes().put("allemand", new Node("allemand"));
				net.getNodes().put("espagnol", new Node("espagnol"));
				net.getNodes().put("algérien", new Node("algérien"));
				net.getNodes().put("marocain", new Node("marocain"));
				net.getNodes().put("chilien", new Node("chilien"));
				net.getNodes().put("brésilien", new Node("brésilien"));
				net.getNodes().put("mexicain", new Node("mexicain"));
				net.getNodes().put("riz", new Node("riz"));
				net.getNodes().put("insects", new Node("insects"));
				net.getNodes().put("sushi", new Node("sushi"));
				net.getNodes().put("escargots", new Node("escargots"));
				net.getNodes().put("bierre", new Node("bierre"));
				net.getNodes().put("couscous", new Node("couscous"));
				net.getNodes().put("hrira", new Node("hrira"));
				net.getNodes().put("bananes", new Node("bananes"));
				net.getNodes().put("fajitas", new Node("fajitas"));
				
				new Relation("is-a",net.getNodes().get("asiatique"),net.getNodes().get("humain"));
				new Relation("is-a",net.getNodes().get("européen"),net.getNodes().get("humain"));
				new Relation("is-a",net.getNodes().get("africain"),net.getNodes().get("humain"));
				new Relation("is-a",net.getNodes().get("américain"),net.getNodes().get("humain"));
				new Relation("is-a",net.getNodes().get("viétnamien"),net.getNodes().get("asiatique"));
				new Relation("is-a",net.getNodes().get("japonais"),net.getNodes().get("asiatique"));
				new Relation("is-a",net.getNodes().get("français"),net.getNodes().get("européen"));
				new Relation("is-a",net.getNodes().get("allemand"),net.getNodes().get("européen"));
				new Relation("is-a",net.getNodes().get("espagnol"),net.getNodes().get("européen"));
				new Relation("is-a",net.getNodes().get("algérien"),net.getNodes().get("africain"));
				new Relation("is-a",net.getNodes().get("marocain"),net.getNodes().get("africain"));
				new Relation("is-a",net.getNodes().get("chilien"),net.getNodes().get("américain"));
				new Relation("is-a",net.getNodes().get("brésilien"),net.getNodes().get("américain"));
				new Relation("is-a",net.getNodes().get("mexicain"),net.getNodes().get("américain"));
				
				new Relation("mange",net.getNodes().get("chinois"),net.getNodes().get("riz"));
				new Relation("mange",net.getNodes().get("viétnamien"),net.getNodes().get("insects"));
				new Relation("mange",net.getNodes().get("japonais"),net.getNodes().get("sushi"));
				new Relation("mange",net.getNodes().get("français"),net.getNodes().get("escargots"));
				new Relation("mange",net.getNodes().get("allemand"),net.getNodes().get("bierre"));
				new Relation("mange",net.getNodes().get("algérien"),net.getNodes().get("couscous"));
				new Relation("mange",net.getNodes().get("marocain"),net.getNodes().get("hrira"));
				new Relation("mange",net.getNodes().get("brésilien"),net.getNodes().get("bananes"));
				new Relation("mange",net.getNodes().get("mexicain"),net.getNodes().get("fajitas"));
				new Relation("mange",net.getNodes().get("algérien"),net.getNodes().get("bananes"));

				break;
		}
		
		//Choix de condition d'arrêt du marquage
		System.out.println("Donnez le type de question:");
		System.out.println("1.Quel(le)s sont ...?");
		System.out.println("2.Quel est/exist-t-il ...? ");
		choix=sc.nextInt();
		sc.nextLine();
		boolean unique=true;
		if(choix==1) unique=false;
		
		//Choix du premier élément à marquer M1
		boolean retry=true;
		System.out.println("Donnez la première entité en question ");
		String nodeM1=sc.nextLine();
		while(retry){
			if(net.getNodes().containsKey(nodeM1)){
				retry=false;
			}
			else{
				System.out.println("Ce noeud n'appartient pas au réseau sémantique, réessayez");
				nodeM1=sc.nextLine();
			}
		}
		//Choix de la relation en question
		System.out.println("Donnez la relation en question ");
		String relation=sc.nextLine();
		
		//Choix du premier élément a marquer M2
		System.out.println("Donnez la deuxième entité en question ");
		retry=true;
		String nodeM2=sc.nextLine();
		while(retry){
			if(net.getNodes().containsKey(nodeM2)){
				retry=false;
			}
			else{
				System.out.println("Ce noeud n'appartient pas au réseau sémantique, réessayez");
				nodeM2=sc.nextLine();
			}
		}
		
		//Inférance
		net.init(nodeM1,nodeM2,relation,unique);
		
		//affichermarquge
		net.afficherMarquage();
		
		sc.close();
	}
}
