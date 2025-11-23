/**
 * Classe FinPartie
 *
 * Gère la détection de fin de partie pour le jeu SameGame.
 * Une partie est considérée comme terminée lorsqu'il ne reste
 * plus aucun groupe de blocs adjacents (horizontalement ou verticalement)
 * de même couleur de taille supérieure ou égale à 2.
 *
 * Cette classe fournit une méthode statique permettant de tester
 * si la grille contient encore au moins un coup possible.
 *
 * @author
 * Canpolat DEMIRCI--ÖZMEN  
 * Théo ANASTASIO
 */
public class FinPartie {

    /**
     * Détermine si la partie est terminée.
     * Une partie est terminée lorsqu'il n'existe plus aucun groupe
     * de blocs adjacents de même couleur de taille ≥ 2.
     *
     * @param grille la grille actuelle du jeu (chaînes de caractères)
     * @return true si aucun coup n’est possible (fin de partie), false sinon
     */
    public static boolean plusDeCoup(String[] grille) {
        int lignes = grille.length;
        int colonnes = grille[0].length();
        boolean[][] visite = new boolean[lignes][colonnes];

        for (int y = 0; y < lignes; y++) {
            for (int x = 0; x < colonnes; x++) {
                char couleur = grille[y].charAt(x);
                if (couleur == ' ' || visite[y][x]) continue;

                int tailleGroupe = explorer(grille, x, y, couleur, visite);
                if (tailleGroupe >= 2) {
                    return false; // Il reste au moins un groupe jouable
                }
            }
        }
        return true; // Aucun groupe de taille suffisante, donc fin de partie
    }

    /**
     * Exploration récursive en profondeur d’un groupe de blocs
     * pour en déterminer la taille.
     *
     * @param grille la grille actuelle
     * @param x coordonnée x du point de départ
     * @param y coordonnée y du point de départ
     * @param couleur couleur à suivre dans l'exploration
     * @param visite matrice des cases déjà visitées
     * @return le nombre total de blocs dans le groupe
     */
    private static int explorer(String[] grille, int x, int y, char couleur, boolean[][] visite) {
        int lignes = grille.length;
        int colonnes = grille[0].length();

        if (x < 0 || x >= colonnes || y < 0 || y >= lignes ||
            visite[y][x] || grille[y].charAt(x) != couleur) {
            return 0;
        }

        visite[y][x] = true;
        int taille = 1;

        taille += explorer(grille, x + 1, y, couleur, visite);
        taille += explorer(grille, x - 1, y, couleur, visite);
        taille += explorer(grille, x, y + 1, couleur, visite);
        taille += explorer(grille, x, y - 1, couleur, visite);

        return taille;
    }
}
