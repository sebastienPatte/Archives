#include <iostream>
#include "PolyAbstr.hpp"

/** Infrastructure minimale de test **/
#define ASSERT(test) if (!(test)) cout << "Test failed in file " << __FILE__ \
                                       << " line " << __LINE__ << ": " #test << endl

/////////////////////////////////////////////////////////////////////////////////
//                   Utilisation du type abstrait : affichages                 //
/////////////////////////////////////////////////////////////////////////////////

using namespace std;

/* affiche le monome d'un polynome
 * @param[in] i Degré du monome
 * @param[in] co Coefficient du monome
 * @param[in] premier Est-ce le premier monome affiché pour un polynome ?
 */
void afficheMonome(int i, float co, bool premier) {
  if (co != 0) {
    // affiche le signe
    if (co > 0 && not premier) {
      cout << " + ";
    }
    if (co < 0) {
      cout << " - ";
      co = -co;
    }
    // affiche le coefficient
    if ((co != 1) || (i == 0))
      cout << co;
    // affiche la puissance de X
    if (i > 1) cout << "X^" << i;
    else if (i == 1) cout << "X";
  }
}


/* Affiche un polynome dans la console
 * @param[in] p Polynome à afficher
 */
void affichePoly(Polynome p) {
  int d, i;
  if (estNulPoly(p)) cout << 0 << endl;
  else {
    d = degrePoly(p);
    afficheMonome(d, coeffPoly(p, d), true);
    for (i = d-1; i >= 0; i--)
      afficheMonome(i, coeffPoly(p, i), false);
    cout << endl;
  }
}

/////////////////////////////////////////////////////////////////////////////////
//             Utilisation du type abstrait : fonction vue en cours            //
/////////////////////////////////////////////////////////////////////////////////

/* Multiple un polynome par une constante
 * @param[in/out] p Polynome à multiplier
 * @param[in] la Constante
 */
void multPolyConst(Polynome &p, float a) {
  int d, i;
  float c;

  if (a == 0)
    PolynomeNul(p);
  else {
    d = degrePoly(p);
    for (i=0; i<=d; i++) {
      c = coeffPoly(p, i);
      modifierCoeffPoly(p, i, c*a);
    }
  }
}

/* Dérive un polynome
 * @param[in] p Polynome à dériver
 * @param[out] res le résultat de la dérivation
 */
void deriveePoly(Polynome p, Polynome &res) {
  int d, i;
  d = degrePoly(p);
  PolynomeNul(res);
  for (i=0; i<d; i++) {
    modifierCoeffPoly(res, i, (i+1)*coeffPoly(p, i+1));
  }
}

/////////////////////////////////////////////////////////////////////////////////
//               Utilisation du type abstrait : fonction à écrire              //
/////////////////////////////////////////////////////////////////////////////////

float evalPoly(Polynome p, float x) {
  int d, i;
  float res = 0;
  d = degrePoly(p);
  for (i=0; i<=d; i++)
    res += coeffPoly(p, i) * puissance(x, i);
  return res;
}

float evalHornerPoly(Polynome p, float x) {
  int d, i;
  float res = 0;
  d = degrePoly(p);
  for (i=d; i>=0; i--)
    res = res*x + coeffPoly(p, i);
  return res;
}

void polynomeCoeffEgaux(Polynome &p, int degree, float coeff){
	PolynomeNul(p);
	for (int i=0; i<=degree; i++)
	modifierCoeffPoly(p, i, coeff);
}

void produitPolyXn(Polynome p, int n, Polynome &res);
void produitPoly(Polynome p, Polynome q, Polynome &res);
void puissancePoly(Polynome p, int n, Polynome &res);


int main() {
  Polynome p, q;

  //TESTS
  PolynomeNul(p);
  modifierCoeffPoly(p, 5,  4.);
  modifierCoeffPoly(p, 2, -5.);
  modifierCoeffPoly(p, 1,  1.);
  modifierCoeffPoly(p, 0, -1.);

  cout << "Le polynome p est : ";
  affichePoly(p);

  ASSERT( egalPoly(p, p) );

  cout << "Le polynome 3*p est : ";
  q = p;
  multPolyConst(q, 3.);
  affichePoly(q);

  cout << "La derivee de p est : ";
  deriveePoly(p, q);
  affichePoly(q);

  ASSERT( not egalPoly(p, q) );

  //Q2
  PolynomeNul(p);
  modifierCoeffPoly(p, 4,  1.);
  modifierCoeffPoly(p, 2, 2.);
  modifierCoeffPoly(p, 0,  -5.);

  // cout << "X^4 + 2 X^2 - 5 evalue en 2 : " << EvalPoly(p, 2.) << endl; //Doit afficher 19
	cout<<endl;
	//Q 3.1
	PolynomeNul(p);
	cout<<"question 3.1"<<endl;
	polynomeCoeffEgaux(p, 3, 2);
	affichePoly(p);
	cout<<endl;

	//Q 3.2.a
	PolynomeNul(p);
	cout<<"question 3.2.a"<<endl;
	modifierCoeffPoly(p,4,1);
	modifierCoeffPoly(p,2,2);
	modifierCoeffPoly(p,0,-5);
	affichePoly(p);
	cout<<"pour X=2, p="<<evalPoly(p,2)<<endl;
	cout<<endl;
	
	//Q 3.2.b
	PolynomeNul(p);
	cout<<"question 3.2.b"<<endl;
	polynomeCoeffEgaux(p, 1000, 1.0001);
	cout<<"polynome de degré mille dont tout les coeffs sont egaux a 1.0001"<<endl;
	cout<<"pour X=1.0001, p="<<evalPoly(p, 1.0001)<<endl;
	cout<<endl;	
	//Q 3.2.c
	cout<<"question 3.2.c"<<endl;
	PolynomeNul(p);
	modifierCoeffPoly(p,10,1);
	modifierCoeffPoly(p,9, -99);
	affichePoly(p);
	cout<<"pour X=100 :"<<endl; 
	cout<<"(methode classique), p="<<evalPoly(p,100)<<endl;
	cout<<"(methode Horner), p="<<evalHornerPoly(p,100)<<endl;
	cout<<endl;
  return 0;
}
