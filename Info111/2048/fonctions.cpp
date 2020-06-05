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
using namespace std;

const int GAUCHE = 7, DROITE = 4, HAUT = 8, BAS = 2;

typedef vector< vector<int> > Plateau;
//#################################################################################################################################################
//prend en paramètre min et max et renvoie un chiffre aléàtoire entre min et max
int randint( int min, int max ) {
	return min + rand() % ( max - min );
	/* 'rand()' renvoie un chiffre entre 0 et 'temps écoulé depuis le lancement' ( voir première ligne du main() ) . 
	    on prend modulo ( 'max' - 'min' )  pour avoir un chiffre entre 0 et la différence entre 'max'et 'min' .
	    on ajoute le minimum pour être sur d'avoir au moins 'min' . */
}

//#################################################################################################################################################
//initialise un Plateau de 4*4 (sauf la ligne 0 qui comporte 5 cases) avec un '0' dans chaque case 
Plateau plateauVide(){
	Plateau P(4);
	for (int i=0;i<4;i++){
		if (i==0)
		{
			P[i].resize(5); //la cinquième case de la ligne 0 sert à stocker le score
		}else{
			P[i].resize(4);
		}
		
	}
	return P;
}

//#################################################################################################################################################
/* initialise un plateau avec 2 cases au hasard remplies avec soit un 2(90% de chance) soit un 4(10% de chance) */
Plateau plateauInitial(){
	Plateau P=plateauVide();
	for (int compteur=0;compteur<2;compteur++){
		int i=randint(0,3);
		int j=randint(0,3);
		int c=randint(1,10); // 1 chance sur 10 soit 10%
		if(P[i][j]==0){
			if (c==1){
				P[i][j]=4;
			}else{
				P[i][j]=2;
			}
		}else{
		compteur-=1;
		}
	}return P;
}

//#################################################################################################################################################
/* entre chaque déplacement, tire une case vide (c-a-d égale à '0') au hasard et lui attribue au hasard soit la valeur 2(90% de chance) 
   soit la valeur 4(10% de chance) */ 
Plateau Actualisation(Plateau P){
     vector<int>iVides(0);
     vector<int>jVides(0);
     int i2 = 0;
     int j2 = 0;
     int c=randint(1,10);
     for (int i=0; i<P.size(); i++){
          for (int j=0; j<P.size();j++){          // Parcours le Plateau P et ajoute les coordonnées(i,j) des cases vides
               if(P[i][j]==0){                    // dans iVides et jVides
                    iVides.push_back (i); 
                    jVides.push_back (j);   
               }
          }
     }
     
         		
         		if(iVides.size()==1)          //si il n'y a plus qu'une case de vide
         		{
         		     i2=iVides[0];                      
         		     j2=jVides[0];
         		          if (c==1){           // on y place soit un 2 soit un 4
     			     	P[i2][j2]=4;
     			     }else{
     			     	P[i2][j2]=2;
     			
         		          }
         		}else{
         		     if(iVides.size()>1)
         		     {
              		int nouvelleCase=randint(0,iVides.size()-1);  // sinon on séléctione aléatoirement une case de coordonnées(i,j)
              		i2=iVides[nouvelleCase];                      
              		j2=jVides[nouvelleCase];
                         		
          			if (c==1){           // et on y place soit un 2 soit un 4
          				P[i2][j2]=4;
          			}else{
          				P[i2][j2]=2;
     		     	}
     		     }
     	     }return P;
               
}
     
