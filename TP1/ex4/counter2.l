%{
	#include <stdlib.h>
	#include <stdio.h>
	
	unsigned int l=0;
	unsigned int w=0;
	unsigned int c=0;
	unsigned int total_l=0;
	unsigned int total_w=0;
	unsigned int total_c=0;
%}

word [^ \t\n]+
eol \n
%%

{eol}   {
	l++;
	total_l++;
        c++;
	total_c++;
}

{word}  {
	w++;
	total_w++;
	c+=yyleng;
	total_c+=yyleng;
}

.     {
	c++;
	total_c++;
}

%%
main(int argc, char *argv[]) {
	int i;
	for (i=1; i<argc; ++i) {
		FILE *file;
		file = fopen(argv[i], "r");
		yyin = file;
		yylex();
		printf("%s:\tlines=%d words=%d chars=%d\n",argv[i],l, w, c);
		l=0;
		w=0;
		c=0;
	}
	printf("\nTOTAL:\tlines=%d words=%d chars=%d\n",total_l, total_w, total_c);
	
}

