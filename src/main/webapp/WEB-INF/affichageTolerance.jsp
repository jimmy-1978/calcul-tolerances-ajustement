<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Affichage du résultat du calcul d'une tolérance d'ajustement pour un alésage avec un arbre</title>
</head>
<body>
	<form method="get" action="SaisieTolerance">
		<input type = "submit" value="Nouveau calcul de tolérances">
	</form>
	<h1>Alésage</h1>
		<p>
		Classe de tolérance : <c:out value="${ ajustement.alesage.classeDeTolerance.codeClasseDeTolerance }"></c:out><br>
		Ecart supérieur (en micron) : <c:out value="${ ajustement.alesage.ecart.ecartSuperieur }"></c:out><br>
		Ecart inférieur (en micron) : <c:out value="${ ajustement.alesage.ecart.ecartInferieur }"></c:out><br>
		Dimension nominale (en mm) : <c:out value="${ ajustement.alesage.dimensionNominale }"></c:out><br>
		Dimension maximum (en mm) : <c:out value="${ ajustement.alesage.dimensionMaximum }"></c:out><br>
		Dimension minimum (en mm) : <c:out value="${ ajustement.alesage.dimensionMinimum }"></c:out><br> 
		</p>
	<h1>Arbre</h1>
		<p>
		Classe de tolérance : <c:out value="${ ajustement.arbre.classeDeTolerance.codeClasseDeTolerance }"></c:out><br>
		Ecart supérieur (en micron) : <c:out value="${ ajustement.arbre.ecart.ecartSuperieur }"></c:out><br>
		Ecart inférieur (en micron) : <c:out value="${ ajustement.arbre.ecart.ecartInferieur }"></c:out><br>
		Dimension nominale (en mm) : <c:out value="${ ajustement.arbre.dimensionNominale }"></c:out><br>
		Dimension maximum (en mm) : <c:out value="${ ajustement.arbre.dimensionMaximum }"></c:out><br>
		Dimension minimum (en mm) : <c:out value="${ ajustement.arbre.dimensionMinimum }"></c:out><br> 
		</p>	
		<p>Type de montage <c:out value="${ ajustement.typeAjustement.description }"></c:out></p>	
</body>
</html>