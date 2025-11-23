### VARIABLES ###

JC = javac
JCFLAGS = -encoding UTF-8 -implicit:none

JVM = java
JVMFLAGS =

### REGLES ESSENTIELLES ###

Main.class : Main.java FenetreAccueil.class
	${JC} ${JCFLAGS} Main.java

FenetreAccueil.class : FenetreAccueil.java Fenetre.class Son.class
	${JC} ${JCFLAGS} FenetreAccueil.java

FenetreFin.class : FenetreFin.java Fenetre.class Son.class
	${JC} ${JCFLAGS} FenetreFin.java

ImageDansTableau.class : ImageDansTableau.java MoteurSameGame.class ControleurSouris.class PanneauGrille.class
	${JC} ${JCFLAGS} ImageDansTableau.java

Fenetre.class : Fenetre.java
	${JC} ${JCFLAGS} Fenetre.java

MoteurSameGame.class : MoteurSameGame.java Groupement.class Decalage.class Fall.class FinJeu.class
	${JC} ${JCFLAGS} MoteurSameGame.java

ControleurSouris.class : ControleurSouris.java
	${JC} ${JCFLAGS} ControleurSouris.java

PanneauGrille.class : PanneauGrille.java
	${JC} ${JCFLAGS} PanneauGrille.java

Groupement.class : Groupement.java
	${JC} ${JCFLAGS} Groupement.java

Fall.class : Fall.java
	${JC} ${JCFLAGS} Fall.java

Decalage.class : Decalage.java
	${JC} ${JCFLAGS} Decalage.java

FinJeu.class : FinJeu.java
	${JC} ${JCFLAGS} FinJeu.java

Son.class : Son.java
	${JC} ${JCFLAGS} Son.java

Surbrillance.class : Surbrillance.java
	${JC} ${JCFLAGS} Surbrillance.java

LectureFichier.class : LectureFichier.java
	${JC} ${JCFLAGS} LectureFichier.java

CassageBlocks.class : CassageBlocks.java
	${JC} ${JCFLAGS} CassageBlocks.java

Sauvegarde.class : Sauvegarde.java
	${JC} ${JCFLAGS} Sauvegarde.java

### REGLE D’EXECUTION ###

run : Main.class
	${JVM} ${JVMFLAGS} Main

### REGLES DE NETTOYAGE ###

clean :
	-rm -f *.class

mrproper : clean

### BUTS FACTICES ###

.PHONY : run clean mrproper

### FIN ###
