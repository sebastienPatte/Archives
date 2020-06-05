#include <iostream>
#include <string.h>
using namespace std;

/** Infrastructure minimale de test **/
#define ASSERT(test) if (!(test)) cout << "Test failed in file " << __FILE__ \
				       << " line " << __LINE__ << ": " #test << endl
				       
				       
				    
int nbMedian(int a,int b,int c){
	if ((a>=b)and(a>=c)){
	
		if (b>=c)return b;
		if (c>=b)return c;
	
	}else{
	
		if ((b>=a)and(b>=c)){
	
			if (a>=c) return a;
			if (c>=a) return c;
	
		}else{
	
			if ((c>=a)and(c>=b)){
	
				if (a>=b) return a;
				if (b>=a) return b;
			}
		}
	}
}				    




void test_nbMedian(){
	ASSERT(nbMedian(4,4,8)==4);
	ASSERT(nbMedian(8,6,4)==6);
	ASSERT(nbMedian(3,5,2)==3);
	ASSERT(nbMedian(485,481,2)==481);
	ASSERT(nbMedian(42,42,8)==42);
	ASSERT(nbMedian(45,4,8)==8);
}



int main(){
int a,b,c;

cout<<"Entrez le premier chiffre: "<<endl;
cin>>a;
cout<<"Entrez le deuxieme chiffre: "<<endl;
cin>>b;
cout<<"Entrez le dernier chiffre: "<<endl;
cin>>c;

cout<< "le nombre Median est: " << nbMedian(a,b,c)<<endl;
}				    
