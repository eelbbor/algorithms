import com.pexercise.service.SearchService;

import java.util.Scanner;

public class Searcher {

	private String filePath;
	private SearchService searchService;

	private void prompt() {
		System.out.print("search> ");
	}


	public Searcher(String filePath) {
		this.filePath = filePath;
		int maxSearchStringsToBeCached = 1000;
		int maxShardCount = 100;
		this.searchService = new SearchService(this.filePath, maxSearchStringsToBeCached, maxShardCount);
	}

	public String search(String query) {
		return searchService.query(query);
	}

	public static void main(String[] args) {
		Searcher searcher = new Searcher(args[0]);

		searcher.prompt();
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			String results = searcher.search(scanner.nextLine());
			System.out.println(results);
			searcher.prompt();
		}
	}

}
