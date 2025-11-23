import java.awt.*;
import java.io.*;
import java.util.Random;
import javax.swing.*;

/**
 * Fenêtre principale du jeu SameGame.
 * Affiche une grille de bonbons à partir d’un fichier ou générée aléatoirement.
 * Permet également de sauvegarder/charger la partie à tout moment.
 *
 * @version 1.4
 */
public class ImageDansTableau extends JFrame {

    /** Grille de caractères représentant les bonbons (R, V, B) */
    public String[] grilleTexte = new String[0];

    /** Images des bonbons rouges, verts et bleus */
    public Image imageRouge, imageVerte, imageBleue;

    /** Panneau central d’affichage de la grille */
    public final PanneauGrille panneauGrille = new PanneauGrille();

    /** Label de score affiché à droite dans la barre de menu */
    public final JLabel labelScore = new JLabel("Score : [à venir]");

    /** Groupe de bonbons actuellement surligné */
    public int[][] surbrillanceActuelle = new int[0][0];

    /** Gestionnaire de score */
    public final Score score = new Score();

    /** État de la musique */
    private boolean musiqueActive = false;

    /** Élément de menu pour la musique */
    private final JMenu menuMusique = new JMenu("Musique");

    /**
     * Constructeur de la fenêtre du jeu.
     */
    public ImageDansTableau() {
        super("SameGame - Grille");

        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        chargerImages();
        setJMenuBar(creerBarreMenu());
        add(panneauGrille, BorderLayout.CENTER);

        panneauGrille.addMouseMotionListener(new EcouteurDeSouris(this));
        panneauGrille.addMouseListener(new EcouteurDeSouris(this));

        musiqueActive = false;
        mettreAJourCouleurMusique(); // bouton rouge au lancement
    }

