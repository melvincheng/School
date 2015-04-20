import java.util.*;
import java.io.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;

public class MainMenu extends JFrame{
	public MainMenu(){
		super("Choose");
		setVisible(true);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
				System.exit(0);
			}
		});
	}
	public void paint(Graphics g){
		JPanel container = new JPanel();
		LayoutManager layout = new LayoutManager();

		container.setLayout(layout);

		JComponent c1 = new Button();
		JComponent c2 = new Button();
		JComponent c3 = new Button();
		container.add(c1);
		container.add(c2);
		container.add(c3, placement);
	}
	static class Button extends JComponent{
		public void button(){

		}
	}
	static class Layout extends BorderLayout{
		public void Layout
	}
}