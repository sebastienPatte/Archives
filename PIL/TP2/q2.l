%{
	unsigned int timeStart=0;
	unsigned int timeStop=0;
%}

%%

^\{[0-9]+\} {
	usleep((timeStop - timeStart)*1000000/24);
	timeStart =  atoi(yytext+1);
	system("clear");
	usleep((timeStart - timeStop)*1000000/24);
}

\{[0-9]+\} {
	timeStop = atoi(yytext+1);
}

[^{|\n][^|\n]+     {
              printf("%s\n", yytext);
}

\|[^|\n]+     {
              printf("%s\n", yytext+1);
}

. {}
%%