//#################################################################################################################################################
/* Déplace les cases du plateau de jeu vers le haut puis fusionne les cases les plus vers le haut 
qui sont l'une en dessous de l'autre et on la meme valeur */
Plateau deplacementHaut(Plateau plateau){	
int iTemp=0;

	          for (int j=0 ; j< plateau.size() ; j++){        //parcours plateau de haut en bas et de droite à gauche
     		     for (int i=0 ; i< plateau.size() ; i++){	
        		            
        		     	if(plateau[i][j]!=0){                             // si la case [i][j] peut etre déplacée vers le haut,				
     		     		while( (i-1>=0)and(plateau[i-1][j]==0) ){	// on la déplace
     		     			plateau[i-1][j]=plateau[i][j];
     		     			plateau[i][j]=0;
     		     			i--;				
     		     		}
     		     		
     		     		if ( (i-1>=0) and (plateau[i-1][j]==plateau[i][j])){	// si la case au dessus de la case[i][j] est égale à 
     		     			plateau[i-1][j]+=plateau[i][j];	               // case[i][j] on deplace la case[i][j] de 1 vers le haut
     		     			plateau[i][j]=0;
     		     			plateau[0][4]+=(plateau[i-1][j]);
     		     			iTemp=i;
     		     			
     		     			     
     		     			     /**AJUSTEMENT --- après la fusion , on déplace toutes les cases
					   		      du dessous (seulement celles qui sont dans la meme colonne)
					   		      vers le haut, une par une**/
     		     			     while((i+1<plateau.size()) and (plateau[i][j]==0)){   // on déplace de 1 vers le haut toutes les 
     		     			     plateau[i][j]=plateau[i+1][j];                        // cases en dessous de la case[i][j]
     		     			     plateau[i+1][j]=0;
     		     			     i++;
     		     			}i=iTemp;
     		     			
     		     			/** Apres la fusion on deplace la case resultante vers le haut tant que la case
					           vers le haut est libre**/
					          while( (i-2>=0) and (plateau[i-2][j]==0) ){
					               plateau[i-2][j]=plateau[i-1][j];
					               plateau[i-1][j]=0;
					               i--;
					          }
					         
					          /**RE-AJUSTEMENT --- après la fusion, le premier ajustement et la verification de deplacement 
					   		, on déplace toutes les cases du dessous (seulement celles qui sont dans 
					   		la meme colonne) vers le haut, une par une**/
     		     			while((i+1<plateau.size()) and (plateau[i][j]==0)){    // on déplace de 1 vers le haut toutes les 
     		     			     plateau[i][j]=plateau[i+1][j];                    // cases en dessous de la case[i][j]
     		     			     plateau[i+1][j]=0;
     		     			     i++;
     		     			}i=plateau.size();// on donne à i sa valeur maximale pour que a la prochaine 
     		     			                  // execution de la boucle on passe à la ligne suivante	
      		     		     
     		     		}
     		     									
     		     	}//fin deplacements et fusions pour i,j	
     		     }
          	}//fin parcours tableau
               return plateau;
}//fin fonction

//#################################################################################################################################################
/* Déplace les cases du plateau de jeu vers le bas puis fusionne les cases les plus vers le bas
qui sont l'une en dessous de l'autre et on la meme valeur */
Plateau deplacementBas(Plateau plateau){	
int iTemp=0;	          
	          for (int j=plateau.size()-1 ; j>=0 ; j--){        //parcours plateau de bas en haut et de gauche à droite
     		     for (int i=plateau.size()-1 ; i>=0 ; i--){

     		     	if(plateau[i][j]!=0){				                    // si la case [i][j] peut etre déplacée vers
     		     	     while( (i+1<plateau.size())and(plateau[i+1][j]==0) ){	//le bas, on la déplace
     		     			plateau[i+1][j]=plateau[i][j];
     		     			plateau[i][j]=0;	
     		     			i++;
     		     		}
     		     		
     		     		//fin deplacement
     		     		if ( (i+1<plateau.size()) and (plateau[i+1][j]==plateau[i][j]) ){    // si la case en dessous de la case[i][j] 
     		     			plateau[i+1][j]+=plateau[i][j];                                 // est égale à case[i][j] on deplace la 
					          plateau[i][j]=0;                                                // case[i][j] de 1 vers le bas
					          plateau[0][4]+=(plateau[i+1][j]);
					          iTemp=i;
					          
					          /**AJUSTEMENT --- après la fusion, on déplace toutes les cases
					   		 du dessus (seulement celles qui sont dans la meme colonne) 
					   		 vers le bas, une par une**/
					   		while((i-1>=0)and(plateau[i][j]==0)){
					 	          plateau[i][j]=plateau[i-1][j];
					 	          plateau[i-1][j]=0;
					   		     i--;
					   		}i=iTemp;
					          
					          /** Apres la fusion on deplace la case resultante vers le bas tant que la case
					           vers le bas est libre**/
					          while( (i+2<plateau.size()) and (plateau[i+2][j]==0) ){
					               plateau[i+2][j]=plateau[i+1][j];
					               plateau[i+1][j]=0;
					               i++;
					          }
					   		
					   		/**RE-AJUSTEMENT --- après la fusion, le premier ajustement et la verification de deplacement 
					   		, on déplace toutes les cases du dessus (seulement celles qui sont dans 
					   		la meme colonne) vers le bas, une par une**/
					   		while((i-1>=0)and(plateau[i][j]==0)){    // on déplace de 1 vers le bas toutes les
					 	          plateau[i][j]=plateau[i-1][j];      // cases au dessus de la case[i][j]
					 	          plateau[i-1][j]=0;
					   		     i--;
					   		}i=0; // on donne à i sa valeur minimale pour que a la prochaine 
     		     			      // execution de la boucle on passe à la ligne suivante	
					   			
     		     	     }	
     		          }//fin deplacements et fusions pour i,j	
          	
                    }
     	     }//fin parcours tableau
          return plateau;
     
}//fin fonction

