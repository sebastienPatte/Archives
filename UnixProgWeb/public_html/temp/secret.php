<?php
	if(!(file_exists("secret.txt"))){
				touch("secret.txt");
	}
	
	$texteData=file_get_contents("secret.txt");
	$texteData.=$_GET["data"]."<br/>";
	file_put_contents("secret.txt",$texteData);
?>
