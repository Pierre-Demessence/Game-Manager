package gamemanager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;
import swing.JStatusBar;
import swing.NewGamePanel;
import table.ButtonColumn;
import table.editor.GenreEditor;
import table.editor.ProgressionEditor;
import table.editor.TermineEditor;
import table.model.GameModel;
import table.renderer.BooleanCellRenderer;
import table.renderer.GameRenderer;
import table.renderer.ProgressRenderer;

@SuppressWarnings("serial")
public class GameManager extends JFrame {

    private JTable tableau;
    private GameModel model = new GameModel();
    private TableRowSorter<TableModel> sorter;
    private StatusBar statusBar = new StatusBar();

    public GameManager() {
	super();
	setTitle("Game Manager");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	    Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
	}

	// ----- ----- ----- ----- ----- ----- ----- ----- ----- -----
	// Tableau principal
	tableau = new JTable(model);
	tableau.setDefaultRenderer(Object.class, new GameRenderer());
	tableau.setDefaultRenderer(JProgressBar.class, new ProgressRenderer());
	tableau.setDefaultRenderer(Boolean.class, new BooleanCellRenderer());

	tableau.setDefaultEditor(Genre.class, new GenreEditor());
	tableau.setDefaultEditor(JProgressBar.class, new ProgressionEditor());
	tableau.setDefaultEditor(Boolean.class, new TermineEditor());

	sorter = new TableRowSorter<>(tableau.getModel());
	sorter.setSortsOnUpdates(true);
	tableau.setRowSorter(sorter);
	getContentPane().add(new JScrollPane(tableau), BorderLayout.NORTH);
	ListSelectionModel list = tableau.getSelectionModel();
	list.addListSelectionListener(new ListSelectionListener() {
	    @Override
	    public void valueChanged(ListSelectionEvent e) {
		ListSelectionModel list = (ListSelectionModel) e.getSource();
		int selectedRows = 0;
		for (int i = list.getMinSelectionIndex(); i <= list.getMaxSelectionIndex(); i++) {
		    if (list.isSelectedIndex(i)) {
			selectedRows++;
		    }
		}
		statusBar.setSelectedRow(selectedRows);
	    }
	});
	ButtonColumn bc = new ButtonColumn(tableau, new LaunchAction(), 5);

	// ----- ----- ----- ----- ----- ----- ----- ----- ----- -----
	// Boutons en bas
	JPanel boutons = new JPanel();
	boutons.add(new JButton(new AddAction()));
	boutons.add(new JButton(new RemoveAction()));
	boutons.add(new JLabel("Recherche :"));
	JTextField filterArea = new JTextField(15);
	filterArea.getDocument().addDocumentListener(new FilterAction());
	boutons.add(filterArea);
	getContentPane().add(boutons, BorderLayout.SOUTH);

	// ----- ----- ----- ----- ----- ----- ----- ----- ----- -----
	// Menu en haut
	JMenuBar menuBar = new JMenuBar();
	JMenu menuFichier = new JMenu("Fichier");
	JMenuItem itemNew = new JMenuItem("Nouveau...");
	JMenuItem itemLoad = new JMenuItem("Charger...");
	JMenuItem itemSave = new JMenuItem("Enregistrer");
	JMenuItem itemSaveAs = new JMenuItem("Enregistrer sous...");
	JMenu menuJeux = new JMenu("Jeux");
	JMenuItem itemAdd = new JMenuItem("Ajouter...");
	itemAdd.addActionListener(new AddAction());

	// On créé l'arborescence
	this.setJMenuBar(menuBar);
	menuBar.add(menuFichier);
	menuFichier.add(itemNew);
	menuFichier.add(itemLoad);
	menuFichier.add(itemSave);
	menuFichier.add(itemSaveAs);
	menuBar.add(menuJeux);
	menuJeux.add(itemAdd);

	// ----- ----- ----- ----- ----- ----- ----- ----- ----- -----
	// On ajoute une barre de status
	this.getContentPane().add(statusBar, BorderLayout.CENTER);

	// ----- ----- ----- ----- ----- ----- ----- ----- ----- -----
	// On affiche
	pack();
	setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
	new GameManager().setVisible(true);
    }

    private class AddAction extends AbstractAction {

	private AddAction() {
	    super("Ajouter");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    NewGamePanel ngp = new NewGamePanel();
	    if (JOptionPane.showConfirmDialog(
		    null,
		    ngp,
		    "Nouveau Jeu",
		    JOptionPane.OK_CANCEL_OPTION,
		    JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
		model.add(ngp.getNewGame());
	    }
	}
    }

    private class RemoveAction extends AbstractAction {

	private RemoveAction() {
	    super("Supprimer");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    int[] selection = tableau.getSelectedRows();
	    int[] modelIndexes = new int[selection.length];

	    for (int i = 0; i < selection.length; i++) {
		modelIndexes[i] = tableau.getRowSorter().convertRowIndexToModel(selection[i]);
	    }

	    Arrays.sort(modelIndexes);

	    for (int i = modelIndexes.length - 1; i >= 0; i--) {
		model.remove(modelIndexes[i]);
	    }
	}
    }

    private class FilterAction implements DocumentListener {

	@Override
	public void insertUpdate(DocumentEvent e) {
	    String regex = "";
	    try {
		regex = e.getDocument().getText(0, e.getDocument().getLength());
	    } catch (BadLocationException ex) {
		Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    filter(regex);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
	    String regex = "";
	    try {
		regex = e.getDocument().getText(0, e.getDocument().getLength());
	    } catch (BadLocationException ex) {
		Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    filter(regex);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	private void filter(String regex) {
	    sorter.setRowFilter(RowFilter.regexFilter(regex, 0, 1));
	    statusBar.setFilter(regex);
	    System.out.println("i");
	}
    }

    private class LaunchAction extends AbstractAction {

	LaunchAction() {
	    super();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    JTable tableau = (JTable) e.getSource();
	    int modelRow = Integer.valueOf(e.getActionCommand());
	    GameModel model = (GameModel) tableau.getModel();
	    model.getGameAt(modelRow).launchGame();
	}
    }

    private class StatusBar extends JStatusBar {

	private JLabel selectionStatus = new JLabel();
	private JLabel filterStatus = new JLabel();
	private int selectedRows = 0;
	private String filter;

	StatusBar() {
	    super();
	    this.setLeftComponent(selectionStatus);
	    this.addRightComponent(filterStatus);
	    this.updateStatusBar();
	}

	public void setSelectedRow(int selectedRows) {
	    this.selectedRows = selectedRows;
	    updateStatusBar();
	}

	public void setFilter(String filter) {
	    this.filter = filter;
	    updateStatusBar();
	}

	private void updateStatusBar() {
	    this.updateSelectionStatus();
	    this.updateFilterStatus();
	}

	private void updateSelectionStatus() {
	    switch (this.selectedRows) {
	    case 0:
		this.selectionStatus.setText("Aucun jeu sélectionné.");
		break;
	    case 1:
		this.selectionStatus.setText("1 jeu sélectionné.");
		break;
	    default:
		this.selectionStatus.setText(this.selectedRows + " jeux sélectionné.");
		break;
	    }
	}

	private void updateFilterStatus() {
	    if (this.filter != null && !this.filter.equals("")) {
		this.filterStatus.setText("Filtre de recherche : " + this.filter + ".");
	    } else {
		this.filterStatus.setText("Aucun filtre de recherche.");
	    }
	}
    }
}
