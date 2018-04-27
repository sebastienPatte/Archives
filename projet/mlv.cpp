#include <MLV/MLV_all.h>
#include <vector>
#include "projet.cpp"
using namespace std;
        

	
//
// exit_function est la fonction de call back qui sera appelée par la librairie
// MLV au moment où l'utilisateur demandera à l'application de s'arrêter 
// en utilisant un signal d'arrêt ou en appuyant sur la croix de la 
// fenêtre.
//
void exit_function( void* data ){
        int* arret = (int*)  data;
        *arret = 1;
}

// pose un sucre sur la case contenant la position de la souris si l'utilisateur clique sur le clique gauche
void poseSucre(Grille &T, int largeur_case, int hauteur_case){
	int x=0, y=0;
	if(MLV_get_mouse_button_state(MLV_BUTTON_LEFT)==MLV_PRESSED){
		MLV_get_mouse_position(&x,&y);
		x/= largeur_case;
		y/= hauteur_case;
		if(placeVide(T[x][y])) T[x][y].sucre=5;

	}
}

// affiche tout les éléments de la grille dans la fenetre MLV
void affichage( int &width, int &height, int nb_case_largeur, int largeur_case, int hauteur_case, int dureePheromonesSucre, MLV_Image *fourmiHaut, MLV_Image *fourmiDroite, MLV_Image *fourmiBas, MLV_Image *fourmiGauche, vector<Fourmi> tabFourmis,  Grille T ){
    //
    // On nettoie l'écran
    //
    MLV_clear_window( MLV_COLOR_WHITE );
    /*
    ce qu'il y a a afficher
    */
                 
        // INSERER ICI LES CONDITIONS D AFFICHAGE DE LA COULEUR DU TABLEAU COULEUR (COMME DANS LA FONCTION AFFICHE GRILLE)
        for (int i=0; i<nb_case_largeur; i++){
            for (int j=0; j<nb_case_largeur; j++){
                      
                if(T[i][j].sucre!=0){
                	//colorie la case en orange
                    MLV_draw_filled_rectangle(i*largeur_case, j*hauteur_case, largeur_case, hauteur_case, MLV_COLOR_ORANGE);
                              
                    //dessinne les carrés de sucres dans les cases oranges
                    MLV_draw_filled_rectangle( (i*largeur_case)+(largeur_case/2)-(largeur_case/8), (j*hauteur_case)+(hauteur_case/2)-(hauteur_case/8), largeur_case/4, hauteur_case/4, MLV_COLOR_WHITE);
                        if(T[i][j].sucre>=2){
                      		MLV_draw_filled_rectangle( (i*largeur_case)+(largeur_case/6)-(largeur_case/8), (j*hauteur_case)+(hauteur_case/6)-(hauteur_case/8), largeur_case/4, hauteur_case/4, MLV_COLOR_WHITE);
                           		if(T[i][j].sucre>=3){
                           			MLV_draw_filled_rectangle( (i*largeur_case)+(largeur_case*5/6)-(largeur_case/8), (j*hauteur_case)+(hauteur_case*5/6)-(hauteur_case/8), largeur_case/4, hauteur_case/4, MLV_COLOR_WHITE);
                           			if(T[i][j].sucre>=4){
                           				MLV_draw_filled_rectangle( (i*largeur_case)+(largeur_case/6)-(largeur_case/8), (j*hauteur_case)+(hauteur_case*5/6)-(hauteur_case/8), largeur_case/4, hauteur_case/4, MLV_COLOR_WHITE);
                           				if(T[i][j].sucre==5) MLV_draw_filled_rectangle( (i*largeur_case)+(largeur_case*5/6)-(largeur_case/8), (j*hauteur_case)+(hauteur_case/6)-(hauteur_case/8), largeur_case/4, hauteur_case/4, MLV_COLOR_WHITE);
                              		
                          			}
                           		}		
                        }
                }else{
                    if(T[i][j].nid){
                        MLV_draw_filled_rectangle(i*largeur_case, j*hauteur_case, largeur_case, hauteur_case, MLV_COLOR_BLUE);
                    }else{
              			if(T[i][j].pheromonesSucre!=0){
              				MLV_Color couleurPheromoneSucre= MLV_rgba  (   100 +((T[i][j].pheromonesSucre*155)/dureePheromonesSucre) 	, 0 , 0 , 255);
              				MLV_draw_filled_rectangle(i*largeur_case, j*hauteur_case, largeur_case, hauteur_case, couleurPheromoneSucre);	
              						
              			}else{
              				MLV_Color couleurPheromoneNid= MLV_rgba  (   0, T[i][j].pheromoneNid*255, 0 , 255);
              				MLV_draw_filled_rectangle(i*largeur_case, j*hauteur_case, largeur_case, hauteur_case, couleurPheromoneNid);
                        }
                    }
                }
                if(T[i][j].indiceFourmi!=-1){
                    // MLV_draw_filled_rectangle(i*largeur_case, j*hauteur_case, largeur_case, hauteur_case, MLV_COLOR_WHITE);
                    // On teste l'orientation de la fourmi et on rotate son image selon celle-ci
                    if(tabFourmis[T[i][j].indiceFourmi].orientation == 2) MLV_draw_image(fourmiHaut, i*largeur_case, j*hauteur_case);
                    if(tabFourmis[T[i][j].indiceFourmi].orientation == 1) MLV_draw_image(fourmiDroite, i*largeur_case, j*hauteur_case);
                    if(tabFourmis[T[i][j].indiceFourmi].orientation == 0) MLV_draw_image(fourmiBas, i*largeur_case, j*hauteur_case);
                    if(tabFourmis[T[i][j].indiceFourmi].orientation == 3) MLV_draw_image(fourmiGauche, i*largeur_case, j*hauteur_case);

                    
                    if(tabFourmis[T[i][j].indiceFourmi].porteSucre){
                      	MLV_draw_filled_rectangle((i*largeur_case)+(largeur_case/2)-(largeur_case/8), (j*hauteur_case)+(largeur_case/2)-(hauteur_case/8), largeur_case/4, hauteur_case/4, MLV_COLOR_WHITE);
                    }
                }
                      
            }
        }

              // Pour colorer en RGB (pour les phéromones) :
              //"MLV_Color MLV_rgba  (   Uint8   red,Uint8   green,Uint8   blue,Uint8   alpha )"
    MLV_actualise_window();
}