//#################################################################################################################################################
/* Déplace les cases du plateau de jeu vers la droite puis fusionne les cases les plus vers la droite
qui sont à coté l'une en de l'autre(horizontalement) et on la meme valeur */
Plateau deplacementDroite(Plateau plateau){	
int jTemp=0;         
	          for (int i=plateau.size()-1 ; i>=0 ; i--){        //parcours plateau de droite à gauche et de bas en haut
     		     for (int j=plateau.size()-1 ; j>=0 ; j--){
     		     
     		     	if(plateau[i][j]!=0){				                      // si la case [i][j] peut etre déplacée vers
     		     		while( (j+1<plateau.size())and(plateau[i][j+1]==0) ){    //la droite, on la déplace
     		     			plateau[i][j+1]=plateau[i][j];	
					          plateau[i][j]=0;	
					          j++;	
     		     		}
     		     		
     		     		//fin déplacement
     		     		if ( (j+1<plateau.size()) and (plateau[i][j+1]==plateau[i][j]) ){    // si la case à droite de la case[i][j] 
     		     			plateau[i][j+1]+=plateau[i][j];	                             // est égale à case[i][j] on deplace la 
					          plateau[i][j]=0;                                                // case[i][j] de 1 vers la droite
					          plateau[0][4]+=(plateau[i][j+1]);
					          jTemp=j;
					          
					          /**AJUSTEMENT --- après la fusion , on déplace toutes les cases
					   		 de gauche (seulement celles qui sont dans la meme colonne)
					   		 vers la doite, une par une**/
					   		while((j-1>=0)and(plateau[i][j]==0)){    // on déplace de 1 vers la droite toutes les
					 	          plateau[i][j]=plateau[i][j-1];      // cases à gauche de la case[i][j]
					 	          plateau[i][j-1]=0;
					   		     j--;
					   		}j=jTemp; 
					         
					          /** Apres la fusion on deplace la case resultante vers la droite tant que la case
					           vers la droite est libre**/
					          while( (j+2<plateau.size()) and (plateau[i][j+2]==0) ){
					               plateau[i][j+2]=plateau[i][j+1];
					               plateau[i][j+1]=0;
					               j++;
					          }
					   		
					   		/**RE-AJUSTEMENT --- après la fusion, le premier ajustement et la verification de deplacement 
					   		, on déplace toutes les cases de gauche (seulement celles qui sont dans 
					   		la meme colonne) vers la doite, une par une**/
					   		while((j-1>=0)and(plateau[i][j]==0)){
					 	          plateau[i][j]=plateau[i][j-1];
					 	          plateau[i][j-1]=0;
					   		     j--;
					   		}j=0; // on donne à j sa valeur minimale pour que a la prochaine 
     		     			      // execution de la boucle on passe à la colonne suivante									
     		     	     }	
     		          }//fin deplacements et fusions pour i,j	
          	
                    }
     	     }//fin parcours tableau
               return plateau;
     
}//fin fonction

