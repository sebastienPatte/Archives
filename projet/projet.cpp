#include <iostream>
#include <vector>
using namespace std;
#define SHOW_EMPTY_PLACES false
#define SHOW_PRINTS false



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
	if(SHOW_PRINTS)cout<<"CREATED ANT NUMBER ["<<indiceFourmi<<"] AT "<<f.coordonnees.X<<", "<<f.coordonnees.Y<<endl;
	f.porteSucre=false;
	tabFourmis[indiceFourmi]= f;

	tabFourmis[indiceFourmi].coordonnees=positionFourmi;
	T[f.coordonnees.X][f.coordonnees.Y].indiceFourmi= indiceFourmi;
}


	

void initPlateau(int nbSucres,vector<Fourmi> &tabFourmis,Grille &T){
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
				if(SHOW_PRINTS)cout<<"INIT ANT AT "<<i<<", "<<j<<" : "<<positionFourmi.X<<", "<<positionFourmi.Y<<endl;
				creerFourmi(indiceFourmi,positionFourmi,tabFourmis,T);
				indiceFourmi++;
			}
		}
	}
	
}



void afficheGrille(vector<Fourmi> &tabFourmis, Grille T){
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
						if (SHOW_EMPTY_PLACES) {
							cout<<"-";
						} else {
							cout<<" ";
						}
					}
				}
			}
		}cout<<"#"<<endl;
	}
	for(int c=0;c<42;c++)cout<<"#";
	cout<<endl;
}

void deplaceFourmi(Fourmi f, place &p1, place &p2, coord coord_p2, vector<Fourmi> &tabFourmis){
	coord coord_p1=f.coordonnees;
	
	f.coordonnees=coord_p2;
	
	tabFourmis[p1.indiceFourmi]=f;
	
	p2.indiceFourmi=p1.indiceFourmi;
	p1.indiceFourmi=-1;

}

bool placeVide(place p){
	return ((p.indiceFourmi==-1)&&(!p.sucre)&&(!p.nid));
}

bool dansGrille(coord res, Grille T){

	if((res.X>=T.size()) or (res.Y>=T.size()))return false;
	if ((res.X<0) or (res.Y<0 ))return false;
	return true;
	
}

coord choisiCoordAleatoirement(vector<coord> tabCoord, Grille T){
	coord res;
	do{
		res = tabCoord[randint(0,tabCoord.size())];
	}while(!(dansGrille(res,T)) or !placeVide(T[res.X][res.Y]) );
	return res;
	
}


vector<coord> voisins(Fourmi f,Grille T){

	vector<coord> res(4);			
	res[0]= {f.coordonnees.X+1,f.coordonnees.Y};
	res[1]= {f.coordonnees.X,f.coordonnees.Y+1};
	res[2]= {f.coordonnees.X-1,f.coordonnees.Y};
	res[3]= {f.coordonnees.X,f.coordonnees.Y-1};


	return res;
		    
}

bool sucreVoisin(Fourmi &f, vector<Fourmi> tabFourmis, Grille &T){
	
	vector<coord> coord_voisins= voisins(f,T);
	
	for(int i=0;i<coord_voisins.size();i++){
		if( dansGrille(coord_voisins[i],T) && T[coord_voisins[i].X][coord_voisins[i].Y].sucre){
			T[coord_voisins[i].X][coord_voisins[i].Y].sucre=false;
			f.porteSucre=true;
			tabFourmis[T[f.coordonnees.X][f.coordonnees.Y].indiceFourmi]=f;
			return true;
		}
	}return false;
}

void unTour(vector<Fourmi> &tabFourmis , Grille &T){
	coord newCoord;
	for(int i=0;i<tabFourmis.size();i++){
		if(!sucreVoisin(tabFourmis[i], tabFourmis, T)){
			newCoord=choisiCoordAleatoirement(voisins(tabFourmis[i],T),T);
			if(SHOW_PRINTS)printf("MOVE ANT TO COORDS : %d, %d\n", newCoord.X, newCoord.Y);
			deplaceFourmi(tabFourmis[i], T[tabFourmis[i].coordonnees.X][tabFourmis[i].coordonnees.Y], T[newCoord.X][newCoord.Y], newCoord, tabFourmis );
		}
	}

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
	
	int test=0;
	do{
		unTour(tabFourmis,T);
		afficheGrille(tabFourmis,T);
		cin>>test;
	}while(test==0);
	if(SHOW_PRINTS)cout<<"SHOWING ANTS"<<endl;
	for(int i=0; i<tabFourmis.size();i++) {
		if(SHOW_PRINTS)cout<<tabFourmis[i].coordonnees.X<<", "<<tabFourmis[i].coordonnees.Y<<endl;
	}
/*
	//tests deplacement de fourmi
	coord coord_p2={5,3};
	deplaceFourmi(tabFourmis[0], T[(T.size()/2)-2][(T.size()/2)-2], T[5][3], coord_p2, tabFourmis);
	cout<<T[T.size()/2-2][T.size()/2-2].indiceFourmi<<endl;
	cout<<tabFourmis[0].coordonnees.X<<" "<<tabFourmis[0].coordonnees.Y<<endl;
	afficheGrille(tabFourmis,T);
	
*/	
}
