%{
	unsigned int nbLines=0;
	unsigned int nbSubtitles=0;
%}

%%
^\{[0-9]+\} {
	nbSubtitles++;
	nbLines++;
}

\| {
	nbLines++;
}

(.|\n) {}

%%

main(){
	yylex();
	printf("nbLines = %d \n nbSubtitles = %d" ,nbLines, nbSubtitles );
}
