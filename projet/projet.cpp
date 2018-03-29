#include <iostream>
#include <vector>
using namespace std;

typedef vector< vector<int> > Plateau;


//prend en paramètre min et max et renvoie un chiffre aléàtoire entre min et max
int randint( int min, int max ) {
	return min + rand() % ( max - min );
	/* 'rand()' renvoie un chiffre entre 0 et 'temps écoulé depuis le lancement' ( voir première ligne du main() ) . 
	    on prend modulo ( 'max' - 'min' )  pour avoir un chiffre entre 0 et la différence entre 'max'et 'min' .
	    on ajoute le minimum pour être sur d'avoir au moins 'min' . */
}


Plateau initPlateau(int nbSucres){
	Plateau T(20);
	int zoneSucres,posSucreX,posSucreY;
	for(int i=0;i<T.size();i++){
		T[i].resize(20);
		for(int j=0;j<T[i].size();j++){
			T[i][j]=0;
		}
	}
	zoneSucres=randint(0,10);	
	int k=0;
	do{
		posSucreX=randint(0,5);
		posSucreY=randint(0,5);
		if(T[posSucreX+zoneSucres][posSucreY+zoneSucres]!=2){
			T[posSucreX+zoneSucres][posSucreY+zoneSucres]=2;
			k++;
		}
	}while(k<nbSucres);
	T[1][1]=3;
	return T;
}


void affichePlateau(Plateau T){
	for (int i=0; i<T.size(); i++){
		for (int j=0; j<T[i].size(); j++){
			cout<<T[i][j]<<" ";
		}cout<<endl;
	}cout<<endl;
}


int main(){
	Plateau T=initPlateau(5);
	affichePlateau(T);
	T=initPlateau(5);
	affichePlateau(T);
}
