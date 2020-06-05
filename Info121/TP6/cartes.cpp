#include <iostream>
#include <cstdlib>
using namespace std;

enum Couleur { P, Co, T, Ca };

struct Carte {
  int valeur;
  Couleur couleur;
};

struct Paquet {
  int taille;
  Carte carte[40];
};

//prend en paramètre min et max et renvoie un chiffre aléàtoire entre min et max
int randint( int min, int max ) {
	return min + rand() % ( max - min );
	/* 'rand()' renvoie un chiffre entre 0 et 'temps écoulé depuis le lancement' ( voir première ligne du main() ) . 
	    on prend modulo ( 'max' - 'min' )  pour avoir un chiffre entre 0 et la différence entre 'max'et 'min' .
	    on ajoute le minimum pour être sur d'avoir au moins 'min' . */
}

void initPaquet (Paquet &paquet) {
  paquet.taille=40;
  int i;
  for(i=0; i<10; i++)
    {
      paquet.carte[4*i].valeur = i+1;
      paquet.carte[4*i].couleur = P;
      paquet.carte[4*i+1].valeur = i+1;
      paquet.carte[4*i+1].couleur = Co;
      paquet.carte[4*i+2].valeur = i+1;
      paquet.carte[4*i+2].couleur = T;
      paquet.carte[4*i+3].valeur = i+1;
      paquet.carte[4*i+3].couleur = Ca;
    }
}

void affichePaquet(Paquet paquet){
	for(int i=0; i<paquet.taille;i++){
		cout<< paquet.carte[i].valeur <<" ";
		switch(paquet.carte[i].couleur){
			case 0: cout<<"Pique"<<endl;
				   break; 
			case 1: cout<<"Coeur"<<endl;
				   break;
			case 2: cout<<"Trefle"<<endl;
				   break;
			case 3: cout<<"Carreau"<<endl;
				   break; 			
		}
	}
	cout<< endl;
}

void permuter(int ind1, int ind2, Paquet &paquet){
	Carte carteTemp= paquet.carte[ind1];
	paquet.carte[ind1]=paquet.carte[ind2];
	paquet.carte[ind2]=carteTemp;
}


void melangePaquet(Paquet &paquet){
	int ind1,ind2;
	
	for(int i=0; i<paquet.taille; i++){
		ind1=randint(0,39);
		ind2=randint(0,39);
		permuter(ind1,ind2, paquet);
	}
	
}

int main(){
	Paquet paquet;
	initPaquet(paquet);
	affichePaquet(paquet);
	melangePaquet(paquet);
	affichePaquet(paquet);
}
