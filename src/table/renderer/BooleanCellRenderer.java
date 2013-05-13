package table.renderer;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class BooleanCellRenderer extends GameRenderer {
    private Icon tickIcon;
    private Icon crossIcon;

    public BooleanCellRenderer() {
	super();
	
	tickIcon = new ImageIcon("src/images/tickIcon.png");
	crossIcon = new ImageIcon("src/images/crossIcon.png");
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
 
        setText("");
	if((boolean)value)
	    setIcon(tickIcon);
	else
	    setIcon(crossIcon);
 
        return this;
    }
}