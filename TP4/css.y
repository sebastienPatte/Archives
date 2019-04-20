%{#include <stdio.h>%}
%token BALISE
%token CLASS
%token ID
%token DECLARATION

%%

ligne : BALISE;

%%

#include "lex.yy.c"

