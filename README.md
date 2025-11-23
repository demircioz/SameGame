# 🎮 SameGame – Java Project DEV2.1

A colorful puzzle game developed in Java with Swing, as part of the second-semester Java development project. 

---

## 🧠 Game Concept

SameGame is a puzzle game where the player must remove groups of **adjacent blocks of the same color** to **score as many points as possible**.
The larger the group, the higher the score.

The game is based on a **dynamic grid** where blocks **fall down** and **columns shift** automatically after each removal.

---

## 🚀 Main Features

* 🎲 **Grid generation**:

  * Random (with difficulty control)
  * Predefined from a `.txt` file (R, V, B)

* 🖱️ **User interaction**:

  * Hover = group highlighting (yellow background + zoom effect)
  * Click = remove group + score update

* 💾 **Save & Load system**:

  * Saving into a timestamped file (`sauvegardes/`)
  * Manual loading of saved games

* 🔊 **Background music**:

  * Can be enabled/disabled in every window
  * Different background sound depending on context (main menu / game)

* 🎯 **End of game detection**:

  * Display of a final screen with the score

---

## 🧩 Project Structure

```
SameGame/

│── .gitignore
│── LICENSE
│── README.md
│── Makefile
│
│── Main.java                 # Application entry point
│── Fenetre.java              # Main game window/frame
│── FenetreAccueil.java       # Home / main menu window
│── FenetreFin.java           # End game window
│── PanneauGrille.java        # Game grid panel (main board)
│── ImageDansTableau.java     # Representation of game images in a grid
│── CassageBlocks.java        # Logic for breaking/removing blocks
│── FinPartie.java            # End of game detection and handling
│── Surbrillance.java         # Block/group highlight system
│── EcouteurDeSouris.java     # Mouse event listener (click, hover…)
│── Sauvegarde.java           # Save and load system for games
│── Score.java                 # Score calculation and management
│── Son.java                   # Sound and music manager
│
├── img/           # Images and visual resources
├── sons/          # Audio resources
├── sauvegardes/   # Saved games
├── configuration/ # Predefined grids
└── rapport/       # Project report and diagrams

```

---

## 🖼️ Resources

* 📁 `img/` : contains background, candy, and logo images
* 📁 `sons/` : contains the `.wav` files used as background music
* 📁 `configuration/` : contains the predefined grids
* 📁 `sauvegardes/` : directory automatically created to store saved games

---

## ▶️ Running the Project

Make sure you have a JDK installed (**Java 17 recommended**).
Compile and run using:

```
make run
```

If the Makefile doesn’t work on your machine, then:

1. Compile all `.java` files manually with:

   ```
   javac *.java
   ```
2. Run the program:

   ```
   java Main
   ```

---

## 👨‍💻 Authors

Canpolat DEMIRCI–ÖZMEN [Git](https://github.com/demircioz/)
&
Théo ANASTASIO [Git](https://grond.iut-fbleau.fr/anastasi)

> Final Note : 16.00 / 20

Project for our 2nd semester of Computer Sciences – University Institute of Technology of Fontainebleau – UPEC.
