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

struct dlist{
	Departement *first = NULL;
	Departement *last = NULL;
};

dlist *lire_fichier(string s,dlist *p_dlist){
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

		struct Departement *ptr = p_dlist->first;
		while (ptr != NULL && ptr->numero != v->departement)
			ptr = ptr->next;



		if (ptr == NULL) {
			ptr = (Departement *)malloc(sizeof(Departement));
			if(ptr!=NULL){
				ptr->population = 0;
				ptr->numero     = v->departement;
				ptr->next=NULL;
				if(p_dlist->first==NULL){
					ptr->prev = NULL;
					p_dlist->first=ptr;
					p_dlist->last=ptr;	

				}else{
					p_dlist->last->next=ptr;
					ptr->prev= p_dlist->last;
					
					cout<<p_dlist->last->next<<endl;
					
					p_dlist->last=ptr;
				}
			}		
			
			cout<<ptr->prev<<"  "<<ptr<<" ";
		}
		ptr->population = ptr->population + v->population;

		
		liste.push_back(v);
	}
	cout<<"0"<<endl;
	file.close();
	return p_dlist;
}

dlist *insertion_en_tete (int population, int numero,dlist *p_dlist){
	struct Departement *ptr = p_dlist->first;
	ptr = (Departement *)malloc(sizeof(Departement));
	

	if(ptr!=NULL){
				ptr->population = population;
				ptr->numero     = numero;
				ptr->next=NULL;
				if(p_dlist->last==NULL){
					ptr->prev = NULL;
					p_dlist->first=ptr;
					p_dlist->last=ptr;	

				}else{
					p_dlist->first->prev=ptr;
					ptr->next= p_dlist->first;
					p_dlist->first=ptr;
				}
	}return p_dlist;
}

dlist *insertion_fin(int population, int numero, dlist *p_dlist){
	struct Departement *ptr= p_dlist->first;
	ptr= (Departement *)malloc(sizeof(Departement));

	if(ptr!=NULL){
		ptr->population=population;
		ptr->numero=numero;
		ptr->next=NULL;

		
		if(p_dlist->last==NULL){
			cout<<"p_dlist->last=NULL"<<endl;
			ptr->prev = NULL;
			p_dlist->first=ptr;
			p_dlist->last=ptr;	
		}else{

			p_dlist->last->next=ptr;
			ptr->prev = p_dlist->last;
			p_dlist->last=ptr;
		}
	}
	return p_dlist;
}

dlist *insertion_numero(int population, int numero,dlist *p_dlist){
	
	
	Departement *ptr = (Departement *)malloc(sizeof(Departement));
	ptr=p_dlist->first;
	int i=0;

	while (ptr->next != NULL && ptr->numero <= numero){
		
		ptr = ptr->next;
		//cout<<"place_numero !!! "<<ptr->numero<<endl;

	}


		if(ptr->prev==NULL){
			p_dlist=insertion_en_tete(population,numero,p_dlist);
			cout<<"Insertion tete"<<endl;
		}else{
			if(ptr->next==NULL && ptr->numero < numero){
				cout<<"insertion_fin"<<endl;
				p_dlist=insertion_fin(population,numero,p_dlist);
			}else{	
				struct Departement *ptr_temp = (Departement *)malloc(sizeof(Departement));
				cout<<"Insertion"<<endl;
				ptr_temp->population=population;
				ptr_temp->numero=numero;

				ptr->prev->next=ptr_temp;
				ptr_temp->prev=ptr->prev;
				ptr_temp->next=ptr;
				ptr->prev=ptr_temp;
				


				
			}
		}

		
		
		return p_dlist;
}
			
	

dlist *insere_valeur_croissant(dlist *p_dlist, dlist *p_new_dlist){
	Departement *ptr = (Departement *)malloc(sizeof(Departement));
	ptr=p_dlist->first;
	while(ptr != NULL){
		p_new_dlist=insertion_numero(ptr->population,ptr->numero,p_new_dlist);
		cout<<ptr->numero<<endl;
		ptr=ptr->next;
	}
	return p_new_dlist;
}


	

int main(int, char*[]) {
	dlist *p_dlist=(dlist *)malloc(sizeof(dlist));
	p_dlist=lire_fichier("tp1-data.csv",p_dlist);

	dlist *p_new_dlist=(dlist *)malloc(sizeof(dlist));
	p_new_dlist->first=NULL;
	p_new_dlist->last=NULL;
	p_new_dlist=insertion_en_tete(0,1,p_new_dlist);
	p_new_dlist=insertion_en_tete(0,0,p_new_dlist);
/*
	for (size_t i = 0; i < liste.size(); i++)
		cout << i << " " << *liste[i]->nom
		          << " " << *liste[i]->code << endl;
*/


	p_new_dlist=insere_valeur_croissant(p_dlist,p_new_dlist);
	
	struct Departement *ptr = p_new_dlist->first;
	

	while (ptr != NULL){
		cout << " le departement No " << ptr->numero
		     << " a une population de " << ptr->population
		     << " habitants" << endl;
		ptr = ptr->next;
	}


	return 0;
}
