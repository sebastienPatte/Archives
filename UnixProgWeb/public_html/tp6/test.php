<html>
	<body>
		<?php
				
				$a = 1;
function f()
{
global $a;
$a = 10;
return $a;
}
echo f() + 1;
				
			
		?>
	</body>
</html>
