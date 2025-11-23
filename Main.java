/**
 * Classe principale {@code Main}, point d'entrée de l'application SameGame.
 * <p>
 * Elle se contente de lancer l'écran d'accueil {@code FenetreAccueil},
 * sans contenir de logique interne, conformément aux bonnes pratiques de structure du projet.
 * </p>
 *
 * @author Canpolat DEMIRCI--ÖZMEN
 * @author Théo ANASTASIO
 * @version 1.0
 */
public class Main
{
    /**
     * Point d'entrée de l'application.
     *
     * @param args les arguments passés en ligne de commande (non utilisés ici)
     */
    public static void main(String[] args)
    {
        new FenetreAccueil();
    }
}
