#include <iostream>
#include <sstream>
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

float max(float nb1,float  nb2){
	if(nb1>nb2)return nb1;
	else return nb2;
}

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
bool contientSucre(place p){ return p.sucre;}
bool surUnePiste(place p){ return (p.pheromonesSucre!=0);}
//-------------------------------------------------------------------------------------------------


void creerFourmi(int indiceFourmi, coord positionFourmi, vector<Fourmi> &tabFourmis, Grille &T){
	Fourmi f;
	f.coordonnees=positionFourmi;
	if(SHOW_PRINTS)cout<<"CREATED ANT NUMBER ["<<indiceFourmi<<"] AT "<<f.coordonnees.X<<", "<<f.coordonnees.Y<<endl;
	f.porteSucre=false;
	tabFourmis[indiceFourmi]= f;

	tabFourmis[indiceFourmi].coordonnees=positionFourmi;
	T[f.coordonnees.X][f.coordonnees.Y].indiceFourmi= indiceFourmi;
}



bool dansGrille(coord res, Grille T){

	if((res.X>=T.size()) or (res.Y>=T.size()))return false;
	if ((res.X<0) or (res.Y<0 ))return false;
	return true;
	
}

vector<coord> voisins(coord coordonnees,Grille T){

	vector<coord> res(4);			
	res[0]= {coordonnees.X+1,coordonnees.Y};
	res[1]= {coordonnees.X,coordonnees.Y+1};
	res[2]= {coordonnees.X-1,coordonnees.Y};
	res[3]= {coordonnees.X,coordonnees.Y-1};


	return res;
		    
}


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
						cout<<float(max(maxPheromoneNidVoisins(i,j,T)-tailleInv,0))<<endl;
				}
			}
		}
}
	

void initPlateau(int nbSucres,vector<Fourmi> &tabFourmis,Grille &T){
	int zoneSucres,posSucreX,posSucreY;
	// INIT GRILLE
	for(int i=0;i<T.size();i++){
		T[i].resize(T.size());
		for(int j=0;j<T[i].size();j++){
			T[i][j]= {-1,false,false};
		}
	}

	// INIT SUCRE
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
	initPheromonesNid(T);	
}

void EspaceSiFloatTropCourt(float nb){


	stringstream ss (stringstream::in | stringstream::out);
  	ss << nb;
	string chaine = ss.str();

	if(chaine.size() <=3)cout<<" ";
}


void afficheGrille(vector<Fourmi> &tabFourmis, Grille T){
	for(int c=0;c<T.size()*2+2;c++)cout<<"#";
	cout<<endl;
	for (int i=0; i<T.size(); i++){
		cout<<"#";
		for (int j=0; j<T[i].size(); j++){
			cout<<" ";
			
			if(T[i][j].indiceFourmi!=-1){
				if(tabFourmis[T[i][j].indiceFourmi].porteSucre){
/**/				if(SHOW_EMPTY_PLACES)cout<<"  ";
					cout<<"F";
					if(SHOW_EMPTY_PLACES)cout<<"  ";
				}else{
					if(SHOW_EMPTY_PLACES)cout<<"  ";
/**/					cout<<"f";
					if(SHOW_EMPTY_PLACES)cout<<"  ";
				}
			}else{
				if(T[i][j].sucre){
/**/				if(SHOW_EMPTY_PLACES)cout<<"  ";
					cout<<"S";
					if(SHOW_EMPTY_PLACES)cout<<"  ";
				}else{
					if(T[i][j].nid){
/**/						if(SHOW_EMPTY_PLACES)cout<<"  ";
						cout<<"N";
						if(SHOW_EMPTY_PLACES)cout<<"  ";
					}else{
						if (SHOW_EMPTY_PLACES) {
							cout<<" "<<tronqueFloat(T[i][j].pheromoneNid);
							EspaceSiFloatTropCourt(tronqueFloat(T[i][j].pheromoneNid));
						} else {
							cout<<" ";
						}
					}
				}
			}
		}cout<<"#"<<endl;
	}
	for(int c=0;c<T.size()*2+2;c++)cout<<"#";
	cout<<endl;
}

void deplaceFourmi(Fourmi f, place &p1, place &p2, coord coord_p2, vector<Fourmi> &tabFourmis, Grille &T){
	coord coord_p1=f.coordonnees;
	f.coordonnees=coord_p2;
	tabFourmis[p1.indiceFourmi]=f;
	p2.indiceFourmi=p1.indiceFourmi;
	p1.indiceFourmi=-1;
	
	T[coord_p2.X][coord_p2.Y]=p2;
	T[coord_p1.X][coord_p1.Y]=p1;

}



bool placeVide(place p){
	return ((p.indiceFourmi==-1)&&(!p.sucre)&&(!p.nid));
}





bool regle1(Fourmi &f, vector<coord> coord_voisins, vector<Fourmi> &tabFourmis, Grille &T){
	place p1,p2;
	p1=T[f.coordonnees.X][f.coordonnees.Y];

	for(int i=0; i<coord_voisins.size();i++){
		if(dansGrille(coord_voisins[i],T)){
			p2=T[coord_voisins[i].X][coord_voisins[i].Y];
			if(chercheSucre(f)&&(contientSucre(p2))){
						f.porteSucre=true;
						p2.sucre=false;
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

bool regle4(Fourmi &f, vector<coord> coord_voisins, vector<Fourmi> &tabFourmis, Grille &T){
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

bool regle6(Fourmi &f, vector<coord> coord_voisins, vector<Fourmi> &tabFourmis, Grille &T){
	place p1,p2;
	int i;
	p1=T[f.coordonnees.X][f.coordonnees.Y];
	do{
		i=randint(0,coord_voisins.size());
	}while(!dansGrille(coord_voisins[i],T));

	p2=T[coord_voisins[i].X][coord_voisins[i].Y];
	if(chercheSucre(f) && estVide(p2)){
		cout<<"deplace"<<endl;
		deplaceFourmi(f, p1, p2, {coord_voisins[i].X, coord_voisins[i].Y}, tabFourmis, T);
				
		return true;
	}
	return false;
}

void unTour(vector<Fourmi> &tabFourmis , Grille &T){
	coord newCoord;
	place p1,p2;
	Fourmi f;
	for(int i=0;i<tabFourmis.size();i++){
		cout<<i<<endl;
	
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
