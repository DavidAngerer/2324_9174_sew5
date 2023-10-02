import random
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


def create_class_user(path:str):
    with open("create_users.sh", 'w') as file, open("delete_users.sh") as file_delete, open("userlist"):
        file.write("#! /bin/sh\n")
        file_delete.write("#! /bin/sh\n")
        file.write("groupadd klasse\n")
        user_dict = dict()
        for class_name, room, kv in get_classes_from_class_file(path):
            if username in user_dict:
                print("Error: Duplicate User")#TODO
                break
            class_name = str(class_name)
            room = str(room)
            kv = str(kv)
            username = f'k{str(class_name).lower()}'
            command = f"useradd -d /home/klassen/{username} -g klasse -s /bin/sh -G cdrom,plugdev,sambashare {username}\n"
            password = f'{class_name}{get_random_pw_char()}{room[0:2]}{get_random_pw_char()}{kv}{get_random_pw_char()}'
            passwd_command = f'echo {username}:{password} | chpasswd\n'
            file.write(command)
            file.write(passwd_command)
            file_delete.write(f'userdel -r {username}')

def get_random_pw_char():
    return "!%&(),._-=^#"[random.randint(0, 11)]


def get_classes_from_class_file(path:str):
    wb = load_workbook(Path(path), read_only=True)
    ws = wb[wb.sheetnames[0]]
    for row in ws.iter_rows(min_row=2):
        class_name = row[0].value
        room = row[1].value
        kv = row[2].value
        if class_name is None:
            break
        yield class_name, room, kv


# create_user_from_name_file("../../res/Namen.xlsx")
create_class_user("../../res/Klassenraeume_2023.xlsx")

