import argparse
import random
from pathlib import Path

import unicodedata
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
            command = f"useradd {replace_umlaute_and_remove_accents(username)} -d "

def get_people_from_name_file(path):
    wb = load_workbook(Path(path), read_only=True)
    ws = wb[wb.sheetnames[0]]
    for row in ws.iter_rows(min_row=2):
        first_name = row[0].value
        last_name = row[1].value
        group = row[2].value
        school_class = row[3].value
        yield first_name, last_name, group, school_class