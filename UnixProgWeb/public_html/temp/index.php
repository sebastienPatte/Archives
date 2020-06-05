<html>
	<body>
		<h1>Title</h1>
		<p>
		« Texte » est issu du mot latin « textum », dérivé du verbe « texere » qui signifie « tisser ». Le mot s'applique à l'entrelacement des fibres utilisées dans le tissage, voir par 			exemple Ovide : « Quo super iniecit textum rude sedula Baucis = (un siège) sur lequel Baucis empressée avait jeté un tissu grossier »2 ou au tressage (exemple chez Martial « Vimineum 			textum = panier d'osier tressé »). Le verbe a aussi le sens large de construire comme dans « basilicam texere = construire une basilique » chez Cicéron3. 
		</p>
		
		
		<form method="post">
		<input type="text" name="name" value=<?php if(isset($_POST["name"])){echo ($_POST["name"]);} ?> >
		<input type="text" name="email" value=<?php if(isset($_POST["email"])){echo ($_POST["email"]);} ?> >
		<input type="textarea" name="comment"/>
		<input type="submit"/>
		</form>
		<?php
			if(isset($_POST["name"])){
				if (!(isset($_COOKIE["id"]))){
					setcookie("id", $_POST["name"], time()+2592000);
				}else{
				$_POST["name"]= $_COOKIE["id"];
				}
			}
			if(isset($_POST["email"])){
				if (!(isset($_COOKIE["email"]))){
					setcookie("email", $_POST["email"], time()+2592000); 
				}else{
				$_POST["email"]= $_COOKIE["email"];
				}
			}
			if(!(file_exists("comments.txt"))){
				touch("comments.txt");
			}
			if(isset($_POST["name"])&&($_POST["comment"])){
				$texte=file_get_contents("comments.txt");
				$texte.="Commentaire de ".$_POST["name"]."<br/>".$_POST["comment"]."<br/>";
				echo($texte);
				file_put_contents("comments.txt",$texte);
			}	
			
		?>
	</body>
</html>	
