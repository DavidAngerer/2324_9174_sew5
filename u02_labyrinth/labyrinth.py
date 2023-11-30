import argparse
import time
from pathlib import Path
from typing import List

def do_labyrinthy_code_on_file(filepath:str, x_pos:int, y_pos:int):
    labyrinth = get_labyrinth_from_filepath(filepath)
    #print_labyrinth(labyrinth)
    start_time = time.perf_counter_ns()
    doable = (suchen(x_pos, y_pos, labyrinth))
    print("Ausgang gefunden: " + ("ja" if doable else "nein"))
    if doable:
        print(f"Anzahl Wege: {anzahl_wege(x_pos, y_pos, labyrinth)}")
    end_time = time.perf_counter_ns()
    elapsed_time = (end_time - start_time) / 1000
    print(f"Elapsed time: {int(elapsed_time)} microseconds")


def get_labyrinth_from_filepath(filepath):
    with open(Path(filepath), 'r') as f:
        strings = f.readlines()
    labyrinth = convert_strings_to_chars([string.strip() for string in strings])
    if labyrinth[-1] == []:
        labyrinth.pop()
    return labyrinth


def anzahl_wege(zeile:int, spalte:int,lab:List[List],print_path=False, wait_time=0) -> int:
    path = get_path(lab)
    return getNumberPaths(zeile=zeile,spalte=spalte,lab=lab,path=path,print_path=print_path, wait_time=wait_time)


def getNumberPaths(zeile:int, spalte:int,lab:List[List], path:List[List],print_path=False,wait_time=0, counter=0) -> int:
    path[zeile][spalte] = True
    neighbors = get_Neighbors(zeile, spalte, lab, path)
    if isNeighborGoal(neighbors, lab):
        if print_path:
            if wait_time>0:
                time.sleep(wait_time/1000)
            print_labyrinth(lab, path)
        path[zeile][spalte] = False
        return counter+1
    for neighbor in neighbors:
        counter = getNumberPaths(zeile=neighbor[0], spalte=neighbor[1], lab=lab, path=path, print_path=print_path,wait_time=wait_time, counter=counter)
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
    print("------------------------------------")
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
    parser = argparse.ArgumentParser(description='Calculate number of ways through a labyrinth')
    parser.add_argument('filename', help='File containing the labyrinth to solve')
    parser.add_argument('-x', '--xstart', type=int, help='x-coordinate to start', default=1)
    parser.add_argument('-y', '--ystart', type=int, help='y-coordinate to start', default=1)
    parser.add_argument('-p', '--print', action='store_true', help='Print output of every solution')
    parser.add_argument('-t', '--time', action='store_true', help='Print total calculation time (in milliseconds)', default=0)
    parser.add_argument('-d', '--delay', type=int, help='Delay after printing a solution (in milliseconds)', default=0)

    args = parser.parse_args()
    if not args.filename:
        do_labyrinthy_code_on_file("l1.txt", 5, 5)
        do_labyrinthy_code_on_file("l2.txt", 5, 5)
        do_labyrinthy_code_on_file("l3.txt", 5, 5)
    else:
        lab = get_labyrinth_from_filepath(args.filename)
        if args.time:
            start_time = time.perf_counter_ns()
        number_of_paths = anzahl_wege(zeile=args.xstart, spalte=args.ystart,lab=lab,print_path=args.print, wait_time=args.delay)
        print(f"Number of Ways: {number_of_paths}")
        if args.time:
            end_time = time.perf_counter_ns()
            elapsed_time = (end_time - start_time) / 1000
            print(f"Elapsed time: {int(elapsed_time)} microseconds")