import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/**
 * Classe Fenetre
 * 
 * Classe de base pour les fenêtres graphiques du projet SameGame.
 * Fournit un fond d'écran, des méthodes utilitaires pour créer des boutons
 * et gère la taille et l'initialisation commune des fenêtres.
 * 
 * @version 1.0
 * @author
 * Canpolat DEMIRCI--ÖZMEN  
 * Théo ANASTASIO
 */
public abstract class Fenetre extends JFrame
{
  private static final Dimension TAILLE_BOUTON = new Dimension(200, 50);
  private static final Font POLICE_BOUTON = new Font("Arial", Font.BOLD, 16);
  private static final String CHEMIN_FOND = "img/FondMenu.png";

  /**
   * Constructeur de base pour toute fenêtre du jeu.
   * Initialise la taille, le fond et la disposition.
   * 
   * @param titre le titre affiché dans la barre de la fenêtre
   */
  public Fenetre(String titre)
  {
    super(titre);
    setSize(1280, 720);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setContentPane(new PanelFond(CHEMIN_FOND));
    setLocationRelativeTo(null);
  }

  /**
   * Crée un bouton standardisé (taille et police prédéfinies).
   * 
   * @param texte le texte à afficher sur le bouton
   * @return un bouton stylisé
   */
  protected JButton creerBouton(String texte)
  {
    JButton bouton = new JButton(texte);
    bouton.setFont(POLICE_BOUTON);
    bouton.setPreferredSize(TAILLE_BOUTON);
    return bouton;
  }

  
  /**
   * Classe interne PanelFond
   * 
   * Panneau personnalisé qui dessine une image en fond de fenêtre.
   */
  private static class PanelFond extends JPanel
  {
    private transient Image imageFond;

    /**
     * Construit un panel avec l'image de fond donnée.
     * 
     * @param cheminImage chemin relatif de l'image à charger
     */
    public PanelFond(String cheminImage)
    {
      super(new BorderLayout());
      try {
        imageFond = ImageIO.read(new File(cheminImage));
      } catch (IOException e) {
        System.err.println("Erreur chargement fond : " + e.getMessage());
      }
    }

    /**
     * Dessine l'image de fond sur toute la surface disponible.
     * 
     * @param g contexte graphique Swing
     */
    @Override
    protected void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      if (imageFond != null) {
        g.drawImage(imageFond, 0, 0, getWidth(), getHeight(), this);
      }
    }
  }
}
