package swing;

import gamemanager.Game;
import gamemanager.Genre;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class NewGamePanel extends JPanel {
    private JTextField titre = new JTextField(30);
    private JComboBox<Genre> genre = new JComboBox(Genre.values());
    private JCheckBox termine = new JCheckBox();
    private JSlider progress = new JSlider(0, 100);
    private JFileChooser path = new JFileChooser();
    
    public NewGamePanel() {
	super(new SpringLayout());
	
	this.add(new JLabel("Titre :"));
	this.add(this.titre);	

	// ----- ----- ----- ----- ----- ----- ----- ----- ----- -----
	this.add(new JLabel("Genre :"));
	this.add(this.genre);

	// ----- ----- ----- ----- ----- ----- ----- ----- ----- -----
	this.progress.setMajorTickSpacing(10);
	this.progress.setMinorTickSpacing(5);
	this.progress.setPaintTicks(true);
	this.progress.setPaintLabels(true);
	final JTextField progressLabel = new JTextField("50%", 4);
	    progressLabel.setEditable(false);
	
	this.progress.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		JSlider progress = (JSlider)e.getSource();
		progressLabel.setText(String.valueOf(progress.getValue())+"%");
	    }
	});
	
	this.add(new JLabel("Progression :"));
	JPanel progressPanel = new JPanel(new FlowLayout());
	    progressPanel.add(this.progress);
	    progressPanel.add(progressLabel);
	this.add(progressPanel);

	// ----- ----- ----- ----- ----- ----- ----- ----- ----- -----
	this.termine.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
	    JCheckBox termine = (JCheckBox)e.getSource();
	    progress.setEnabled(!termine.isSelected());
	    progress.setValue( termine.isSelected() ? 100 : progress.getValue() );

	}
    });
	this.add(new JLabel("Terminé :"));
	this.add(this.termine);
	
	// ----- ----- ----- ----- ----- ----- ----- ----- ----- -----
	final JTextField fileChooserLabel = new JTextField();
	    fileChooserLabel.setEditable(false);

	this.path.removeChoosableFileFilter(this.path.getAcceptAllFileFilter());
	this.path.addChoosableFileFilter(new FileNameExtensionFilter("Programmes (.exe)", "exe"));
	JButton fileChooserButton = new JButton("Selectionner un fichier...");
	    fileChooserButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    JFileChooser path = NewGamePanel.this.path;
		    path.showOpenDialog(NewGamePanel.this);
		    if(path.getSelectedFile() != null)
			fileChooserLabel.setText(path.getSelectedFile().getPath());
		}
	    });
	    
	this.add(new JLabel("Lanceur :"));
	JPanel fileChooserPanel = new JPanel(new GridLayout(2, 1));
	    fileChooserPanel.add(fileChooserButton);
	    fileChooserPanel.add(fileChooserLabel);
	this.add(fileChooserPanel);
	
	
	SpringUtilities.makeCompactGrid(this, 5, 2, 5, 5, 5, 15);
    }
    
    public Game getNewGame() {
	Game game = new Game(titre.getText(), (Genre)genre.getSelectedItem());
	game.setTermine(termine.isSelected());
	game.setProgress(progress.getValue());
	game.setPath(path.getSelectedFile());
	return game;
    }

}