// compile avec g++ tp2-code.cpp -o tp2
#include <iostream>
#include <fstream>
#include <vector>
#include <stdlib.h>

using namespace std;

char MUR     = '#';
char VIDE    = ' ';
char ENCOURS = '?';
char IMPASSE = '@';
char CHEMIN  = 'O';

typedef vector<vector<char> > Labyrinthe;
Labyrinthe laby;

/* Initialize le labyrinthe avec juste des colonnes pour soutenir le plafond !
 */
void initLayrinthe0(int tX, int tY) {
	for (int j = 0; j < 2 * tY + 1; j++) {
		vector<char> v(2 * tX + 1);
		laby.push_back(v);
		for (int i = 0; i < 2 * tX + 1; i++) {
			char c;
			if (i % 2 == 1 && j % 2 == 1)
				c = ENCOURS;
			else
				c = MUR;
			laby[j][i] = c;
		}
	}
	for (int j = 1; j < 2 * tY - 0; j++) {
		for (int i = 1; i < 2 * tX - 0; i++) {
			if (i % 2 == 0 && j % 2 == 0)
				laby[j][i] = MUR;
			else
				laby[j][i] = VIDE;
		}
	}
	laby[0][1] = VIDE;
	laby[2 * tY - 1][2 * tX] = VIDE;
}

/* Initialize le labyrinthe en creant une serie de salles non reliees puis en
 * percant des murs au hasard du parcours d'un fantome qui troue les murs si et
 * seulement si il arrive dans une salle inexploree
 */
void initLayrinthe1(int tX, int tY) {
	for (int j = 0; j < 2 * tY + 1; j++) {
		vector<char> v(2 * tX + 1);
		laby.push_back(v);
		for (int i = 0; i < 2 * tX + 1; i++) {
			char c;
			if (i % 2 == 1 && j % 2 == 1)
				c = ENCOURS;
			else
				c = MUR;
			laby[j][i] = c;
		}
	}
	int cpt = tX * tY - 1;
	int x = 2 * tX - 1, y = 2 * tY - 1, xx, yy;
	laby[y][x] = VIDE;
	int alea;
	while (cpt > 0) {
		xx = x;
		yy = y;
		alea = rand() % 4;
		switch (alea) {
			case 0:
				if (x > 1) x -= 2;
				break;
			case 1:
				if (y > 1) y -= 2;
				break;
			case 2:
				if (x < 2 * tX - 1 - 1) x += 2;
				break;
			default:
				if (y < 2 * tY - 1 - 1) y += 2;
		}
		if (laby[y][x] == ENCOURS) {
			cpt--;
			laby[y][x] = VIDE;
			laby[(y + yy) / 2][(x + xx) / 2] = VIDE;
		}
	}
	laby[0][1] = VIDE;
	laby[2 * tY - 1][2 * tX] = VIDE;
}

/* Affiche le labyrinthe "ligne par ligne".
 */
void dispLayrinthe() {
	cout << " E" << endl;
	cout << " n" << endl;
	cout << " t" << endl;
	cout << " r" << endl;
	cout << " e" << endl;
	cout << " e" ;
	for (int j = 0; j < laby.size() - 1; j++) {
		cout << endl;
		for (int i = 0; i < laby[j].size(); i++)
			cout << laby[j][i];
	}
	cout << "Sortie"<<endl;
	for (int i = 0; i < laby[laby.size() - 1].size(); i++)
		cout << laby[laby.size() - 1][i];
	cout <<endl;
}

/* Cherche un chemin en "quasi diagonale"
 */
bool chercheChemin0() {
	int x = 1;
	int y = 1;
	laby[y][x] = CHEMIN;
	while (x != laby[laby.size() - 2].size() - 2 && y != laby.size() - 2) {
		if (   laby[y    ][x + 1] == VIDE && laby[y    ][x + 2] == VIDE
		    && laby[y + 1][x + 2] == VIDE && laby[y + 2][x + 2] == VIDE) {
			laby[y    ][x + 1] = CHEMIN;
			laby[y    ][x + 2] = CHEMIN;
			laby[y + 1][x + 2] = CHEMIN;
			laby[y + 2][x + 2] = CHEMIN;
			x = x + 2;
			y = y + 2;
		} else {
			return false;
		}
	}
	return true;
}

bool cheminExiste(int x, int y){
    
    if (x==laby[0].size()-2 && y ==laby[0].size()-2)
        return true ;
    
    if(laby[y][x] == VIDE){
        
        bool res=false;
        laby[y][x] = ENCOURS;

    	if(x>1 && cheminExiste(x-1,y))
       		laby[x+1][y]=CHEMIN;
       		res= true ;
    	if(y>1 && cheminExiste(x,y-1))
        	laby[x][y+1]=CHEMIN;
        	res= true ;
    	if(x<laby[0].size()-2 && cheminExiste(x+1,y))
        	laby[x-1][y]=CHEMIN;
        	return true ;
    	if(y<laby[0].size()-2 && cheminExiste(x,y+1))
        	laby[x][y-1]=CHEMIN;
        	return true ;

    	
    	if(res)
    		laby[y][x] = CHEMIN;
    	else
    		laby[y][x] = IMPASSE;

    	return res;
	}
	return false;
}

void bouche(){
	for(int y=1; y<laby.size()-1;y++)
		for(int x=1; x<laby.size()-1;x++)
			if(laby[x][y]==CHEMIN){
				if(laby[y][x-1]==VIDE)
					laby[y][x-1]==MUR;
			if(laby[y][x+1]==VIDE)
				
			}
}

int main(){
	srand(time(NULL));
	initLayrinthe1(14, 14);
	dispLayrinthe();
	if (cheminExiste(13,13))
		cout << endl << "TROUVE !" << endl << endl;
	else
		cout << endl << "PAS TROUVE !" << endl << endl;
	dispLayrinthe();
	return 0;
	
}

