package work;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import dsai.core.Entry;
import dsai.core.Iterator;
import dsai.core.List;
import dsai.core.Position;
import dsai.impl.LinkedList;
import dsai.impl.ListIterator;
import dsai.impl.SortedListPriorityQueue;
import dsaii.core.Map;
import dsaii.core.Tree;
import dsaii.impl.ChainMap;
import dsaii.impl.LinkedTree;

/**
 * This class implements the dictionary component of the predictive text module
 * of our mobile phone demonstrator. Underlying the implementation is a tree
 * data structure in which each node contains:
 * 
 * <ul>
 * <li>an integer value representing the current keystroke
 * <li>a list of strings that contains the word fragments that correspond to the
 * sequence of keystrokes in the path from the root node to that node.
 * </ul>
 * 
 * This node data is modelled through the inner Keystroke class.
 * 
 * @author remcollier
 */
public class Dictionary {
	/**
	 * This class represents the contents of each node in the tree-based
	 * implementation of the dictionary. Each node basically represents a single
	 * keystroke, this class associates that keystroke with a list of words the
	 * sequence of keystrokes corresponding to the path from the root node to
	 * this node.
	 */
	// test if the addWord method is valid.
	public static void main(String[] args) {
		Keystroke ks = new Dictionary().new Keystroke(0);
		ks.addWord("hello");
		ks.addWord("apply");
		ks.addWord("banana");
		ks.addWord("apple");
		ks.addWord("pineapple");
		System.out.println(ks.getWords());
	}
	
	private class Keystroke {
		int key;
		private List<String> words;

		/**
		 * Constructor for the Keystroke class, that takes an integer (the
		 * keystroke) as a parameter.
		 * 
		 * @param key
		 */
		public Keystroke(int key) {
			this.key = key;
			words = new LinkedList<String>();
		}

		/**
		 * Add another word to this node (this means that the word is a
		 * potential word for the combination of keystrokes that matches the
		 * path from the root node to this node).
		 * 
		 * @param word
		 */
		public void addWord(String word) {
			// TODO: This method must be implemented in answer to question A1
            // use Iterator to traverse the list
			boolean f = true;
			Iterator<String> it = new ListIterator<String>(words);
			while (it.hasNext()) {

				if (it.next().equals(word)) {
					f = false;
					break;
				}
			}

			if (f) {
				words.insertLast(word);

			}

		}

		/**
		 * Return the list of words that is associated with this keystroke. The
		 * current implementation does not impose any ordering on the list (it
		 * is built based on the order in which words are inserted into the
		 * node). In part B of the assignment, you will need to modify this
		 * method to return an ordered list of wo
		 * 
		 * 
		 * rds.
		 * 
		 * @return a list of words
		 */
		public List<String> getWords() {
			/*List<String> sorted=new LinkedList<String>();
			Iterator<String> it=new ListIterator<>(words);
			ArrayList<String> storing=new ArrayList<String>();
			String w;
			while(it.hasNext()){
				w=it.next();
				storing.add(w);
				
			}
			Collections.sort(storing);
			for(String s:storing){
				sorted.insertLast(s);
			}
			return sorted;*/		
			List<String> sortedWords=new LinkedList<String>();
			Map<String,Integer> map=new ChainMap<String,Integer>();
			// the first word's position in the linkedlist
			Position<String> p = this.words.first();  
			//current position in the keystroke
			Position<Dictionary.Keystroke> currentP = null;
        	Iterator<Position<Dictionary.Keystroke>> dicP = tree.positions();
        	// put words in to the map until second last word
        	map.put(p.element(), 0);
        	while(p!=this.words.last()){			
				p=this.words.next(p);
				map.put(p.element(), 0);
			}
			//get a position until it's null
			while(dicP.hasNext()){
				currentP=dicP.next();
				if(currentP.element().equals(this)){
					break;
				}
			}
			// if there are lots of keystroke behind it rather than root node
        	if(tree.parent(currentP) != tree.root())
        	{
        		Iterator<Position<Dictionary.Keystroke>> children = tree.children(currentP);
	        	while(children.hasNext())
	        	{    //every keystroke in children
	        		Position<Dictionary.Keystroke> son = children.next();
	        		//the words in every children
	        		List<String> sonContents = son.element().words;
	        		//the current positin in the children linkedList word list 
	        		Position<String> sonP = sonContents.first();
	        		// the lenght of element's words
	        		int sonlength=sonP.element().length();
	        		//insert the content in the keystroke linkedlist to the map
	        		map.put(sonP.element().substring(0,sonlength-1), map.get(sonP.element().substring(0, sonlength-1))+1);
                    while(sonP!=sonContents.last()){                   	  
						sonP=sonContents.next(sonP);
						map.put(sonP.element().substring(0,sonlength-1), map.get(sonP.element().substring(0, sonlength-1))+1);
					}
	        	}
	        	Iterator<Entry<String,Integer>> mapItr = map.entries();
	        	//put the values into a SortedListPriorityQueue
	        	SortedListPriorityQueue<Integer, String> sortedList = new SortedListPriorityQueue<Integer, String>();
	        	while(mapItr.hasNext())
	        	{
	        		Entry<String,Integer> entry = mapItr.next();
	        		sortedList.insert(entry.value(), entry.key());
	        	}
	        	while(true)
	        	{
	        		if(sortedList.isEmpty())
	        			break;
	        		else
	        		{	
	        			Entry<Integer,String> sortedentry = sortedList.removeMin();
	        			sortedWords.insertFirst(sortedentry.value());
	        		}
	        	}
	        	words = sortedWords;
        	}
        	return words;
			
			
		}

