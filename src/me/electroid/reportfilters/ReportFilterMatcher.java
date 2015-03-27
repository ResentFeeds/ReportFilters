package me.electroid.reportfilters;

import java.util.Set;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

public class ReportFilterMatcher {

	protected Set<ReportFilter> filters;
	
	/**
	 * A ReportFilterMatcher, queries any ReportFilter
	 * @param filters A set of report filters to check against given strings
	 */
	public ReportFilterMatcher(Set<ReportFilter> filters) {
		Preconditions.checkNotNull(filters, "filters may not be null");
		this.filters = filters;
	}
	
	public Set<ReportFilter> getFilters() {
		return this.filters;
	}
	
	public void setFilters(Set<ReportFilter> filters) {
		Preconditions.checkNotNull(filters, "filters may not be null");
		this.filters = filters;
	}
	
	public void addFilter(ReportFilter filter) {
		Preconditions.checkNotNull(filter, "filter may not be null");
		this.filters.add(filter);
	}
	
	public void removeFilter(ReportFilter filter) {
		Preconditions.checkArgument(filters.contains(filter), "filter is not in the filters list");
		this.filters.remove(filter);
	}
	
	/**
	 * Check if @param string passes the filters. If it passes, return the string. If it fails, return the reason in the locale.
	 * @param string The string to check against the filters.
	 * @param locale The language to return the reason in, if applicable.
	 * @return A string, either the original message or the reason why it failed.
	 */
	public String matches(String string, String locale) {
		for (ReportFilter filter : filters) {
			for (Pattern regex : filter.getRegexes()) {
				if (string.matches(regex.pattern()) == true) {
					if (filter.containsLanguage(locale)) {
						// Return the reason the string passed if it matches
						return filter.getReason(locale);
					} else {
						return filter.getReason();
					}
				}
			}
		}	
		return string;	
	}
	
	public boolean matches(String string) {
		if (matches(string, "").equalsIgnoreCase(string)) {
			return true;
		} else {
			return false;
		}
	}
	
}
