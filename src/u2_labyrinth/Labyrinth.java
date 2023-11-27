package u2_labyrinth;
//TODO: Mein Name in der Javadoc

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
		char [][] erg = new char[map.length][map[0].length()];
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
		// TODO Code fehlt noch
		// nur lab[zeile][spalte] betrachten

		return false;
	}

	public static boolean check_out_neighbor(int zeile, int spalte, char[][] lab, boolean[][] path) {
		path[zeile][spalte] = true;
		ArrayList<int[]> neighborList = getNeighbors(zeile, spalte, lab, path);
		if (neighborIsGoal(neighborList,lab)) {
			return true;
		}
		for (int[] neighbor: neighborList) {
			if (check_out_neighbor(zeile, spalte, lab, path)) {
				return true;
			}
		}
		return false;
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
		char[][] labyrinth = fromStrings(maps[0]);
		printLabyrinth(labyrinth);
		System.out.println("Ausgang gefunden: " + (suchen(5, 5, labyrinth) ? "ja" : "nein"));
		// TODO: System.out.println("Anzahl Wege: " + suchenAlle(5, 5, labyrinth));
	}
}
