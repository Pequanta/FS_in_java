import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.io.*;
import java.io.File;
import java.awt.Desktop;
import java.util.*;
public class MainSurface{
	//the following Global declarations ensure the declared components are common and could be exploited throught the class
	static JFrame mainFrame;
	static JLabel currentFileLabel;
	static LinkedList<JButton> subFiles = new LinkedList<JButton>();
	static JButton[] quicklyAccessable;
	static MainFilesystem fileSystemInstance;//this instance extends the functionality of The filesystem class to the the surfaces interface;
	static JPanel mainWindowPanel, quickAccessPanel, optionMenuPanel;
	static JScrollPane mainWindowPane, quickAccessPane, optionMenuPane;
	static JButton nextButton, previousButton;
	Desktop desktop;
	final static FileTraversingClass fileTraversal = new FileTraversingClass();
	//this constants help with the configuration of the desplay.... the size of the main window and its sub-windows
	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	final int MAIN_FRAME_HEIGHT = (int) size.getHeight() - 50 , MAIN_FRAME_WIDTH = (int) size.getWidth() - 200;
	final int OPTION_MENU_W = (int) size.getWidth(), OPTION_MENU_H = 50;
	final int QUICK_ACCESS_WINDOW_H = (int) size.getHeight() - 50, QUICK_ACCESS_WINDOW_W = 200;
	final int MAIN_WINDOW_W = (int) size.getWidth(), MAIN_WINDOW_H = (int)size.getHeight();
	Icon iconFolder = new ImageIcon("folder.png");
	Icon iconFile = new ImageIcon("file.png");
	int CURRENT_ITEMS_SIZE;
	class FileOpener implements ActionListener{
		private File fileRecieved;
		private String fileIndicator;
		public FileOpener(File fileRecieved){	
			this.fileRecieved = fileRecieved;
		}
		public FileOpener(String filesInd){
			this.fileIndicator = filesInd;
		}
		public void actionPerformed(ActionEvent ae){
			//This conditional expression will later be handled using try and catch expression
			if(this.fileIndicator != null){
				if(this.fileIndicator == "previous"){
					try{
						fileTraversal.previous();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				else if(this.fileIndicator == "next"){
					try{
						fileTraversal.next();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			else{
				try{
					if(this.fileRecieved.isDirectory()){ 
						fileTraversal.nextDir(this.fileRecieved);
						fileSystemInstance.currentFile = fileTraversal.currentDirectoryForward.fileCont;
						fileSystemInstance.nearFolder = fileSystemInstance.currentFile;
						fileSystemInstance.currentFilePath = fileSystemInstance.currentFile.getAbsolutePath();
						fileSystemInstance.currentFolderItems = fileSystemInstance.nearFolder.listFiles();
						mainWindowPanel.removeAll();
						//optionMenuPanel.removeAll();//this operation should be reconstructed only to modify the directory;
						mainWindowPanelH();
						//optionMenuPanel.add()
						mainWindowPanel.revalidate();
						//optionMenuPanel.revalidate();
						//optionMenuPanel.repaint();
						mainWindowPanel.repaint();
					}	
					else if(this.fileRecieved.isFile()){
						if(!Desktop.isDesktopSupported()){
							System.out.println("not supported");
							return;
						}
						try{
							desktop = Desktop.getDesktop();
						desktop.open(this.fileRecieved);
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}			
				}
				catch(Exception e){
					e.printStackTrace();				
				}
			}
		}
	}
	public MainSurface(){

		fileSystemInstance = new MainFilesystem();
		mainFrame = new JFrame("QuantaFS");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
		mainWindowPanel = new JPanel();
		quickAccessPanel = new JPanel();
		optionMenuPanel = new JPanel();
		//***********************************//
		mainWindowPanel.setLayout(null);
		mainWindowPanel.setBackground(Color.GRAY);
		mainWindowPanel.setLayout(new GridLayout(10, 3));
		//***********************************//
		
		quickAccessPanel.setBackground(Color.DARK_GRAY);
		//************************************//
		optionMenuPanel.setBorder(BorderFactory.createRaisedBevelBorder());	
		//************************************//
		mainWindowPanelH();
		quickAccessPanelH();
		optionMenuPanelH();
		mainWindowPane = new JScrollPane(mainWindowPanel);
		quickAccessPane = new JScrollPane(quickAccessPanel);
		optionMenuPane = new JScrollPane(optionMenuPanel);


		mainWindowPane.setLocation(200, 50);
		mainWindowPane.setSize(MAIN_WINDOW_W, MAIN_WINDOW_H);

		quickAccessPane.setLocation(0, 50);
		quickAccessPane.setSize(QUICK_ACCESS_WINDOW_W, QUICK_ACCESS_WINDOW_H);

		optionMenuPane.setLocation(0,0);
		optionMenuPane.setSize(OPTION_MENU_W, OPTION_MENU_H);

		mainFrame.add(mainWindowPane);
		mainFrame.add(quickAccessPane);
		mainFrame.add(optionMenuPane);
		mainFrame.setVisible(true);
		//This program classify the main window into 3 distinict frames
		//The first window used to handle the file accessing request ----> mainWindow --- could be accessed using the variable name mainWindowPanel
		}
	public void mainWindowPanelH(){
		int place = 0, i = 0;

		CURRENT_ITEMS_SIZE = fileSystemInstance.currentFolderItems.length;
		System.out.println(CURRENT_ITEMS_SIZE);

		while(place < CURRENT_ITEMS_SIZE){
			if(fileSystemInstance.currentFolderItems[i].isDirectory()){
				subFiles.addFirst(new JButton(fileSystemInstance.currentFolderItems[i].getName(), iconFolder));
				subFiles.peek().addActionListener(new FileOpener(fileSystemInstance.currentFolderItems[i]));
				subFiles.peek().setOpaque(false);
				subFiles.peek().setContentAreaFilled(false);
				subFiles.peek().setBorderPainted(false);
			}else{
				subFiles.addLast(new JButton(fileSystemInstance.currentFolderItems[i].getName(), iconFile));
				subFiles.getLast().setOpaque(false);
				subFiles.getLast().setContentAreaFilled(false);
				subFiles.getLast().setBorderPainted(false);
				subFiles.peek().addActionListener(new FileOpener(fileSystemInstance.currentFolderItems[i]));
			}
			i++;
			place++;
		}
		while(subFiles.size() > 0){
				mainWindowPanel.add(subFiles.peek());
				subFiles.pop();
		}


	}
	//The second window which is used to desplay quickly accessable files ---quckAccess --->> could be accessed using the variable name quickAccessPanel
	public void quickAccessPanelH(){
			quicklyAccessable = new JButton[5];
			Set<String> namesAccessQuick = new HashSet<String>(Arrays.asList("Documents", "Downloads", "Videos", "Pictures","Music"));
			File[] quickAccessFiles = new File[5];
			File[] defaultDir = (new File("/home/quantap")).listFiles();
			int i = 0;
			for(File file: defaultDir){
				if(namesAccessQuick.contains(file.getName())){
					quicklyAccessable[i] = new JButton(file.getName());
					quicklyAccessable[i].setFont(new Font("verdana", Font.BOLD, 20));
					quicklyAccessable[i].setForeground(Color.white);
					
					quicklyAccessable[i].setOpaque(false);
					quicklyAccessable[i].setContentAreaFilled(false);
					quicklyAccessable[i].setBorderPainted(false);	
					quicklyAccessable[i].addActionListener(new FileOpener(file));
					i++;
				}
			}
			quickAccessPanel.setLayout(new GridLayout(7, 0));
			for(JButton btn: quicklyAccessable){
				quickAccessPanel.add(btn);
			}



		}
	//The third window serves to desplay information and options ---> Option Window----could be accessed using the 
	public void optionMenuPanelH(){
		optionMenuPanel.setLayout(new FlowLayout());
		previousButton = new JButton("<<");
		nextButton = new JButton(">>");
		FileOpener prev= new FileOpener("previous");
		FileOpener next = new FileOpener("next");
		previousButton.addActionListener(prev);
		nextButton.addActionListener(next);
		optionMenuPanel.add(previousButton);
		optionMenuPanel.add(nextButton);
		optionMenuPanel.add(new JLabel(fileSystemInstance.currentFilePath));
	}

	public static void main(String[] args){
		MainSurface mainSurface = new MainSurface();
	}
}

