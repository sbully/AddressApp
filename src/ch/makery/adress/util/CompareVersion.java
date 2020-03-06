package ch.makery.adress.util;

public class CompareVersion {
	
	public static int compareVersions(String versionMini, String versionActu) {
		String[] tabVMin = versionMini.split("\\.");
		String[] tabVActu = versionActu.split("\\.");
		
		int length = Math.max(tabVMin.length, tabVActu.length);
		
		for(int i=0; i<length; i++) {
			Integer Vmin = i< tabVMin.length ? Integer.parseInt(tabVMin[i]) :0;
			Integer VActu = i< tabVMin.length ? Integer.parseInt(tabVActu[i]) :0;
			int compare = VActu.compareTo(Vmin);
			if(compare < 0) {
				return compare;
			}
			
		}
		
		
		
		return 0;
	}

}
