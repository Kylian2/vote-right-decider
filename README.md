# Les algorithmes

Les résultats attendu sont donnés selon les jeux de test contenu dans les json d'exemples.

Les critères gloutons sont regroupés dans la classe `Greedy`, les algorithmes de force brute dans la classe `BruteForce`

## Maximiser la satisfaction en respectant les contraintes budgétaires
*Kylian Richard*

Ce mode de décision permet de choisir la combinaison de proposition qui abouti au nombre d'utilisateur satisfait le plus élévé 
en selectionnant uniquement les propositions qui respectent les contraintes budgetaires. 

### Critère gloutons 1 

`maximizeTotalSatisfaction(ArrayList<Proposal>, Community)`
Parcours les propositions qui respectent les contraintes budgétaires en comptant le nombre de personne satisfaite par chaque proposition, 
selectionne celle qui a le plus satisfaction puis relance l'algorithme avec les propositions restantes.

#### Test 

- Classique, résultat attendu 64.
- Aucun budget disponible, résultat attendu 0 (liste vide).
- Liste vide, le lancement d'une erreur est attendu
- Communauté invalide, le lancement d'une erreur est attendu

### Critère gloutons 2

`maximizeTotalSatisfactionRatio(ArrayList<Proposal>, Community)`
Parcours les propositions qui respectent les contraintes budgétaires en faisant un ratio budget/(utilisateur satisfait) puis selectionne la proposition 
avec le ratio le plus faible. Relance l'algorithme sur la liste de proposition restante.

#### Test 

- Classique, résultat attendu 70.
- Aucun budget disponible, résultat attendu 0 (liste vide).
- Liste vide, le lancement d'une erreur est attendu.
- Communauté invalide, le lancement d'une erreur est attendu.

### Brute Force

`maximizeTotalSatisfaction(ArrayList<Proposal>, Community)`
Renvoie la meilleur combinaison du mode de décision en parcourant chacune des combinaisons possibles. L'algorithme a été améliorer en utilisant la mémoïsation
pour ne pas avoir a recalculer des problèmes déjà résolus.

#### Test 

- Classique, résultat attendu 70.
- Aucun budget disponible, résultat attendu 0 (liste vide).
- Liste vide, le lancement d'une erreur est attendu.
- Communauté invalide, le lancement d'une erreur est attendu.

# Documentation de la Classe `Mock` - Génération de Données Fictives

La classe `Mock` fournit des méthodes utilitaires pour générer des données fictives utilisées dans des simulations ou des tests d'algorithmes de recommandation. 
Les données générées incluent des propositions avec des budgets, des votes, des réactions, ainsi que des communautés avec des thèmes, des budgets et des utilisateurs.

## Fonctionnalités

- Génération de propositions en format JSON, incluant des informations telles que le budget, les thèmes, les votes et les réactions.
- Génération d'une communauté en format JSON, incluant des informations sur le budget global, les thèmes et les utilisateurs.
- Conversion des données JSON en objets Java pour permettre leur utilisation dans l'application.

## Prérequis

- La bibliothèque Gson pour la manipulation de données JSON (disponible via Maven ou téléchargement direct).

## Méthodes

### 1. `generateProposals(int nbProposal, int nbUser, int budget, int nbTheme)`

Cette méthode génère une liste de propositions en format JSON avec les informations suivantes :

- **Proposition** : ID, titre, budget, thème
- **Votes** : Liste des utilisateurs ayant voté pour ou contre
- **Réactions** : Liste des utilisateurs ayant aimé ou détesté la proposition

#### Paramètres :
- `nbProposal` : Le nombre de propositions à générer.
- `nbUser` : Le nombre d'utilisateurs disponibles pour voter ou réagir.
- `budget` : Le budget de référence pour les propositions.
- `nbTheme` : Le nombre total de thèmes disponibles pour les propositions.

#### Retour :
- Une chaîne JSON contenant la liste des propositions générées.

### 2. `generateCommunity(int nbTheme, int globalBudget, int nbUser)`

Cette méthode génère une communauté en format JSON avec les informations suivantes :

- **Communauté** : ID, budget global, nombre d'utilisateurs
- **Thèmes** : Liste des thèmes avec leurs budgets respectifs
- **Frais fixes** : Une fraction aléatoire du budget global allouée aux frais fixes

#### Paramètres :
- `nbTheme` : Le nombre de thèmes disponibles dans la communauté.
- `globalBudget` : Le budget global de la communauté.
- `nbUser` : Le nombre d'utilisateurs dans la communauté.

#### Retour :
- Une chaîne JSON représentant la communauté générée.

### 3. `convertJsonToProposals(String json)`

Cette méthode convertit une chaîne JSON représentant une liste de propositions en une liste d'objets `Proposal`.

#### Paramètre :
- `json` : La chaîne JSON contenant les propositions à convertir.

#### Retour :
- Une liste d'objets `Proposal` convertis depuis le JSON.

### 4. `convertJsonToCommunity(String json)`

Cette méthode convertit une chaîne JSON représentant une communauté en un objet `Community`.

#### Paramètre :
- `json` : La chaîne JSON contenant les informations de la communauté à convertir.

#### Retour :
- Un objet `Community` converti depuis le JSON.

### 5. `getRandomUsers(int numUsers, Random random)`

Cette méthode génère une liste aléatoire d'utilisateurs uniques à partir d'un nombre d'utilisateurs maximum.

#### Paramètres :
- `numUsers` : Le nombre maximal d'utilisateurs possibles.
- `random` : L'instance de la classe `Random` utilisée pour générer des nombres aléatoires.

#### Retour :
- Une liste d'identifiants d'utilisateurs uniques.
