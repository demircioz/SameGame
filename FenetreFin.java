import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * La classe {@code FenetreFin} représente la fenêtre affichée à la fin d’une partie de SameGame.
 * Elle montre un fond personnalisé, un logo central, le score final du joueur, et trois boutons :
 * "Accueil", "Quitter" et "Musique".
 * <p>
 * La musique d’accueil/fin est jouée automatiquement à l’ouverture, et peut être contrôlée via
 * le bouton "Musique".
 * <p>
 * Cette classe hérite de {@code Fenetre}, et repose sur un panneau personnalisé {@code PanelImage}
 * pour afficher les éléments graphiques et placer les boutons dynamiquement selon la taille de la fenêtre.
 *
 * @author Canpolat DEMIRCI--ÖZMEN
 * @author Théo ANASTASIO
 * @version 1.3
 */
public class FenetreFin extends Fenetre {

    private final int score;

    /**
     * Crée une nouvelle fenêtre de fin de partie, affichant le score et les boutons interactifs.
     *
     * @param scoreFinal le score obtenu à la fin de la partie
     */
    public FenetreFin(int scoreFinal) {
        super("SameGame - Fin de partie");
        this.score = scoreFinal;

        Son.jouer("sons/accueilfin.wav"); // musique de fin

        setContentPane(new PanelImage());
        setVisible(true);
    }

    /**
     * Panneau personnalisé qui gère l'affichage du fond, du logo, du score et des boutons.
     */
    private class PanelImage extends JPanel {

        private BufferedImage fond;
        private BufferedImage logo;
        private final JButton btnAccueil;
        private final JButton btnQuitter;
        private final JButton btnMusique;
        private boolean musiqueActive = true;

        public PanelImage() {
            setLayout(null);
            setOpaque(false);

            try {
                fond = ImageIO.read(new File("img/FondFin.png"));
                logo = ImageIO.read(new File("img/TexteFin.png"));
            } catch (IOException e) {
                System.err.println("Erreur chargement images : " + e.getMessage());
            }

            btnAccueil = creerBouton("Accueil");
            btnQuitter = creerBouton("Quitter");
            btnMusique = creerBouton("Musique");

            btnMusique.setForeground(Color.GREEN.darker());
            btnMusique.addActionListener(e -> {
                musiqueActive = !musiqueActive;
                if (musiqueActive) {
                    Son.jouer("sons/accueilfin.wav");
                    btnMusique.setForeground(Color.GREEN.darker());
                } else {
                    Son.stop();
                    btnMusique.setForeground(Color.RED);
                }
            });

            btnAccueil.addActionListener(e -> {
                Son.stop();
                dispose();
                new FenetreAccueil();
            });

            btnQuitter.addActionListener(e -> {
                Son.stop();
                dispose();
                System.exit(0);
            });

            add(btnAccueil);
            add(btnQuitter);
            add(btnMusique);

            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    repositionnerElements();
                }
            });

            repositionnerElements();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (fond != null) {
                g.drawImage(fond, 0, 0, getWidth(), getHeight(), this);
            }

            // Affichage du score final
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.setColor(Color.WHITE);
            String texteScore = "Score : " + score + " points";
            FontMetrics fm = g.getFontMetrics();
            int xTexte = (getWidth() - fm.stringWidth(texteScore)) / 2;
            int yTexte = (int) (getHeight() * (550.0 / 720));
            g.drawString(texteScore, xTexte, yTexte);

            // Affichage du logo centré
            if (logo != null) {
                int largeur = 500;
                int hauteur = logo.getHeight() * largeur / logo.getWidth();
                int x = (getWidth() - largeur) / 2;
                int y = (getHeight() - hauteur) / 2;
                g.drawImage(logo, x, y, largeur, hauteur, this);
            }
        }

        /**
         * Repositionne dynamiquement les boutons selon la taille de la fenêtre.
         */
        private void repositionnerElements() {
            int totalWidth = btnAccueil.getPreferredSize().width
                           + btnQuitter.getPreferredSize().width
                           + btnMusique.getPreferredSize().width + 40;

            int startX = (getWidth() - totalWidth) / 2;
            int y = getHeight() - 100;

            btnAccueil.setBounds(startX, y,
                    btnAccueil.getPreferredSize().width,
                    btnAccueil.getPreferredSize().height);

            btnQuitter.setBounds(startX + btnAccueil.getPreferredSize().width + 20, y,
                    btnQuitter.getPreferredSize().width,
                    btnQuitter.getPreferredSize().height);

            btnMusique.setBounds(startX + btnAccueil.getPreferredSize().width +
                                 btnQuitter.getPreferredSize().width + 40, y,
                    btnMusique.getPreferredSize().width,
                    btnMusique.getPreferredSize().height);
        }
    }
}
