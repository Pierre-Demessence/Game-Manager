package table.model;

import gamemanager.Game;
import gamemanager.Genre;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class GameModel extends AbstractTableModel {
    public static final int ID		= 0;
    public static final int TITRE	= 1;
    public static final int GENRE	= 2;
    public static final int PROGRESSION	= 3;
    public static final int TERMINE	= 4;
    public static final int ACTION	= 5;
    public static final int PATH	= 6;
    
    private final List<Game> data = new ArrayList<>();
    private final String[] header = {"Id", "Titre", "Genre", "Progression", "Terminé", "Action"};
    
    public GameModel() {
	super();
	Game g = new Game("Toto", Genre.Aventure);
	g.setPath(new File("C:\\Users\\Pierre\\Music\\XMPlay\\xmplay.exe"));
	data.add(g);
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
	return header.length;
    }
    
    @Override
    public Class getColumnClass(int columnIndex) {
	switch(columnIndex) {
	case GameModel.ID:
	    return Integer.class;
	case GameModel.TITRE:
	    return String.class;
	case GameModel.GENRE:
	    return Genre.class;
	case GameModel.PROGRESSION:
	    return JProgressBar.class;
	case GameModel.TERMINE:
	    return Boolean.class;
	case GameModel.ACTION:
	    return JButton.class;
	case GameModel.PATH:
	    return Path.class;
	default:
	    return Object.class;
        }
    }
    
    @Override
    public String getColumnName(int columnIndex) {
	return header[columnIndex];
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
	switch(columnIndex) {
	case GameModel.ID:
	    return false;
	}
	return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	if(aValue != null) {
	    Game game = data.get(rowIndex);

	    switch(columnIndex){
	    case GameModel.TITRE :
		game.setTitre((String)aValue);
	    break;
	    case GameModel.GENRE:
		game.setGenre((Genre)aValue);
	    break;
	    case GameModel.PROGRESSION:
		game.setProgress((int)aValue);
	    break;
	    case GameModel.TERMINE:
		game.setTermine((boolean)aValue);
	    break;
	    case GameModel.PATH:
		game.setPath((File)aValue);
	    break;
	    }
	}
    }    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	Game game = this.getGameAt(rowIndex);
	switch(columnIndex) {
	case GameModel.ID:
	    return rowIndex+1;
	case GameModel.TITRE:
	    return game.getTitre();
	case GameModel.GENRE:
	    return game.getGenre();
	case GameModel.PROGRESSION:
	    return game.getProgress();
	case GameModel.TERMINE:
	    return game.isTermine();
	case GameModel.ACTION:
	    return "Lancer";
	case GameModel.PATH:
	    return game.getPath();
	default:
	    return null;
        }
    }
    
    public Game getGameAt(int rowIndex) {
	return data.get(rowIndex);
    }
    
    public void add(Game game) {
        data.add(game);
        fireTableRowsInserted(data.size() -1, data.size() -1);
    }
 
    public void remove(int rowIndex) {
        data.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

}