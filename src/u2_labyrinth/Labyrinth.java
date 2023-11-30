package u2_labyrinth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * The Labyrinth class represents a labyrinth puzzle solver.
 * It includes methods to find a path through a labyrinth,
 * count the number of possible paths, and print the labyrinth.
 * @author David Angelo 5CN
 */
public class Labyrinth {
	public static String[][] maps = {{
		"############",
		"#  #     # #",
		"## # ### # #",
		"#  # # # # #",
		"## ### # # #",
		"#        # #",
		"## ####### #",
		"#          #",
		"# ######## #",
		"# #   #    #",
		"#   #   # ##",
		"######A#####"
	}, {
		"################################",
		"#                              #",
		"# ############################ #",
		"# # ###       ##  #          # #",
		"# #     ##### ### # ########## #",
		"# #   ##### #     # #      ### #",
		"# # ##### #   ###   # # ## # # #",
		"# # ### # ## ######## # ##   # #",
		"# ##### #  # #   #    #    ### #",
		"# # ### ## # # # # ####### # # #",
		"# #        # #   #     #     # #",
		"# ######## # ######### # ### # #",
		"# ####     #  # #   #  # ##### #",
		"# # #### #### # # # # ## # ### #",
		"#                      # #     #",
		"###########################A####"
	}, {
		"###########################A####",
		"#   #      ## # # ###  #     # #",
		"# ###### #### # # #### ##### # #",
		"# # ###  ## # # # #          # #",
		"# # ### ### # # # # # #### # # #",
		"# #     ### # # # # # ## # # # #",
		"# # # # ### # # # # ######## # #",
		"# # # #     #          #     # #",
		"# ### ################ # # # # #",
		"# #   #             ## # #   # #",
		"# # #### ############# # #   # #",
		"# #                    #     # #",
		"# # #################### # # # #",
		"# # #### #           ###     # #",
		"# # ## # ### ### ### ### # ### #",
		"# #    #     ##  ##  # ###   # #",
		"# ####   ###### #### # ###  ## #",
		"###########################A####"
	}, {
		"#############",
		"#           #",
		"#           #",
		"#           #",
		"###########A#"
	}};

