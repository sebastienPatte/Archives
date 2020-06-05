<html>
    <body>
     	<form method="POST">
			<textarea rows="4" cols="50" name="text"></textarea>
			<br/>
  			<input type="file" name="upload" />	
     		<input type="submit" />
     	</form>
		
		<?php
			$texte="";
			if ( isset($_POST["text"]) && ($_POST["text"] !== "") ){
				$texte=$_POST["text"];
			}
			else if ( isset($_POST['upload']) && ($_POST['upload'] !== "") ){
				$texte=file_get_contents($_POST['upload']);
			}
			echo $texte;
			echo "taille = ";
			echo strlen($texte);
			//echo preg_match()
		?>
		
    </body>
  </html> 
