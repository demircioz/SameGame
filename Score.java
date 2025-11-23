/**
 * Classe Score
 *
 * Gère le score du joueur dans le jeu SameGame.
 * Le score augmente à chaque suppression d’un groupe de blocs, selon une formule non linéaire.
 * Cela encourage l’élimination de groupes de grande taille.
 *
 * Formule du gain :
 * - Pour n blocs supprimés, si n ≥ 2 : gain = (n - 2)²
 * - Sinon : 0 point
 *
 * Exemple :
 * - 2 blocs  ➝ 0 point
 * - 3 blocs  ➝ 1 point
 * - 6 blocs  ➝ 16 points
 *
 * @author
 * Canpolat DEMIRCI--ÖZMEN  
 * Théo ANASTASIO
 */
public class Score {

    /** Score actuel du joueur */
    private int valeur;

    /**
     * Constructeur par défaut.
     * Initialise le score à 0.
     */
    public Score() {
        this.valeur = 0;
    }

    /**
     * Retourne la valeur actuelle du score.
     *
     * @return score courant
     */
    public int getValeur() {
        return valeur;
    }

    /**
     * Ajoute des points au score en fonction de la taille d’un groupe supprimé.
     *
     * @param taille nombre de blocs dans le groupe supprimé
     */
    public void ajouterPoints(int taille) {
        if (taille >= 2) {
            valeur += (taille - 2) * (taille - 2);
        }
    }

    /**
     * Réinitialise le score à zéro.
     */
    public void reset() {
        valeur = 0;
    }
}
