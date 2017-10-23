//Author: Jose Eduardo Sandoval Polanco

package com.edsand;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.io.File;


public class Eview extends JFrame {
  private JPanel panel;
  private JButton btn_next, btn_back;
  private JLabel lblBigImage, lblAct;
  private JMenu menu;
  private JMenuBar menuBar;
  private JMenuItem openItem, exitItem;
  private JLabel lblAnt1, lblAnt2, lblAnt3, lblAnt4, lblAnt5;
  private JLabel lblSig1, lblSig2, lblSig3, lblSig4, lblSig5;
  private File image_dir;
  private JFileChooser chooser;
  private Imagen imagen_actual;
  private Lista lista;
  private static final int DEFAULT_WIDTH = 1366;
  private static final int DEFAULT_HEIGHT = 768;
  private static final int DEFAULT_IMAGEN_ACTUAL_WIDTH_BIG = 700;
  private static final int DEFAULT_IMAGEN_ACTUAL_HEIGHT_BIG = (int)(700*0.77);
  private static final int DEFAULT_IMAGEN_THUMB_WIDTH = 100;
  private static final int DEFAULT_IMAGEN_THUMB_HEIGTH = 77;

  public Eview() {
    setTitle("Eview");
    setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    setVariables();
    JButton Buttons[] = {btn_back, btn_next};
    getImagenes();
    paintImages("none", getLabels());
    setLabelBounds(getLabels());
    addComponets(Buttons, getLabels());
    addActionListeners();
  }
  
  public void paintImages(String movement, JLabel Labels[]) {
  	if (movement.equals("none")) {
    	Labels[0].setIcon(imagen_actual.getImagen());
    	Labels[1].setIcon(resize(imagen_actual.getImagen()));
    	for (int i = 2; i < 7; i++) 
      	Labels[i].setIcon(resize(getAnt(imagen_actual, i-1)));
    	for (int i = 7; i < Labels.length; i++) 
      	Labels[i].setIcon(resize(getSig(imagen_actual, i-6)));
  	} else if (movement.equals("back")) {
    	Labels[0].setIcon(getAnt(imagen_actual, 1));
    	for (int i = 1; i < 7; i++)
      	Labels[i].setIcon(resize(getAnt(imagen_actual, i)));
    	for (int i = 7; i < Labels.length; i++) 
      	Labels[i].setIcon(resize(getSig(imagen_actual, i-7)));
    	imagen_actual = imagen_actual.getAnt();
  	} else {
    	Labels[0].setIcon(getSig(imagen_actual, 1));
    	Labels[1].setIcon(resize(getSig(imagen_actual, 1)));
    	for (int i = 2; i < 7; i++)
      	Labels[i].setIcon(resize(getAnt(imagen_actual, i-2)));
    	for (int i = 7; i < Labels.length; i++)
      	Labels[i].setIcon(resize(getSig(imagen_actual, i-5)));
    	imagen_actual = imagen_actual.getSig();
  	}
  }
  
  public void getImagenes() {
    for (File f: image_dir.listFiles()) {
      ImageIcon icon = new ImageIcon(f.getPath());
      lista.meter(new Imagen(icon, DEFAULT_IMAGEN_ACTUAL_WIDTH_BIG,
      						DEFAULT_IMAGEN_ACTUAL_HEIGHT_BIG));
    }
    imagen_actual = lista.getInicio();
  }
  
