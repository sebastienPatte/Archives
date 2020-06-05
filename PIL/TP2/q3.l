%{
	int debut=0;
	int fin=0;
	int latence=0;
	float factor;
%}

%%

^\{[0-9]+/\} {
		debut=atoi(yytext+1);
		latence=debut-fin;
		system("clear");
		usleep(latence*1000000/24*factor);
}

\{[0-9]+/\} {
		fin=atoi(yytext+1);
}

[^{}|\n][^|\n]+		printf("%s\n", yytext);
\|[^|\n]+				printf("%s\n", yytext+1);		
\n	{
		usleep((fin-debut)*1000000/24*factor);
		debut=fin;
	};
. ;

%%

int main(int argc,char *argv[]){
	if(argc !=2){
		printf("Argument error: multiplication factor needed.");
		exit(1);
	}
	factor=atof(argv[1]);
	yylex();
	return 0;
}


