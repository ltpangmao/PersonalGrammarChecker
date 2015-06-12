package it.unitn.grammar.pattern;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unitn.grammar.parser.ParserAPIs;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.util.Pair;

public class PatternAnalyzer {

	public LinkedList<LinkedList<Pair<String, String>>> matchPatterns(String sentences, String pattern) {
		// final results. The structure is a bit complex...
		LinkedList<LinkedList<Pair<String, String>>> results = new LinkedList<LinkedList<Pair<String, String>>>();

		// detect variables of the pattern
		LinkedList<String> variables = obtainVariables(pattern);

		// parse initial sentence
		ParserAPIs parser = new ParserAPIs();
		Tree tree = parser.parse(sentences);

		TregexPattern tregex = TregexPattern.compile(pattern);
		TregexMatcher matcher = tregex.matcher(tree);

		while (matcher.find()) {
			// individual match
			LinkedList<Pair<String, String>> individual_match = new LinkedList<Pair<String, String>>();

			// automatically fill all parameters
			for (String variable : variables) {
				// individual variable
				Pair<String, String> individual_variable_pair = new Pair<String, String>();
				// first check whether the variable is found in the string, as it can be an optional variable
				if (matcher.getNode(variable) != null) {
					individual_variable_pair.first = variable;
					individual_variable_pair.second = parser.getLeafWords(matcher.getNode(variable));
					// add the variable of the matched phrase
					individual_match.add(individual_variable_pair);
				}
			}
			// add the matched phrase
			results.add(individual_match);
		}

		return results;
	}

	/**
	 * separate variables from specific RegEx patterns
	 * 
	 * @param patternString
	 * @return
	 */
	private LinkedList<String> obtainVariables(String patternString) {
		LinkedList<String> variables = new LinkedList<String>();

		Pattern pattern = Pattern.compile("=[\\w]*");
		Matcher matcher = pattern.matcher(patternString);
		while (matcher.find()) {
			// remove the "=" of the matched variable (e.g., "=vp")
			String s = matcher.group().substring(1);
			variables.add(s);
		}

		return variables;
	}

	public static void main(String[] args) {
		PatternAnalyzer pa = new PatternAnalyzer();

		String sentences = "A smart meter is an electrical device that records consumption of electrical energy and enables the measurement of energy in both directions";
		sentences = "Send clinical publications to HCN hub";
		String pattern = "/VB.?/=vb $+ (NP=np ?$+ PP=pp)";

		// for(Pair<String, String> variable: variables){
		// System.out.println(variable.first);
		// }

		LinkedList<LinkedList<Pair<String, String>>> results = null;
		results = pa.matchPatterns(sentences, pattern);

		for (LinkedList<Pair<String, String>> match : results) {
			for (Pair<String, String> variable : match) {
				System.out.println(variable.first + ":" + variable.second);
			}
			System.out.println();
		}

	}

}
