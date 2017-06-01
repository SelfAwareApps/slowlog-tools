package elastictools;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class teststub {

	public static void main(String[] args) {
		SlowLogParser slowlog = new SlowLogParser();
		File file = new File("E:/slowlog.txt");

		try {
			List<String> contents = FileUtils.readLines(file);

			// Iterate the result to print each line of the file.
			for (String line : contents) {
				System.out.println("read " + line);
				slowlog.parse(line);
				System.out.println(slowlog.getall());
				try {
					SlowLogParser.setEmpty(slowlog);
				} catch (IllegalArgumentException | IllegalAccessException e1) {
					e1.printStackTrace();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
