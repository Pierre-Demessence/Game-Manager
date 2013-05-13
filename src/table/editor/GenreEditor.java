package table.editor;

import gamemanager.Genre;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class GenreEditor extends DefaultCellEditor {
    public GenreEditor() {
	super(new JComboBox(Genre.values()));
    }
}