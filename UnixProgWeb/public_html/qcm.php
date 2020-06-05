<?php

session_start();

/*

Chargement d'un questionnaire:

*/

/* Fonction de chargement d'une question individuelle */
function parse_question($str)
{

	///A COMPLETER
	///CONVERTI LA CHAINE $str EN TABLEAU COMME
	///INDIQUÉ DANS L'ÉNONCÉ.
	$data = explode("--\n", $str);
	$result= array();
	$result["question"]= $data[0];
	$result["choices"] = array();
	for( $i=1; $i< count($data)-3 ; $i++){
		$result["choices"][$i]=$data[$i];
	}
	$result["points"]= explode(",", $data[$i]);
	$result["time"]= $data[$i+1];
	$result["answers"]= explode(",", $data[$i+2]);
	
	return $result;	
}

/* Fonction chargeant le contenu du fichier $file
   sous forme de tableau
*/

function load_questions($file)
{
	$str = file_get_contents($file);
	$data = explode("---\n", $str);
	$result = array();
	$result[ "description" ] = $data[0];
	$result[ "questions" ] = array();
	for ($i = 1; $i < count($data); $i++)
	{
	    $result["questions"][$i - 1] = parse_question($data[$i]);
	}
	return $result;
}




/* Initialisation des variables de sessions */

//Cette variable de session stockera l'heure à laquelle la question précédente
//a été validée

$_SESSION["time"] = 0;


//Stocke le questionnaire complet
$_SESSION["qcm"] = load_questions("questions.txt");


//Stocke le numéro de la question en cours
$_SESSION["q"] = -1;


//Sotcke le score
$_SESSION["score"] = 0;


?>
<html>
<body>

<div>

<?php echo $_SESSION["qcm"]["description"]; ?>
</div>
<div>
<?php

	if (isset($_COOKIE["QCM"]) && ($_COOKIE["QCM"] === "started"))
	{
		//A COMPLETER : AFFICHER UN MESSAGE DISANT QUE LE QUESTIONNAIRE
		//A DEJA ÉTÉ VU

		echo "Vous avez déjà tenté de répondre au questionnaire<br/>";
	} else {

		//A COMPLETER : AFFICHER UN LIEN VERS LA PAGE question.php

	        echo "Pour le QCM <a href='question.php'>c&apos;est par ici</a>";
	}

?>
</body>
</html>