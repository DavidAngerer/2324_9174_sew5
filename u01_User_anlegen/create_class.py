import argparse
import random
from pathlib import Path
from openpyxl import load_workbook
import logging
from logging.handlers import RotatingFileHandler
from logging import StreamHandler

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
    try:
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
                    logger.error(f"User duplicate: {username}. Programm is terminating!")
                    break
                command = f"useradd -d /home/klassen/{username} -g klasse -c \"{class_name} - {kv}\" -s /bin/bash -G cdrom,plugdev,sambashare {username}\n"
                password = f'{class_name}{get_random_pw_char()}{room[0:2]}{get_random_pw_char()}{kv}{get_random_pw_char()}'
                passwd_command = f'echo {username}:{password} | chpasswd\n'
                file.write(command)
                logger.debug(f"User created: {username}")
                file.write(passwd_command)
                logger.debug(f"User password created for {username}")
                file_delete.write(f'userdel -r {username}\n')
                logger.debug(f"User deletion written for {username}")
                user_list_file.write(f'username: {username}, password: {password}\n')
                logger.debug(f"User \"{username}\" added to list")
    except Exception:
        logger.critical("Couldn't write in one of the Files")

def get_random_pw_char():
    return "!%&(),._-=^#"[random.randint(0, 11)]


def get_classes_from_class_file(path:str):
    try:
        wb = load_workbook(Path(path), read_only=True)
        ws = wb[wb.sheetnames[0]]
        logger.debug("Reading class excel file")
        for row in ws.iter_rows(min_row=2):
            class_name = row[0].value
            room = row[1].value
            kv = row[2].value
            if class_name is None:
                break
            yield class_name, room, kv
    except FileNotFoundError:
        logger.critical(f"Couldn't find file {path}")
    except Exception:
        logger.critical(f"Couldn't access file {path}")

if __name__ == '__main__':
    parser = argparse.ArgumentParser()

    parser.add_argument("-q", "--quiet", help='Only Log Errors', required=False, action='store_true')
    parser.add_argument("-v", "--verbose", help='Log Verbose Debug', required=False, action='store_true')


    args = parser.parse_args()
    global logger
    logger = logging.getLogger('my_logger')
    if args.verbose:
        logger.setLevel(logging.DEBUG)
    elif args.quiet:
        logger.setLevel(logging.ERROR)
    else:
        logger.setLevel(logging.INFO)

    file_handler = RotatingFileHandler('user_logfile.log', maxBytes=10000, backupCount=5)
    file_handler.setFormatter(logging.Formatter('[%(asctime)s] %(levelname)s %(message)s'))
    stream_handler = StreamHandler()
    stream_formatter = logging.Formatter('%(levelname)s - %(message)s')
    stream_handler.setFormatter(stream_formatter)
    logger.addHandler(file_handler)
    logger.addHandler(stream_handler)
    logger.info("Logging turned on " + str(logger.level))
    # create_user_from_name_file("../../res/Namen.xlsx")
    create_class_user_files("../../res/Klajssenraeume_2023.xlsx")

