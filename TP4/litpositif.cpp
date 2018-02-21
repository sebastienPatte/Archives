#include<iostream>
#include<string>

using namespace std;

int litpositif(int &erreurs) {
  int resultat;
  erreurs--;
  do {
    cout << "Donner la valeur de l'entier positif : ";
    cin >> resultat;
    erreurs++;
  } while (resultat < 0);
  return resultat;
}

int main() {
  /* à compléter */
    int nberreurs=0;
    int res=litpositif(nberreurs);
    cout<<"test"<<endl;
    cout<<"Entier entré : "<<res<<endl;
    cout<<"nombre d\'erreurs : "<<nberreurs<<endl;
}
