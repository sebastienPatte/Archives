%{
	unsigned String srt = "";
%}

%%
^\{[0-9]+\}\{[0-9]+\} {
	
}

srt {
	
}

%%
int main(int argc,char *argv[]){                                            │                                                                         
        if(argc !=3){                                                       │                                                                         
                printf("Argument error: 2 string arguments needed.");    │                                                                         
                exit(1);                                                    │                                                                         
        }                                                                   │                                                                         
        str=atof(argv[1]);                                               │                                                                         
        yylex();                                                            │                                                                         
        return 0;                                                           │                                                                         
}
