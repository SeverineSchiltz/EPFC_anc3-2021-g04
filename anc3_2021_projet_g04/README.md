# Projet ANC3 2021 - Trello

## Notes de livraison itération 1
- Diagrammes: les diagrammes sont dans le dossier "Diagramme" qui se trouve à la racine du dossier bitbucket ("anc3_2021_g04").
- Limites techniques: 
     1) Problème à la fin du ListView: il n'y a plus moyen de cliquer sur des cellules vides et donc d’ajouter des colonnes ou des cartes.
     2) Problème d’affichage: les titres des cartes et des colonnes changent de couleurs (deviennent blancs) lorsqu’on clique dessus.
     3) Problème d’affichage: les boutons « down » des cartes mangent le bord de la carte.
     4) Problème d’affichage: quand le titre d'une colonne (ou d'une carte dans une moindre mesure) est relativement long, cela a pour effet d'augmenter la taille de la colonne (donc le VBox mais pas la taille de la ListView).
     
## Notes de livraison itération 2
- Le design pattern (DP) Command peut fonctionner sans le DP Memento. Nous avons intégré le DP Memento pour notre apprentissage :-)
- Lorsque nous avons implémenté le DP Command, nous avons créé des commandes qui se basent sur les références des objets du modèle. Par conséquent, lorsque nous avons voulu implémenter le DP Memento, nous avons dû garder les références des objets pour effectuer les copies profondes. Sans cela, cela créait des bugs avec les commandes. Pour ne pas apporter trop de modifications aux classes du modèle et des commandes, le Memento tel qu'implémenté ici ne fonctionne que sur les modifications de titre des objets du modèle. Pour résoudre ce problème, nous avons d'abord pensé à utiliser les positions des objets (cf. commit bcd5b69) mais le code en devenait alourdi. Nous pourrions ajouter des ID aux objets du modèle pour ne plus utiliser les références, mais se baser sur ces ID dans les commandes. Nous examinerons la possibilité de le faire dans l'itération 3.
- Le diagramme de classes logicielles ne contient pas toutes les classes commandes du package "commands" pour des raisons de lisibilité du diagramme.

## Notes de livraison itération 3

## Notes de livraison itération 4


