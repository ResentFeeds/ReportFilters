package me.electroid.reportfilters.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import me.electroid.reportfilters.ReportFilter;

import com.google.common.base.Preconditions;

public class SimpleReportFilter implements ReportFilter {

	protected final SimpleReportSensitivity sensitivity;
	protected final Map<String, String> reasons;
	protected final Set<String> phrases;
	protected final Set<Pattern> regexes;
	
	/**
	 * A SimpleReportFilter, implements ReportFilter
	 * @param sensitivity An enumeration of the sensitivity level of the regex pattern. (@see ReportSensitivity)
	 * @param reasons The reasons why the filter passed. (languages mapped to reasons)
	 * @param phrases The phrases that help construct the regex.
	 */
	public SimpleReportFilter(SimpleReportSensitivity sensitivity, Map<String, String> reasons, Set<String> phrases) {
		Preconditions.checkNotNull(reasons, "reasons map may not be null");
		Preconditions.checkArgument(reasons.size() >= 1, "reasons map must have at least one entry");
		
		this.sensitivity = sensitivity;
		this.reasons = reasons;
		this.phrases = phrases;
		this.regexes = generateRegexes(sensitivity, phrases);
	}
	
	public SimpleReportSensitivity getSensitivity() {
		return this.sensitivity;
	}
	
	public Map<String, String> getReasons() {
		return this.reasons;
	}
	
	public String getReason() {		
		for (Map.Entry<String, String> entry : this.reasons.entrySet()) {
			// Return the first value if no locale is specified
			return entry.getValue();
		}
		return null;	
	}
	
	public String getReason(String locale) {
		Preconditions.checkNotNull(locale, "language may not be null");
		Preconditions.checkArgument(this.reasons.containsKey(locale), "language must be in the list");

		for (Map.Entry<String, String> entry : this.reasons.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(locale)) {
				return entry.getValue();
			}
		}
		return getReason();
	}
	
	public boolean containsLanguage(String locale) {
		Preconditions.checkNotNull(locale, "language may not be null");
		return this.reasons.containsKey(locale);
	}
	
	public Set<String> getPhrases() {
		return this.phrases;
	}

	public Set<Pattern> getRegexes() {
		return this.regexes;
	}

	public String toString() {
		return "SimpleReportFilter{" + this.sensitivity + "," + this.reasons + "," + this.phrases + "}";
	}
	
	private Set<Pattern> generateRegexes(SimpleReportSensitivity sensitivity, Set<String> phrases) {
		Preconditions.checkNotNull(sensitivity, "sensitivity may not be null");
		Preconditions.checkNotNull(phrases, "phrases may not be null");
		Set<Pattern> set = new HashSet<Pattern>();

		if (sensitivity == SimpleReportSensitivity.ANY) {
			for (String phrase : phrases) {
				set.add(Pattern.compile(phrase, Pattern.CASE_INSENSITIVE));
			}
		} else if (sensitivity == SimpleReportSensitivity.ALL) {
			for (String phrase : phrases) {
				// Remove all non-alphanumeric characters and search for an exact match
				set.add(Pattern.compile("^" + phrase.replaceAll("\\W", "").trim() + "$", Pattern.CASE_INSENSITIVE));
			}
		}
		
		return set;		
	}
	
}
