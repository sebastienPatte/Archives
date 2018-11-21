<html>
    <body>
      <form method="get">
        Entrez un nombre entre 3 et 1000 :
  <input type="text" name="champ" />
        <input type="submit" />
  
      </form>
		<?php
			if(($_GET['champ'] <3) || ($_GET['champ'] >1000))
				echo "Erreur, entrée non correcte";
			else
				$entier= $_GET['champ'];
			$input()= array(TRUE) ;
			$tab= array_pad($input ,$entier ,TRUE );

		?>
		
    </body>
  </html> 
