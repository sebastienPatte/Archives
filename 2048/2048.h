#include <vector>
#include <iostream>
#include <stdexcept>
#include <vector>
#include <stdlib.h>
#include <time.h>
#include <cstdlib>
#include <stdio.h>
#include <ncurses.h>
#include <string>
#define couleur(param) printf("\033[%sm",param) 
using namespace std;

const int GAUCHE = 7, DROITE = 4, HAUT = 8, BAS = 2;

typedef vector< vector<int> > Plateau;


int randint( int min, int max );
Plateau plateauVide();
Plateau plateauInitial();
Plateau Actualisation(Plateau P);
Plateau deplacementHaut(Plateau plateau);
Plateau deplacementBas(Plateau plateau);
Plateau deplacementDroite(Plateau plateau);
Plateau deplacementGauche(Plateau plateau);
bool estGagnant(Plateau plateau);
bool estTermine(Plateau plateau);
int taille_int(int n);
string espace(int n);
string convertInt(int number);
string dessine (Plateau g);
int score(Plateau plateau);
Plateau deplacement(Plateau plateau, int direction);



	












