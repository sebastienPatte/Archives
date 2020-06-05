# ici all lance:
# Compilation des fichiers temporaires
# Compilation des fichier exécutables
# Supprime les fichiers temporaires (".o")
# ainsi on écrit juste 'make' (all est sous entendu) dans la console et tout les exécutables sont compilé et les fichiers temporaires supprimés
# pour supprimer les exécutables on écrit 'make clean_exe'

all: 2048.o fonctions.o 2048 test_2048.o test_2048 clean

# Compilation des fichiers temporaires
2048.o: 2048.cpp
	g++ -std=c++11 -c 2048.cpp -lncurses

fonctions.o: fonctions.cpp
	g++ -std=c++11 -c fonctions.cpp -lncurses

test_2048.o:
	g++ -std=c++11 -c test_2048.cpp -lncurses 
	
# Compilation des fichier exécutables
2048: 2048.o fonctions.o
		g++ -std=c++11 2048.o fonctions.o -o 2048 -lncurses
	
test_2048: test_2048.o fonctions.o
	g++ -std=c++11 test_2048.o fonctions.o -o test_2048 -lncurses 

# Supprime les fichiers temporaires (".o")
clean:
	rm -rf *.o

# Supprime les exécutables
clean_exe:
	rm -rf 2048
	rm -rf test_2048

 