    /**
     * Crée la barre de menu avec Accueil, Grille, Sauvegarde, Musique, Quitter, et score à droite.
     *
     * @return la barre de menu Swing
     */
    private JMenuBar creerBarreMenu() {
        JMenuBar barre = new JMenuBar();

        // Menu Accueil
        JMenu menuAccueil = new JMenu("Accueil");
        menuAccueil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int reponse = JOptionPane.showConfirmDialog(ImageDansTableau.this,
                        "Voulez-vous vraiment retourner à l'accueil ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (reponse == JOptionPane.YES_OPTION) {
                    Son.stop();
                    dispose();
                    new FenetreAccueil();
                }
            }
        });
        barre.add(menuAccueil);

        // Menu Grille
        JMenu menuGrille = new JMenu("Grille");

        JMenuItem itemCharger = new JMenuItem("Charger une grille");
        itemCharger.addActionListener(e -> ouvrirFichier());

        JMenuItem itemAleatoire = new JMenuItem("Grille aléatoire");
        itemAleatoire.addActionListener(e -> genererGrilleAleatoire(10, 15));

        menuGrille.add(itemCharger);
        menuGrille.add(itemAleatoire);
        barre.add(menuGrille);

        // Menu Sauvegarde
        JMenu menuSauvegarde = new JMenu("Sauvegarde");

        JMenuItem itemSauvegarder = new JMenuItem("Sauvegarder");
        itemSauvegarder.addActionListener(e -> Sauvegarde.enregistrer(grilleTexte, score.getValeur()));

        JMenuItem itemChargerPartie = new JMenuItem("Charger");
        itemChargerPartie.addActionListener(e -> {
            JFileChooser selecteur = new JFileChooser("sauvegardes/");
            int resultat = selecteur.showOpenDialog(this);
            if (resultat == JFileChooser.APPROVE_OPTION) {
                File fichier = selecteur.getSelectedFile();
                Sauvegarde.SauvegardePartie partie = Sauvegarde.chargerDepuisFichier(fichier);
                if (partie != null) {
                    grilleTexte = partie.grille;
                    score.reset();
                    score.ajouterPoints(partie.score);
                    labelScore.setText("Score : " + score.getValeur());
                    panneauGrille.repaint();
                }
            }
        });

        menuSauvegarde.add(itemSauvegarder);
        menuSauvegarde.add(itemChargerPartie);
        barre.add(menuSauvegarde);

        // Menu Musique
        menuMusique.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                musiqueActive = !musiqueActive;
                activerMusique();
            }
        });
        barre.add(menuMusique);

        // Menu Quitter
        JMenu menuQuitter = new JMenu("Quitter");
        menuQuitter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int reponse = JOptionPane.showConfirmDialog(ImageDansTableau.this,
                        "Voulez-vous vraiment quitter le jeu ?",
                        "Quitter",
                        JOptionPane.YES_NO_OPTION);
                if (reponse == JOptionPane.YES_OPTION) {
                    Son.stop();
                    System.exit(0);
                }
            }
        });
        barre.add(menuQuitter);

        // Score à droite
        barre.add(Box.createHorizontalGlue());
        labelScore.setFont(new Font("Arial", Font.BOLD, 14));
        barre.add(labelScore);
        barre.add(Box.createHorizontalStrut(10));

        return barre;
    }

    /**
     * Active ou désactive la musique selon l’état actuel.
     * Met à jour la couleur du bouton et joue/arrête le son.
     */
    private void activerMusique() {
        if (musiqueActive) {
            Son.jouer("sons/jeu.wav");
        } else {
            Son.stop();
        }
        mettreAJourCouleurMusique();
    }

    /**
     * Met à jour la couleur du texte du bouton "Musique" selon son état.
     */
    private void mettreAJourCouleurMusique() {
        menuMusique.setForeground(musiqueActive ? Color.GREEN.darker() : Color.RED);
    }

    /**
     * Ouvre une boîte de dialogue pour choisir un fichier de grille.
     */
    private void ouvrirFichier() {
        JFileChooser selecteur = new JFileChooser("configuration/");
        int resultat = selecteur.showOpenDialog(this);

        if (resultat == JFileChooser.APPROVE_OPTION) {
            File fichier = selecteur.getSelectedFile();
            lireFichier(fichier);
            panneauGrille.repaint();
        }
    }

    /**
     * Appelé depuis FenetreAccueil : ouvre un fichier et affiche la grille.
     *
     * @return true si un fichier a été chargé, false sinon
     */
    public boolean ouvrirDepuisDialogue() {
        JFileChooser selecteur = new JFileChooser("configuration/");
        int resultat = selecteur.showOpenDialog(this);

        if (resultat == JFileChooser.APPROVE_OPTION) {
            File fichier = selecteur.getSelectedFile();
            lireFichier(fichier);
            setVisible(true);
            panneauGrille.repaint();
            return true;
        }

        return false;
    }

    /**
     * Charge les images de bonbons depuis le dossier img/.
     */
    private void chargerImages() {
        imageRouge = new ImageIcon("img/red.png").getImage();
        imageVerte = new ImageIcon("img/green.png").getImage();
        imageBleue = new ImageIcon("img/blue.png").getImage();
    }

    /**
     * Lit le contenu d’un fichier texte et le transforme en grille.
     * Les lignes sont automatiquement complétées pour avoir la même longueur.
     *
     * @param fichier le fichier contenant les lignes de grille
     */
    private void lireFichier(File fichier) {
        try (BufferedReader lecteur = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            int index = 0;
            String[] temp = new String[100];
            int maxLongueur = 0;

            while ((ligne = lecteur.readLine()) != null) {
                ligne = ligne.trim().toUpperCase();
                StringBuilder propre = new StringBuilder();

                for (char c : ligne.toCharArray()) {
                    if (c == 'R' || c == 'V' || c == 'B') {
                        propre.append(c);
                    }
                }

                temp[index++] = propre.toString();
                if (propre.length() > maxLongueur) {
                    maxLongueur = propre.length();
                }
            }

            grilleTexte = new String[index];
            for (int i = 0; i < index; i++) {
                String ligneOriginale = temp[i];
                int espaces = maxLongueur - ligneOriginale.length();
                grilleTexte[i] = ligneOriginale + " ".repeat(espaces);
            }

            score.reset();
            labelScore.setText("Score : " + score.getValeur());

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur de lecture : " + e.getMessage());
        }
    }

    /**
     * Génère une grille aléatoire de dimensions données,
     * en évitant les gros blocs de couleur consécutifs.
     *
     * @param lignes    nombre de lignes
     * @param colonnes  nombre de colonnes
     */
    public void genererGrilleAleatoire(int lignes, int colonnes) {
        String[] grille = new String[lignes];
        Random rand = new Random();
        char[] couleurs = {'R', 'V', 'B'};

        for (int y = 0; y < lignes; y++) {
            StringBuilder ligne = new StringBuilder();
            for (int x = 0; x < colonnes; x++) {
                char couleurChoisie;
                int essais = 0;

                do {
                    couleurChoisie = couleurs[rand.nextInt(couleurs.length)];
                    essais++;
                } while (
                    (x > 1 && couleurChoisie == ligne.charAt(x - 1) && couleurChoisie == ligne.charAt(x - 2)) ||
                    (y > 1 && couleurChoisie == grille[y - 1].charAt(x) && couleurChoisie == grille[y - 2].charAt(x)) &&
                    essais < 10
                );

                ligne.append(couleurChoisie);
            }
            grille[y] = ligne.toString();
        }

        this.grilleTexte = grille;
        score.reset();
        labelScore.setText("Score : 0");
        panneauGrille.repaint();
    }
}
