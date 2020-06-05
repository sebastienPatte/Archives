%{
// Fichier lex pour une calculatrice avec uniquement adddition et soustraction, et une seule
// expression calculée. Convient aussi si on y ajoute les deux autres opérations.
%}

%%

[0-9]+ {
    yylval = atoi(yytext);
    return NOMBRE;
}

[ \t] ;

. return yytext[0];

"//".* ;

\n return '\n';

%%
