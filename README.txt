
----- alakaZAMM  v1.0 -----


>>> Auteur

Edouard Murat



>>> Description

alakaZAMM est un algorithme de matchmaking en Java. Son objectif initial est de composer des groupes d'étudiants, suivant des règles définies, faisant en sorte de mettre en relation des personnes n'ayant jamais ou peu travaillé ensemble auparavant.



>>> Installation

Dezipper alakaZAMM.rar

Depuis un terminal : 
Dans le dossier où se trouve le .jar, executer la commande :
	java -jar alakaZAMM.jar

Pour conserver l'encodage :
	java -Dfile.encoding=UTF-8 -jar alakaZAMM.jar



>>> Usage

Le dossier où se trouve alakaZAMM.jar doit contenir OBLIGATOIREMENT :
- un dossier "pool_1"
- un dossier "pool_2"
- le ficher "config.txt"

Completez le fichier "config.txt"
Modifiez les paramètres dans "config.txt", si besoin

Concernant le fichier de données :
Le ficher doit être un fichier CSV
Le séparateur doit être une virgule (,)
Les colonnes doivent respecter le format du fichier CSV "template.csv"

Au lancement du programme :
L'algorithme compose automatiquement des groupes et choisit le meilleur au fil de l'execution. Le programme s'arrete lorsque le nombre de composition totale atteint le maximum donné dans le fichier "config.txt"

Après l'exécution :
Les résultats se trouvent dans "pool_1/" (pour la classe 1) et "pool_2/" (pour la classe 2)
Les fichers de resultat sont les fichiers "draft_<NUMERO DE DRAFT>.txt"
Le ficher avec le plus grand numéro de draft est supposé être la meilleure composition à la fin de l'execution.



>>> Contribution & Support

Les pulls requests sont possible sur le GitHub du projet :
https://github.com/edmurat-RBK/zeldaction-matchmaking

Si vous remarquez une erreur ou souhaitez un changement, ouvrez un ticket (Issue) pour pouvoir en discuter



>>> Roadmap

- Interface graphique
- Flexibilité sur l'entrée de données
- Viabilité de l'algorithme pour les projets futurs
- ...



>>> Licence

Ce projet est sous licence GNU GPLv3
Consultez le ficher "LICENSE.txt"
