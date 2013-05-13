package table.editor;

import java.awt.Component;
import java.awt.event.MouseEvent;
import javax.swing.AbstractCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.TableCellEditor;

@SuppressWarnings("serial")
public class ProgressionEditor extends AbstractCellEditor implements TableCellEditor {
    private int progress;
    private JProgressBar bar = new JProgressBar(0, 100);
 
    public ProgressionEditor() {
        super();
 
	bar.setStringPainted(true);
	bar.addMouseListener(new MouseInputAdapter() {
	    @Override
	    public void mousePressed(MouseEvent e) {
		bar.setValue(progress);
		try {
		    int value = Integer.parseInt(JOptionPane.showInputDialog("Taux de progression ?"));
		    ProgressionEditor.this.progress = Math.max(0, Math.min(100, value));
		} catch(NumberFormatException exception) {}
		fireEditingStopped();
	    }
	});
    }
 
    @Override
    public Object getCellEditorValue() {
        return progress;
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        progress = (int)value;
 
        return bar;
    }
}