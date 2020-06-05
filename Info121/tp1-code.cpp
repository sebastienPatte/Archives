#include <iostream>
#include <fstream>
#include <vector>
#include <stdlib.h>

using namespace std;

struct Ville {
	string nom;
	string code;
	float  longitude;
	float  latitude;
	int    population;
};
vector<Ville> liste;

void permute(int i, int j) {
	Ville temp = liste[i];
	liste[i] = liste[j];
	liste[j] = temp;
}

void triselection (int n) {
	for (int k = 0; k < n - 1; k++) {
		int meilleur = k;
		for (int i = k + 1; i < n; i++)
			if (liste[meilleur].population > liste[i].population)
				meilleur = i;
		permute(k, meilleur);
	}
}

void lire_fichier(string s){
	ifstream file(s.c_str());
	string line;
	getline(file, line);
	while (getline(file, line)) {
		Ville v;
		int pos;

		pos          = line.find(";");
		v.nom        = line.substr(0, pos);
		line         = line.substr(pos + 1, line.size() - 1);

		pos          = line.find(";");
		v.code       = line.substr(0, pos);
		line         = line.substr(pos + 1, line.size() - 1);

		pos          = line.find(";");
		v.longitude  = atof(line.substr(0, pos).c_str());
		line         = line.substr(pos + 1, line.size() - 1);

		pos          = line.find(";");
		v.latitude   = atof(line.substr(0, pos).c_str());
		line         = line.substr(pos + 1, line.size() - 1);

		pos          = line.find(";");
		v.population = atoi(line.substr(0, pos).c_str());

		liste.push_back(v);
	}
	file.close();
}

void moyenne (int n){
	int res=0;
	for(int i=0;i<n-1;i++){
		res+=liste[i].population;
	}
	res/=n;
	cout<<"moyenne de population : "<<res<<endl;
}

void triInsertion(int n){
	int j=0;
	for(int i=1;i<n;i++){
		j=i;	
		while((j>0)&&(!(liste[j-1].population<liste[j].population))){
			permute(j,j-1);
			j--;
		}
	}
}

void triAbulles (int n){
	bool stop;
	do{
		stop=true;
		for(int i=1;i<n;i++){
			if(!(liste[i-1].population<liste[i].population)){
				permute(i,i-1);
				stop=false;
			}
		}
	}while(!stop);
}

int main(int, char*[]) {
	lire_fichier("tp1-data.csv");

//	triselection(liste.size());
//	triInsertion(liste.size());
	triAbulles(liste.size());
	for (size_t i = 0; i < liste.size(); i++)
		cout << i << " " << liste[i].nom << " " << liste[i].population << endl;
	moyenne(liste.size());
	cout<<"mediane des populations : "<<liste[(liste.size()-1)/2].population<<endl;
	return 0;
}
