
text [a-zA-Z0-9\-]+
html h1|h2|h3|p|div|span|a|img
comm "/*"([^*]|[*]+[^/])*[*]+"/"
val [a-zA-Z]+|[0-9]+(%|em|pt|cm)|#[0-9a-fA-F]{6}
space [ \t\r\n]
ppt "text-align"|"font-family"|"font-size"|"font-style"|"color"

%%
{html} {return TAG;}

#{text} {return ID;}

\.{text} {return CLASS;}

{comm} {}

\{ {return ACCO;}

\} {return ACCF;}

";" {return PTV;}

":" {return DPT;}

{ppt} {return PPT;}

{val} {return VAL;}

{space} {}

. {}

%%
