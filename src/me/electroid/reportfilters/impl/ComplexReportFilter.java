package me.electroid.reportfilters.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class ComplexReportFilter extends SimpleReportFilter {
	
	protected final Set<Pattern> regexes;
	
	/**
	 * A ComplexReportFilter, extends SimpleReportFilter
	 * @param regexes A set of patterns that the filter contains.
	 * @param reasons The reasons why the filter passed. (language mapped to reason)
	 */
	public ComplexReportFilter(Set<Pattern> regexes, Map<String, String> reasons) {
		super(null, reasons, null);
		this.regexes = regexes;
	}
	
	/**
	 * A simpler constructor of ComplexReportFilter with only one regex
	 * @param regex The regex for this filter.
	 * @param reasons The reasons why the filter passes. (language mapped to reason)
	 */
	public ComplexReportFilter(Pattern regex, Map<String, String> reasons) {
		super(null, reasons, null);
		Set<Pattern> set = new HashSet<Pattern>();
		set.add(regex);
		this.regexes = set;
	}
	
	@Override
	public String toString() {
		return "ComplexReportFilter{" + this.reasons + "," + this.regexes + "}";
	}
	
}
