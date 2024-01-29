import java.io.File;
public class MainFilesystem extends FileTraversingClass{
	File currentFile;
	File nearFolder;
	File[] currentFolderItems;
	String currentFilePath;
	int IS_FILE = 2, IS_FOLDER = 3;
	/*
		*The mainFs class will handle requests such as Reading a file , Asserting a directory and many other Non GUI components.
	*/
	public MainFilesystem(){
		this.nearFolder = new  File("/");
		this.currentFile = this.nearFolder;
		this.currentFilePath = this.currentFile.getAbsolutePath();
		//I'm not certain if the following assignment always yields the desired result .... but right know I'm sure it works for what is in my mind 
		//that's why I didn't handle the error;
		this.currentFolderItems = this.nearFolder.listFiles();
	}
	public String returnCurrentLocation(){
		return this.currentFilePath;
	}
	
}	