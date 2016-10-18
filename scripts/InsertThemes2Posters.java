import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 *  @author ssomasundaran  in project EMNLP2016 on Oct 17, 2016  
 */

/**
 * @author ssomasundaran
 *
 */
public class InsertThemes2Posters {

	public static  HashMap<String, String[]> readThemeCSV (String fName) throws IOException{
		BufferedReader b = new BufferedReader( new FileReader(fName));
		String line  = null;
		String id  =  null;
		String theme =  null;
		String tag  =  null;
		HashMap<String, String[]> posterM  =  new HashMap<String, String[]>();
		while (  (line  = b.readLine()) != null){
			String [] cells =  line.split(",");
			id = cells[2];
			if(id.equals("1240/TACL")){
				id = "TACL-009";
			} else {
				if(id.length()==1){
					id = "00"+id;
				}
				if(id.length()==2){
					id = "0"+id;
				}
				id = "papers-" + id;
			}
			posterM.put(id, cells);
		}
		b.close();
		return posterM;
	}
	
	/**
	 * 
	 * @author ssomasundaran  in project EMNLP2016 on Oct 17, 2016  
	 * @param posterM
	 * @param line = \posterabstract{papers-640}{}
	 * @return
	 */
	public static String makeIndexedPosterLine (HashMap<String, String[]> posterM  , String line){
		String index []  =  line.split("[{}]");
		String key = index[1];
		String info [] =  posterM.get(key);
		String out =  "\\hfill \\textit{["+ info[3].replaceAll("&", "\\\\&") + "]  [ID:"+info[1]+"]} \\\\ \n"+ line ;
		return out;
	}
	
	/** @author ssomasundaran  in project EMNLP2016 on Oct 17, 2016  
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String texF =  args[0];
		String themeF =  args[1];
		HashMap<String, String[]> posterM  =  readThemeCSV(themeF);
		BufferedReader b =  new BufferedReader( new FileReader (texF));
		String line =  null; 
		BufferedWriter  wr =  new BufferedWriter(new FileWriter (texF.replace(".tex", "-withThemes.tex")));
		while (  ( line  =  b.readLine()) != null ){
			if(line.trim().startsWith("\\posterabstract")){
				String nLine  =  makeIndexedPosterLine(posterM, line);
				wr.write(nLine + "\n");
			} else {
				wr.write(line +"\n");
			}
		}
		wr.flush();
		wr.close();

	}

}
