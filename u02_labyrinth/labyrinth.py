from pathlib import Path
from typing import List

def do_labyrinthy_code_on_file(filepath:str):
    with open(Path(filepath), 'r') as f:
        strings = f.readlines()
    labyrinth = convert_strings_to_chars([string.strip() for string in strings])
    print_labyrinth(labyrinth)
    print("Ausgang gefunden: " + ("ja" if (suchen(1, 1, labyrinth)) else "nein"))



def suchen(zeile:int, spalte:int,lab:List[List]) -> bool:
    path = [[]]
    for index, line in enumerate(lab):
        path.append([])
        for char in line:
            path[-1].append(False)
    return is_path_possible()


def is_path_possible(zeile:int, spalte:int,lab:List[List], path:List[List]) -> bool:
    pass

def print_labyrinth(labyrinth:List[List]):
    for line in labyrinth:
        for char in line:
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
    do_labyrinthy_code_on_file("l1.txt")
    do_labyrinthy_code_on_file("l2.txt")
    #do_labyrinthy_code_on_file("l3.txt")