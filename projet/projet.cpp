#include <iostream>
#include <vector>
using namespace std;





struct coord{
	int X;
	int Y;
};

struct Fourmi{
	coord coordonnees;
	bool porteSucre;
};

struct place{
	int indiceFourmi;
	bool sucre;
	bool nid;
};

typedef vector< vector<place> > Grille;


//prend en paramètre min et max et renvoie un chiffre aléàtoire entre min et max
int randint( int min, int max ) {
	return min + rand() % ( max - min );
	/* 'rand()' renvoie un chiffre entre 0 et 'temps écoulé depuis le lancement' ( voir première ligne du main() ) . 
	    on prend modulo ( 'max' - 'min' )  pour avoir un chiffre entre 0 et la différence entre 'max'et 'min' .
	    on ajoute le minimum pour être sur d'avoir au moins 'min' . */
}

void creerFourmi(int indiceFourmi, coord positionFourmi, vector<Fourmi> &tabFourmis, Grille &T){
	Fourmi f;
	f.coordonnees=positionFourmi;
	f.porteSucre=false;
	tabFourmis[indiceFourmi]= f;
	T[f.coordonnees.X][f.coordonnees.Y].indiceFourmi= indiceFourmi;
}


	

void initPlateau(int nbSucres,vector<Fourmi> tabFourmis,Grille &T){
	int zoneSucres,posSucreX,posSucreY;
	for(int i=0;i<T.size();i++){
		T[i].resize(T.size());
		for(int j=0;j<T[i].size();j++){
			T[i][j]= {-1,false,false};
		}
	}
	zoneSucres=randint(0,10);
	int k=0;
	do{
		posSucreX=randint(0,5);
		posSucreY=randint(0,5);
		if(T[posSucreX+zoneSucres][posSucreY+zoneSucres].sucre!=true ){
			T[posSucreX+zoneSucres][posSucreY+zoneSucres].sucre=true;
			k++;
		}
	}while(k<nbSucres);
	
	T[T.size()/2][T.size()/2].nid= true;
	T[T.size()/2-1][T.size()/2].nid= true;
	T[T.size()/2][T.size()/2-1].nid= true;
	T[T.size()/2-1][T.size()/2-1].nid= true;
	
	int indiceFourmi=0;
	
	coord positionFourmi;
	for(int i=(T.size()/2)-2 ; i<=(T.size()/2)+1 ; i++){
		for(int j=(T.size()/2)-2; j<=(T.size()/2)+1; j++){
			if(!(T[i][j].nid)){
				positionFourmi={i,j};
				creerFourmi(indiceFourmi,positionFourmi,tabFourmis,T);
				indiceFourmi++;
			}
		}
	}
	
}



void afficheGrille(vector<Fourmi> tabFourmis, Grille T){
	for(int c=0;c<42;c++)cout<<"#";
	cout<<endl;
	for (int i=0; i<T.size(); i++){
		cout<<"#";
		for (int j=0; j<T[i].size(); j++){
			cout<<" ";
			
			if(T[i][j].indiceFourmi!=-1){
				if(tabFourmis[T[i][j].indiceFourmi].porteSucre){
					cout<<"F";
				}else{
					cout<<"f";
				}
			}else{
				if(T[i][j].sucre){
					cout<<"S";
				}else{
					if(T[i][j].nid){
						cout<<"N";
					}else{
						cout<<" ";
					}
				}
			}
		}cout<<"#"<<endl;
	}
	for(int c=0;c<42;c++)cout<<"#";
	cout<<endl;
}



int main(){
	int nbFourmis=12;
	int tailleCarte=20;
	int nbSucres=5;
	coord positionFourmi={2,1};
	vector<Fourmi> tabFourmis(nbFourmis);
	
	Grille T(tailleCarte);
	initPlateau(nbSucres,tabFourmis,T);
	afficheGrille(tabFourmis,T);
	
	
}
