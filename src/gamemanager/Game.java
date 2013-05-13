package gamemanager;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    private String titre = "";
    private Genre genre = null;
    private boolean termine = false;
    private File path = null;
    private int progress = 20;

    public Game(String titre) {
	this.titre = titre;
    }
    public Game(String titre, Genre genre) {
	this(titre);
	this.genre = genre;
    }
    
    public Game(String titre, Genre genre, boolean termine) {
	this(titre, genre);
	this.termine = termine;
    }

    public String getTitre() {
	return titre;
    }

    public void setTitre(String titre) {
	this.titre = titre;
    }

    public Genre getGenre() {
	return genre;
    }

    public void setGenre(Genre genre) {
	this.genre = genre;
    }

    public boolean isTermine() {
	return termine;
    }

    public void setTermine(boolean termine) {
	this.termine = termine;
    }

    public File getPath() {
	return path;
    }

    public void setPath(File path) {
	this.path = path;
    }
    
    public int getProgress() {
	return progress;
    }

    public void setProgress(int progress) {
	this.progress = progress;
    }
    
    public void launchGame() {
	if(this.path != null && this.path.canExecute()) {
	    try {
		Runtime.getRuntime().exec(this.path.getPath());
	    } catch (IOException ex) {
		Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
    
}