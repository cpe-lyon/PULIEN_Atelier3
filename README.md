### PULIEN_Atelier3

# Atelier n°3 du module de WebDynamique - Microservices

## Contributeurs :
* Théo CLERE
* Maxime BROSSARD
* Sandro SPINA
* Ceif-Edine MAROUANI
* Julien BUC


L'application web présenté dans ce git est une plateforme d'achat/vente des cartes Fifa.

# Fonctionnalités


- Création de compte utilisateur

![alt text](ImgReadme/register.png)


- Connexion

![alt text](ImgReadme/login.png)

- L'**inventaire** permet à l'utilisateur connecté de consulter la liste de cartes qu'il possède

![alt text](ImgReadme/market.png)


- Le **Market Place** liste les cartes que l'utilisateur connecté peux acheter

![alt text](ImgReadme/mycollection.png)



- La page **Vitrine** montre toutes les cartes disponnibles de notre application

![alt text](ImgReadme/cardcollection.png)


Voir le rapport

# Structure du Projet

## Frontend

La partie front end à été réalisée en à l'aide du framework javascript React. (dans le dossier /front)

## Backend

Le server Spring se décompose en plusieurs micro service nécessaire au bon fonctionnement de notre application:
- AuthService : Service responsable de l'authentification (JWT Token).
- UserService : Service responsable de la gestion de utilisateurs.
- CardService : Service responsable de la gestion des cartes.
- DiscoveryServer :  Server auquel s'enregistrent tous les microservices ainsi que l'orchestrator.
- Gateway : Porte d'entrée à tous les requêtes client.
- Orchestrator Cammunda : Service permettant de faire des transactions distribuées

Voir les rapport .pdf pour en savoir plus.



# Cahier des charges 

## Fonctionnalités réalisés

- Tous les attendus de l'atelier 2 on été réalisés
  - AuthModule -> Théo
  - UserModule -> Théo
  - Encryption des mots de passe -> Maxime
  - page vitrine -> Théo
  - page marketplace -> Théo
  - navnbar -> Théo
  - composant react Card -> Maxime
  - Card, CardInstance module -> Sandro
  - React Setup -> Ceif
  - Marketplace module -> Sandro, Julien
  - Rapport -> Ceif & Maxime
  - Schémas d'architecture -> Ceif, Théo, Sandro & Maxime
  - Page d'inventaire -> Julien, Ceif & Sandro
  - Readme.md -> Théo

- Atelier 3 
  - Séparation en Micro services -> Théo
  - Test Junit -> Maxime
  - Gateway -> Maxime
  - Discovery -> Maxime
  - Sonar -> Maxime
  - Serveur délivrant les sources front -> en cours pour Ceif
  - Cors policy issue sur la gateway -> Théo
  - Orchestrator Camunda -> Théo & Sandro

## Fonctionnalités non réalisés
- Game + Room
- Serveur délivrant les sources front -> en cours pour Ceif