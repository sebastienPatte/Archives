#include <iostream>
#include <sstream>
#include <vector>
#include <string>

using namespace std;

#define SHOW_EMPTY_PLACES true
#define SHOW_PRINTS false





struct coord{
	int X;
	int Y;
};

struct Fourmi{
	coord coordonnees;
	bool porteSucre;
	int orientation; // de 0 à 3 {HAUT, DROITE, BAS, GAUCHE}
};

struct place{
	int indiceFourmi;
	int sucre;
	bool nid;
	float pheromoneNid;
	int pheromonesSucre;
};

typedef vector< vector<place> > Grille;


//prend en paramètre min et max et renvoie un chiffre aléàtoire entre min et max
int randint( int min, int max ) {
	return min + rand() % ( max - min );
	/* 'rand()' renvoie un chiffre entre 0 et 'temps écoulé depuis le lancement' ( voir première ligne du main() ) . 
	    on prend modulo ( 'max' - 'min' )  pour avoir un chiffre entre 0 et la différence entre 'max'et 'min' .
	    on ajoute le minimum pour être sur d'avoir au moins 'min' . */
}

// renvoie le maximum entre deux float
float max(float nb1,float  nb2){
	if(nb1>nb2)return nb1;
	else return nb2;
}

// revoie un float tronqué au centième
float tronqueFloat(float nb){
	nb*=100;
	nb= ((int) nb)/((float)100); 
	return nb;
}

// predicats---------------------------------------------------------------------------------------
bool chercheSucre(Fourmi f){ return !f.porteSucre;}
bool rentreNid(Fourmi f){ return f.porteSucre;}
bool estVide(place p){ return (p.indiceFourmi==-1 && !p.sucre && !p.nid);}
bool contientNid(place p){ return p.nid;}
bool plusProcheNid(place p1,place p2){ return (p1.pheromoneNid > p2.pheromoneNid);}
bool plusLoinNid(place p1, place p2){ return (p1.pheromoneNid < p2.pheromoneNid);}
bool contientSucre(place p){ return p.sucre!=0;}
bool surUnePiste(place p){ return (p.pheromonesSucre!=0);}
//bool plusProcheSucre(place p1,p2){ return (p1.pheromonesSucre < p2.pheromonesSucre);}
//-------------------------------------------------------------------------------------------------

// créé une fourmi a la position 'positionFourmi'
void creerFourmi(int indiceFourmi, coord positionFourmi, vector<Fourmi> &tabFourmis, Grille &T){
	Fourmi f;
	f.coordonnees=positionFourmi;
	if(SHOW_PRINTS)cout<<"CREATED ANT NUMBER ["<<indiceFourmi<<"] AT "<<f.coordonnees.X<<", "<<f.coordonnees.Y<<endl;
	f.porteSucre=false;
	f.orientation=0;
	tabFourmis[indiceFourmi]= f;

	tabFourmis[indiceFourmi].coordonnees=positionFourmi;
	T[f.coordonnees.X][f.coordonnees.Y].indiceFourmi= indiceFourmi;
}


// vérifie si une case de coordonnée 'res' est dans la grille
bool dansGrille(coord res, Grille T){

	if((res.X>=T.size()) or (res.Y>=T.size()))return false;
	if ((res.X<0) or (res.Y<0 ))return false;
	return true;
	
}

// @ param: coord 
// renvoie un tableau contenant les coordonnées de toutes les cases voisines de la case de coordonnées 'coord' entrée en paramètre
vector<coord> voisins(coord coordonnees,Grille T){

	vector<coord> res(4);			
	res[0]= {coordonnees.X+1,coordonnees.Y};
	res[1]= {coordonnees.X,coordonnees.Y+1};
	res[2]= {coordonnees.X-1,coordonnees.Y};
	res[3]= {coordonnees.X,coordonnees.Y-1};
	//res[4]= {coordonnees.X+1,coordonnees.Y+1};
	//res[5]= {coordonnees.X-1,coordonnees.Y+1};
	//res[6]= {coordonnees.X-1,coordonnees.Y-1};
	//res[7]= {coordonnees.X+1,coordonnees.Y-1};



	return res;
		    
}

// renvoie la case voisine de la case {i,j} ayant la plus grande concentration de pheromonesNid
float maxPheromoneNidVoisins(int i, int j, Grille T){
	coord coordonnees={i,j};
	vector<coord> coord_voisins= voisins(coordonnees,T);
	float res=0;

	for (int k=0; k<coord_voisins.size(); k++){
		if(dansGrille(coord_voisins[k], T)){
			res=float(max(res,T[coord_voisins[k].X][coord_voisins[k].Y].pheromoneNid));
		}
	}
	return res;

}

