#include <iostream>
using namespace std;
const float epsilon= 1e-12;

/** saisie de la partie entière et imaginaire d'un nombre 
 *  @param '&reel_saisie' un nombre positif 
 *  @param '&imaginaire_saisie' un nombre positif 
**/
void saisie(float &reel_saisie,float &imaginaire_saisie){
	
	cout<<"Entrez la partie réelle"<<endl;
	cin>>reel_saisie;
	cout<<"Entrez la partie imaginaire"<<endl;
	cin>>imaginaire_saisie;
}


/** affiche un nombre imaginaire sour la forme 'a+ib' 
 *  @param '&reel_affiche' un nombre positif 
 *  @param '&imaginaire_affiche' un nombre positif 
**/
void affiche(float &reel_affiche, float imaginaire_affiche){
	
	cout<<reel_affiche<<" + ("<<imaginaire_affiche<<")i"<<endl;
}


/** calcule la somme de deux nombres complexes 
 *  @param 'reel_somme_1' un nombre positif
 *  @param 'imaginaire_somme_1' un nombre positif
 *  @param 'reel_somme_2' un nombre positif
 *  @param 'imaginaire_somme_2' un nombre positif 
 *  @param '&res_reel_somme' un nombre positif
 *  @param '&res_imaginaire_somme' un nombre positif 
**/	
void somme(float reel_somme_1, float imaginaire_somme_1,float reel_somme_2, float imaginaire_somme_2, float &res_reel_somme, float &res_imaginaire_somme){
	
	res_reel_somme = reel_somme_1+reel_somme_2;
	res_imaginaire_somme = imaginaire_somme_1 + imaginaire_somme_2;	
}

/** calcule la soustraction de deux nombres complexes 
 *  @param 'reel_soustraction_1' un nombre positif
 *  @param 'imaginaire_soustraction_1' un nombre positif
 *  @param 'reel_soustraction_2' un nombre positif
 *  @param 'imaginaire_soustraction_2' un nombre positif 
 *  @param '&res_reel_soustraction' un nombre positif
 *  @param '&res_imaginaire_soustraction' un nombre positif 
**/	
void soustraction(float reel_soustraction_1, float imaginaire_soustraction_1,float reel_soustraction_2, float imaginaire_soustraction_2, float &res_reel_soustraction, float &res_imaginaire_soustraction){

	res_reel_soustraction = reel_soustraction_1-reel_soustraction_2;
	res_imaginaire_soustraction = imaginaire_soustraction_1 - imaginaire_soustraction_2;
}

/** calcule le produit de deux nombres complexes
 *  @param 'reel_produit_1' un nombre positif
 *  @param 'imaginaire_produit_1' un nombre positif
 *  @param 'reel_produit_2' un nombre positif
 *  @param 'imaginaire_produit_2' un nombre positif 
 *  @param '&res_reel_produit' un nombre positif
 *  @param '&res_imaginaire_produit' un nombre positif 
**/	
void produit(float &reel_produit_1, float &imaginaire_produit_1,float &reel_produit_2, float &imaginaire_produit_2, float &res_reel_produit, float &res_imaginaire_produit){

	res_reel_produit = (reel_produit_1*reel_produit_2) - (imaginaire_produit_1*imaginaire_produit_2);
	res_imaginaire_produit = (reel_produit_1*imaginaire_produit_2) + (imaginaire_produit_1*reel_produit_2);
}

/** retourne la norme au carré d'un nombre complexe
 *  @param 'reel' un nombre positif
 *  @param 'imaginaire' un nombre positif
 *  @return un nombre positif
**/
float norme_carree (float reel, float imaginaire){
	return (reel*reel)+(imaginaire*imaginaire);
}

/** calcule l'inverse d'un nombre complexe
 *  @param '&reel_inverse' un nombre positif
 *  @param '&imaginaire_inverse' un nombre positif
**/
void inverse( float &reel_inverse, float &imaginaire_inverse){
	float res_reel_inverse, res_imaginaire_inverse;
	res_reel_inverse= reel_inverse / norme_carree(reel_inverse,imaginaire_inverse);
	res_imaginaire_inverse=  -imaginaire_inverse / norme_carree(reel_inverse,imaginaire_inverse); 
	reel_inverse=res_reel_inverse;
	imaginaire_inverse=res_imaginaire_inverse;
}

