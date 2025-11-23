import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaire {@code Surbrillance} utilisée pour détecter les groupes de blocs adjacents
 * de même couleur dans une grille de SameGame.
 * <p>
 * Cette classe utilise un algorithme de type "flood fill" récursif pour explorer les cases voisines
 * ayant la même couleur que la case sélectionnée.
 * </p>
 *
 * <p>Elle permet de déterminer les blocs à surbriller lorsqu’un joueur survole ou clique sur une case.</p>
 *
 * @author Canpolat DEMIRCI--ÖZMEN
 * @author Théo ANASTASIO
 * @version 1.0
 */
public class Surbrillance {

    /**
     * Cherche tous les blocs connectés (adjacents orthogonalement) à partir d'une case donnée,
     * ayant la même couleur, pour former un groupe à surbriller.
     *
     * @param grilleTexte la grille actuelle (tableau de chaînes de caractères)
     * @param x la colonne de départ
     * @param y la ligne de départ
     * @return un tableau de coordonnées [x, y] représentant le groupe trouvé
     */
    public static int[][] chercherGroupes(String[] grilleTexte, int x, int y) {
        // Vérification des bornes de la grille
        if (x < 0 || x >= grilleTexte[0].length() || y < 0 || y >= grilleTexte.length) {
            return new int[0][0];
        }

        // Récupération de la couleur de départ
        char couleur = grilleTexte[y].charAt(x);
        if (couleur == ' ') {
            return new int[0][0]; // case vide
        }

        boolean[][] visite = new boolean[grilleTexte.length][grilleTexte[0].length()];
        List<int[]> groupe = new ArrayList<>();

        // Lancement de la recherche récursive
        explorerGroupe(grilleTexte, x, y, couleur, visite, groupe);

        // Conversion de la liste vers un tableau classique
        int[][] groupeArray = new int[groupe.size()][2];
        for (int i = 0; i < groupe.size(); i++) {
            groupeArray[i] = groupe.get(i);
        }

        return groupeArray;
    }

    /**
     * Explore récursivement les cases adjacentes qui appartiennent au même groupe (même couleur).
     *
     * @param grilleTexte la grille de jeu
     * @param x position x de la case courante
     * @param y position y de la case courante
     * @param couleur couleur de référence du groupe
     * @param visite tableau de cases déjà visitées
     * @param groupe liste accumulant les positions du groupe trouvé
     */
    private static void explorerGroupe(String[] grilleTexte, int x, int y, char couleur,
                                       boolean[][] visite, List<int[]> groupe) {
        // Vérification des bornes, des doublons ou de la couleur différente
        if (x < 0 || x >= grilleTexte[0].length() || y < 0 || y >= grilleTexte.length ||
            visite[y][x] || grilleTexte[y].charAt(x) != couleur) {
            return;
        }

        // Marquer la case comme visitée et l'ajouter au groupe
        visite[y][x] = true;
        groupe.add(new int[]{x, y});

        // Exploration récursive dans les 4 directions
        explorerGroupe(grilleTexte, x + 1, y, couleur, visite, groupe); // droite
        explorerGroupe(grilleTexte, x - 1, y, couleur, visite, groupe); // gauche
        explorerGroupe(grilleTexte, x, y + 1, couleur, visite, groupe); // bas
        explorerGroupe(grilleTexte, x, y - 1, couleur, visite, groupe); // haut
    }
}
