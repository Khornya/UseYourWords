# USE YOUR WORDS
### Marine MINARD, Yannig JEGADEN

## Pré-requis
- Une base de données MySQL
- NodeJS et npm

## Installation
1. Remplacer les identifiants Cloudinary dans le fichier application.properties
2. Remplacer si nécessaire l'url, le login et le mot de passe de connexion à la base de données dans le fichier application.properties
3. Se rendre dans le dossier app et exécuter la commande `npm install`

## Démarrage

1. Se rendre dans le dossier app et exécuter la commande `npm start`
2. Exécuter le projet Spring Boot (dossier src)
L'URL de la page d'accueil du projet est http://localhost:8080/home.

## Informations importantes

Au premier démarrage de l'application, la base de données useyourwords sera créée. Vous pouvez ajouter des éléments via l'interface d'administration ou l'API REST
Pour jouer immédiatement, vous pouvez également exécuter le script values.sql dans votre base nouvellement créée.

## API REST
Lister tous les éléments : GET http://localhost:8080/api/element
Ajouter un élément : POST http://localhost:8080/api/element
Editer un élément : PUT http://localhost:8080/api/element/{id}
Editer partiellement un élément : PATCH http://localhost:8080/api/element/{id}
