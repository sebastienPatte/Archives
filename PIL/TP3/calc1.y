%{
// Une calculatrice avec uniquement addition et soustraction
// et une seule expression est calculée (une seule ligne)
#include <stdio.h>
int A;
%}

%token NOMBRE

%%
fichier : fichier ligne
		| ligne
		;

ligne   : expr '\n' {printf("%d\n",$1);} 
		| 'A' '=' expr {A=$3; printf("A=%d\n",$3);}
		| '\n'
		;

expr    : expr '+' terme {$$ = $1 + $3;}
        | expr '-' terme {$$ = $1 - $3;}
		| terme
        ;

terme   : terme '*' facteur {$$ = $1 * $3;}
        | terme '/' facteur {$$= $1 / $3;}
		| facteur
        ;

facteur : '(' expr ')' {$$ = $2;}
        | NOMBRE
		| 'A' {$$=A;}	
        ;

%%

#include "lex.yy.c"
