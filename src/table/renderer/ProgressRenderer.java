package table.renderer;

import java.awt.Component;
import javax.swing.BoundedRangeModel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class ProgressRenderer extends JProgressBar implements TableCellRenderer {

    public ProgressRenderer() {
	super();
	this.setStringPainted(true);
    }

    public ProgressRenderer(BoundedRangeModel newModel) {
	super(newModel);
    }

    public ProgressRenderer(int orient) {
	super(orient);
    }

    public ProgressRenderer(int min, int max) {
	super(min, max);
    }

    public ProgressRenderer(int orient, int min, int max) {
	super(orient, min, max);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	setValue((int)value);
	return this;
    }
}