%{
	unsigned int l=0;
	unsigned int w=0;
	unsigned int c=0;
%}

word [^ \t\n]+
eol \n
%%

{eol}   {
	l++;
        c++;
}

{word}  {
	w++;
	c+=yyleng;
}

.     {c++;}

%%
main() {
	yylex();
	printf("l=%d w=%d c=%d\n",l, w, c);
}
