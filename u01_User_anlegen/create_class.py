import argparse
import logging
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


def create_class_user_files(path:str):
    with open("create_users.sh", 'w') as file, open("delete_users.sh", 'w') as file_delete, \
            open("userlist.txt", 'w') as user_list_file:
        file.write("#! /bin/sh\n")
        file_delete.write("#! /bin/sh\n")
        file.write("groupadd klasse\n")
        user_dict = dict()
        for class_name, room, kv in get_classes_from_class_file(path):
            class_name = str(class_name)
            room = str(room)
            kv = str(kv)
            username = f'k{str(class_name).lower()}'
            if username in user_dict:
                logging.log(logging.ERROR, f"User duplicate: {username}. Programm is terminating!")
                break
            command = f"useradd -d /home/klassen/{username} -g klasse -c \"{class_name} - {kv}\" -s /bin/bash -G cdrom,plugdev,sambashare {username}\n"
            password = f'{class_name}{get_random_pw_char()}{room[0:2]}{get_random_pw_char()}{kv}{get_random_pw_char()}'
            passwd_command = f'echo {username}:{password} | chpasswd\n'
            file.write(command)
            logging.log(logging.DEBUG, f"User created: {username}")
            file.write(passwd_command)
            logging.log(logging.DEBUG, f"User password created for {username}")
            file_delete.write(f'userdel -r {username}\n')
            logging.log(logging.DEBUG, f"User deletion written for {username}")
            user_list_file.write(f'username: {username}, password: {password}\n')
            logging.log(logging.DEBUG, f"User \"{username}\" added to list")

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

if __name__ == '__main__':
    parser = argparse.ArgumentParser()

    parser.add_argument("-q", "--quiet", type=str, help='Only Log Errors', required=False)
    parser.add_argument("-v", "--verbose", type=str, help='Log Verbose Debug', required=False)


    args = parser.parse_args()
    if args.verbose:
        loglevel = "DEBUG"
    elif args.quiet:
        loglevel = "ERROR"
    else:
        loglevel = "INFO"
    logging.basicConfig(level=getattr(logging, loglevel), format='[%(asctime)s] %(levelname)s %(message)s',
                        datefmt='%Y-%m-%d %H:%M:%S')
    logging.log(logging.INFO, "Logging turned on " + args.loglevel)
    # create_user_from_name_file("../../res/Namen.xlsx")
    create_class_user_files("../../res/Klassenraeume_2023.xlsx")

