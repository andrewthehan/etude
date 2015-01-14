
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.text.NumberFormat;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;
import javax.swing.UIManager;
import org.jfugue.Pattern;
import org.jfugue.Player;

class Infinotes extends JFrame implements ActionListener{
	Player player;
	JSpinner keySignature;
	JRadioButton major, minor;
	JTextField timeSignature;
	JTextField tempo;
	JSpinner measures;
	JButton generate;
	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception e){
		}
		new Infinotes();
	}
	public Infinotes(){
		setTitle("Infinotes");
		setLocationByPlatform(true);
		setResizable(false);
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initGui();
		pack();
		setSize(200, getHeight());
		
		player=new Player();
		setVisible(true);
	}
	private void initGui(){
		GridBagConstraints c=new GridBagConstraints(); 
		
		JPanel keyPanel=new JPanel();
		keyPanel.setBorder(BorderFactory.createTitledBorder("Key Signature"));
		keyPanel.setLayout(new GridBagLayout());
		c.gridx=0;
		c.gridy=0;
		c.gridwidth=2;
		c.gridheight=1;
		c.weightx=1;
		c.weighty=1;
		c.fill=GridBagConstraints.BOTH;
		add(keyPanel, c);
		
		CircularSpinnerListModel keyModel=new CircularSpinnerListModel(Music.CHROMATIC_KEYS_ENHARMONIC);
		keySignature=new JSpinner(keyModel);
		JTextField ksTF=((JSpinner.DefaultEditor)keySignature.getEditor()).getTextField();
		ksTF.setEditable(false);
		ksTF.setColumns(5);
		c.gridx=0;
		c.gridy=0;
		c.gridwidth=1;
		c.gridheight=1;
		c.weightx=1;
		c.weighty=1;
		c.fill=GridBagConstraints.HORIZONTAL;
		keyPanel.add(keySignature, c);

		ButtonGroup group=new ButtonGroup();
		major=new JRadioButton("Major");
		major.setSelected(true);
		group.add(major);
		c.gridx=1;
		c.gridy=0;
		c.gridwidth=1;
		c.gridheight=1;
		c.weightx=1;
		c.weighty=1;
		c.fill=GridBagConstraints.NONE;
		keyPanel.add(major, c);
		minor=new JRadioButton("Minor");
		group.add(minor);
		c.gridx=2;
		c.gridy=0;
		c.gridwidth=1;
		c.gridheight=1;
		c.weightx=1;
		c.weighty=1;
		c.fill=GridBagConstraints.NONE;
		keyPanel.add(minor, c);
		
		JPanel timePanel=new JPanel();
		timePanel.setBorder(BorderFactory.createTitledBorder("Time Signature"));
		timePanel.setLayout(new GridBagLayout());
		c.gridx=0;
		c.gridy=1;
		c.gridwidth=1;
		c.gridheight=1;
		c.weightx=1;
		c.weighty=1;
		c.fill=GridBagConstraints.BOTH;
		add(timePanel, c);
		
		timeSignature=new JTextField("4/4");
		c.gridx=0;
		c.gridy=0;
		c.gridwidth=1;
		c.gridheight=1;
		c.weightx=1;
		c.weighty=1;
		c.fill=GridBagConstraints.BOTH;
		timePanel.add(timeSignature, c);
		
		JPanel tempoPanel=new JPanel();
		tempoPanel.setBorder(BorderFactory.createTitledBorder("Tempo"));
		tempoPanel.setLayout(new GridBagLayout());
		c.gridx=1;
		c.gridy=1;
		c.gridwidth=1;
		c.gridheight=1;
		c.weightx=1;
		c.weighty=1;
		c.fill=GridBagConstraints.BOTH;
		add(tempoPanel, c);
		
		tempo=new JTextField("120");
		c.gridx=0;
		c.gridy=0;
		c.gridwidth=1;
		c.gridheight=1;
		c.weightx=1;
		c.weighty=1;
		c.fill=GridBagConstraints.BOTH;
		tempoPanel.add(tempo, c);
		
		JPanel measuresPanel=new JPanel();
		measuresPanel.setBorder(BorderFactory.createTitledBorder("Measures"));
		measuresPanel.setLayout(new GridBagLayout());
		c.gridx=0;
		c.gridy=2;
		c.gridwidth=2;
		c.gridheight=1;
		c.weightx=1;
		c.weighty=1;
		c.fill=GridBagConstraints.BOTH;
		add(measuresPanel, c);
		
		measures=new JSpinner();
		measures.setModel(new SpinnerNumberModel(1, 1, 10000, 1));
		JFormattedTextField mTF=((JSpinner.NumberEditor)measures.getEditor()).getTextField();
		((NumberFormatter) mTF.getFormatter()).setAllowsInvalid(false);
		c.gridx=0;
		c.gridy=4;
		c.gridwidth=2;
		c.gridheight=1;
		c.weightx=1;
		c.weighty=1;
		c.fill=GridBagConstraints.BOTH;
		measuresPanel.add(measures, c);
		
		generate=new JButton("Generate");
		generate.addActionListener(this);
		c.gridx=0;
		c.gridy=3;
		c.gridwidth=2;
		c.gridheight=1;
		c.weightx=1;
		c.weighty=0;
		c.fill=GridBagConstraints.BOTH;
		add(generate, c);
	}
	private void generateMusic(String keySignatureEnharmonic, String timeSignature, String tempo, boolean isMajor, int measures){
		String keySignature=Music.CHROMATIC_KEYS[Arrays.asList(Music.CHROMATIC_KEYS_ENHARMONIC).indexOf(keySignatureEnharmonic)];
		Pattern pattern=new Pattern();
		pattern.add("K"+keySignature+(isMajor?"maj":"min"));
		pattern.add("T"+tempo);
		
		Pattern melody=new Pattern("V0");
		melody.add("I0");
		melody.add(Music.getRandomMelody(timeSignature, measures));
		pattern.add(melody);
		
		Pattern harmony=new Pattern("V1");
		harmony.add("I0");
		harmony.add(Music.getRandomHarmony(timeSignature, measures, keySignature, isMajor));
		pattern.add(harmony);
		
		try{
			pattern.savePattern(new File("pattern.txt"));
			player.saveMidi(pattern, new File("Infinotes.mid"));
			player.play(pattern);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent e){
		new Thread(new Runnable(){
			@Override
			public void run(){
				generateMusic((String)keySignature.getValue(), timeSignature.getText(), tempo.getText(), major.isSelected(), (int)measures.getValue());
			}
		}).start();
	}
}