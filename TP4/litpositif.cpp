#include<iostream>
#include<string>

using namespace std;

/** lit un entier positif
 *  @param &erreurs un entier
 **/
void litpositif(int &erreurs, int &resultat) {
  erreurs--;
  do {
    cout << "Donner la valeur de l'entier positif : ";
    cin >> resultat;
    erreurs++;
  } while (resultat < 0);
}

int main() {
  /* à compléter */
    int nberreurs=0;
    int res=0;
    litpositif(nberreurs,res);
    cout<<"test"<<endl;
    cout<<"Entier entré : "<<res<<endl;
    cout<<"nombre d\'erreurs : "<<nberreurs<<endl;
}
