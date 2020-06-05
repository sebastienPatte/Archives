#include "2048.h"

     
//###################################################################################################
                                             /*--------------------------------*/
                                             /*---Peut servir pour des tests---*/
                                             /*--------------------------------*/
/*
int afficheTableau(Plateau P){
	for (int i=0; i<P.size();i++){
		for (int j=0; j<P[i].size();j++){
	     	cout<<P[i][j]<<" ";
		}cout<<endl;			
	}
}
*/

/*
Plateau deplacement(Plateau plateau, int direction){
	if (direction==8){
		return deplacementHaut(plateau);		
	}else if (direction==2){
		return deplacementBas(plateau);
	}else if (direction==6){
		return deplacementDroite(plateau);
	}else if (direction==4){
		return deplacementGauche(plateau);
	}else{
		cout<<"erreur veuillez reesayer"<<endl;
		return plateau ;
	}	
	
}
*/
//###################################################################################################

                                             /*--------------------------------*/
                                             /*---           MAIN           ---*/
                                             /*--------------------------------*/


int main(){
    
    /* Initialisation */
    srand( time( NULL ) ); /*initialise la valeur maximale de 'rand()' au temps écoulé depuis le lancement du programmme. 
                             la valeur maximale de 'rand()' augmente donc en fonction du temps éccoulé depuis le lancement du programme */
    string chaine_score;
    int direction;
    initscr();
    keypad(stdscr, true);
    Plateau p=plateauInitial();
    /* Fin Initialisation */
    
    /* pour tester Victoire()
    //p[3][3]=1024;
    //p[3][2]=1024;*/
    
        	
    addstr((dessine(p)).c_str()); // '.c_str()' récupère le 'char*' dans une 'string' 
    
    
    do {
    		
               chaine_score = convertInt(score(p));
   		 	addstr ("Score: ");
    		     addstr (chaine_score.c_str());
    
            
	        	direction=getch();
	        	p=Actualisation(deplacement(p,direction));
	          
	          //pour verifier ce qui ne vas pas dans les tests
	          //p=deplacement(p,direction);
				
			  
				        	
        		
            
        	
        	clear();
        	
        	addstr((dessine(p)).c_str()); //le ".c_str()" récupère le const char* contenu dans une string 

               if(estGagnant(p)){
               	addstr("Bravo, vous avez gagné :)\nAppuyez sur F5 pour quitter\n");
				
				//REJOUER LA PARTIE - BROUILLON
				/*
				addstr("appuyez sur F4 pour continuer la partie ou F5 pour quitter le jeu");
				do{
					continuer=getch();
					if (continuer==KEY_F(5))break;
				}while(continuer!=KEY_F(4));
				*/
				//FIN BROUILLON
		     }        
               
               if(estTermine(p)){
            		addstr("Vous avez perdu :(\nAppuyez sur F5 pour quitter\n");
               }
          
        	    
        }while (direction != KEY_F(5));
    
    endwin();
    return 0;
}

