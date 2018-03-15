#include <cstdlib>
#include <iostream>
#include <string>
using namespace std;

const int MAXPERSONNES = 50;

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
  AjoutPersonne("Nom2", M, -1, -1, EC);
  AjoutPersonne("Nom3", F, -1, -1, EC);
  AjoutPersonne("Nom4", M, 0, -1, EC);
  AjoutPersonne("Nom5", F, -1, 1, EC);
  AjoutPersonne("Nom6", M, 3, 2, EC);
  AjoutPersonne("Nom7", F, 5, 4, EC);
  AjoutPersonne("Nom8", F, 7, 6, EC);
  AjoutPersonne("Nom9", F, 5, 10, EC);
  AjoutPersonne("Nom10", M, 12, 11, EC);
  AjoutPersonne("Nom11", M, -1, -1, EC);
  AjoutPersonne("Nom12", F, -1, -1, EC);
  AjoutPersonne("Nom13", M, 9, 14, EC);
  AjoutPersonne("Nom14", M, 16, 15, EC);
  AjoutPersonne("Nom15", M, -1, 17, EC);
  AjoutPersonne("Nom16", F, 19, 18, EC);
  AjoutPersonne("Nom17", M, -1, -1, EC);
  AjoutPersonne("Nom18", M, -1, -1, EC);
  AjoutPersonne("Nom19", F, -1, -1, EC);
}

int cherchePersonne(string nom, EtatCivil &EC){
    for (int i=0;i<=EC.nbP;i++){
        if(nom==EC.tableP[i].nom){
            return i;
        }
    }return -1;
}

void mariage(string nom1, string nom2, EtatCivil &EC){
    if((cherchePersonne(nom1)==-1)&&(cherchePersonne(nom2)==-1){
    
    }
}

int main() {
  EtatCivil EC;
  EC.nbP = 0; // il est necessaire d'initialiser le nombre de personnes
  RemplitEtatCivil(EC);
  AfficheEtatCivil(EC);
  cout<<cherchePersonne("Nom9",EC)<<endl;
  
}
