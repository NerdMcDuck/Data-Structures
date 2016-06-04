package apps;

import search.*;
import java.io.*;


public class LittleSearchEngineDriver
{
	
	//static BufferedReader inputFile = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String args[]) throws IOException
	{
		String docsFile = "docs.txt";
		String noiseWordsFile = "noisewords.txt";
		
		LittleSearchEngine nerdMcDuckEngine = new LittleSearchEngine();	
		
		nerdMcDuckEngine.makeIndex(docsFile, noiseWordsFile);
		
		String kw1 = "deep";
		String kw2 = "world";
		
		nerdMcDuckEngine.top5search(kw1, kw2);
	
		
	}
	
}