#include <iostream>
using namespace std;

int compte1 = 1000;
int compte2 = 2000;
int compte3 = 1500;
int compte4 = 3000;

/** Effectue un virement de "somme", de "&compte_orig" vers "&compte_dest" 
 *  @param "&compte_orig" un entier 
 *  @param &compte_dest un entier
 *  @param somme un entier
 *  @param &virement_ok un booléen
 **/
void virement(int &compte_orig, int &compte_dest, int somme, bool &virement_ok){
	if(compte_orig >= somme){
		compte_orig -= somme;
		compte_dest += somme;
		virement_ok = true;
	}virement_ok = false;
}
//affiche l'etat des variables globales compte1, compte2, compte3, compte4
void etat_comptes() {
  cout << "Etat des comptes : " << endl;
  cout << "Compte n 1 : " << compte1 << endl;
  cout << "Compte n 2 : " << compte2 << endl;
  cout << "Compte n 3 : " << compte3 << endl;
  cout << "Compte n 4 : " << compte4 << endl;
}


int main() {
  bool v=false;
  etat_comptes();
  cout<<endl;
  virement(compte1, compte2, 100, v);
  if(v=false){
	cout<<"erreur du virement"<<endl;
  }else{
	cout<<"virement effectué"<<endl;  
  }
  cout<<endl;
  etat_comptes();
  return 0;
}
