#include <iostream>
using namespace std;

void syracuse(int &n){
	if(n<=0)cout<<"erreur, le terme de départ est inférieur à zéro"<<endl;
	if(n%2==0){
		n = n/2;
	}else{
		n = (n*3)+1;
	}
}

int longueurTransient (int un){
int i=0;
while (un != 1) { syracuse(un); i++; }
return i;
}


int main(){
for(int i=1; i<=1000;i++)cout<<longueurTransient(i)<<endl;	
}
