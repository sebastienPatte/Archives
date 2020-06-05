#include <iostream>
#include <string.h>
using namespace std;

/** Infrastructure minimale de test **/
#define ASSERT(test) if (!(test)) cout << "Test failed in file " << __FILE__ \
				       << " line " << __LINE__ << ": " #test << endl


/** La forme d'un individu
 *  @param repos  nbr de battement cardiaque à la minute au repos
 *  @param effort nbr de battement cardiaque à la minute après l'effort
 *  @param recup  nbr de battement cardiaque à la minute après une minute de récupération
 *  @return une chaîne de caractère décrivant la forme
 **/
string forme(int repos, int effort, int recup) {
  // À compléter
  float res= 0.1*(repos+effort+recup-200);
  	if(res==0)
  		return "excellente"; 
   if ((res>0)and(res<=5))
  		return "tres bonne";
   if ((res>5)and(res<=10))
  		return "bonne";
   if ((res>10)and(res<=15))
  		return "moyenne";
     
  		return "faible";
  
}


void formeTest(void) {
  ASSERT( forme(60,80,60) == "excellente" );
  ASSERT( forme(70,90,80) == "tres bonne" );
  ASSERT( forme(80,120,80) == "bonne" );
  ASSERT( forme(80,150,100) == "moyenne" );
  ASSERT( forme(90,150,130) == "faible" );
}

int main() {
  formeTest();
  // À compléter
  int repos,effort,recup;
  cout<<"Entrez votre frequence cardiaque au repos"<<endl;
  cin>>repos;
  cout<<"Entrez votre frequence cardiaque durant l'effort"<<endl;
  cin>>effort;
  cout<<"Entrez votre frequence cardiaque apres l'effort"<<endl;
  cin>>recup;
  cout<<"Votre forme physique est "<<forme(repos,effort,recup)<<endl;
}
