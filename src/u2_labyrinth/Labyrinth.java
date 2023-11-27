package u2_labyrinth;
//TODO: Mein Name in der Javadoc

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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


	public static int suchenAlle(int zeile, int spalte, char[][] lab) throws InterruptedException {
		boolean[][] path = new boolean[lab.length][lab[0].length];
		for (int i = 0; i < lab.length; i++) {
			for (int j = 0; j < lab[i].length; j++) {
				path[i][j] = false;
			}
		}
		return get_number_paths(zeile,spalte,lab,path,0);
	}


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

	public static boolean neighborIsGoal(ArrayList<int[]> neighborList, char[][] lab) {
		for (int[] neighbor: neighborList) {
			if (lab[neighbor[0]][neighbor[1]] == 'A') {
				return true;
			}
		}
		return false;
	}

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
		System.out.println("Ausgang gefunden: " + (suchen(1, 1, labyrinth) ? "ja" : "nein"));
		System.out.println("Anzahl Wege: " + suchenAlle(1, 1, labyrinth));
	}
}
