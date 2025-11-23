import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Classe utilitaire pour gérer les sauvegardes de parties dans SameGame.
 * <p>
 * Chaque sauvegarde est enregistrée dans un fichier texte dans le dossier {@code sauvegardes}.
 * Le format du fichier est le suivant :
 * <ul>
 *     <li>1ère ligne : le score (entier)</li>
 *     <li>Lignes suivantes : contenu de la grille, une ligne par rangée</li>
 * </ul>
 * <p>
 * Cette classe fournit aussi une méthode pour recharger une partie à partir d’un fichier.
 *
 * @author Canpolat DEMIRCI--ÖZMEN
 * @author Théo ANASTASIO
 * @version 1.0
 */
public class Sauvegarde {

    private static final String DOSSIER = "sauvegardes";

    /**
     * Enregistre la grille et le score actuel dans un fichier texte horodaté.
     * Le dossier "sauvegardes" est créé s’il n’existe pas.
     *
     * @param grille la grille du jeu à sauvegarder
     * @param score  le score à enregistrer
     */
    public static void enregistrer(String[] grille, int score) {
        try {
            // Création du dossier s'il n'existe pas
            Files.createDirectories(Paths.get(DOSSIER));

            // Nom du fichier basé sur la date et l'heure actuelles
            String horodatage = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            File fichier = new File(DOSSIER + "/partie_" + horodatage + ".txt");

            // Écriture dans le fichier
            try (PrintWriter writer = new PrintWriter(new FileWriter(fichier))) {
                writer.println(score); // Ligne 1 : score
                for (String ligne : grille) {
                    writer.println(ligne); // Lignes suivantes : contenu de la grille
                }
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    /**
     * Charge une sauvegarde à partir d’un fichier texte.
     * <p>
     * Le score est récupéré sur la première ligne, suivi des lignes de la grille.
     *
     * @param fichier le fichier texte contenant une sauvegarde valide
     * @return un objet {@code SauvegardePartie} contenant le score et la grille, ou {@code null} en cas d’erreur
     */
    public static SauvegardePartie chargerDepuisFichier(File fichier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            // Lecture du score
            String ligneScore = reader.readLine();
            if (ligneScore == null) return null;

            int score = 0;
            try {
                score = Integer.parseInt(ligneScore.trim());
            } catch (NumberFormatException e) {
                System.err.println("Score invalide en première ligne : " + ligneScore);
                return null;
            }

            // Lecture de la grille
            List<String> lignes = new ArrayList<>();
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                lignes.add(ligne);
            }

            String[] grille = lignes.toArray(new String[0]);
            return new SauvegardePartie(grille, score);

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la sauvegarde : " + e.getMessage());
            return null;
        }
    }

    /**
     * Classe interne représentant une sauvegarde : score + grille.
     * Cette classe est utilisée comme conteneur pour transférer les données de sauvegarde.
     */
    public static class SauvegardePartie {
        public final String[] grille;
        public final int score;

        /**
         * Construit une nouvelle instance avec les données chargées.
         *
         * @param grille la grille lue depuis le fichier
         * @param score  le score lu depuis le fichier
         */
        public SauvegardePartie(String[] grille, int score) {
            this.grille = grille;
            this.score = score;
        }
    }
}