	/**
	 * Wandelt (unveränderliche) Strings in Char-Arrays
	 * @param map  der Plan, ein String je Zeile
	 * @return char[][] des Plans
	 */
	public static char[][] fromStrings(String[] map) {
		int maxLength = 0;
		for (String str : map) {
			if (str.length() > maxLength) {
				maxLength = str.length();
			}
		}
		char [][] erg = new char[map.length][maxLength];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length(); j++) {
				erg[i][j] = map[i].charAt(j);
			}
		}
		return erg;
	}


	/**
	 * Ausgabe des Layrinths
	 * @param lab
	 */
	public static void printLabyrinth(char[][] lab) {
		for (char[] chars : lab) {
			for (char aChar : chars) {
				System.out.print(aChar);
			}
			System.out.println();
		}
	}

	/**
	 * Suche den Weg
	 * @param zeile     aktuelle Position
	 * @param spalte     aktuelle Position
	 * @param lab
	 * @throws InterruptedException    für die verlangsamte Ausgabe mit sleep()
	 */
	public static boolean suchen(int zeile, int spalte, char[][] lab) throws InterruptedException {
		boolean[][] path = new boolean[lab.length][lab[0].length];
		for (int i = 0; i < lab.length; i++) {
			for (int j = 0; j < lab[i].length; j++) {
				path[i][j] = false;
			}
		}
		return check_out_neighbor(zeile,spalte,lab,path);
	}


	/**
	 * Counts all possible paths from a starting position to the goal in the labyrinth.
	 *
	 * @param zeile   The starting row.
	 * @param spalte  The starting column.
	 * @param lab     The labyrinth represented as a 2D char array.
	 * @return The number of possible paths to the goal.
	 * @throws InterruptedException If the thread is interrupted during sleep.
	 */
	public static int suchenAlle(int zeile, int spalte, char[][] lab) throws InterruptedException {
		boolean[][] path = new boolean[lab.length][lab[0].length];
		for (int i = 0; i < lab.length; i++) {
			for (int j = 0; j < lab[i].length; j++) {
				path[i][j] = false;
			}
		}
		return get_number_paths(zeile,spalte,lab,path,0);
	}


	/**
	 * Recursively counts all paths from a starting position to the goal in the labyrinth.
	 *
	 * @param zeile The current row in the labyrinth.
	 * @param spalte The current column in the labyrinth.
	 * @param lab The labyrinth represented as a 2D char array.
	 * @param path A 2D boolean array representing visited positions.
	 * @param counter The count of paths found so far.
	 * @return The updated number of paths to the goal.
	 */
	public static int get_number_paths(int zeile, int spalte, char[][] lab, boolean[][] path, int counter) {
		path[zeile][spalte] = true;
		ArrayList<int[]> neighborList = getNeighbors(zeile, spalte, lab, path);
		//printLabyrinthpath(lab,path);
		//System.out.println("---------------------------------------------------");
		if (neighborIsGoal(neighborList,lab)) {
			//printLabyrinthpath(lab,path);
			path[zeile][spalte] = false;
			return counter+1;
		}
		for (int[] neighbor: neighborList) {
			counter = get_number_paths(neighbor[0], neighbor[1], lab, path, counter);
		}
		path[zeile][spalte] = false;
		return counter;
	}


	/**
	 * Explores neighboring cells to find a path to the goal in the labyrinth.
	 *
	 * @param zeile The current row in the labyrinth.
	 * @param spalte The current column in the labyrinth.
	 * @param lab The labyrinth represented as a 2D char array.
	 * @param path A 2D boolean array representing visited positions.
	 * @return True if a path to the goal is found, false otherwise.
	 */
	public static boolean check_out_neighbor(int zeile, int spalte, char[][] lab, boolean[][] path) {
		path[zeile][spalte] = true;
		ArrayList<int[]> neighborList = getNeighbors(zeile, spalte, lab, path);
		if (neighborIsGoal(neighborList,lab)) {
			//printLabyrinthpath(lab,path);
			return true;
		}
		for (int[] neighbor: neighborList) {
			if (check_out_neighbor(neighbor[0], neighbor[1], lab, path)) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Prints the current path through the labyrinth to the console.
	 * Marks the path with 'P' and unvisited parts with their original characters.
	 *
	 * @param lab The labyrinth represented as a 2D char array.
	 * @param path A 2D boolean array representing the path taken.
	 */
	public static void printLabyrinthpath(char[][] lab, boolean[][] path) {
		for (int i = 0; i < lab.length; i++) {
			for (int j = 0; j < lab[i].length; j++) {
				if (path[i][j]) {
					System.out.print("P");
				}else{
					System.out.print(lab[i][j]);
				}
			}
			System.out.println();
		}
	}


	/**
	 * Finds all possible neighboring cells that can be moved to from the current position.
	 *
	 * @param zeile The current row in the labyrinth.
	 * @param spalte The current column in the labyrinth.
	 * @param lab The labyrinth represented as a 2D char array.
	 * @param path A 2D boolean array representing visited positions.
	 * @return A list of int arrays, each representing the row and column of a neighbor.
	 */
	public static ArrayList<int[]> getNeighbors(int zeile, int spalte, char[][] lab, boolean[][] path) {
		ArrayList<int[]> neighborList = new ArrayList<>();
		if (zeile > 1 && (lab[zeile-1][spalte] == ' ' || lab[zeile-1][spalte] == 'A') && !path[zeile-1][spalte]) {
			neighborList.add(new int[]{zeile-1,spalte});
		}
		if (spalte > 1 && (lab[zeile][spalte-1] == ' ' || lab[zeile][spalte-1] == 'A') && !path[zeile][spalte-1]) {
			neighborList.add(new int[]{zeile,spalte-1});
		}
		if (zeile+1 < lab.length && (lab[zeile+1][spalte] == ' ' || lab[zeile+1][spalte] == 'A') && !path[zeile+1][spalte]) {
			neighborList.add(new int[]{zeile+1,spalte});
		}
		if (spalte +1 < lab[zeile].length && (lab[zeile][spalte+1] == ' ' || lab[zeile][spalte+1] == 'A') && !path[zeile][spalte+1]) {
			neighborList.add(new int[]{zeile,spalte+1});
		}
		return neighborList;
	}


	/**
	 * Checks if any of the neighboring cells is the goal ('A').
	 *
	 * @param neighborList A list of neighboring cells to check.
	 * @param lab The labyrinth represented as a 2D char array.
	 * @return True if the goal is a neighbor, false otherwise.
	 */
	public static boolean neighborIsGoal(ArrayList<int[]> neighborList, char[][] lab) {
		for (int[] neighbor: neighborList) {
			if (lab[neighbor[0]][neighbor[1]] == 'A') {
				return true;
			}
		}
		return false;
	}

	/**
	 * The main method to run the labyrinth solver.
	 *
	 * @param args Command line arguments.
	 * @throws InterruptedException If the thread is interrupted during sleep.
	 */
	public static void main(String[] args) throws InterruptedException {
		//System.out.println(Arrays.toString(maps[2]));
		for (int i = 0; i < maps.length-1; i++) {
			char[][] labyrinth = fromStrings(maps[i]);
			//printLabyrinth(labyrinth);
			System.out.println("Ausgang gefunden: " + (suchen(5, 5, labyrinth) ? "ja" : "nein"));
			System.out.println("Anzahl Wege: " + suchenAlle(5, 5, labyrinth));
		}
		char[][] labyrinth = fromStrings(maps[3]);
		//printLabyrinth(labyrinth);
		System.out.println("Ausgang gefunden: " + (suchen(1, 1, labyrinth) ? "ja" : "nein"));
		System.out.println("Anzahl Wege: " + suchenAlle(1, 1, labyrinth));

		doLabyrinthCodeOnFile("src/u2_labyrinth/l1.txt");
		doLabyrinthCodeOnFile("src/u2_labyrinth/l2.txt");
		doLabyrinthCodeOnFile("src/u2_labyrinth/l3.txt");
	}

	/**
	 * Processes a labyrinth defined in a file and prints the results.
	 *
	 * @param filePath The path to the file containing the labyrinth.
	 * @throws InterruptedException If the thread is interrupted during sleep.
	 */
	private static void doLabyrinthCodeOnFile(String filePath) throws InterruptedException {
		 // Replace with your file path
		ArrayList<String> lines = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Convert the list to an array
		String[] linesArray = lines.toArray(new String[0]);

		char[][] labyrinth = fromStrings(linesArray);
		//printLabyrinth(labyrinth);
		long startTime = System.nanoTime();
		System.out.println("Ausgang gefunden: " + (suchen(1, 1, labyrinth) ? "ja" : "nein"));
		System.out.println("Anzahl Wege: " + suchenAlle(1, 1, labyrinth));
		// Get the end time
		long endTime = System.nanoTime();

		// Calculate the elapsed time in milliseconds
		long elapsedTimeMillis = (endTime - startTime) / 1000;

		System.out.println("Elapsed Time: " + elapsedTimeMillis + " microseconds");
	}
}