// initialise les pheromonesNid dans la grille T
void initPheromonesNid(Grille &T){
	// INIT PHEROMONES NID
		T[T.size()/2][T.size()/2].pheromoneNid= 1.0;
		T[(T.size()/2)-1][T.size()/2].pheromoneNid= 1.0;
		T[T.size()/2][(T.size()/2)-1].pheromoneNid= 1.0;
		T[(T.size()/2)-1][(T.size()/2)-1].pheromoneNid= 1.0;

		float tailleInv=1/(1.0*T.size());
		for(int k=0; k<T.size(); k++){
			for(int i=0; i<T.size(); i++){
				for(int j=0; j<T.size(); j++){
					if(T[i][j].pheromoneNid <1)
						T[i][j].pheromoneNid = max(maxPheromoneNidVoisins(i,j,T)-tailleInv,0);
				}
			}
		}
}

//pour verifier que les tas de sucres générés au lancement du programme ne sont pas trop proches du nid	
bool sucreTropProcheNid(int posSucreX, int posSucreY, Grille T){
	if( (posSucreX<(T.size()/2)-(T.size()/4)) || (posSucreX > (T.size()/2)+(T.size()/4) ) ){
		if( (posSucreY<(T.size()/2)-(T.size()/4)) || (posSucreY > (T.size()/2)+(T.size()/4) ) ){
			return false;
		}
	}
}

// retourne true si la case est vide, false sinon
bool placeVide(place p){
	return ((p.indiceFourmi==-1)&&(!p.sucre)&&(!p.nid));
}


// initialise toute la grille T
void initPlateau(int nbSucres,vector<Fourmi> &tabFourmis,Grille &T){
	int zoneSucres,posSucreX,posSucreY;
	// INIT GRILLE
	for(int i=0;i<T.size();i++){
		T[i].resize(T.size());
		for(int j=0;j<T[i].size();j++){
			T[i][j]= {-1,false,false};
		}
	}


	
	// INIT NID
	T[T.size()/2][T.size()/2].nid= true;
	T[T.size()/2-1][T.size()/2].nid= true;
	T[T.size()/2][T.size()/2-1].nid= true;
	T[T.size()/2-1][T.size()/2-1].nid= true;


	int indiceFourmi=0;
	// INIT FOURMIS
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
	
	// INIT SUCRE
	for(int k=0; k<nbSucres;k++){
		do{
			posSucreX=randint(0,T.size()-1);
			posSucreY=randint(0,T.size()-1);
		}while(sucreTropProcheNid(posSucreX,posSucreY,T)|| !placeVide(T[posSucreX][posSucreY]));
		T[posSucreX][posSucreY].sucre=5;
	}	

	// INIT PHEROMONES NID
	initPheromonesNid(T);	
}

void majOrientation(Fourmi &f, coord coord_p1, coord coord_p2){
	if(coord_p1.X < coord_p2.X){
		f.orientation=1;
	}else{
		if(coord_p1.Y < coord_p2.Y){
			f.orientation=0;
		}else{
			if(coord_p1.X > coord_p2.X){
				f.orientation=3;
			}else{
				f.orientation=2;
			}
		}
	}
}


// deplace une fourmi d'une case p1 à une case p2
void deplaceFourmi(Fourmi f, place &p1, place &p2, coord coord_p2, vector<Fourmi> &tabFourmis, Grille &T){
	coord coord_p1=f.coordonnees;
	f.coordonnees=coord_p2;
	majOrientation(f,coord_p1,coord_p2);
	tabFourmis[p1.indiceFourmi]=f;
	p2.indiceFourmi=p1.indiceFourmi;
	p1.indiceFourmi=-1;
	T[coord_p2.X][coord_p2.Y]=p2;
	T[coord_p1.X][coord_p1.Y]=p1;

}




//regle 1: ramassage du sucre
bool regle1(Fourmi &f, vector<coord> coord_voisins, vector<Fourmi> &tabFourmis, Grille &T){
	place p1,p2;
	p1=T[f.coordonnees.X][f.coordonnees.Y];

	for(int i=0; i<coord_voisins.size();i++){
		if(dansGrille(coord_voisins[i],T)){
			p2=T[coord_voisins[i].X][coord_voisins[i].Y];
			if(chercheSucre(f)&&(contientSucre(p2))){
						f.porteSucre=true;
						p2.sucre--;
						p1.pheromonesSucre=255;


						//on remet les valeurs dans tabFourmis et la Grille
						T[coord_voisins[i].X][coord_voisins[i].Y]=p2;
						T[f.coordonnees.X][f.coordonnees.Y]=p1;
						tabFourmis[p1.indiceFourmi]=f;

						return true;

			}
		}
	}return false;
}

