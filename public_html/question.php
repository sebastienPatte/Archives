<?php
session_start();

//A COMPLETER: CREER UN COOKIE "QCM" AYANT POUR VALEUR "started"
//ET EXPIRANT DANS 1 AN.
setcookie("QCM", "started", time()+31536000); 

//La question en cours.
$q = $_SESSION["q"];


if ($q >= 0)
{
/* On est pas sur la première question, on calcule le nouveau score
   On vérifie le timeout etc…
*/
   $q_data = $_SESSION["qcm"]["questions"][$q];
   $user_answers = $_POST["user_answers"];
   $answers = $q_data["answers"];

   $correct = FALSE;

   //A COMPLETER : METTRE TRUE DANS $correct SI ET SEULEMENT SI
   // - LES TABLEAUX $user_answers et $answers CONTIENNENT LES MEMES ENTIERS
   // - IL S'EST ECOULÉ MOINS DE TEMPS QUE $q_data["time"]
   
   
   if(($user_answers === $answers) && (time() - $_SESSION['time']<= $q_data["time"])){
   	foreach(super_answers as $i => $v)
   	{
   		if($v !== $anwers[$i])
   		{
   			break;
   		}
   	}
   	
   			
   	$correct = TRUE;
   
   }






   if ($correct)
   {
      $_SESSION["score"] += $q_data["points"][0];

   } else {
      $_SESSION["score"] -= $q_data["points"][1];
   }

}

// On prépare les variables de session pour la question suivante.

$_SESSION["q"]++;
$q++;
$_SESSION["time"] = time();

?>
<html>
<body>
<div>
<?php

//A COMPLETER : SI ON EST PAS SUR LA DERNIERE QUESTION,
//AFFICHER UN FORMULAIRE AVEC LA QUESTION ET UNE CHECKBOX PAR
//CHOIX POSSIBLE AINSI QU'UN BOUTON DE SOUMISSION.
//L'ATTRIBUT name POUR LES CHECKBOX DOIT ETRE 'user_answers[]'
//
//SI ON EST SUR LA DERNIERE QUESTION, AFFICHER LE SCORE.

if($q< count($_SESSION["qcm"]["questions"])){
	
	for($i=0; $i< count($_SESSION["qcm"]["questions"][$q]["choices"]); $i++){
		$value=$_SESSION["qcm"]["questions"][$q]["choices"][$i];
		echo($value);
		echo('<input type="checkbox" name="user_answers[]" value='+$i+'/>'+$value+'</br>');
	}
	echo('<a href="https://tp-ssh1.dep-informatique.u-psud.fr/~spatte/question.php"> Question Suivante </a>');
}else{
	echo("score : " + $_SESSION["qcm"]["score"]);
}

?>
</div>
</body>
</html>
