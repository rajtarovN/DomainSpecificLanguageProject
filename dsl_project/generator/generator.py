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
       else:
           current_directory_path = Path(
               "D:\\fakultet\\master\\rad\\rad2\\DomainSpecificLanguageProject\\dsl_project")

           source_path = current_directory_path / 'model.j2'
           # destination_path = current_directory_path / 'generator' / 'templates' / 'model.j2'

           # shutil.copy(source_path, destination_path)

    #create output folders
    output_folder = Path(output_path) / 'output'
    output_folder.mkdir(parents=True, exist_ok=True)

    template_path = os.path.join(current_directory_path, 'generator\\templates')

    #jinja template loader
    file_system_loader = jinja2.FileSystemLoader(template_path)
    jinja_env = jinja2.Environment(loader=file_system_loader, trim_blocks=True, lstrip_blocks=True)

    template = jinja_env.get_template('application.properties.j2')
    file_name = 'application.properties'
    folder = output_folder /'src'/'main'/'resources'
    folder.mkdir(parents=True, exist_ok=True)
    output_html_path = output_folder /'src'/'main'/'resources'/ file_name

    with output_html_path.open('w', encoding="utf-8") as f:
        f.write(template.render())

    template = jinja_env.get_template('pomxml.j2')
    file_name = 'pom.xml'
    output_html_path = output_folder / file_name

    with output_html_path.open('w') as f:
        f.write(template.render(projectName="output", groupId="uns.ac.rs.mbrs" )) # todo

    template = jinja_env.get_template('springapplication.j2')
    folder = output_folder / 'src' / 'main' / 'java'/'uns'/'ac'/'rs'/'mbrs'
    folder.mkdir(parents=True, exist_ok=True)
    file_name = 'OutputApplication.java' # todo naziv applikacije i u pomui u ovde, package u svim fajlovima
    output_html_path = output_folder / 'src' / 'main' / 'java'/'uns'/'ac'/'rs'/'mbrs'/ file_name

    with output_html_path.open('w') as f:
        f.write(template.render())


    #  todo ovde petlja
    for cl in model.classes:
    #render HTML template
        template = jinja_env.get_template('model.j2')
        file_name = cl.name+'.java'
        folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' /'model'
        folder.mkdir(parents=True, exist_ok=True)
        output_html_path = output_folder / 'src' / 'main' / 'java'/'uns'/'ac'/'rs'/'mbrs'/ 'model'/ file_name

        with output_html_path.open('w') as f:
            # print(model)
            f.write(template.render(model=model, current_class=cl))

        template = jinja_env.get_template('dto.j2')
        file_name = cl.name + 'DTO.java'
        folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'dtos'
        folder.mkdir(parents=True, exist_ok=True)
        output_html_path = output_folder / 'src' / 'main' / 'java'/'uns'/'ac'/'rs'/'mbrs'/'dtos'/ file_name

        with output_html_path.open('w') as f:
            # print(model)
            f.write(template.render(model=model, current_class=cl))



        template = jinja_env.get_template('mapper.j2')
        file_name = cl.name + 'Mapper.java'
        folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs'/ 'mapper'
        folder.mkdir(parents=True, exist_ok=True)
        output_html_path = output_folder / 'src' / 'main' / 'java'/'uns'/'ac'/'rs'/'mbrs'/'mapper'/ file_name

        with output_html_path.open('w') as f:
            # print(model)
            f.write(template.render(model=model, current_class=cl))

        template = jinja_env.get_template('repository.j2')
        file_name = cl.name + 'Repository.java'
        folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs'/ 'repository'
        folder.mkdir(parents=True, exist_ok=True)
        output_html_path = output_folder / 'src' / 'main' / 'java'/'uns'/'ac'/'rs'/'mbrs'/'repository'/ file_name

        with output_html_path.open('w') as f:
            # print(model)
            f.write(template.render(model=model, current_class=cl))

        template = jinja_env.get_template('controller.j2')
        file_name = cl.name + 'Controller.java'
        folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'controller'
        folder.mkdir(parents=True, exist_ok=True)
        output_html_path = output_folder / 'src' / 'main' / 'java'/'uns'/'ac'/'rs'/'mbrs'/'controller'/ file_name

        with output_html_path.open('w') as f:
            # print(model)
            f.write(template.render(model=model, current_class=cl))

        template = jinja_env.get_template('service.j2')
        file_name = cl.name + 'Service.java'
        folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs'/ 'service'
        folder.mkdir(parents=True, exist_ok=True)
        output_html_path = output_folder / 'src' / 'main' / 'java'/'uns'/'ac'/'rs'/'mbrs'/ 'service' / file_name

        with output_html_path.open('w') as f:
            # print(model)
            f.write(template.render(model=model, current_class=cl))

    # print(len(model.enums))
    for enum in model.enums:
        # print(enum.values)
        template = jinja_env.get_template('enum.j2')
        file_name = enum.name + '.java'
        folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs'/ 'enums'
        folder.mkdir(parents=True, exist_ok=True)
        output_html_path = output_folder / 'src' / 'main' / 'java'/'uns'/'ac'/'rs'/'mbrs'/ 'enums' / file_name

        with output_html_path.open('w') as f:
            # print(model)
            f.write(template.render(model=model, enum=enum))