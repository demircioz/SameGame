import java.awt.event.*;

/**
 * Classe EcouteurDeSouris
 *
 * Gère les interactions de la souris sur la grille du jeu.
 * - Lors du déplacement, surligne un groupe de blocs de même couleur.
 * - Lors du clic, supprime le groupe s'il contient plus d'un bloc,
 *   applique les effets de gravité, met à jour le score et rafraîchit l'affichage.
 *
 * @author
 * Canpolat DEMIRCI--ÖZMEN  
 * Théo ANASTASIO
 */
public class EcouteurDeSouris extends MouseAdapter {

    /** Référence vers la fenêtre principale contenant la grille */
    private final ImageDansTableau parent;

    /**
     * Constructeur.
     *
     * @param parent la fenêtre principale du jeu
     */
    public EcouteurDeSouris(ImageDansTableau parent) {
        this.parent = parent;
    }

    /**
     * Survole de la souris : met à jour la surbrillance du groupe sous le curseur.
     *
     * @param e l’événement de déplacement de la souris
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        int lignes = parent.grilleTexte.length;
        int colonnes = parent.grilleTexte[0].length();

        int largeurCase = parent.panneauGrille.getWidth() / colonnes;
        int hauteurCase = parent.panneauGrille.getHeight() / lignes;
        int tailleCase = Math.min(largeurCase, hauteurCase);

        int largeurGrille = colonnes * tailleCase;
        int hauteurGrille = lignes * tailleCase;

        int decalageX = (parent.panneauGrille.getWidth() - largeurGrille) / 2;
        int decalageY = (parent.panneauGrille.getHeight() - hauteurGrille) / 2;

        int colonne = (x - decalageX) / tailleCase;
        int ligne = (y - decalageY) / tailleCase;

        if (ligne >= 0 &&
            ligne < lignes &&
            colonne >= 0 &&
            colonne < parent.grilleTexte[ligne].length()) {

            parent.surbrillanceActuelle = Surbrillance.chercherGroupes(parent.grilleTexte, colonne, ligne);
            parent.panneauGrille.repaint();
        }
    }

    /**
     * Clic de souris : casse un groupe s’il est valide, applique la gravité,
     * met à jour le score et l’affichage.
     *
     * @param e l’événement de clic de souris
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int tailleGroupe = parent.surbrillanceActuelle.length;

        if (tailleGroupe > 1) {
            parent.grilleTexte = CassageBlocks.casser(parent.grilleTexte, parent.surbrillanceActuelle);
            parent.score.ajouterPoints(tailleGroupe);
            parent.labelScore.setText("Score : " + parent.score.getValeur());

            parent.surbrillanceActuelle = new int[0][0];
            parent.panneauGrille.repaint();

            // Vérification directe de fin de partie
            if (FinPartie.plusDeCoup(parent.grilleTexte)) {
                parent.dispose(); // Ferme la fenêtre de jeu
                new FenetreFin(parent.score.getValeur()); // Affiche l'écran de fin
            }
        }
    }
}
