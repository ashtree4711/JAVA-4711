package t12.phones.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import t12.spinphone.T12Interpreter;
import t12.util.KeyConverter;

/**
 * <p>
 * Diese Klasse enthält den Code, der das "Telefon"-Fenster implementiert.
 * </p>
 * 
 * <p>
 * Contributors: <br>
 * Stephan Schwiebert - Initial API and implementation (2006)<br>
 * Mihail Atanassov - Refactoring (2015)
 * </p>
 */
public class SpinPhone extends JFrame {

	private static final long serialVersionUID = 8428148445736767880L;

	/**
	 * @see t12.phones.gui.SpinPhone#connectToT12(T12Interpreter)
	 */
	private T12Interpreter interpreter;
	private String oldText = ""; // Enthält den bisher eingegebenen Text ohne
									// das aktuelle Wort
	private String currentWord = ""; // Enthält das aktuelle Wort

	private JButton[] buttons;
	private SettingsFrame settingsFrame;
	private Properties props; // Mit Hilfe dieses Objekts werden die
								// Einstellungen gelesen und gespeichert
	private JTextArea display; // Das "Display" des Telefons
	private JScrollPane displayScrollPane; // Hilfsobjekt, damit das Display
											// Scrollbalken bekommt

	private Color displayColor;

	/**
	 * Konstruktor: Erzeugt die GUI und lädt die Einstellungen aus der Datei
	 * phone_settings.conf, falls vorhanden.
	 */
	public SpinPhone() {
		props = loadProperties();
		initComponents();
		settingsFrame = new SettingsFrame(props);
		boolean shouldStorePositions = false;
		try {
			shouldStorePositions = Boolean.parseBoolean(props.getProperty(
					"storeWindowPositions", "false"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (shouldStorePositions) {
			String x = props.getProperty("phone_x", "0");
			String y = props.getProperty("phone_y", "0");
			this.setLocation(Integer.parseInt(x), Integer.parseInt(y));
		}
	}

	/**
	 * Das Telefon wird mit dem T12Interpreter verbunden.
	 * 
	 * @param interpreter
	 *            eine T12Interpreter-Implementation.
	 */
	public void connectToT12(T12Interpreter interpreter) {
		this.interpreter = interpreter;
		if (Boolean.parseBoolean(props.getProperty("autoLoadLex", "false"))) {
			interpreter.loadLexicon(props.getProperty("lexiconFile"));
		}
		this.setVisible(true);
	}

	/**
	 * Lädt die Einstellungen aus der Datei phone_settings.conf, falls diese
	 * existiert.
	 * 
	 * @return Einstellungen als Properties-Objekt
	 */
	private Properties loadProperties() {
		Properties props = new Properties();
		try {
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream("phone-settings.conf"));
			props.loadFromXML(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return props;
	}

	/**
	 * Diese Methode wird vom Konstruktor ausgeführt und erzeugt die GUI des
	 * Telefons, inkl. ToolTips, Listener usw.
	 */
	private void initComponents() {

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("SpinPhone\u2122 2015");
		setName("phone");

		initDisplay();
		JPanel controlsPanel = initControlsPanel();
		JPanel buttonPanel = initButtonPanel();
		groupLayout(controlsPanel, buttonPanel);

		addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				formKeyTyped(evt);
			}
		});

		addMenuBar();
		pack();
	}

	private void addMenuBar() {

		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu();
		menu.setText("Menu");

		JMenu preference = addColorChooser();

		menuBar.add(menu);
		menuBar.add(preference);

		JMenuItem aboutItem = new JMenuItem();
		aboutItem.setMnemonic('A');
		aboutItem.setText("\u00dcber dieses Programm...");
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				aboutActionPerformed(evt);
			}
		});
		menu.add(aboutItem);

		menu.add(new JSeparator()); // horizontale Trennlinie

		JMenuItem createLexiconItem = new JMenuItem();
		createLexiconItem.setMnemonic('E');
		createLexiconItem.setText("T12 Lexikon erzeugen");
		createLexiconItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				interpreter.generateLexicon(
						settingsFrame.getLexSourceDirectoryName(),
						settingsFrame.getLexFileName());
			}
		});
		menu.add(createLexiconItem);

		JMenuItem loadLexiconItem = new JMenuItem();
		loadLexiconItem.setMnemonic('L');
		loadLexiconItem.setText("T12 Lexikon laden");
		loadLexiconItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				interpreter.loadLexicon(settingsFrame.getLexFileName());
			}
		});
		menu.add(loadLexiconItem);

		menu.add(new JSeparator());

		JMenuItem settingsItem = new JMenuItem();
		settingsItem.setText("Einstellungen");
		settingsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				settingsFrame.setVisible(true);
			}
		});
		menu.add(settingsItem);

		JMenuItem learnItem = new JMenuItem();
		learnItem.setText("Neues Wort lernen");
		learnItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				learnActionPerformed(evt);
			}
		});
		menu.add(learnItem);

		menu.add(new JSeparator());

		JMenuItem quitItem = new JMenuItem();
		quitItem.setMnemonic('B');
		quitItem.setText("Beenden");
		quitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				quitActionPerformed(evt);
			}
		});
		menu.add(quitItem);

		setJMenuBar(menuBar);
	}

	public JMenu addColorChooser() {
		JMenu preference = new JMenu();
		preference.setText("Style");
		JMenuItem displayColorItem = new JMenuItem();
		displayColorItem.setMnemonic('C');
		displayColorItem.setText("Hintergrundfarbe");
		displayColorItem.addActionListener(new ActionListener() {

			private JFrame colorFrame;

			public void actionPerformed(ActionEvent evt) {
				if (colorFrame == null || !colorFrame.isVisible())
					openColorChooser(display);
			}

			public void openColorChooser(final JTextArea display) {
				colorFrame = new JFrame();
				Color initialColor = displayColor != null ? displayColor
						: Color.WHITE;
				final JColorChooser colorChooser = new JColorChooser(
						initialColor);
				AbstractColorChooserPanel[] chooserPanels = colorChooser
						.getChooserPanels();
				AbstractColorChooserPanel[] copyOfPanels = Arrays.copyOfRange(
						chooserPanels, 3, chooserPanels.length);
				colorChooser.setChooserPanels(copyOfPanels);
				colorChooser.getSelectionModel().addChangeListener(
						new ChangeListener() {

							@Override
							public void stateChanged(ChangeEvent e) {
								displayColor = colorChooser.getColor();
								display.setBackground(displayColor);
							}
						});
				colorChooser.setBorder(BorderFactory
						.createTitledBorder("Wähle eine Hintergrundfrabe"));

				colorFrame.add(colorChooser);
				colorFrame.pack();
				colorFrame.setVisible(true);
			}
		});
		preference.add(displayColorItem);
		return preference;
	}

	private JPanel initButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttons = new JButton[12];
		for (int i = 0; i <= 11; i++) {
			buttons[i] = createNumberButton(i);
		}
		GridLayout layoutManager = new GridLayout(4, 3);
		buttonPanel.setLayout(layoutManager);
		for (JButton button : buttons) {
			buttonPanel.add(button);
		}
		return buttonPanel;
	}

	private JPanel initControlsPanel() {
		JPanel controls = new JPanel();
		JButton del = new JButton();
		del.setText("löschen");
		del.setFocusable(false);
		del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setCurrentWord(interpreter.delButtonPressed());
			}
		});
		JButton alt = new JButton();
		alt.setText("alternative");
		alt.setToolTipText("Sucht nach einer Alternative zum aktuellen Wort");
		alt.setFocusable(false);
		alt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setCurrentWord(interpreter.getAlternative());
			}
		});

		GridLayout controlsLayoutManager = new GridLayout(0, 2);
		controls.setLayout(controlsLayoutManager);
		controls.add(del);
		controls.add(alt);
		return controls;
	}

	private void initDisplay() {
		displayScrollPane = new JScrollPane();
		display = new JTextArea();
		display.setColumns(20);
		display.setLineWrap(true);
		display.setRows(5);
		display.setWrapStyleWord(true);
		display.setFocusable(false);
		displayScrollPane.setViewportView(display);
	}

	private void groupLayout(JPanel controlsPanel, JPanel buttonPanel) {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.LEADING).add(
				GroupLayout.TRAILING,
				layout.createSequentialGroup()
						.addContainerGap()
						.add(layout
								.createParallelGroup(GroupLayout.TRAILING)
								.add(GroupLayout.LEADING, controlsPanel,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.add(GroupLayout.LEADING, buttonPanel,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.add(GroupLayout.LEADING, displayScrollPane,
										GroupLayout.DEFAULT_SIZE, 350,
										Short.MAX_VALUE)).addContainerGap()));

		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.LEADING)
				.add(layout
						.createSequentialGroup()
						.addContainerGap()
						.add(displayScrollPane, GroupLayout.PREFERRED_SIZE,
								150, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.RELATED)
						.add(controlsPanel, GroupLayout.DEFAULT_SIZE, 50,
								Short.MAX_VALUE)
						.add(buttonPanel, GroupLayout.DEFAULT_SIZE, 200,
								Short.MAX_VALUE).addContainerGap()));
	}

	private JButton createNumberButton(final int i) {
		final JButton button = new JButton(
				props.getProperty("button_text_" + i));
		button.setToolTipText(props.getProperty("button_tip_" + i));
		button.setFocusable(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (i == 0) {
					// oldText += currentWord + " ";
					// display.setText(oldText);
					// currentWord = "";
					interpreter.wordCompleted();
				} else if (i == 10) {
					interpreter.asteriskButtonPressed();
				} else if (i == 11) {
					interpreter.numberSignButtonPressed();
				} else {
					setCurrentWord(interpreter.buttonPressed(i));
				}
			}
		});
		return button;
	}

	/**
	 * Wird ausgeführt, wenn der Menupunkt "Lernen" ausgewählt wird. Der String,
	 * der eingegeben wird, wird an die Methode learn() des T12Interpreters
	 * weitergegeben. Führende Leerzeichen werden entfernt, falls mehrere Wörter
	 * eingegeben werden, wird nur das erste Wort betrachtet.
	 * 
	 * @see t12.spinphone.T12Interpreter#learn(String)
	 * @param evt
	 *            ActionEvent
	 */
	private void learnActionPerformed(ActionEvent evt) {
		System.out.println(evt.getActionCommand());
		String word = JOptionPane.showInputDialog(this,
				props.get("action_learn"));
		if (word == null)
			return;
		word = word.trim();
		if (word.indexOf(' ') != -1) {
			word = word.substring(0, word.indexOf(' '));
		}
		interpreter.learn(word);
	}

	/**
	 * Wird ausgeführt, wenn der MenuPunkt "Über dieses Programm" aufgerufen
	 * wird. Es wird ein kleiner Info-Dialog gezeigt, als Autor wird das
	 * angezeigt, was die Methode getAuthorName() des T12Interpreters
	 * zurückgibt.
	 * 
	 * @see t12.spinphone.T12Interpreter#getAuthorName()
	 * @param evt
	 *            {@link ActionEvent}
	 */
	private void aboutActionPerformed(ActionEvent evt) {
		System.out.println(evt.getActionCommand());
		ImageIcon icon = new ImageIcon("images/info.png");
		JOptionPane
				.showMessageDialog(
						this,
						"SpinPhone\u2122 2015\nHausarbeit im Javakurs der Sprachlichen Informationsverarbeitung\nAutoren dieses Programms sind "
								+ interpreter.getAuthorName(),
						"Über SpinPhone\u2122 2015",
						JOptionPane.INFORMATION_MESSAGE, icon);
	}

	/**
	 * Wird ausgeführt, wenn das Programm beendet wird. Die Position des
	 * Fensters wird gespeichert.
	 * 
	 * @param evt
	 *            {@link ActionEvent}
	 */
	private void quitActionPerformed(ActionEvent evt) {
		System.out.println(evt.getActionCommand());
		props.put("phone_x", Integer.toString(getX()));
		props.put("phone_y", Integer.toString(getY()));
		try {
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream("phone-settings.conf"));
			props.storeToXML(out,
					"Einstellungen von SpinPhone\u2122 - nicht manuell bearbeiten!");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	/**
	 * Diese Methode konvertiert eingaben über die Tastatur des Computers zu
	 * Eingaben über die Tastatur des Telefons. Alle Buchstaben, die mit Hilfe
	 * des KeyConverters zu Zahlen konvertiert werden können, werden über die
	 * Tastatur des Telefons eingegeben. Whitespaces werden als Betätigung der
	 * Taste '0' interpretiert, die gesondert behandelt wird. Satzzeichen werden
	 * direkt dargestellt, alle übrigen Zeichen werden verworfen.
	 * 
	 * @see t12.util.KeyConverter#convertToNumber(char)
	 * @param evt
	 *            {@link ActionEvent}
	 */
	private void formKeyTyped(KeyEvent evt) {
		char key = Character.toLowerCase(evt.getKeyChar());
		int pressed = KeyConverter.convertToNumber(key);

		System.out.println("Taste '" + evt.getKeyChar() + "' [" + pressed
				+ "] wurde gedrückt");

		switch (pressed) {
		case 2:
			buttons[2].doClick(50);
			break;
		case 3:
			buttons[3].doClick(50);
			break;
		case 4:
			buttons[4].doClick(50);
			break;
		case 5:
			buttons[5].doClick(50);
			break;
		case 6:
			buttons[6].doClick(50);
			break;
		case 7:
			buttons[7].doClick(50);
			break;
		case 8:
			buttons[8].doClick(50);
			break;
		case 9:
			buttons[9].doClick(50);
			break;
		case 0:
			buttons[0].doClick(50);
			break;
		default:
			break;
		}

		if (pressed == -1) {
			// Whitespaces werden als Betätigung der Taste '0' interpretiert:
			if (Character.isSpaceChar(key)) {
				buttons[0].doClick(50);
				return;
			}
			// Prüfung auf Satzzeichen, die direkt übernommen werden:
			if (key == ',' || key == '.' || key == ';' || key == '-'
					|| key == '+' || key == '*' || key == '?' || key == '!'
					|| key == '(' || key == ')' || key == ':') {
				oldText += currentWord + key;
				display.setText(oldText);
				currentWord = "";
				interpreter.wordCompleted();
			}

		}
	}

	/**
	 * Der Variablen <code>currentWord</code> wird der String
	 * <code>newWord</code> zugewiesen, anschließend wird er im Display
	 * dargestellt. Wird der Methode <code>null</code> als Parameter übergeben,
	 * so wird ein Dialog geöffnet, um ein neues Wort zu lernen. Dieses wird
	 * ggf. an die Methode learn() des T12Interpreters weitergegeben.
	 * 
	 * @param newWord
	 *            ein neues Wort (darf null sein).
	 * 
	 * @see T12Interpreter#learn(String newWord)
	 */
	private void setCurrentWord(String newWord) {
		if (newWord == null) {
			newWord = JOptionPane
					.showInputDialog(this,
							"Das Lexikon enthält dieses Wort nicht. Bitte fügen Sie es hinzu");
		}
		if (newWord != null) {
			interpreter.learn(newWord);
			this.currentWord = newWord;
			display.setText(oldText + newWord);
		} else {
			currentWord = "";
			display.setText(oldText);
		}
	}

}
