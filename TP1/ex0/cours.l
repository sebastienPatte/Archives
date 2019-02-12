%%

[0-9]+  	{
			printf("Un entier : %s\n", yytext);
		}

[0-9]+"."[0-9]* {
			printf("Un réel: %s\n",yytext);
		}
.		{}

%%
