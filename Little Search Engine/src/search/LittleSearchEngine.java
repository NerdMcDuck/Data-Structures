package search;

import java.io.*;
import java.util.*;

/**
 * This class encapsulates an occurrence of a keyword in a document. It stores the
 * document name, and the frequency of occurrence in that document. Occurrences are
 * associated with keywords in an index hash table.
 * 
 * @author Sesh Venugopal
 * 
 */
class Occurrence {
	/**
	 * Document in which a keyword occurs.
	 */
	String document;
	
	/**
	 * The frequency (number of times) the keyword occurs in the above document.
	 */
	int frequency;
	
	/**
	 * Initializes this occurrence with the given document,frequency pair.
	 * 
	 * @param doc Document name
	 * @param freq Frequency
	 */
	public Occurrence(String doc, int freq) {
		document = doc;
		frequency = freq;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "(" + document + "," + frequency + ")";
	}
}

/**
 * This class builds an index of keywords. Each keyword maps to a set of documents in
 * which it occurs, with frequency of occurrence in each document. Once the index is built,
 * the documents can searched on for keywords.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in descending
	 * order of occurrence frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash table of all noise words - mapping is from word to itself.
	 */
	HashMap<String,String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashMap<String,String>(100,2.0f);
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.put(word,word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeyWords(docFile);
			mergeKeyWords(kws);
		}
		
	}

	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeyWords(String docFile) 
	throws FileNotFoundException {
		// COMPLETE THIS METHOD
		Scanner sc = new Scanner(new File(docFile));
		HashMap<String, Occurrence> keywords = new HashMap<String,Occurrence>();//a hashmap of keywords
		
			while(sc.hasNext()){//while there is still more
				String currWord = sc.next();
				currWord = getKeyWord(currWord);
				if(currWord == null){ //if the current word isn't valid
					//System.out.println("Nullkeywords: " + currWord); //should just print out null
					continue;
					
				}
				//System.out.println("currWord: " + currWord); //prints out the current word
				if(!(keywords.containsKey(currWord))){//if not in hashmap, add to hashmap
					
					int freqValue = 1; //create a new frequency value to go into the occurrence. Must be 1 since the value is now in the hashmap
					Occurrence occurrence = new Occurrence(currWord,freqValue); //create a new occurrence for the new word
					keywords.put(currWord, occurrence); //add the word/occurrence pair to the hashmap
					//System.out.println("keyword: " + keywords.toString() + "\n");	// prints out the hashmap					
					
				}else /*(keywords.containsKey(getKeyWord(currWord)))*/{ //checks if it's already in the hashmap
					//System.out.println("currWord: " +currWord + " frequency: " + keywords.get(currWord).frequency);
					keywords.get(currWord).frequency++; //if it's already in hashmap, increase it's occurrence 
					//System.out.println("currWord: " +currWord + " frequency: " + keywords.get(currWord).frequency); //prints out the current word and it's frequency
				}//end if
			}//end while
		
		//System.out.println(keywords.toString() + "\n"); //For Debugging purposes
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		return keywords;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeyWords(HashMap<String,Occurrence> kws) {
	ArrayList<Occurrence> occurrence = new ArrayList<Occurrence>(); //Occurrence array list
	Iterator<String> itKey= kws.keySet().iterator(); //An arrayList of iterators over the kws keySet
	
	while(itKey.hasNext()){ //while there are still values in the list
		String currWord = itKey.next(); //assign the next element to a string variable words
		Occurrence occurrenceKey = kws.get(currWord);
		//System.out.println("keyWords: " + currWords + " occurrenceKey: " + occurKey);
		if(!(keywordsIndex.containsKey(currWord))){ //if the master keywordsIndex does not have the currwords
			
			ArrayList<Occurrence> currWordsList = new ArrayList<Occurrence>(); //create a new array list for occurrences
			currWordsList.add(occurrenceKey); //add the current occurrence to the newly created list
			keywordsIndex.put(currWord, currWordsList); //add the string word and it's occurrence into the master hash table
			
		} else { //if it's already in the keywordsIndex
			
			occurrence = keywordsIndex.get(currWord);
			occurrence.add(occurrenceKey);
			insertLastOccurrence(occurrence);
			keywordsIndex.put(currWord, occurrence);
		}
			
	}
	
		// COMPLETE THIS METHOD
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * TRAILING punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyWord(String word) {
		// COMPLETE THIS METHOD
		String str = word.trim().toLowerCase(); //remove all spaces and make lower case
		if(str.length() > 1){ //if the str is greater than 1 char/symbol ex: if str = . 
			char t = str.charAt(str.length()-1); //last character at the end of the string
			
			if(t == '.'|| t == ',' || t == '?' || t == ':'|| t == ';' ||t == '!'){//checks if the last character of the string is any of the following
				//System.out.println("tStr: " + str);
				str = str.substring(0, str.length()-1); //removes trailing punctuation
				t = str.charAt(str.length()-1); //get the last character
				while(t == '.'|| t == ',' || t == '?' || t == ':'|| t == ';' ||t == '!') //if there's still more punctuation 
					str = str.substring(0, str.length()-1); //strips the string of the trailing punctuation
					t = str.charAt(str.length()-1);
					//System.out.println("StringPunctuation: " + str); //debugging stuff 
			}
		}//if not greater than 1
		//System.out.println("str: " + str); //just test
		for(int i = 0; i < str.length(); i++){
			char ch = str.charAt(i); //current character
			//System.out.println("strC: " + c); //even moar tests!
			if(!Character.isLetter(ch)){ //not a valid keyword if it contains any nonAlphabetic Characters 
				//System.out.println("NotValid: " + str); //all the non-valid entries
				return null;
				
			} 
				
		}
		if(noiseWords.containsKey(str)){ //checks if it's a noise word
			return null;
		}
		
		//System.out.println("str: " + str.toString());
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		return str;
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * same list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion of the last element
	 * (the one at index n-1) is done by first finding the correct spot using binary search, 
	 * then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		// COMPLETE THIS METHOD
		if(occs.size() == 1){ // null if the size of the input list is 1
			
			return null;
		}
		
		ArrayList<Integer> midPoints = new ArrayList<Integer>(); //the midpoints
		
		int high = occs.size()-2; //next to last element in ArrayList - last element is the one to be inserted
		int low = 0; //Highest item in the ArrayList since it's in descending order
		int target = occs.get(occs.size()-1).frequency; //the target's frequency, which can be found at the last index of the Occurrence ArrayList
		
		while(high >= low){ //start of Binary Search, 
			
			int mid = (high+low)/2; //midpoint
			midPoints.add(mid);
			
			if(target == occs.get(mid).frequency){ //if targets' frequency equals the midpoint
				
				midPoints.add(mid+1); //add the target
				break; //exit loop
				
			} else if(target > occs.get(mid).frequency) { //if the target is greater than the mid
				
				low = mid + 1;
				
			} else {
				
				high = mid - 1;
			}
				
		}//endWhile and end Binary Search
		
		if(occs.get(midPoints.get(midPoints.size()-1)).frequency < occs.get(occs.size()-1).frequency){
			
			occs.add(1, occs.get(occs.size()-1)); //adds target to the arrayList 
			
			
		} else {
			
			occs.add(midPoints.get(midPoints.size()-1), occs.get(occs.size()-1) );
			
		}	
		occs.remove(occs.size()-1);
		
		//System.out.println("MidPoints: " + midPoints);

		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		return midPoints;
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of occurrence frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will appear before doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matching documents, the result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of NAMES of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matching documents,
	 *         the result is null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		// COMPLETE THIS METHOD
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<Occurrence> kw1List = new ArrayList<Occurrence>();
		ArrayList<Occurrence> kw2List = new ArrayList<Occurrence>();
		
		if(keywordsIndex.get(kw1.toLowerCase()) != null){//only equals if kw1 is not null, this prevents the list themselves from going null
			kw1List = keywordsIndex.get(kw1.toLowerCase());
		}
		if(keywordsIndex.get(kw2.toLowerCase()) != null){//same as kw1
			kw2List = keywordsIndex.get(kw2.toLowerCase());
		}
		int count = 0;
		for(int i = 0; i < kw1List.size(); i++){//iterate over kw1List
			
			for(int j = 0; j < kw2List.size(); j++){
				
				if(kw1List.get(i).frequency > kw2List.get(j).frequency){//if the frequency of the kw1List is greater 
					
					if(!result.contains(kw1List.get(i).document) && count != 5){//check if it's a duplicate and check if result isn't full
						result.add(kw1List.get(i).document); //add to result
						count++;						//increase count	
					} //if contains kw1list or count == 5 move on
						
				}else if(kw1List.get(i).frequency < kw2List.get(j).frequency){
					
					if(!result.contains(kw2List.get(j).document) && count != 5){
						result.add(kw2List.get(j).document);
						count++;
					}
				} else if(kw1List.get(i).frequency == kw2List.get(j).frequency){//if both equals add from kw1List
					
					if(!result.contains(kw1List.get(i).document) && count != 5){
						result.add(kw1List.get(i).document);
						count++;
					}
				}
				
			}//endjfor
				
			
		}//endifor
		
		if(result.isEmpty() == true){
			return null;
		}
		//System.out.println("top5search: " + result);
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		return result;
	}
}
