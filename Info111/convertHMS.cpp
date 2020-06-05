#include <iostream>
#include <vector>
using namespace std;

/** Infrastructure minimale de test **/
#define ASSERT(test) if (!(test)) cout << "Test failed in file " << __FILE__ \
				       << " line " << __LINE__ << ": " #test << endl


/** Converti une durée en Heure, Minute, Seconde en seconde
 *  @param À compléter
 *  @return À compléter
 **/
int convertHMS2S(vector<int> hms) {
  // À compléter
  return (hms[2])+(hms[1]*60)+(hms[0]*3600);
}

void testConvertHMS2S() {
  ASSERT( convertHMS2S({0,1,15}) == 75 );
  ASSERT( convertHMS2S({1,0,0}) == 3600 );
  ASSERT( convertHMS2S({1,10,15}) == 4215 );
  ASSERT( convertHMS2S({0,0,59}) == 59);
  // À compléter
}

/** À compléter
 *  @param À compléter
 *  @return À compléter
 **/
vector<int> convertS2HMS(int d) {
  // À compléter
  vector<int> t={0,0,0};
  while(d>0){
  	if(d>=3600){
  		t[0]++;
  		d-=3600;
  	}else{
  		if(d>=60){
  			t[1]++;
  			d-=60;	
  		}else{
  			t[2]+=d;
  			d=0;	
  		}
  	}	
  }return t;
}

void testConvertS2HMS() {
  ASSERT( convertS2HMS(75) == vector<int>({0,1,15}) );
  // À compléter
}


void testHMS(vector<int> hms) {
  // À compléter
  ASSERT( hms[0]<24);
  ASSERT( hms[1]<60);
  ASSERT( hms[2]<60);
}

int main() {
  testConvertHMS2S();
  testConvertS2HMS();
  
  int duree;
  cout<<"Entrez une duree en secondes: ";
  cin>>duree;
  
  testHMS(convertS2HMS(duree));
  vector<int> duree2 = convertS2HMS(duree);
  
  cout<<"temps en HMS: "<<duree2[0]<<"h"<<duree2[1]<<"m"<<duree2[2]<<"s"<<endl;
  cout<<"temps en sec: "<<convertHMS2S(duree2)<<endl;
  
  if (duree==convertHMS2S(duree2)){
  cout<< "test reussi"<<endl; 
  }else{
  	cout<< "erreur test rate"<<endl;
  }
  
  
  
  // À compléter
}
