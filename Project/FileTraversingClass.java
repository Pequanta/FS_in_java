import java.io.File;
class Node{
	File fileCont;
	Node nextFile;
	public Node(File fileSent){
		this.fileCont = fileSent;
		this.nextFile = null;
	}
}
public class FileTraversingClass{
	Node currentDirectoryForward;
	Node currentDirectoryBackward;

	public FileTraversingClass(){
		/*this method accepts a selected File path from the caller and checks if it is traversable;
		 *if it is,it lets the user to go forward with the selected directory else it returns false
		 *the implementation uses the Stack Data structure to store.There are two stacks here. The 
		 *first stack keeps track of the forward traverse while the second one helps to record movement to the previous 
		*/
		this.currentDirectoryForward = null;
		this.currentDirectoryBackward = null;
	}
	public File nextDir(File selectedFile){
		if(selectedFile.isDirectory()){
			Node newDirectory = new Node(selectedFile);
			newDirectory.nextFile = this.currentDirectoryForward;
			this.currentDirectoryForward = newDirectory;
			return this.currentDirectoryForward.fileCont;
		}	
		else return null;
	}
	public File previous(){
		if(this.currentDirectoryForward == null) return null;
		Node newDirectory = new Node(this.currentDirectoryForward.fileCont);
		newDirectory.nextFile = this.currentDirectoryBackward;
		this.currentDirectoryBackward = newDirectory;
		this.currentDirectoryForward = this.currentDirectoryForward.nextFile;
		return this.currentDirectoryForward.fileCont;
	}
	public File next(){
		if(this.currentDirectoryBackward != null){
				Node newDirectory = new Node(this.currentDirectoryBackward.fileCont);
				newDirectory.nextFile = this.currentDirectoryForward;
				this.currentDirectoryForward = newDirectory;
				this.currentDirectoryBackward = this.currentDirectoryBackward.nextFile;
		}
		return this.currentDirectoryForward.fileCont;
	}
}