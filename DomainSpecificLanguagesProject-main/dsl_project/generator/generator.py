import jinja2
import os
import sys

from pathlib import Path
from shutil import copy
from textx import metamodel_for_language, model as md
import shutil

def generate(output_path):#todo
    metamodel = metamodel_for_language('model')
    model = metamodel.model_from_file(sys.argv[2])

    # a hack we used because of the virtual environment
    current_directory_path = os.getcwd()
    current_directory_path = current_directory_path.replace('test', '')
    for field_type in model.classes:
       if hasattr(field_type, 'file') and field_type.file is not None: pass
       #     field_template_path = current_directory_path / field_type.file
       #     if not field_template_path.exists():
       #         print(f'Error: Template path {field_template_path} does not exist.')
       #         return
       #     else:
       #         # self-defined template
       #         copy(field_template_path, current_directory_path / 'generator\\templates' / ('proba.j2'))
       else:
           current_directory_path = Path(
               "D:\\fakultet\\master\\rad\\rad2\\DomainSpecificLanguagesProject-main\\dsl_project")

           # Concatenate paths using / operator with Path objects
           source_path = current_directory_path / 'proba.j2'
           destination_path = current_directory_path / 'generator' / 'templates' / 'proba.j2'

           # Copy the file
           shutil.copy(source_path, destination_path)
           # copy(current_directory_path / 'proba.j2', current_directory_path / 'generator\\templates' / ('proba.j2'))

    #create output folders
    output_folder = Path(output_path) / 'output'
    txt_output_folder = output_folder / 'txt'
    output_folder.mkdir(parents=True, exist_ok=True)
    txt_output_folder.mkdir(exist_ok=True)

    template_path = os.path.join(current_directory_path, 'generator\\templates')

    #jinja template loader
    file_system_loader = jinja2.FileSystemLoader(template_path)
    jinja_env = jinja2.Environment(loader=file_system_loader, trim_blocks=True, lstrip_blocks=True)

    #render HTML template
    template = jinja_env.get_template('proba.j2')
    output_html_path = output_folder / 'output.txt'

    with output_html_path.open('w') as f:
       f.write(template.render(nelly=model))

