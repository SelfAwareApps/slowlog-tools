package elastictools;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * parses ElasticSearch slow logs
 * Expected line format is 
 * [<time>][<severity>][<source>] [<node name>] [<index name>][<shard>] took[<took], took_millis[<took_millis>], types[<types>], stats[<stats], search_type[<search_type>, total_shards[<total_shards>], source[<source_body>], extra_source[<extra_source>],
 * some fields such as <node name>, <stats> may be empty
 * returns strings that may require post processing as they have multiple field values
 */

public class SlowLogParser {

	final static String parse_exception="Slowlog parse exception";
	final static String logregex = "^\\[(?<time>\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3})\\]\\s?\\[(?<severity>[A-Z]+\\s*)\\]\\s?\\[(?<source>[^\\]]+)\\]\\s?\\[(?<node>[^\\]]+)\\]\\s?\\[(?<index>[^\\]]+)\\]\\s?\\[(?<shard>[^\\]]+)\\]\\s?took\\[(?<took>[^\\]]+)\\],\\s?took_millis\\[(?<took_millis>[^\\]]+)\\],\\s?types\\[(?<types>[^\\]]+)\\],\\s?stats\\[(?<stats>[^\\]]*)\\],\\s?search_type\\[(?<search_types>[^\\]]+)\\],\\s?total_shards\\[(?<total_shards>[^\\]]+)\\],\\s?source(?<source_body>[^\\]]+)\\],\\s?extra_source\\[(?<extra_source>[^\\]]*)";
	
	private String time, severity,source,node_name,index_name,shard,took,took_millis,types,stats,search_type,total_shards,source_body,extra_source;

	public void parse(String logline) throws Exception {
		Pattern logparse = Pattern.compile(logregex);
		Matcher logmatch = logparse.matcher(logline);
		if (logmatch.matches()){
			time=logmatch.group(time);
			//	time is in ISO 8601 format
			severity=logmatch.group(severity);
			source=logmatch.group(source);
			node_name=logmatch.group(node_name);
			index_name=logmatch.group(index_name);
			shard=logmatch.group(shard);
			took=logmatch.group(took);
			types=logmatch.group(types);
			stats=logmatch.group(stats);
			search_type=logmatch.group(search_type);
			total_shards=logmatch.group(total_shards);
			source_body=logmatch.group(source_body);
			extra_source=logmatch.group(extra_source);
		} else throw new Exception (parse_exception);

	}

	public String getTime() {
		return time;
	}


	public String getSeverity() {
		return severity;
	}


	public String getSource() {
		return source;
	}


	public String getNode_name() {
		return node_name;
	}


	public String getIndex_name() {
		return index_name;
	}


	public String getShard() {
		return shard;
	}


	public String getTook() {
		return took;
	}


	public String getTook_millis() {
		return took_millis;
	}


	public String getTypes() {
		return types;
	}


	public String getStats() {
		return stats;
	}



	public String getSearch_type() {
		return search_type;
	}



	public String getTotal_shards() {
		return total_shards;
	}



	public String getSource_body() {
		return source_body;
	}



	public String getExtra_source() {
		return extra_source;
	}

	//	empty all the non final string fields
	public static void setEmpty(Object object) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if ((String.class.equals(field.getType())) && !Modifier.isFinal(field.getModifiers())) {
				field.setAccessible(true);
				if (field.get(object) != null) {
					field.set(object, "");
				}
			}
		}
	}
}
