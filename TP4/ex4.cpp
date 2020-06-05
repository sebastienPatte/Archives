#include <iostream>
using namespace std;


/** calcule la suite de syracuse en partant d'un terme de départ '&n'
 *  @param '&n' un entier
 **/
void syracuse(int &n){
	if(n<=0)cout<<"erreur, le terme de départ est inférieur à zéro"<<endl;
	if(n%2==0){
		n = n/2;
	}else{
		n = (n*3)+1;
	}
}

/** Calcule le nombre de termes entre le terme de départ et 1 par la suite de syracuse
 *  @param 'un' un entier
 *  @return un entier
 **/
int longueurTransient (int un){
int i=0;
while (un != 1) { syracuse(un); i++; }
return i;
}


int main(){
for(int i=1; i<=1000;i++)cout<<longueurTransient(i)<<endl;	
}