int main(){
		MLV_Image *fourmiHaut, *fourmiDroite, *fourmiBas, *fourmiGauche;
        int arret = 0;
        int width = 650, height = 650;
        int reglages;
        int nbFourmis=12;
        int tailleCarte=30;
        int nbSucres=5;
        int vitesseAnimation=7;
        int dureePheromonesSucre=50;

        // REGLAGES
        do{
        	cout<<"Utiliser les réglages par défaut ? (1/0)"<<endl;
        	cin>>reglages;
        	if(reglages==0){
       			do{
        			cout<<"tailleCarte=? (nombre pair entre 6 et 60)"<<endl;
        			cin>>tailleCarte;
        		}while(!(tailleCarte >=6 && tailleCarte <=60 && tailleCarte%2 == 0));
        
    
        		do{
        			cout << "nbSucres=? (<=" << tailleCarte << ")" << endl;
        			cin>>nbSucres;
        		}while(nbSucres > tailleCarte); 
        		
        		do{
        			cout << "vitesseAnimation=? (entre 1 et 10)"<< endl;
        			cin>>vitesseAnimation;
        		}while(!(vitesseAnimation >=1 && vitesseAnimation <=10));

        		do{
        			cout << "duree de vie des Pheromones de Sucre =? (entre 1 et 255)"<< endl;
        			cin>>dureePheromonesSucre;
        		}while(!(dureePheromonesSucre >=1 && dureePheromonesSucre <=255));

        	}
        }while(reglages!=0 && reglages!=1);
        
        // INITIALISATION
        vector<Fourmi> tabFourmis(nbFourmis);
        Grille T(tailleCarte);
        initPlateau(nbSucres,tabFourmis,T);

        int nb_case_largeur=T.size();
        int largeur_case=width/T.size();
        int hauteur_case=height/T.size();
//      Mise à jour de width et height pour que la fenetre fasse pile la taille de la grille
        width=nb_case_largeur*largeur_case;
        height=nb_case_largeur*hauteur_case;

        //
        // On enregistre la fonction de call back exit_function
        // dans la librairie MLV.
        // Cette ligne doit toujours précéder l'appel de la fonction
        // MLV_create_window
        //
        MLV_execute_at_exit( exit_function, &arret );
        //
        // Créé la fenêtre et l'affiche.
        //
        MLV_create_window( "Projet Fourmis", "exit", width, height );
        
        fourmiHaut = MLV_load_image("fourmiHaut.png");
        MLV_resize_image(fourmiHaut,largeur_case,hauteur_case);
		
		fourmiDroite = MLV_load_image("fourmiDroite.png");
        MLV_resize_image(fourmiDroite,largeur_case,hauteur_case);
        
        fourmiBas = MLV_load_image("fourmiBas.png");
        MLV_resize_image(fourmiBas,largeur_case,hauteur_case);
        
        fourmiGauche = MLV_load_image("fourmiGauche.png");
        MLV_resize_image(fourmiGauche,largeur_case,hauteur_case);
        

        //
        // Tant que l'utilisateur ne demande pas un arret du programme,
        // on actualise l'affichage de l'écran.
        //
        while( ! arret ){

                
                unTour(dureePheromonesSucre,tabFourmis,T);
                MLV_wait_milliseconds((11-vitesseAnimation)*50);  

                poseSucre(T,largeur_case, hauteur_case);
                affichage( width, height, nb_case_largeur, largeur_case, hauteur_case, dureePheromonesSucre, fourmiHaut,fourmiDroite,fourmiBas,fourmiGauche, tabFourmis, T );

        }
        
    // Ferme la fenêtre
    //
    MLV_free_window();
    return 0;
}

