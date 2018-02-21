#include <iostream>
using namespace std;
const float epsilon= 1e-12;


void saisie(float &reel_saisie,float &complexe_saisie){
	
	cout<<"Entrez la partie réelle"<<endl;
	cin>>reel_saisie;
	cout<<"Entrez la partie complexe"<<endl;
	cin>>complexe_saisie;
}


void affiche(float &reel_affiche, float complexe_affiche){
	
	cout<<reel_affiche<<" + ("<<complexe_affiche<<")i"<<endl;
}

	
void somme(float reel_somme_1, float complexe_somme_1,float reel_somme_2, float complexe_somme_2, float &res_reel_somme, float &res_complexe_somme){
	
	res_reel_somme = reel_somme_1+reel_somme_2;
	res_complexe_somme = complexe_somme_1 + complexe_somme_2;	
}

void soustraction(float reel_soustraction_1, float complexe_soustraction_1,float reel_soustraction_2, float complexe_soustraction_2, float &res_reel_soustraction, float &res_complexe_soustraction){

	res_reel_soustraction = reel_soustraction_1-reel_soustraction_2;
	res_complexe_soustraction = complexe_soustraction_1 - complexe_soustraction_2;
}

void produit(float &reel_produit_1, float &complexe_produit_1,float &reel_produit_2, float &complexe_produit_2, float &res_reel_produit, float &res_complexe_produit){

	res_reel_produit = (reel_produit_1*reel_produit_2) - (complexe_produit_1*complexe_produit_2);
	res_complexe_produit = (reel_produit_1*complexe_produit_2) + (complexe_produit_1*reel_produit_2);
}

float norme_carree (float reel, float complexe){
	return (reel*reel)+(complexe*complexe);
}

void inverse( float &reel_inverse, float &complexe_inverse){
	float res_reel_inverse, res_complexe_inverse;
	res_reel_inverse= reel_inverse / norme_carree(reel_inverse,complexe_inverse);
	res_complexe_inverse=  -complexe_inverse / norme_carree(reel_inverse,complexe_inverse); 
	reel_inverse=res_reel_inverse;
	complexe_inverse=res_complexe_inverse;
}

void division(float reel1, float complexe1, float reel2, float complexe2, float &res_reel_div, float &res_complexe_div ){
	inverse(reel2, complexe2);
	produit(reel1,complexe1,reel2,complexe2,res_reel_div,res_complexe_div);
}


void racine(float reel_racine, float complexe_racine, float &res_reel_racine, float &res_complexe_racine){
    float approximation_reel,approximation_complexe,approximation,approximation_numerateur,approximation_denominateur,Un_reel,Un_complexe;
    
    if((reel_racine < 0)and(complexe_racine==0)){    		
    		Un_complexe=epsilon;
    }else{
    		Un_complexe=complexe_racine;
    }
    Un_reel=reel_racine;
    
    do{
        division(reel_racine,complexe_racine,Un_reel,Un_complexe,res_reel_racine,res_complexe_racine);
        somme(Un_reel,Un_complexe,res_reel_racine,res_complexe_racine,res_reel_racine,res_complexe_racine);
        Un_reel=res_reel_racine/2;
        Un_complexe=res_complexe_racine/2;
        
        //----------------------------------
        
 	   
 	   produit(Un_reel,Un_complexe,Un_reel,Un_complexe,approximation_reel,approximation_complexe);
 	   soustraction(approximation_reel,approximation_complexe,reel_racine,complexe_racine,approximation_reel,approximation_complexe);
 	   approximation_numerateur=norme_carree(approximation_reel,approximation_complexe);   
    	   approximation_denominateur=norme_carree(reel_racine,complexe_racine);
    	   approximation=approximation_numerateur/approximation_denominateur;
    }while(not(approximation < epsilon));
    	res_reel_racine=Un_reel;
    	res_complexe_racine=Un_complexe;
}


int main(){
	float reel_1,complexe_1,reel_2,complexe_2,res_reel,res_complexe,res_reel_carree,res_complexe_carree;
	saisie(reel_1,complexe_1);
	cout<<"Le nombre complexe que vous avez saisi est : ";
	affiche(reel_1,complexe_1);
	cout<<endl;
	
	racine(reel_1,complexe_1,res_reel,res_complexe);
	cout<<"racine du nombre complexe : "<<endl;
	affiche(res_reel,res_complexe);
	produit(res_reel,res_complexe,res_reel,res_complexe,res_reel_carree,res_complexe_carree);
	cout<<"racine au carree : "<<endl;
	affiche(res_reel_carree,res_complexe_carree);
	
	/*
	inverse(reel_1,complexe_1);
	cout<<"inverse du nombre complexe : ";
	affiche(reel_1,complexe_1);
	cout<<endl;
	
	
	saisie(reel_2,complexe_2);
	cout<<"Le nombre complexe que vous avez saisi est : ";
	affiche(reel_2,complexe_2);
	cout<<endl;
	
	
	inverse(reel_2,complexe_2);
	cout<<"inverse du nombre complexe : ";
	affiche(reel_2,complexe_2);
	cout<<endl;
	
	somme(reel_1,complexe_1,reel_2,complexe_2,res_reel,res_complexe);
	cout<<"Somme des deux inverses de nombres complexes : ";
	affiche(res_reel,res_complexe);
	cout<<endl;
	
	produit(reel_1,complexe_1,reel_2,complexe_2,res_reel,res_complexe);
	cout<<"produit des deux inverses de nombres complexes : ";
	affiche(res_reel,res_complexe);
	*/
	
	
	cout<<endl;
}