//#################################################################################################################################################
/* Déplace les cases du plateau de jeu vers la gauche puis fusionne les cases les plus vers la gauche
qui sont à coté l'une en de l'autre(horizontalement) et on la meme valeur */
Plateau deplacementGauche(Plateau plateau){	
int jTemp=0;

	          for (int i=0 ; i< plateau.size() ; i++){          //parcours plateau de gauche à droite et de haut en bas
     		     for (int j=0 ; j<plateau.size() ; j++){		

     		     	if(plateau[i][j]!=0){				                        // si la case [i][j] peut etre déplacée vers
     		     		while( (j-1<plateau.size())and(plateau[i][j-1]==0) ){      // la gauche, on la déplace
     		     			plateau[i][j-1]=plateau[i][j];	
					          plateau[i][j]=0;	
					          j--;	
     		     		}
     		     		
     		     		if ( (j-1<plateau.size()) and (plateau[i][j-1]==plateau[i][j]) ){   // si la case à gauche de la case[i][j]
     		     			plateau[i][j-1]+=plateau[i][j];	                            // est égale à case[i][j] on deplace la
					          plateau[i][j]=0;                                               // case[i][j] de 1 vers la gauche
					          plateau[0][4]+=(plateau[i][j-1]);
					          jTemp=j;
					         
					          /**AJUSTEMENT --- après la fusion, on déplace toutes les cases
					   		 de droite (seulement celles qui sont dans la meme colonne)
					   		 vers la gauche, une par une**/
					          while((j+1<plateau.size()) and (plateau[i][j]==0)){    // on déplace de 1 vers la gauche toutes les
     		     			     plateau[i][j]=plateau[i][j+1];                    // cases à droite de la case[i][j]
     		     			     plateau[i][j+1]=0;
     		     			     j++;
     		     			}j=jTemp;	
     		     			
     		     			/** Apres la fusion on deplace la case resultante vers la gauche tant que la case
					           vers la gauche est libre**/
					          while( (j-2>=0) and (plateau[i][j-2]==0) ){
					               plateau[i][j-2]=plateau[i][j-1];
					               plateau[i][j-1]=0;
					               j--;
					          }
					          
					          /**RE-AJUSTEMENT --- après la fusion, le premier ajustement et la verification de deplacement 
					   		, on déplace toutes les cases de droite (seulement celles qui sont dans 
					   		la meme colonne) vers la gauche, une par une**/
     		     			while((j+1<plateau.size()) and (plateau[i][j]==0)){
     		     			     plateau[i][j]=plateau[i][j+1];
     		     			     plateau[i][j+1]=0;
     		     			     j++;
     		     			}j=plateau.size(); // on donne à j sa valeur maximale pour que a la prochaine 
     		     			                   //execution de la boucle on passe à la colonne suivante							
     		     	     }	
     		          }//fin deplacements et fusions pour i,j	
          	
                    }
     	     }//fin parcours tableau
          return plateau;
     
}//fin fonction

//#################################################################################################################################################
// vérifie si un plateau est gagnant : retourne 'vrai' si un des chiffres du plateau de jeu est "2048" et retourne "faux" sinon
bool estGagnant(Plateau plateau){
     for (int i=0; i<plateau.size(); i++){
          for (int j=0; j<plateau.size(); j++){
               if (plateau[i][j]==2048)return true;
          }
     }return false;
}

//#################################################################################################################################################
// vérifie si le jeu est terminé : si aucun déplacement ne change le plateau, retourne 'vrai', "faux" sinon
bool estTermine(Plateau plateau){
     
     if ((plateau==(deplacementHaut(plateau))) and (plateau==(deplacementDroite(plateau))) and (plateau==(deplacementBas(plateau))) and (plateau==(deplacementGauche(plateau)))){
         return true;
     }
     return false;
}

//#################################################################################################################################################

                                   /*-------------------------------------------------*/
                                   /*--Fonctions pour la fonction dessine(Plateau g)--*/
                                   /*-------------------------------------------------*/

