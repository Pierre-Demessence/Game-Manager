package table.editor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

@SuppressWarnings("serial")
public class TermineEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private boolean termine;
    private JButton bouton;
 
    public TermineEditor() {
        super();
 
        bouton = new JButton();
        bouton.addActionListener(this);
        bouton.setBorderPainted(false);
	bouton.setVisible(false);
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        termine ^= true;
 
        fireEditingStopped();
    }
 
    @Override
    public Object getCellEditorValue() {
        return termine;
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        termine = (Boolean)value;
 
        return bouton;
    }
}