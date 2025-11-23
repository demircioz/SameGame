import java.awt.*;
import javax.swing.*;

/**
 * Composant graphique personnalisé qui affiche la grille de jeu SameGame.
 * <p>
 * Chaque cellule de la grille peut contenir un bonbon coloré (rouge, vert, bleu),
 * être vide (fond gris clair), ou faire partie d'un groupe sélectionné (surbrillance jaune + zoom).
 * Ce panneau s’adapte dynamiquement à la taille de la fenêtre et utilise les ressources
 * et données stockées dans la classe parente {@code ImageDansTableau}.
 * </p>
 *
 * <ul>
 *     <li>Affichage des bonbons centrés avec espacement</li>
 *     <li>Surbrillance visuelle pour les groupes sélectionnés</li>
 *     <li>Zoom léger + fond jaune pour la sélection</li>
 *     <li>Remplissage gris clair pour les cellules vides</li>
 * </ul>
 *
 * @author Canpolat DEMIRCI--ÖZMEN
 * @author Théo ANASTASIO
 * @version 1.1
 */
public class PanneauGrille extends JPanel {

    /**
     * Redéfinit l'affichage du composant à chaque rafraîchissement graphique.
     *
     * @param g l'objet Graphics utilisé pour dessiner
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Récupération de la fenêtre principale qui contient la grille et les images
        ImageDansTableau parent = (ImageDansTableau) SwingUtilities.getWindowAncestor(this);
        if (parent.grilleTexte.length == 0) return;

        int lignes = parent.grilleTexte.length;
        int colonnes = parent.grilleTexte[0].length();

        // Calcul de la taille idéale pour chaque case en fonction du panneau
        int largeurCase = getWidth() / colonnes;
        int hauteurCase = getHeight() / lignes;
        int tailleCase = Math.min(largeurCase, hauteurCase);  // carré
        int marge = 4; // espace entre les blocs

        // Centrage de la grille dans le panneau
        int largeurGrille = colonnes * tailleCase;
        int hauteurGrille = lignes * tailleCase;
        int decalageX = (getWidth() - largeurGrille) / 2;
        int decalageY = (getHeight() - hauteurGrille) / 2;

        // Parcours de chaque case de la grille
        for (int y = 0; y < lignes; y++) {
            String ligne = parent.grilleTexte[y];
            for (int x = 0; x < ligne.length(); x++) {
                char c = ligne.charAt(x);
                Image image = null;

                // Choix de l'image selon la lettre (R, V, B)
                if (c == 'R') image = parent.imageRouge;
                else if (c == 'V') image = parent.imageVerte;
                else if (c == 'B') image = parent.imageBleue;

                // Position de la case avec marges
                int px = decalageX + x * tailleCase + marge / 2;
                int py = decalageY + y * tailleCase + marge / 2;
                int taille = tailleCase - marge;

                // Vérifie si la case fait partie de la surbrillance
                boolean surbrillance = false;
                for (int[] coord : parent.surbrillanceActuelle) {
                    if (coord[0] == x && coord[1] == y) {
                        surbrillance = true;
                        break;
                    }
                }

                // Application d'un zoom sur les blocs surbrillés
                int zoom = surbrillance ? (int) (taille * 0.1) : 0;
                int pxZoom = px - zoom / 2;
                int pyZoom = py - zoom / 2;
                int tailleZoom = taille + zoom;

                // Fond jaune transparent si la case est surbrillée
                if (surbrillance) {
                    g.setColor(new Color(255, 255, 0, 120));
                    g.fillRect(pxZoom, pyZoom, tailleZoom, tailleZoom);
                }

                // Dessine l'image du bonbon, ou un fond gris si la case est vide
                if (image != null) {
                    g.drawImage(image, pxZoom, pyZoom, tailleZoom, tailleZoom, this);
                } else {
                    g.setColor(new Color(220, 220, 220));
                    g.fillRect(pxZoom, pyZoom, tailleZoom, tailleZoom);
                }

                // Bordure orange si surbrillance
                if (surbrillance) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setColor(Color.ORANGE);
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawRect(pxZoom, pyZoom, tailleZoom - 1, tailleZoom - 1);
                }
            }
        }
    }
}
