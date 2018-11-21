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
	for( $i=0; $i< count($data)-3 ; $i++){
		$result["reponse"][$i]=$data[$i+1];
	}
	$result["points"]= explode(",", $data[count($data)-3]);
	$result["time"]= $data[count($data)-2];
	$result["answers"]= explode(",", $data[count($data)-1]);
	
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
<?php
/// À COMPLETER : AFFICHER LA DESCRIPTION
echo ($_SESSION["qcm"]["description"]);
?>
</div>
<div>
<?php

	if (isset($_COOKIE["QCM"]) && ($_COOKIE["QCM"] === "started"))
	{
		//A COMPLETER : AFFICHER UN MESSAGE DISANT QUE LE QUESTIONNAIRE
		//A DEJA ÉTÉ VU
		echo ("le questionnaire a déjà été vu");

	} else {

		//A COMPLETER : AFFICHER UN LIEN VERS LA PAGE question.php
		echo('<a href="https://tp-ssh1.dep-informatique.u-psud.fr/~spatte/question.php"> Commencer le test </a>');
	}

?>
</body>
</html>