		/**
		 * Generate a string representation of the node data for outputing of
		 * the state of the tree during testing.
		 * 
		 * @return
		 */
		@Override
		public String toString() {
			StringBuffer buf = new StringBuffer();
			buf.append(key);
			buf.append(":");
			Iterator<String> it = new ListIterator<String>(words);
			while (it.hasNext()) {
				buf.append(" ");
				buf.append(it.next());
			}
			return buf.toString();
		}
	}

	/**
	 * This map associates characters with keystrokes and is used by the
	 * insertion algorithm to work out how to add words to the tree.
	 */
	private static Map<Character, Integer> characterMap;

	/**
	 * Initialization block for the characterMap
	 */
	static {
		characterMap = new ChainMap<Character, Integer>();
		characterMap.put('a', 2);
		characterMap.put('b', 2);
		characterMap.put('c', 2);
		characterMap.put('d', 3);
		characterMap.put('e', 3);
		characterMap.put('f', 3);
		characterMap.put('g', 4);
		characterMap.put('h', 4);
		characterMap.put('i', 4);
		characterMap.put('j', 5);
		characterMap.put('k', 5);
		characterMap.put('l', 5);
		characterMap.put('m', 6);
		characterMap.put('n', 6);
		characterMap.put('o', 6);
		characterMap.put('p', 7);
		characterMap.put('q', 7);
		characterMap.put('r', 7);
		characterMap.put('s', 7);
		characterMap.put('t', 8);
		characterMap.put('u', 8);
		characterMap.put('v', 8);
		characterMap.put('w', 9);
		characterMap.put('x', 9);
		characterMap.put('y', 9);
		characterMap.put('z', 9);
	}

	/**
	 * The tree
	 */
	private Tree<Keystroke> tree;

	/**
	 * Default Constructor that creates an empty dictionary.
	 */
	public Dictionary() {
		tree = new LinkedTree<Keystroke>();
		tree.addRoot(new Keystroke(-1));
	}

