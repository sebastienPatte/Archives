#include <iostream>
using namespace std;





void permuter(int &c, int &d) {
int temp;
temp=c; c=d; d=temp;
}

void ordonner(int &a, int &b, int &c){
	if(a>b)permuter(a,b);
	if(b>c)permuter(b,c);
	if(a>b)permuter(a,b);
	
	
	
}

int main() {
	int nb1, nb2,nb3;
	cout<<"Entrez le premier nombre"<<endl;
	cin>>nb1;
	cout<<"Entrez le deuxième nombre"<<endl;
	cin>>nb2;
	cout<<"Entrez le troisième nombre"<<endl;
	cin>>nb3;
	ordonner(nb1,nb2,nb3);
	cout<<"ordre croissant : "<<nb1<<" , "<<nb2<<" , "<<nb3<<endl; 
}
