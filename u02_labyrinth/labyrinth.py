import time
from pathlib import Path
from typing import List

def do_labyrinthy_code_on_file(filepath:str, x_pos:int, y_pos:int):
    with open(Path(filepath), 'r') as f:
        strings = f.readlines()
    labyrinth = convert_strings_to_chars([string.strip() for string in strings])
    if labyrinth[-1]==[]:
        labyrinth.pop()
    #print_labyrinth(labyrinth)
    start_time = time.perf_counter_ns()
    doable = (suchen(x_pos, y_pos, labyrinth))
    print("Ausgang gefunden: " + ("ja" if doable else "nein"))
    if doable:
        print(f"Anzahl Wege: {anzahl_wege(x_pos, y_pos, labyrinth)}")
    end_time = time.perf_counter_ns()
    elapsed_time = (end_time - start_time) / 1000
    print(f"Elapsed time: {int(elapsed_time)} microseconds")


def anzahl_wege(zeile:int, spalte:int,lab:List[List]) -> int:
    path = get_path(lab)
    return getNumberPaths(zeile,spalte,lab,path)


def getNumberPaths(zeile:int, spalte:int,lab:List[List], path:List[List],counter=0) -> int:
    path[zeile][spalte] = True
    neighbors = get_Neighbors(zeile, spalte, lab, path)
    if isNeighborGoal(neighbors, lab):
        # print_labyrinth(lab, path)
        path[zeile][spalte] = False
        return counter+1
    for neighbor in neighbors:
        counter = getNumberPaths(neighbor[0], neighbor[1], lab, path,counter)
    path[zeile][spalte] = False
    return counter



def suchen(zeile:int, spalte:int,lab:List[List]) -> bool:
    path = get_path(lab)
    return is_path_possible(zeile, spalte, lab, path)


def get_path(lab) -> List[List]:
    path = []
    for index, line in enumerate(lab):
        path.append([])
        for char in line:
            path[-1].append(False)
    return path


def is_path_possible(zeile:int, spalte:int,lab:List[List], path:List[List]) -> bool:
    path[zeile][spalte] = True
    neighbors = get_Neighbors(zeile, spalte, lab, path)
    if isNeighborGoal(neighbors,lab):
        #print_labyrinth(lab, path)
        return True
    for neighbor in neighbors:
        if is_path_possible(neighbor[0], neighbor[1],lab,path):
            return True
    return False



def isNeighborGoal(neighbors: List[tuple[int,int]], lab:List[List]) -> bool:
    for neighbor in neighbors:
        if lab[neighbor[0]][neighbor[1]] == 'A':
            return True
    return False

def get_Neighbors(zeile:int, spalte:int,lab:List[List], path:List[List]) -> List[tuple[int,int]]:
    neighbors = []
    if (zeile > 1) and ((lab[zeile-1][spalte] == ' ') or (lab[zeile-1][spalte] == 'A')) and not(path[zeile-1][spalte]):
        neighbors.append((zeile-1,spalte))
    if (spalte > 1) and ((lab[zeile][spalte-1] == ' ') or (lab[zeile][spalte-1] == 'A')) and not(path[zeile][spalte-1]):
        neighbors.append((zeile,spalte-1))
    if (zeile + 1 < len(lab)) and ((lab[zeile + 1][spalte] == ' ') or (lab[zeile + 1][spalte] == 'A')) and not(path[zeile + 1][spalte]):
        neighbors.append((zeile+1,spalte))
    if (spalte +1 < len(lab[0])) and ((lab[zeile][spalte + 1] == ' ') or (lab[zeile][spalte + 1] == 'A')) and not(path[zeile][spalte + 1]):
        neighbors.append((zeile,spalte+1))
    return neighbors


def print_labyrinth(labyrinth:List[List], path=[]):
    for index, line in enumerate(labyrinth):
        for index_col,char in enumerate(line):
            if path and path[index][index_col]:
                print("P", end="")
            else:
                print(char, end="")
        print()


def convert_strings_to_chars(strings:List) -> List[List]:
    erg = [[]]
    for index_string, string in enumerate(strings):
        erg.append([])
        for index_char, char in enumerate(string):
            erg[index_string].append(char)
    return erg


if __name__ == '__main__':
    do_labyrinthy_code_on_file("l1.txt", 5, 5)
    do_labyrinthy_code_on_file("l2.txt", 5, 5)
    do_labyrinthy_code_on_file("l3.txt", 5, 5)