	/**
	 * Load the specified dictionary file. Each word in the file must be
	 * inserted into the dictionary.
	 * 
	 * @param filename
	 *            the dictionary file to be loaded
	 */
	public void load(String filename) {
		// TODO: This method must be completed for question A4
		BufferedReader in=null;
		try {
			in=new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("no such file found");
			e.printStackTrace();
			
		}
		try {
			//reads a line from the file or returns null if 
			//there's no more to read
			String line=in.readLine();
			while(line!=null){
				insert(line);
				line=in.readLine();
			}
			System.out.println(toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("there's error in reading");
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error closing");
			e.printStackTrace();
		}
	}

	/**
	 * Insert the word into the dictionary. This algorithm loops through the
	 * characters in the word, and uses the character map to work out what
	 * keystroke should be used to select that character (e.g. a,b,c would be
	 * selected by pressing the 2 key).
	 * 
	 * For each sequence of keystrokes, the substring that corresponds to that
	 * sequence is stored at the corresponding node so that
	 * 
	 * @param word
	 */
	public void insert(String word) {
		// TODO: This method must be completed for question A2
		Character c;
		String content=""; // the content in each keystroke node
		
		Position<Keystroke> p=tree.root(); //get the current location of the node 
		for(int i=0;i<word.length();i++){
			c=word.charAt(i);
			content=content+c;
			boolean leaf=true;  // there's no children in this node
			boolean add=false;  // if the word has added
			int phoneNum=characterMap.get(c); // get the num associated with the single word
			Iterator<Position<Keystroke>> children=tree.children(p);
			
			while(children.hasNext()&&!add){
				leaf=false;
				p=children.next();
				int currNum=p.element().key;
				if(currNum==phoneNum){ // if the phone num node has exists 
					boolean exists=false; // judge if the content has in the node
					// if the tree has the node/position already, decide whether add the word to it
					List<String> wordsInNode=p.element().getWords();  //get all words in the node
					Position<String> firstWord=wordsInNode.first(); // get the first word in the list
					Position<String> lastWord=wordsInNode.last(); // get the last word in the list
					while(true){
					   if(firstWord.element().equals(content)){// if the content equals to the first element in the list, change the state of exists to true and add to true;
						   exists=true;
						   add=true;
						   break;
					   }
					   if(firstWord==lastWord){
						   break;
					   }else{
						   firstWord=wordsInNode.next(firstWord);
					   }
					}
					if(!exists){
						p.element().addWord(content);
						add=true;
					}
				}
			}
			
			if(leaf){  // if no children
				Keystroke newNum=new Keystroke(phoneNum);
				newNum.addWord(content);
				p=tree.addChild(p, newNum);
			}
			if(!leaf&&!add){
				p=tree.parent(p); // move the pointer to his parent node 
				Keystroke newNum=new Keystroke(phoneNum);
				newNum.addWord(content);
				p=tree.addChild(p, newNum);
			}
		}
	}

	/**
	 * Output the state of the dictionary (via delegation to the underlying tree
	 * implementation).
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return tree.toString();
	}

	/**
	 * Find the list of words that corresponds to the given sequence of
	 * keystrokes.
	 * 
	 * @param keystrokes
	 *            a sequence of keystrokes
	 * 
	 * @return a list of words
	 */
	public List<String> findWords(List<Integer> keystrokes) {
		// TODO: This method must be completed for question A3
		
		/**
		 * The default null response will cause the invoking method to create a
		 * default word based on the sequence of keystrokes (i.e. the same
		 * behaviour as if there was not entry in the dictionary for the
		 * sequence of keystrokes).
		 */
		List<String> words=new LinkedList<String>();  // a linkedlist to store the words in the keystokes
		Position<Keystroke> r=tree.root();  // the position we traced in the tree
		Position<Integer> phoneNum=keystrokes.first();  
		for(int i=0;i<keystrokes.size();i++){
			boolean flag=false;// a flag to judge if the phoneNum is match each other 
			boolean leaf=true; //judge if there's children in the location
			Iterator<Position<Keystroke>> keys=tree.children(r);
			while(keys.hasNext()){
				leaf=false;
				r=keys.next();
				/*if it matches successfully, then jump out of the while in this layer an go on the next layer,
				if this is the last element in the keystrokes and matches successfully, return the words
				in it, which is the result we want*/
				if(r.element().key==phoneNum.element()){
					flag=true;
					if(i==keystrokes.size()-1){
						words=r.element().getWords();
					}
					break;
				}
			}
			// if there's no children in it which means no words after the location, return null.
			if(leaf){
				return null;
			}
			//if there's more children nodes in it, but no num has match successfully, return null.
			if(!leaf&&!flag){
				return null;
			}
			
			if(phoneNum!=keystrokes.last()){
				phoneNum=keystrokes.next(phoneNum);
			}
		}
		return words;
	}
}
