#include "2048.h"
//----------INFRASTRUCTURE MINIMALE DE TEST-----------
#define ASSERT(test) if (!(test)) cout << "Test failed in file " << __FILE__ << " line " << __LINE__ << ": " #test << endl

                                             /*--------------------------------*/
                                             /*---    FONCTIONS DE TEST     ---*/
                                             /*--------------------------------*/


//###################################################################################################
Plateau test_deplacementHaut_1 =   { {(8),(2),(4),(4),(0)},
                                     {(4),(2),(2),(2)},
                                     {(2),(4),(2),(0)},
                                     {(2),(8),(8),(2)},
                                   };
                                 
Plateau test_deplacementHaut_2 =   { {(8),(4),(4),(4),(16)},
                                     {(4),(4),(4),(4)},
                                     {(4),(8),(8),(0)},
                                     {(0),(0),(0),(0)},
                                   };

Plateau test_deplacementDroite_1 = { {(2),(0),(4),(2),(0)},
                                     {(4),(0),(4),(8)},
                                     {(8),(8),(0),(16)},
                                     {(8),(8),(16),(2)},
                                   };
                                 
Plateau test_deplacementDroite_2 = { {(0),(2),(4),(2),(40)},
                                     {(0),(0),(8),(8)},
                                     {(0),(0),(16),(16)},
                                     {(0),(16),(16),(2)},
                                   };

Plateau test_deplacementBas_1 =    { {(2),(2 ),(4),(0),(0)},
                                     {(0),(0 ),(4),(4)},
                                     {(2),(16),(2),(2)},
                                     {(0),(2 ),(2),(2)},
                                   };
                                 
Plateau test_deplacementBas_2 =    { {(0),(0 ),(0),(0),(12)},
                                     {(0),(2 ),(4),(0)},
                                     {(0),(16),(4),(4)},
                                     {(4),(2 ),(4),(4)},
                                   };
                                   
Plateau test_deplacementGauche_1 = { {(0),(8),(0),(8),(0)},
                                     {(2),(0),(2),(4)},
                                     {(2),(2),(4),(2)},
                                     {(0),(2),(2),(4)},
                                   };
                                 
Plateau test_deplacementGauche_2 = { {(16),(0),(0),(0),(28)},
                                     {(4),(4),(0),(0)},
                                     {(4),(4),(2),(0)},
                                     {(4),(4),(0),(0)},
                                   };        
                                   

Plateau test_estGagnant_1  =      { {(16),(0),(0),(0),(0)},
                                     {(4),(4),(0),(0)},
                                     {(2048),(4),(2),(0)},
                                     {(4),(4),(0),(0)},
                                   };

Plateau test_estGagnant_2  =      { {(16),(0),(0),(0),(0)},
                                     {(4),(4),(0),(0)},
                                     {(8),(4),(2),(0)},
                                     {(4),(4),(0),(2048)},
                                   };

Plateau test_estGagnant_3  =      { {(0),(0),(0),(0),(0)},
                                     {(0),(0),(2048),(0)},
                                     {(0),(0),(0),(0)},
                                     {(0),(0),(0),(0)},
                                   };              
                                   
                                   
Plateau test_estTermine_1  =      {  {(16),(8),(2 ),(4 ),(0)},
                                     {(4 ),(2),(16),(8 )},
                                     {(32),(4),(2 ),(4 )},
                                     {(2 ),(8),(32),(16)},
                                   };

Plateau test_estTermine_2  =      {  {(2),(4),(2),(4),(0)},
                                     {(4),(2),(4),(2)},
                                     {(2),(4),(2),(4)},
                                     {(4),(2),(4),(2)},
                                   };

Plateau test_estTermine_3  =      {  {(1024),(8),(512),(32),(0)},
                                     {(512 ),(4),(16 ),(2)},
                                     {(256 ),(2),(8  ),(4)},
                                     {(128 ),(8),(4  ),(16)},
                                   };                                                                                                                                                                 
//###################################################################################################
bool test_deplacement(int direction, Plateau p, Plateau p2){
     
     switch(direction){     
          case HAUT:
          {     
               if(p2==deplacementHaut(p)){
                    return true;
               }return false;
          }
          case DROITE:
          {     
               if(p2==deplacementDroite(p)){
                    return true;
               }return false;
          }
          case BAS:
          {     
               if(p2==deplacementBas(p)){
                    return true;
               }return false;
          }
          case GAUCHE:
          {     
               if(p2==deplacementGauche(p)){
                    return true;
               }return false;
          }
          default:
          {
               cout<<"ERREUR dans la fonction 'test_deplacement' dans 'test_2048.cpp' "<<endl;
          }
          
     }          
}                                 
//###################################################################################################
bool test_estGagnant(Plateau p,Plateau p2,Plateau p3){
     if ((estGagnant(p))and(estGagnant(p2))and(estGagnant(p3))){
          return true;
     }else{
          return false;
     }
}
//###################################################################################################
bool test_estTermine(Plateau p,Plateau p2,Plateau p3){
     if ((estTermine(p))and(estTermine(p2))and(estTermine(p3))){
          return true;
     }else{
          return false;
     }
}
//###################################################################################################

//###################################################################################################

//###################################################################################################

//###################################################################################################


main(){
     //-- tests des fonctions de déplacement
     ASSERT(test_deplacement(HAUT,test_deplacementHaut_1,test_deplacementHaut_2));
     ASSERT(test_deplacement(DROITE,test_deplacementDroite_1,test_deplacementDroite_2));  
     ASSERT(test_deplacement(BAS,test_deplacementBas_1,test_deplacementBas_2));  
     ASSERT(test_deplacement(GAUCHE,test_deplacementGauche_1,test_deplacementGauche_2));  
     
     //-- tests de la fonction estGagnant()
     ASSERT(test_estGagnant(test_estGagnant_1,test_estGagnant_2,test_estGagnant_3));
     
     //-- tests de la fonction estTermine()
     ASSERT(test_estTermine(test_estTermine_1,test_estTermine_2,test_estTermine_3));
}
