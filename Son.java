import java.io.File;
import javax.sound.sampled.*;

/**
 * Classe utilitaire {@code Son} pour gérer la lecture de fichiers audio au format WAV.
 * <p>
 * Elle permet de jouer un son en boucle et de l’arrêter, sans dépendance externe.
 * Le volume est réglé par défaut à un niveau modéré via le contrôle de gain.
 * </p>
 *
 * <p>Les sons sont joués à l’aide de l’API native Java Sound (javax.sound.sampled).</p>
 *
 * @author Canpolat DEMIRCI--ÖZMEN
 * @author Théo ANASTASIO
 * @version 1.0
 */
public class Son {

    private static Clip clip;

    /**
     * Joue un fichier WAV en boucle continue.
     * Si un son est déjà en cours, il est arrêté avant de lancer le nouveau.
     *
     * @param chemin le chemin relatif ou absolu vers le fichier .wav
     */
    public static void jouer(String chemin) {
        stop(); // Arrêter tout son existant avant de démarrer

        try {
            // Chargement du fichier audio (Copyright Naruto ??? nooooo)
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(chemin));
            clip = AudioSystem.getClip();
            clip.open(audioIn);

            // Réglage du volume à -20 dB sinon adieu les oreilles
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);

            // Positionnement au début et lecture en boucle continue
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            System.err.println("Erreur lecture son : " + e.getMessage());
        }
    }

    /**
     * Arrête la lecture du son en cours s’il y en a un.
     */
    public static void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
