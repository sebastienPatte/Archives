
%%
h[1-3]|p|div|span|a|img {
	return BALISE;
}

.[0-Z] {
	return CLASS;
}

#[0-Z] {
	return ID;
}
.*:.*; {
	return DECLARATION;
}

%%

