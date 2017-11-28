package work;

import dsai.core.List;
import dsai.impl.LinkedList;
/*the test about question A2 is in the Dictionary class*/
public class TestInsert {
	public static void main(String[] args) {
        // test the insert method in question A2
		Dictionary dic = new Dictionary();
		// Dictionary.Keystroke key=dic.new Keystroke(0);
		dic.insert("orange");
		System.out.println(dic.toString());
		dic.insert("apply");
		System.out.println(dic.toString());
		dic.insert("apple");
		System.out.println(dic.toString());
		dic.insert("gc");
		System.out.println(dic.toString());
		dic.insert("ban");
		System.out.println(dic.toString());
		
		//test the findWord method in the question A3
		List<Integer> i = new LinkedList<Integer>();

		i.insertLast(2);
		i.insertLast(7);
		i.insertLast(7);
		System.out.println(i.toString());
		System.out.println(dic.findWords(i));

	}
}
