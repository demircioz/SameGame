import java.awt.*;
import javax.swing.*;

/**
 * Classe FenetreAccueil
 * 
 * Représente la fenêtre d’accueil du jeu SameGame.
 * Affiche un logo centré, trois boutons (Jouer, Grille Prédéfinie, Quitter)
 * et un texte de crédits.
 * 
 * @version 1.1
 * @author
 * Canpolat DEMIRCI--ÖZMEN  
 * Théo ANASTASIO
 */
public class FenetreAccueil extends Fenetre
{
  /** Chemin vers l'image du logo affichée au centre */
  private static final String CHEMIN_LOGO = "img/Logo.png";

  /** Mode actuel de génération de la grille (aléatoire ou prédéfini) */
  private boolean modeAleatoire = false;

  /** État de la musique */
  private boolean musiqueActive = true;

  /** Bouton pour activer/désactiver la musique */
  private JButton btnMusique;

  /**
   * Constructeur de la fenêtre d'accueil.
   * Initialise les composants graphiques.
   */
  public FenetreAccueil()
  {
    super("Accueil - SameGame");
    Son.jouer("sons/accueilfin.wav");
    ajouterLogo();
    ajouterBas();
    setVisible(true);
  }

  /**
   * Charge et affiche le logo centré.
   */
  private void ajouterLogo()
  {
    try {
      Image logoBrut = javax.imageio.ImageIO.read(new java.io.File(CHEMIN_LOGO));
      Image logoReduit = logoBrut.getScaledInstance(600, -1, Image.SCALE_SMOOTH);

      JLabel labelLogo = new JLabel(new ImageIcon(logoReduit), JLabel.CENTER);
      labelLogo.setOpaque(false);

      add(labelLogo, BorderLayout.CENTER);

    } catch (java.io.IOException e) {
      System.err.println("Erreur lors du chargement du logo : " + e.getMessage());
    }
  }

  /**
   * Ajoute les boutons de choix (Jouer, Grille Prédéfinie, Quitter, Musique)
   * et le texte de crédits en bas de la fenêtre.
   */
  private void ajouterBas()
  {
    JPanel panelBas = new JPanel();
    panelBas.setLayout(new BoxLayout(panelBas, BoxLayout.Y_AXIS));
    panelBas.setOpaque(false);

    JPanel panelBoutons = new JPanel();
    panelBoutons.setOpaque(false);

    JButton btnJouer = creerBouton("Jouer");
    JButton btnGrille = creerBouton("Grille Prédéfinie");
    JButton btnQuitter = creerBouton("Quitter");
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

    btnJouer.addActionListener(e -> {
      if (modeAleatoire) {
        ImageDansTableau fenetre = new ImageDansTableau();
        fenetre.genererGrilleAleatoire(10, 15);
        fenetre.setVisible(true);
        Son.stop();
        dispose();
      } else {
        ImageDansTableau fenetre = new ImageDansTableau();
        boolean fichierCharge = fenetre.ouvrirDepuisDialogue();
        
        if (fichierCharge) {
          Son.stop();
          dispose();
        }        
      }
    });

    btnGrille.addActionListener(e -> {
      modeAleatoire = !modeAleatoire;
      if (modeAleatoire) {
        btnGrille.setText("Grille Aléatoire");
      } else {
        btnGrille.setText("Grille Prédéfinie");
      }
    });

    btnQuitter.addActionListener(e -> System.exit(0));

    panelBoutons.add(btnJouer);
    panelBoutons.add(btnGrille);
    panelBoutons.add(btnQuitter);
    panelBoutons.add(btnMusique);

    JLabel labelAuteurs = new JLabel("Réalisé par Canpolat DEMIRCI--ÖZMEN et Théo ANASTASIO");
    labelAuteurs.setFont(new Font("Arial", Font.PLAIN, 18));
    labelAuteurs.setForeground(Color.WHITE);
    labelAuteurs.setOpaque(false);
    labelAuteurs.setAlignmentX(CENTER_ALIGNMENT);

    panelBas.add(panelBoutons);
    panelBas.add(Box.createVerticalStrut(10));
    panelBas.add(labelAuteurs);
    panelBas.add(Box.createVerticalStrut(10));

    add(panelBas, BorderLayout.SOUTH);
  }
}
