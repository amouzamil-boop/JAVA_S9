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