//############################################################################
//mesure la taille d'un entier (nombres de chiffres composant un nombre)
int taille_int(int n) {
  int res = 1;
 
  if(n == 0) {
    return 0;
  }
  
  while(n >= 10) {
    n /= 10;
    ++res;
  }
 
  return res;
}

//############################################################################
// retourne une chaine de 'n' espace(s) (n est l'argument int de la fonction)
string espace(int n) {
  string chaine="";
  while(n > 0) {
    chaine+=" ";
    n--;
  }return chaine;
}

//############################################################################
// convertit un entier en chaine de caractère
string convertInt(int number)
{
    if (number == 0)
        return "0";
    string temp="";
    string returnvalue="";
    while (number>0)
    {
        temp+=number%10+48; // '48' est le code ASCII de 0, on ajoute donc le code ASCII du reste de la division de 'number' par 10
        number/=10;         // et on divise 'number' par 10
                            
    }
    for (int i=0;i<temp.length();i++)
        returnvalue+=temp[temp.length()-i-1];
    return returnvalue;
}

//#################################################################################################################################################
                                             
                                             /*-------------------------------*/
                                             /*--Fonction dessine(Plateau g)--*/
                                             /*-------------------------------*/

// crée la chaine de caractère que l'on va afficher à l'écran, correspondant au 'Plateau g'
string dessine (Plateau g){
     
int espaces_devant = 0;
int espaces_derriere = 0;
string chaine = "";

     for(int i=0; i<g.size(); i++){
          //parcours toutes les lignes du Plateu une par une
          for(int l=0; l<25; l++)chaine+="*";
          chaine+="\n";
          //ajoute la ligne d'étoiles entre chaque ligne du tableau
          
          for(int j=0; j<g.size(); j++){
               espaces_devant = (5-taille_int(g[i][j]))/2; //calcule le nombre d'espaces à mettre avant le chiffre
               espaces_derriere = espaces_devant + 1 - (taille_int(g[i][j]))%2; //calcule le nombre d'espaces à mettre après le chiffre
               chaine+="*"; // ajoute l'étoile qui sépare chaque caractère d'une même ligne
               chaine+=(espace(espaces_devant)); //ajoute le(s) espace(s) à mettre avant le chiffre
               if (g[i][j]!=0){
                    chaine += convertInt(g[i][j]); //ajoute le chiffre
               }
               chaine+=(espace(espaces_derriere));   // ajoute le(s) espace(s) à mettre après le chiffre
               
                 
          }
          

          chaine+="*"; //ajoute l'étoile à la fin d'une ligne
          chaine+="\n"; //passe à la ligne d'après
          
     }
     for(int n=0; n<25; n++)chaine+="*"; // affiche la dernière ligne d'étoiles
     chaine+="\n"; //passe à la ligne d'après
     return chaine; //retourne la chaine 
        }

//#################################################################################################################################################
int score(Plateau plateau){
	return plateau[0][4];
}

//#################################################################################################################################################
//Fait un déplacement vers haut, bas, droite ou gauche quand l'utilisateur appuie sur la flèche haut, bas, droite ou gauche
Plateau deplacement(Plateau plateau, int direction){               
               switch (direction){
        	
	        	case KEY_UP:
	          	{
	          		plateau=deplacementHaut(plateau);
	          		break;
	        	}
	            case KEY_RIGHT:
	            {
	            	plateau=deplacementDroite(plateau);
	            	break;
	            }
	            case KEY_DOWN:
	            {
	            	plateau=deplacementBas(plateau);
	            	break;
	            }
	            case KEY_LEFT:
	            {
	            	plateau=deplacementGauche(plateau);
	            	break;
	            }
	            default:
	            {
	            	addstr("erreur deplacement");
	            }
	            }return plateau;
}
//#################################################################################################################################################

//#################################################################################################################################################

//#################################################################################################################################################

//#################################################################################################################################################

//#################################################################################################################################################

//#################################################################################################################################################

//#################################################################################################################################################

//#################################################################################################################################################



