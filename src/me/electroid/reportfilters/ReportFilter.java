package me.electroid.reportfilters;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * An interface for filtering reports (primarily for bukkit servers).
 * @author ElectroidFilms
 *
 */
public interface ReportFilter {
	
    /**
     * The patterns of this filter.
     */
    Set<Pattern> getRegexes();

    /**
     * A reason for why the filter failed to pass. (locale mapped to reason)
     */
    Map<String, String> getReasons();
    
    /**
     * The first reason for why the filter failed to pass. (invoked if locale isn't in the reasons map)
     */
    String getReason();
    
    /**
     * The reason for why the filter failed to pass in a specific locale.
     */
    String getReason(String locale);
    
    /**
     * If the filter's reason map contains a locale.
     */
    boolean containsLanguage(String locale);
    
}
