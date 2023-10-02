from pathlib import Path
from openpyxl import load_workbook

def create_user_from_name_file(path:str, script_file_name:str):
    if not script_file_name.endswith(".sh"): script_file_name = script_file_name + ".sh"
    with open(script_file_name, 'w') as file:
        file.write("#! /bin/sh\n")
        user_dict = dict()
        for first_name, last_name, group, school_class in get_people_from_name_file(path):
            username = last_name.replace(r"\s*", "_")
            if username in user_dict:
                username = username+ f"_{user_dict[username]}"
                user_dict[username] = user_dict[username] + 1
            else:
                user_dict[username] = 1
            command = f"useradd {username} -d "

def get_people_from_name_file(path):
    wb = load_workbook(Path(path), read_only=True)
    ws = wb[wb.sheetnames[0]]
    for row in ws.iter_rows(min_row=2):
        first_name = row[0].value
        last_name = row[1].value
        group = row[2].value
        school_class = row[3].value
        yield first_name, last_name, group, school_class


def create_class_user(path:str,script_file_name:str):
    if not script_file_name.endswith(".sh"): script_file_name = script_file_name + ".sh"
    with open(script_file_name, 'w') as file:
        file.write("#! /bin/sh\n")
        for class_name, room, kv in get_classes_from_class_file(path):
            command = f"useradd -d /home/klassen/k{class_name} -G cdrom,plugdev,sambashare k{class_name}"

def get_classes_from_class_file(path:Path):
    wb = load_workbook(Path(path), read_only=True)
    ws = wb[wb.sheetnames[0]]
    for row in ws.iter_rows(min_row=2):
        class_name = row[0].value
        room = row[1].value
        kv = row[2].value
        yield class_name, room, kv

#create_user_from_name_file("../../res/Namen.xlsx")
