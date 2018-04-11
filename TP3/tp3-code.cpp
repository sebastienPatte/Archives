#include <iostream>
#include <fstream>
#include <vector>
#include <stdlib.h>
#include <cmath>
using namespace std;

int compteur=0;
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

int partitionner(int deb, int fin){
	string pivot = liste[deb].nom;
	int i =deb+1;
	int j=fin;
	while (true){
		while(i < j && liste[i].nom.compare(pivot) <= 0){
			i++;
			//cout<<"i "<<i<<endl;
		}  
		while(i < j && liste[j].nom.compare(pivot) >  0){
			j--;
			//cout<<"j "<<j<<endl;	
		} 

		if(i >= j){
			permute(deb, i-1);
			return i;
		}
		permute(i,j);
		i++;
		j--;
	}
}

void tri_rapide(int deb, int fin) {
    if (deb >= fin)
        return;
    //cout<<"triRapide "<< deb <<" "<< fin <<endl;
    int mid = partitionner(deb, fin);
    compteur++;
    cout<<mid<<endl;
    tri_rapide(deb, mid - 1);
    tri_rapide(mid, fin);
}

int rechercheDicho(string val,int deb, int fin){	
	cout<<endl;
	cout<<"Recherche Dichotomique"<<endl;
	bool stop=false;
	int mid=0;
	int c=0;
	while(!stop && (fin-deb>1)){
		mid=(deb+fin)/2;
		c++;
		if(val.compare(liste[mid].nom) <0){
			fin=mid;
		}else{
			if(val.compare(liste[mid].nom) > 0){
				deb=mid;
			}else{
				cout<<"nb essais : "<<c<<endl;
				if( val.compare(liste[mid].nom) == 0)return mid;		
			}
		}
	}
}

int distanceParis(int indice_ville){
	int indice_paris = rechercheDicho("Paris",0,36551);
	int distance = sqrt(pow((liste[indice_paris].longitude - liste[indice_ville].longitude) * cos((liste[indice_paris].latitude + liste[indice_ville].latitude)/2),2) + pow(liste[indice_ville].latitude - liste[indice_paris].latitude, 2));
	return distance;

}


int main(int, char*[]) {
	lire_fichier("tp1-data.csv");

	tri_rapide(0,liste.size());

	for (size_t i = 0; i < liste.size(); i++)
		cout << i << " " << liste[i].nom << " " << liste[i].code << endl;
	
	cout<<"tri Rapide : "<<compteur<<endl;
	cout<<"Paris : "<<rechercheDicho("Paris",0,36000)<<endl;
	cout<<distanceParis(rechercheDicho("Marseille",0,36000))<<endl;
	return 0;
}






