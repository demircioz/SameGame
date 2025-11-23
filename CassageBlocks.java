/**
 * La classe {@code CassageBlocks} fournit une méthode utilitaire pour gérer
 * la suppression d’un groupe de blocs sélectionnés dans une grille de jeu.
 * Elle applique d’abord la suppression des blocs (surbrillance), puis la gravité
 * verticale (chute des blocs) et horizontale (décalage des colonnes).
 *
 * Cette logique est utilisée dans les jeux de type SameGame après qu’un groupe
 * de blocs a été identifié comme supprimable.
 *
 * @author Canpolat DEMIRCI--ÖZMEN
 * @author Théo ANASTASIO
 * @version 1.0
 */
public class CassageBlocks {

    /**
     * Supprime les blocs surbrillés (désignés par leurs coordonnées) dans la grille,
     * puis applique la gravité verticale (chute) et horizontale (décalage à gauche).
     *
     * @param grille        la grille actuelle du jeu sous forme de tableau de chaînes
     * @param surbrillance tableau des coordonnées [x, y] des blocs à supprimer
     * @return la nouvelle grille après suppression et réorganisation des blocs
     */
    public static String[] casser(String[] grille, int[][] surbrillance) {
        if (grille == null || grille.length == 0 || surbrillance == null || surbrillance.length == 0)
            return grille;

        int lignes = grille.length;
        int colonnes = grille[0].length();

        // 1. Convertir la grille en un tableau 2D de caractères pour la modifier facilement
        char[][] matrice = new char[lignes][colonnes];
        for (int y = 0; y < lignes; y++) {
            matrice[y] = grille[y].toCharArray();
        }

        // 2. Supprimer les blocs sélectionnés (les remplacer par un espace vide)
        for (int[] coord : surbrillance) {
            int x = coord[0];
            int y = coord[1];
            matrice[y][x] = ' ';
        }

        // 3. Gravité verticale : faire tomber les blocs dans chaque colonne
        for (int x = 0; x < colonnes; x++) {
            int index = lignes - 1;
            for (int y = lignes - 1; y >= 0; y--) {
                if (matrice[y][x] != ' ') {
                    matrice[index][x] = matrice[y][x];
                    if (index != y) {
                        matrice[y][x] = ' ';
                    }
                    index--;
                }
            }
        }

        // 4. Gravité horizontale : décaler les colonnes vers la gauche si elles sont vides
        int colonneCible = 0;
        for (int x = 0; x < colonnes; x++) {
            boolean colonneVide = true;
            for (int y = 0; y < lignes; y++) {
                if (matrice[y][x] != ' ') {
                    colonneVide = false;
                    break;
                }
            }

            if (!colonneVide) {
                // Déplacer la colonne vers la position "colonneCible" à gauche
                if (colonneCible != x) {
                    for (int y = 0; y < lignes; y++) {
                        matrice[y][colonneCible] = matrice[y][x];
                        matrice[y][x] = ' ';
                    }
                }
                colonneCible++;
            }
        }

        // 5. Reconvertir le tableau de caractères en tableau de chaînes
        String[] nouvelleGrille = new String[lignes];
        for (int y = 0; y < lignes; y++) {
            nouvelleGrille[y] = new String(matrice[y]);
        }

        return nouvelleGrille;
    }
}
