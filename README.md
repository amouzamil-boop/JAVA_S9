Objectif
Initialiser l’application, structurer les données aéroports et obtenir une première fenêtre JavaFX fonctionnelle.

Environnement
JDK 17/21 aarch64 et JavaFX SDK aarch64. VM options configurées avec --module-path, --add-modules javafx.controls,javafx.fxml, et --enable-native-access=javafx.graphics.

Développement réalisé

Airport : création du modèle (code IATA, nom, latitude, longitude).

World : lecture du CSV, filtrage des aéroports pertinents, stockage en mémoire. Méthodes de recherche par code IATA et par proximité (distance approx. via lat/lon avec correction cos(latitude)).

Interface : squelette JavaFX (Stage/Scene) lancé avec les options VM nécessaires. Affichage d’une fenêtre opérationnelle.

Problème rencontré et résolution
Avertissements de chargement natif JavaFX. Ajout de --enable-native-access=javafx.graphics dans les VM options pour les supprimer et préparer les prochaines versions du JDK.

Tests effectués
Chargement du CSV sans erreur, comptage basique des aéroports, recherche par code IATA, vérification d’une recherche de proximité sur un cas simple. Lancement de l’application JavaFX validé.

Prochaines étapes
Ajouter la caméra perspective et un globe 3D dans l’IHM, appliquer une texture, puis préparer les interactions (zoom, picking) avant l’affichage de marqueurs d’aéroports.

///////////////////////////////////////////////////////////////

TP2
Objectif
Mise en place de l'environnement 3D JavaFX : affichage d'un globe terrestre texturé, configuration de la caméra et animation de la rotation planétaire.
Environnement
Identique à la séance précédente (JDK + JavaFX SDK). Ajout des ressources graphiques (texture haute résolution de la carte du monde) dans le projet.
Développement réalisé
Classe Earth : Création de la classe héritant de Group. Ajout d'une Sphere de rayon 300 pixels.
Caméra : Configuration d'une PerspectiveCamera dans la scène. Recul de la caméra sur l'axe Z (-1000) et réglage des plans de coupure (setNearClip, setFarClip) pour rendre la scène visible.
Texture : Application de l'image du monde sur la sphère via un objet PhongMaterial et la méthode setDiffuseMap.
Animation : Implémentation d'un AnimationTimer pour gérer la rotation continue de la terre autour de l'axe Y (objet Rotate).
Contrôles : Ajout d'événements souris (MouseEvent) pour gérer le zoom via le déplacement de la caméra sur l'axe Z.
Problème rencontré et résolution
Affichage initial : Écran blanc lors du passage à la 3D. Résolu en ajustant la position Z de la caméra (la caméra était initialement positionnée à l'intérieur de la sphère en 0,0,0).
Tests effectués
Lancement de l'application validé : la Terre s'affiche avec sa texture et tourne sur elle-même. Le zoom avant/arrière à la souris est fonctionnel.
Prochaines étapes
Implémenter le "Picking" (clic souris sur le globe) : convertir les coordonnées 2D de l'écran en coordonnées géographiques (Latitude/Longitude) pour identifier et afficher l'aéroport sélectionné.



