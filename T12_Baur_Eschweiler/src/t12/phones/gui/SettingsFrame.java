package t12.phones.gui;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

/**
 * <p>
 * Diese Klasse enthält den Code, um das "Einstellungen"-Fenster des Telefons zu
 * implementieren. Sie wurde mit Hilfe des GUI-Editors von NetBeans erzeugt und
 * um einige Methoden (Speichern und Laden der Einstellungen) erweitert, weshalb
 * der Code einigermaßen unübersichtlich ist. Glücklicherweise müssen Sie in
 * dieser Klasse aber nichts ändern.
 * </p>
 * 
 * <p>
 * Contributors: <br>
 * Stephan Schwiebert - Initial API and implementation (2006)<br>
 * Mihail Atanassov - Refactoring (2015)
 * </p>
 */
public class SettingsFrame extends JFrame {

	private static final long serialVersionUID = 605087244812434042L;
	private Properties props;

	private JCheckBox storePositions;
	private JCheckBox loadLex;
	private JTextField lexSourceDir;
	private JTextField lexStore;
	private JLabel lexSourceLabel;
	private JButton cancel;
	private JButton ok;

	private String path;
	private File selectedFile;

	/**
	 * Settings-Dialog
	 * 
	 * @param props
	 *            Properties
	 * 
	 */
	public SettingsFrame(Properties props) {
		this.props = props;
		initComponents();
		boolean shouldStorePositions = false;
		try {
			shouldStorePositions = Boolean.parseBoolean(props.getProperty(
					"storeWindowPositions", "false"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (shouldStorePositions) {
			String x = props.getProperty("settings_x", "0");
			String y = props.getProperty("settings_y", "0");
			this.setLocation(Integer.parseInt(x), Integer.parseInt(y));
		}

	}

	private void initComponents() {
		ok = new JButton();
		cancel = new JButton();
		lexSourceLabel = new JLabel();
		loadLex = new JCheckBox();
		storePositions = new JCheckBox();
		lexStore = new JTextField();
		lexSourceDir = new JTextField();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("T12 Einstellungen");
		setAlwaysOnTop(true);
		setName("settings");
		ok.setText("OK");
		ok.setName("ok");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				okActionPerformed(evt);
			}
		});

		cancel.setText("Abbrechen");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cancelActionPerformed(evt);
			}
		});

		loadLex.setText("Lexikon automatisch laden");
		loadLex.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		loadLex.setMargin(new Insets(0, 0, 0, 0));
		loadLex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				loadLexActionPerformed(evt);
			}
		});

		try {
			boolean autoLoadLex = Boolean.parseBoolean(props.getProperty(
					"autoLoadLex", "false"));
			if (autoLoadLex) {
				loadLex.setSelected(autoLoadLex);
			} else {
				loadLex.setSelected(false);
				loadLexActionPerformed(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		storePositions.setText("Fensterpositionen speichern");
		storePositions.setMargin(new Insets(0, 0, 0, 0));

		boolean shouldStorePositions = false;
		try {
			shouldStorePositions = Boolean.parseBoolean(props.getProperty(
					"storeWindowPositions", "false"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		storePositions.setSelected(shouldStorePositions);

		lexSourceLabel.setText("Lexikon generieren aus diesem Verzeichnis:");

		final String lexDir = new File(props.getProperty("lexiconDirectory",
				"/data")).getAbsolutePath();
		lexSourceDir.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				openChooseDialog(lexDir);
			}
		});
		lexSourceDir.setText(lexDir);

		String lexFile = props.getProperty("lexiconFile", "SpinPhone.lex");
		lexStore.setText(lexFile);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.LEADING)
				.add(layout
						.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(GroupLayout.TRAILING)
								.add(layout
										.createSequentialGroup()
										.add(cancel)
										.addPreferredGap(LayoutStyle.RELATED)
										.add(ok, GroupLayout.PREFERRED_SIZE,
												75, GroupLayout.PREFERRED_SIZE))
								.add(layout
										.createParallelGroup(
												GroupLayout.LEADING, false)
										.add(layout.createSequentialGroup()
												.add(loadLex)).add(lexStore)
										.add(storePositions)
										.add(lexSourceLabel).add(lexSourceDir)))
						.addContainerGap(16, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.LEADING)
				.add(layout
						.createSequentialGroup()
						.addContainerGap()
						.add(layout.createParallelGroup(GroupLayout.BASELINE)
								.add(loadLex))
						.addPreferredGap(LayoutStyle.RELATED)
						.add(16, 16, 16)
						.add(lexSourceLabel)
						.addPreferredGap(LayoutStyle.RELATED)
						.add(lexSourceDir, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.add(lexStore, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.add(21, 21, 21)
						.add(storePositions)
						.add(39, 39, 39)
						.add(layout.createParallelGroup(GroupLayout.BASELINE)
								.add(ok).add(cancel))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		pack();
	}

	private void openChooseDialog(String file) {
		final JFileChooser fc = path != null ? new JFileChooser(path)
				: new JFileChooser(file);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setMultiSelectionEnabled(false);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(getSelf());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			path = fc.getCurrentDirectory().getAbsolutePath();
			System.out.println("Datei ausgewählt in " + path);
			selectedFile = fc.getSelectedFile();
			lexSourceDir.setText(selectedFile.getAbsolutePath());
		}
	}

	private JFrame getSelf() {
		return this;
	}

	private void loadLexActionPerformed(ActionEvent evt) {
		lexStore.setEnabled(loadLex.isSelected());
	}

	private void cancelActionPerformed(ActionEvent evt) {
		this.setVisible(false);
		this.dispose();
	}

	private void okActionPerformed(ActionEvent evt) {
		boolean autoLoad = loadLex.isSelected();
		boolean storePos = storePositions.isSelected();
		String sourceDir = lexSourceDir.getText();
		String store = lexStore.getText();
		props.put("autoLoadLex", Boolean.toString(autoLoad));
		props.put("storeWindowPositions", Boolean.toString(storePos));
		props.put("lexiconDirectory", sourceDir);
		props.put("lexiconFile", store);
		props.put("settings_x", Integer.toString(getX()));
		props.put("settings_y", Integer.toString(getY()));
		try {
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream("phone-settings.conf"));
			props.storeToXML(out,
					"Einstellungen von SpinPhone - nicht manuell bearbeiten!");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setVisible(false);
		this.dispose();
	}

	public String getLexFileName() {
		return lexStore.getText();
	}

	public String getLexSourceDirectoryName() {
		return lexSourceDir.getText();
	}

}
