%{
// Une calculatrice avec uniquement addition et soustraction
// et une seule expression est calculée (une seule ligne)
#include <stdio.h>
%}

%token NOMBRE
%token LIGNE

%%
ligne   : expr {printf("%d\n",$1);} 
	| expr LIGNE {printf("%d\n",$1);} ligne 
	| LIGNE expr {printf("\n");} ligne
	|	
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
        ;

%%

#include "lex.yy.c"
