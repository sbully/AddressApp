package ch.makery.adress.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
	
	/*
	 * Format de la date utiliser pour la convertion 
	 */
	private static final String DATE_PATTERN = "dd.MM.yyyy";
	
	/*
	 * Formater de DATE
	 */
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

	/*
	 * Convertion d'une LOCALDATE en String
	 * utilisation du format DATE_PATTERN
	 */
	public static String format(LocalDate date) {
		if(date==null) {
			return null;
		}
		return DATE_FORMATTER.format(date);
	}
	
	public static LocalDate parse(String dateString)
	{
		try {
			return DATE_FORMATTER.parse(dateString, LocalDate::from);
		}
		catch (DateTimeParseException e)
		{
			return null;
		}
		
	}

    // Essai de parse la String dateString et retourne un boolean
	public static boolean validDate(String dateString) {
        return DateUtil.parse(dateString) != null;
    }
}
