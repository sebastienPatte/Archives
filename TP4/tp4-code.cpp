#include <iostream>
#include <fstream>
#include <vector>
#include <stdlib.h>

using namespace std;

struct Ville {
	string *nom;
	string *code;
	float   longitude;
	float   latitude;
	int     population;
	int     departement;
};
vector<Ville *> liste;

struct Departement {
	string *nom;
	int     numero;
	int     population;
	struct Departement *next;
	struct Departement *prev;
};
Departement *first = NULL;
Departement *last = NULL;


void lire_fichier(string s){
	ifstream file(s.c_str());
	string line;
	getline(file, line);
	while (getline(file, line)) {
		Ville *v = (Ville *)malloc(sizeof(Ville));
		int pos;

		pos            = line.find(";");
		v->nom         = new string(line.substr(0, pos));
		line           = line.substr(pos + 1, line.size() - 1);

		pos            = line.find(";");
		v->code        = new string(line.substr(0, pos));
		line           = line.substr(pos + 1, line.size() - 1);

		pos            = line.find(";");
		v->longitude   = atof(line.substr(0, pos).c_str());
		line           = line.substr(pos + 1, line.size() - 1);

		pos            = line.find(";");
		v->latitude    = atof(line.substr(0, pos).c_str());
		line           = line.substr(pos + 1, line.size() - 1);

		pos            = line.find(";");
		v->population  = atoi(line.substr(0, pos).c_str());

		v->departement = atoi(v->code->substr(0, 2).c_str());

		struct Departement *ptr = first;
		while (ptr != NULL && ptr->numero != v->departement)
			ptr = ptr->next;



		if (ptr == NULL) {
			ptr = (Departement *)malloc(sizeof(Departement));
			if(ptr!=NULL){
				ptr->population = 0;
				ptr->numero     = v->departement;
				ptr->next=NULL;
				if(first==NULL){
					ptr->prev = NULL;
					first=ptr;
					last=ptr;	

				}else{
					last->next=ptr;
					ptr->prev= last;
					
					cout<<last->next<<endl;
					
					last=ptr;
				}
			}		
			
			cout<<ptr->prev<<"  "<<ptr<<" ";
		}
		ptr->population = ptr->population + v->population;

		
		liste.push_back(v);
	}
	cout<<"0"<<endl;
	file.close();
}

void insertion_en_tete (int population, int numero){
	struct Departement *ptr = first;
	ptr = (Departement *)malloc(sizeof(Departement));
	

	if(ptr!=NULL){
				ptr->population = population;
				ptr->numero     = numero;
				ptr->next=NULL;
				if(last==NULL){
					ptr->prev = NULL;
					first=ptr;
					last=ptr;	

				}else{
					first->prev=ptr;
					ptr->next= first;
					first=ptr;
				}
	}
}

void insertion_numero(int population, int numero){
	cout<<"eeeeee";
	struct Departement *ptr_temp = first;
		
	
	ptr_temp = (Departement *)malloc(sizeof(Departement));
	int i=0;

	while (ptr_temp->next != NULL && ptr_temp->numero <= numero){
		ptr_temp = ptr_temp->next;
		cout<<"ptr_temp   !!! "<<ptr_temp<<endl;
	}
			
		struct Departement *ptr = (Departement *)malloc(sizeof(Departement));
		
		ptr->population=population;

//		ptr_temp->next->prev=ptr;
		/*
		ptr_temp->next->prev=ptr;
		ptr->prev=ptr_temp->prev;
		ptr_temp->prev=ptr;
		ptr->next=ptr_temp;
		*/
}
			
	

	

	

int main(int, char*[]) {
	lire_fichier("tp1-data.csv");
/*
	for (size_t i = 0; i < liste.size(); i++)
		cout << i << " " << *liste[i]->nom
		          << " " << *liste[i]->code << endl;
*/
//	insertion_numero(0,2);
//	insertion_en_tete(0,3);
//	insertion_en_tete(0,5);
//	insertion_en_tete(0,7);
//	insertion_en_tete(0,4);

	insertion_numero(0,6);
	struct Departement *ptr = first;
	while (ptr != NULL){
		cout << " le departement No " << ptr->numero
		     << " a une population de " << ptr->population
		     << " habitants" << endl;
		ptr = ptr->next;
	}


	return 0;
}
