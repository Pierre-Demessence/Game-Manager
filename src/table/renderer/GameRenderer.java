package table.renderer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import table.model.GameModel;

@SuppressWarnings("serial")
public class GameRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	
	setHorizontalAlignment(JLabel.CENTER);
	
	GameModel model = (GameModel) table.getModel();
	if(isSelected)
	    c.setBackground(Color.LIGHT_GRAY);
	else if((boolean)model.getValueAt(row, 4))
	    c.setBackground(Color.GREEN);
	else
	    c.setBackground(Color.WHITE);
	
	return c;
    }
    
}