%{#include <stdio.h>%}
%token TAG
%token ID
%token CLASS
%token ACCO
%token ACCF
%token PTV
%token DPT
%token PPT
%token VAL

%%

fichier : ID ACCO instr ACCF fichier 
		| TAG ACCO instr ACCF fichier 
		| CLASS ACCO instr ACCF fichier 
	    |
		;

instr : PPT DPT VAL PTV instr 
	  |
	  ; 
%%

#include "lex.yy.c"

