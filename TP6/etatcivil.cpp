#include <cstdlib>
#include <iostream>
#include <string>
using namespace std;

const int MAXPERSONNES = 50;
string Tab="	";
enum Genre { M, F };

struct Personne{
  string nom;
  Genre genre;
  int indConjoint, indParent1, indParent2;
};

struct EtatCivil {
   Personne tableP[MAXPERSONNES];
   int nbP;
};


void AjoutPersonne(string sonNom, Genre s, int parent1, int parent2, EtatCivil &EC){
    bool ajout=true;
    for(int i=0;i<=EC.nbP;i++){
        if(sonNom==EC.tableP[i].nom)ajout=false;
    }
    if((EC.nbP<MAXPERSONNES)&&(ajout==true)){
        Personne personne;
        personne.nom=sonNom;
        personne.genre=s;
        personne.indParent1=parent1;
        personne.indParent2=parent2;
        personne.indConjoint=0;
        EC.tableP[EC.nbP+1]=personne;
        EC.nbP++;
    }else{
        cout<<"Erreur lors de l'ajout de la personne"<<endl;
    }
}
void AffichePersonne(int ind, EtatCivil EC){
    if(ind<=EC.nbP){
    cout<<EC.tableP[ind].nom<<endl;
    cout<<EC.tableP[ind].genre<<endl;
    cout<<EC.tableP[ind].indConjoint<<endl;
    cout<<EC.tableP[ind].indParent1<<endl;
    cout<<EC.tableP[ind].indParent2<<endl;
    cout<<endl;
    }else{
        cout<<"Erreur, la personne n'existe pas dans l'etat civil"<<endl;
    }
    
}
void AfficheEtatCivil(EtatCivil EC){
    for(int i=0; i<EC.nbP;i++){
        AffichePersonne(i,EC);
    }
}

void RemplitEtatCivil(EtatCivil &EC) { // remplit l'état civil avec 20 personnes
  AjoutPersonne("Nom0", F, -1, -1, EC);
  AjoutPersonne("Nom1", M, -1, -1, EC);
  AjoutPersonne("Nom2", M, 	-1, -1, EC);
  AjoutPersonne("Nom3", F, -1, -1, EC);
  AjoutPersonne("Nom4", M, 0, -1, EC);   //-------- 
  AjoutPersonne("Nom5", F, -1, 1, EC);
  AjoutPersonne("Nom6", M, 3, 2, EC);
  AjoutPersonne("Nom7", F, 5, 4, EC);   //-------- a cause du 5
  AjoutPersonne("Nom8", F, 7, 6, EC); 
  AjoutPersonne("Nom9", F, 5, 10, EC);   //-------- a cause du 5 et 10
  AjoutPersonne("Nom10", M, 12, 11, EC);   //-------- ses deux parents n on aucun parent
  AjoutPersonne("Nom11", M, -1, -1, EC);
  AjoutPersonne("Nom12", F, -1, -1, EC);
  AjoutPersonne("Nom13", M, 9, 14, EC);   //-------- a cause du 10
  AjoutPersonne("Nom14", M, 16, 15, EC);   //--------
  AjoutPersonne("Nom15", M, -1, 17, EC);
  AjoutPersonne("Nom16", F, 19, 18, EC);
  AjoutPersonne("Nom17", M, -1, -1, EC);
  AjoutPersonne("Nom18", M, -1, -1, EC);
  AjoutPersonne("Nom19", F, -1, -1, EC);
}

int cherchePersonne(string nom, EtatCivil &EC){
    for (int i=0;i<EC.nbP;i++){
        if(nom==EC.tableP[i].nom){
            return i;
        }
    }return -1;
}

bool mariage(string nom1, string nom2, EtatCivil &EC){
    int ind1=cherchePersonne(nom1,EC);
    int ind2=cherchePersonne(nom2,EC);
    if((ind1!=-1)&&(ind2!=-1)&&(EC.tableP[ind1].indConjoint==0)&&(EC.tableP[ind2].indConjoint==0)){
		EC.tableP[ind1].indConjoint=1;
		EC.tableP[ind2].indConjoint=1;
		return true;
    }else{
    		return false;
    }
}


void afficheparents(int ind,int nbTab,EtatCivil &EC){
				
				if(EC.tableP[ind].indParent1 >= 0) {
					for(int i=0;i<=nbTab;i++)cout<<Tab;
					cout<<"Individu "<<EC.tableP[ind].indParent1<<endl;
					nbTab++;
						afficheparents(EC.tableP[ind].indParent1,nbTab,EC);
					
				}else{
					for(int i=0;i<=nbTab;i++)cout<<Tab;
					cout<<"Individu Inconnu"<<endl;
				}
			
		
			
				if(EC.tableP[ind].indParent2 >=0) {
					for(int i=0;i<=nbTab;i++)cout<<Tab;
					cout<<"Individu "<<EC.tableP[ind].indParent2<<endl;
					nbTab++;
					afficheparents(EC.tableP[ind].indParent2,nbTab,EC);
					
				}else{
					for(int i=0;i<=nbTab;i++)cout<<Tab;
					cout<<"Individu Inconnu"<<endl;
					
				}

}

void arbreGenealogique(string nom, EtatCivil &EC){
	int ind=cherchePersonne(nom,EC);
	cout<<"Individu "<<ind<<endl;
	afficheparents(ind,0,EC);
}

int main() {
  EtatCivil EC;
  EC.nbP = 0; // il est necessaire d'initialiser le nombre de personnes
  RemplitEtatCivil(EC);
  AfficheEtatCivil(EC);
  cout<<cherchePersonne("Nom9",EC)<<endl;
// cout<<mariage("Nom1","Nom2",EC)<<endl;
// cout<<mariage("Nom2","Nom4",EC)<<endl;
 arbreGenealogique("Nom9",EC); 
}
