enum Couleur { P, Co, T, Ca };

struct Carte {
  int valeur;
  Couleur couleur;
};

struct Paquet {
  int taille;
  Carte carte[40];
};

void initPaquet (Paquet &paquet) {
  paquet.taille=40;
  int i;
  for(i=0; i<10; i++)
    {
      paquet.carte[4*i].valeur = i+1;
      paquet.carte[4*i].couleur = P;
      paquet.carte[4*i+1].valeur = i+1;
      paquet.carte[4*i+1].couleur = Co;
      paquet.carte[4*i+2].valeur = i+1;
      paquet.carte[4*i+2].couleur = T;
      paquet.carte[4*i+3].valeur = i+1;
      paquet.carte[4*i+3].couleur = Ca;
    }
}
