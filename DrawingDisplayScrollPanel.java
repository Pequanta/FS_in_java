import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel; 
import javax.swing.JFrame;
public class DrawingDisplayScrollPanel extends JPanel {

	public DrawingDisplayScrollPanel() {
		this.setBackground(Color.white);

		this.setPreferredSize(new Dimension(250,250));

	}

	public void paintComponent(Graphics g){

		super.paintComponent(g);

		Color oldColor = g.getColor();

		g.setColor(Color.red);

		g.drawRect(0,0,249,249);

		g.drawString("Border of preferred size.",10,240);

		g.setColor(Color.blue);

		g.fillOval(300,150,20,20);

		g.drawString("This is outside the preferred size",260,180);

		g.setColor(oldColor);

	}
	public static void main(String[] args){
		DrawingDisplayScrollPanel inst = new DrawingDisplayScrollPanel();
		JFrame frame = new JFrame("hello");
		frame.setSize(250, 250);
		frame.add(inst);
		frame.setVisible(true);

	}
}