  public void setVariables() {
    lblBigImage = new JLabel();
    lblAnt1 = new JLabel();
    lblSig1 = new JLabel();
    lblAct = new JLabel();
    lblAnt2 = new JLabel();
    lblSig2 = new JLabel();
    lblAnt3 = new JLabel();
    lblAnt4 = new JLabel();
    lblSig3 = new JLabel();
    lblSig4 = new JLabel();
    lblSig5 = new JLabel();
    lblAnt5 = new JLabel();
    chooser = new JFileChooser();
    chooser.setDialogTitle("Eview: Escoge el Directorio de Imagenes");
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
    	image_dir = new File(chooser.getSelectedFile().getPath());
    else image_dir = new File("images/");
    lista = new Lista();
    menu = new JMenu("Archivo");
    menuBar = new JMenuBar();
    openItem = new JMenuItem("Abrir");
    btn_next = new JButton("Sig");
    btn_back = new JButton("Atras");
    exitItem = new JMenuItem("Exit");
    chooser.setCurrentDirectory(new File("."));
    panel = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        g.setColor(Color.white);
        g.fillRect(DEFAULT_WIDTH/2-65, DEFAULT_HEIGHT-215, 130, 107);
      }
    };
  }
  
  public ImageIcon getAnt(Imagen imagen, int recorrido) {
    if (recorrido == 0) return imagen.getImagen();
    return getAnt(imagen.getAnt(), --recorrido);
  }
  
  public ImageIcon getSig(Imagen imagen, int recorrido) {
    if (recorrido == 0) return imagen.getImagen();
    return getSig(imagen.getSig(), --recorrido);
  }
  
  public void setLabelBounds(JLabel Labels[]) {
    panel.setLayout(null);
    int middle_x = DEFAULT_WIDTH/2;
    int act_x = middle_x-50;
    int act_y = DEFAULT_HEIGHT-200;
    int x_offset = 120;
    btn_back.setBounds(middle_x-110, DEFAULT_HEIGHT-100, 100, 50);
    btn_next.setBounds(middle_x+10, DEFAULT_HEIGHT-100, 100, 50);
    Labels[0].setBounds((DEFAULT_WIDTH - DEFAULT_IMAGEN_ACTUAL_WIDTH_BIG)/2, 10, 
    										DEFAULT_IMAGEN_ACTUAL_WIDTH_BIG, 
    										DEFAULT_IMAGEN_ACTUAL_HEIGHT_BIG);
    Labels[1].setBounds(act_x, act_y, DEFAULT_IMAGEN_THUMB_WIDTH,
    									  DEFAULT_IMAGEN_THUMB_HEIGTH);
    for (int i = 2; i < 7; i++) {
      Labels[i].setBounds(act_x - x_offset, act_y, DEFAULT_IMAGEN_THUMB_WIDTH, 
      										DEFAULT_IMAGEN_THUMB_HEIGTH);
      Labels[i+5].setBounds(act_x + x_offset, act_y, DEFAULT_IMAGEN_THUMB_WIDTH, 
      											DEFAULT_IMAGEN_THUMB_HEIGTH);
      x_offset += 120;
    }
  }
  
  public void addComponets(JButton Buttons[], JLabel Labels[]) {
    menuBar.add(menu);
    setJMenuBar(menuBar);
    for (JButton button: Buttons) panel.add(button);
    for (JLabel label: Labels) panel.add(label);
    menu.add(openItem);
    this.getContentPane().add(panel);
  }
  
  public ImageIcon resize(ImageIcon image) {
    Image img = image.getImage().getScaledInstance(DEFAULT_IMAGEN_THUMB_WIDTH,
    																							 DEFAULT_IMAGEN_THUMB_HEIGTH,
    																						   Image.SCALE_SMOOTH);
    return new ImageIcon(img);
  }
  
  public JLabel[] getLabels() {
    JLabel Labels[] = {lblBigImage, lblAct, 
                       lblAnt1,     lblAnt2,  lblAnt3, lblAnt4, lblAnt5,
                       lblSig1,     lblSig2,  lblSig3, lblSig4,  lblSig5};   
    return Labels;      
  } 
  
  public void addActionListeners() {
    openItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
          String name = chooser.getSelectedFile().getPath();
          lista.meter(new Imagen(new ImageIcon(name), 
          											 DEFAULT_IMAGEN_ACTUAL_WIDTH_BIG,
      													 DEFAULT_IMAGEN_ACTUAL_HEIGHT_BIG));
        }
      }
    });
    menu.add(exitItem);
    exitItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        System.exit(0);
      }
    });
    btn_next.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        if (imagen_actual.getSig() != null) paintImages("next", getLabels());
      }
    });
    btn_back.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        if (imagen_actual.getAnt() != null) paintImages("back", getLabels());
      }
    });
  }
  
  public static void main(String[] args) {
    JFrame frame = new Eview();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}