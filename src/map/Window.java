package map;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame{
	private JPanel jp = new JPanel();
	private String configFile = "";
    private Game game;    
    
	public Window(String configFile){		
		this.configFile = configFile;
		this.game = new Game();
		this.game.mapGenerator(configFile);
        this.jp.setLayout(new BorderLayout());
        this.jp.add(game, BorderLayout.CENTER);        
        this.setContentPane(jp);
        this.setSize(1264, 533);
        this.setVisible(true); 
        this.setResizable(true);
        this.setLocationRelativeTo(null);
              
	}

	public JPanel getJp() {
		return jp;
	}

	public void setJp(JPanel jp) {
		this.jp = jp;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