//regle 2: depose sucre
bool regle2(Fourmi &f, vector<coord> coord_voisins, vector<Fourmi> &tabFourmis, Grille &T){
	place p1,p2;
	p1=T[f.coordonnees.X][f.coordonnees.Y];
	
	for(int i=0; i<coord_voisins.size();i++){
		if(dansGrille(coord_voisins[i],T)){	
				p2=T[coord_voisins[i].X][coord_voisins[i].Y];
			
			if(rentreNid(f)&&(contientNid(p2))){
				f.porteSucre=false;
				//on remet la fourmi f dans tabFourmi
				tabFourmis[p1.indiceFourmi]=f;

				return true;
			}
		}
	}return false;
}

//regle 3: rentre le plus rapidement possible au nid
bool regle3(Fourmi &f, vector<coord> coord_voisins, vector<Fourmi> &tabFourmis, Grille &T){
	place p1,p2;
	p1=T[f.coordonnees.X][f.coordonnees.Y];
	
	for(int i=0; i<coord_voisins.size();i++){
		if(dansGrille(coord_voisins[i],T)){
			p2=T[coord_voisins[i].X][coord_voisins[i].Y];
			if(rentreNid(f) && estVide(p2) && plusProcheNid(p2,p1)){
			
				deplaceFourmi(f, p1, p2, coord_voisins[i], tabFourmis, T);
				p2.pheromonesSucre=255;

				//on remet p2 dans la grille
				T[coord_voisins[i].X][coord_voisins[i].Y]=p2;
				return true;
			}
		}
	}return false;
}

//regle 4: suis une piste
bool regle4(Fourmi &f, vector<coord> coord_voisins, vector<Fourmi> &tabFourmis, Grille &T){
	place p1,p2;
	p1=T[f.coordonnees.X][f.coordonnees.Y];
	
	for(int i=0; i<coord_voisins.size();i++){
		if(dansGrille(coord_voisins[i],T)){	
			p2=T[coord_voisins[i].X][coord_voisins[i].Y];
			if(chercheSucre(f) && surUnePiste(p1) && estVide(p2) && plusLoinNid(p2,p1) && surUnePiste(p2)){
				deplaceFourmi(f, p1, p2, coord_voisins[i], tabFourmis, T);
			
				return true;
			}
		}
	}return false;
}

//regle 5: trouve une piste
bool regle5(Fourmi &f, vector<coord> coord_voisins, vector<Fourmi> &tabFourmis, Grille &T){
	place p1,p2;
	p1=T[f.coordonnees.X][f.coordonnees.Y];
	
	for(int i=0; i<coord_voisins.size();i++){
		if(dansGrille(coord_voisins[i],T)){
			p2=T[coord_voisins[i].X][coord_voisins[i].Y];
			if(chercheSucre(f) && surUnePiste(p2) && estVide(p2)){
				deplaceFourmi(f, p1, p2, coord_voisins[i], tabFourmis, T);

				return true;
			}
		}	
	}return false;
}

//regle 6: se deplace aleatoirement sur une case voisine vide
bool regle6(Fourmi &f, vector<coord> coord_voisins, vector<Fourmi> &tabFourmis, Grille &T){
	place p1,p2;
	int i;
	p1=T[f.coordonnees.X][f.coordonnees.Y];
	do{
		i=randint(0,coord_voisins.size());
	}while(!dansGrille(coord_voisins[i],T));

	p2=T[coord_voisins[i].X][coord_voisins[i].Y];
	if(estVide(p2)){
		deplaceFourmi(f, p1, p2, {coord_voisins[i].X, coord_voisins[i].Y}, tabFourmis, T);
				
		return true;
	}
	return false;
}

//diminue chaque pheromone de sucre présent sur la grille (est lancé à chaque itération unTour) 
void baissePheromonesSucre(Grille &T){
	for (int i=0 ; i<T.size();i++){
		for(int j=0; j<T.size();j++){
			if(T[i][j].pheromonesSucre<=0) T[i][j].pheromonesSucre=0;
			if(T[i][j].pheromonesSucre!=0) T[i][j].pheromonesSucre-=5;
		}
	}
}





// effectue une itération
void unTour(vector<Fourmi> &tabFourmis , Grille &T){
	coord newCoord;
	place p1,p2;
	Fourmi f;

	for(int i=0;i<tabFourmis.size();i++){
		
		f= tabFourmis[i];
		p1=T[f.coordonnees.X][f.coordonnees.Y];
		vector<coord> coord_voisins=voisins(tabFourmis[i].coordonnees,T);
			
			if(!regle1(f, coord_voisins, tabFourmis, T)){
				if(!regle2(f, coord_voisins, tabFourmis, T)){
					if(!regle3(f, coord_voisins, tabFourmis, T)){
						if(!regle4(f, coord_voisins, tabFourmis, T)){
							if(!regle5(f, coord_voisins, tabFourmis, T)){
								regle6(f, coord_voisins, tabFourmis, T);
							}
						}
					}
				}
			}
	}baissePheromonesSucre(T);

}


