%{
// Fichier lex pour une calculatrice avec uniquement adddition et soustraction, et une seule
// expression calcul�e. Convient aussi si on y ajoute les deux autres op�rations.
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