/** calcule la division de deux nombres complexes
 *  @param 'reel1' un nombre positif
 *  @param 'imaginaire1' un nombre positif
 *  @param 'reel2' un nombre positif
 *  @param 'imaginaire2' un nombre positif
 *  @param '&res_reel_div' un nombre positif
 *  @param '&res_imaginaire_div' un nombre positif
**/
void division(float reel1, float imaginaire1, float reel2, float imaginaire2, float &res_reel_div, float &res_imaginaire_div ){
	inverse(reel2, imaginaire2);
	produit(reel1,imaginaire1,reel2,imaginaire2,res_reel_div,res_imaginaire_div);
}


/** calcule la racine carrée d'un nombre complexe par la méthode de Héron 
 *  @param 'reel_racine' un nombre positif
 *  @param 'imaginaire_racine' un nombre positif
 *  @param '&res_reel_racine' un nombre positif
 *  @param '&res_imaginaire_racine' un nombre positif
**/
void racine(float reel_racine, float imaginaire_racine, float &res_reel_racine, float &res_imaginaire_racine){
    float approximation_reel,approximation_imaginaire,approximation,approximation_numerateur,approximation_denominateur,Un_reel,Un_imaginaire;
    
    if((reel_racine < 0)and(imaginaire_racine==0)){    		
    		Un_imaginaire=epsilon;
    }else{
    		Un_imaginaire=imaginaire_racine;
    }
    Un_reel=reel_racine;
    
    do{
        division(reel_racine,imaginaire_racine,Un_reel,Un_imaginaire,res_reel_racine,res_imaginaire_racine);
        somme(Un_reel,Un_imaginaire,res_reel_racine,res_imaginaire_racine,res_reel_racine,res_imaginaire_racine);
        Un_reel=res_reel_racine/2;
        Un_imaginaire=res_imaginaire_racine/2;
        
        //----------------------------------
        
 	   
 	   produit(Un_reel,Un_imaginaire,Un_reel,Un_imaginaire,approximation_reel,approximation_imaginaire);
 	   soustraction(approximation_reel,approximation_imaginaire,reel_racine,imaginaire_racine,approximation_reel,approximation_imaginaire);
 	   approximation_numerateur=norme_carree(approximation_reel,approximation_imaginaire);   
    	   approximation_denominateur=norme_carree(reel_racine,imaginaire_racine);
    	   approximation=approximation_numerateur/approximation_denominateur;
    }while(not(approximation < epsilon));
    	res_reel_racine=Un_reel;
    	res_imaginaire_racine=Un_imaginaire;
}


int main(){
	float reel_1,imaginaire_1,reel_2,imaginaire_2,res_reel,res_imaginaire,res_reel_carree,res_imaginaire_carree;
	saisie(reel_1,imaginaire_1);
	cout<<"Le nombre complexe que vous avez saisi est : ";
	affiche(reel_1,imaginaire_1);
	cout<<endl;
	
	racine(reel_1,imaginaire_1,res_reel,res_imaginaire);
	cout<<"racine du nombre complexe : "<<endl;
	affiche(res_reel,res_imaginaire);
	produit(res_reel,res_imaginaire,res_reel,res_imaginaire,res_reel_carree,res_imaginaire_carree);
	cout<<"racine au carree : "<<endl;
	affiche(res_reel_carree,res_imaginaire_carree);
	
	/*
	inverse(reel_1,imaginaire_1);
	cout<<"inverse du nombre imaginaire : ";
	affiche(reel_1,imaginaire_1);
	cout<<endl;
	
	
	saisie(reel_2,imaginaire_2);
	cout<<"Le nombre imaginaire que vous avez saisi est : ";
	affiche(reel_2,imaginaire_2);
	cout<<endl;
	
	
	inverse(reel_2,imaginaire_2);
	cout<<"inverse du nombre imaginaire : ";
	affiche(reel_2,imaginaire_2);
	cout<<endl;
	
	somme(reel_1,imaginaire_1,reel_2,imaginaire_2,res_reel,res_imaginaire);
	cout<<"Somme des deux inverses de nombres imaginaires : ";
	affiche(res_reel,res_imaginaire);
	cout<<endl;
	
	produit(reel_1,imaginaire_1,reel_2,imaginaire_2,res_reel,res_imaginaire);
	cout<<"produit des deux inverses de nombres imaginaires : ";
	affiche(res_reel,res_imaginaire);
	*/
	
	
	cout<<endl;
}






