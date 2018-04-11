#include <iostream>
#include <fstream>
#include <vector>
#include <stdlib.h>

using namespace std;
//-----------------------------------
vector<int> liste_int = {145, 51, 455, 6, 9, 78 ,54, 62, 45, 75, 54, 74, 1875, 555, 1012, 55, 61};
//---------------------------------------
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
void permute_int(int i, int j) {
	int temp = liste_int[i];
	liste_int[i] = liste_int[j];
	liste_int[j] = temp;
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
    cout<<mid<<endl;
    tri_rapide(deb, mid - 1);
    tri_rapide(mid, fin);
}

//------------------------------------------------------------
int partitionner_int(int deb, int fin){
	int pivot = liste_int[deb];
	int i =deb+1;
	int j=fin;
	while (true){
		while(i < j && liste_int[i] <= pivot){
			i++;
			//cout<<"i "<<i<<endl;
		}  
		while(i < j && liste_int[j]>  pivot){
			j--;
			//cout<<"j "<<j<<endl;	
		} 

		if(i >= j){
			permute_int(deb, i-1);
			return i;
		}
		permute_int(i,j);
		i++;
		j--;
	}
}

void tri_rapide_int(int deb, int fin) {
    if (deb >= fin)
        return;
    //cout<<"triRapide "<< deb <<" "<< fin <<endl;
    int mid = partitionner_int(deb, fin);
    cout<<mid<<endl;
    tri_rapide_int(deb, mid - 1);
    tri_rapide_int(mid, fin);
}
int main(int, char*[]) {
//	lire_fichier("tp1-data.csv");

//	tri_rapide(0,liste.size()-1);
	tri_rapide_int(0,liste_int.size()-1);
	/*
	for (size_t i = 0; i < liste.size(); i++)
		cout << i << " " << liste[i].nom << " " << liste[i].code << endl;
	*/	
	cout<<"LISTE"<<endl;
	for(int i=0; i<liste_int.size();i++)cout<< liste_int[i]<<endl; 
	return 0;
}
