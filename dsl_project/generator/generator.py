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
    memorize_output_path = output_path
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

    template = jinja_env.get_template('tokenUtils.j2')
    file_name = 'TokenUtils.java'
    folder = output_folder / 'src' / 'main' / 'java'/'uns'/'ac'/'rs'/'mbrs'/ 'utils'
    folder.mkdir(parents=True, exist_ok=True)
    output_path = output_folder / 'src' / 'main' / 'java'/'uns'/'ac'/'rs'/'mbrs'/ 'utils' / file_name

    with output_path.open('w', encoding="utf-8") as f:
        f.write(template.render())

    template = jinja_env.get_template('notFoundExc.j2')
    file_name = 'NotFoundException.java'
    folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'exception'
    folder.mkdir(parents=True, exist_ok=True)
    output_path = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'exception' / file_name

    with output_path.open('w', encoding="utf-8") as f:
        f.write(template.render())

    template = jinja_env.get_template('userDetailsService.j2')
    file_name = 'UserDetailsService.java'
    folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'service'
    folder.mkdir(parents=True, exist_ok=True)
    output_path = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'service' / file_name

    with output_path.open('w', encoding="utf-8") as f:
        f.write(template.render())

    template = jinja_env.get_template('AuthenticationTokenFilter.j2')
    file_name = 'AuthenticationTokenFilter.java'
    folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'security'
    folder.mkdir(parents=True, exist_ok=True)
    output_path = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'security' / file_name

    with output_path.open('w', encoding="utf-8") as f:
        f.write(template.render())

    template = jinja_env.get_template('Entry.j2')
    file_name = 'EntryPointUnauthorizedHandler.java'
    folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'security'
    folder.mkdir(parents=True, exist_ok=True)
    output_path = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'security' / file_name

    with output_path.open('w', encoding="utf-8") as f:
        f.write(template.render())

    template = jinja_env.get_template('securityUser.j2')
    file_name = 'SecurityUser.java'
    folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'security'
    folder.mkdir(parents=True, exist_ok=True)
    output_path = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'security' / file_name

    with output_path.open('w', encoding="utf-8") as f:
        f.write(template.render())

    template = jinja_env.get_template('UserFactory.j2')
    file_name = 'UserFactory.java'
    folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'security'
    folder.mkdir(parents=True, exist_ok=True)
    output_path = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'security' / file_name

    with output_path.open('w', encoding="utf-8") as f:
        f.write(template.render())

    template = jinja_env.get_template('userRepository.j2')
    file_name = 'UserRepository.java'
    folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'repository'
    folder.mkdir(parents=True, exist_ok=True)
    output_path = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'repository' / file_name

    with output_path.open('w', encoding="utf-8") as f:
        f.write(template.render())

    template = jinja_env.get_template('UserRoleRepository.j2')
    file_name = 'UserRoleRepository.java'
    folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'repository'
    folder.mkdir(parents=True, exist_ok=True)
    output_path = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'repository' / file_name

    with output_path.open('w', encoding="utf-8") as f:
        f.write(template.render())

    template = jinja_env.get_template('authority.j2')
    file_name = 'Authority.java'
    folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'model'
    folder.mkdir(parents=True, exist_ok=True)
    output_path = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'model' / file_name

    with output_path.open('w', encoding="utf-8") as f:
        f.write(template.render())

    template = jinja_env.get_template('User.j2')
    file_name = 'User.java'
    folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'model'
    folder.mkdir(parents=True, exist_ok=True)
    output_path = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'model' / file_name

    with output_path.open('w', encoding="utf-8") as f:
        f.write(template.render())

    template = jinja_env.get_template('userRole.j2')
    file_name = 'UserRole.java'
    folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'model'
    folder.mkdir(parents=True, exist_ok=True)
    output_path = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'model' / file_name

    with output_path.open('w', encoding="utf-8") as f:
        f.write(template.render())

    template = jinja_env.get_template('webConfig.j2')
    file_name = 'WebConfig.java'
    folder = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'configuration'
    folder.mkdir(parents=True, exist_ok=True)
    output_path = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'configuration' / file_name

    with output_path.open('w', encoding="utf-8") as f:
        f.write(template.render())


    #####################
    template_new = jinja_env.get_template('webSecurityConfiguration.j2')
    file_name_new = 'WebSecurityConfig.java'
    folder_new = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'configuration'
    folder_new.mkdir(parents=True, exist_ok=True)
    output_path_new = output_folder / 'src' / 'main' / 'java' / 'uns' / 'ac' / 'rs' / 'mbrs' / 'configuration' / file_name_new

    with output_path_new.open('w', encoding="utf-8") as f:
        f.write(template_new.render(model =model))

################


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


    output_path = memorize_output_path
    output_folder2 = Path(output_path) / 'output2'
    # output_folder2 = output_folder / 'output2'
    output_folder2.mkdir(parents=True, exist_ok=True)
    print("*****************")
    print(output_path)
    template = jinja_env.get_template('frontend/gitignore.j2')
    folder = output_folder2
    folder.mkdir(parents=True, exist_ok=True)
    file_name = '.gitignore'
    output_html_path = output_folder2 / file_name

    with output_html_path.open('w') as f:
        f.write(template.render(model=model))

    output_folder2 = Path(output_path) / 'output2'
    output_folder2.mkdir(parents=True, exist_ok=True)
    template = jinja_env.get_template('frontend/README.j2')
    folder = output_folder2
    folder.mkdir(parents=True, exist_ok=True)
    file_name = 'README.md'
    output_html_path = output_folder2 / file_name

    with output_html_path.open('w') as f:
        f.write(template.render(model=model))

    output_folder2 = Path(output_path) / 'output2'
    output_folder2.mkdir(parents=True, exist_ok=True)
    template = jinja_env.get_template('frontend/packageJson.j2')
    folder = output_folder2
    folder.mkdir(parents=True, exist_ok=True)
    file_name = 'package.json'
    output_html_path = output_folder2 / file_name

    with output_html_path.open('w') as f:
        f.write(template.render(model=model))

    output_folder2 = Path(output_path) / 'output2'
    output_folder2.mkdir(parents=True, exist_ok=True)
    template = jinja_env.get_template('frontend/indexJs.j2')
    folder = output_folder2
    folder.mkdir(parents=True, exist_ok=True)
    file_name = 'index.js'
    output_html_path = output_folder2 / 'src'/file_name

    with output_html_path.open('w') as f:
        f.write(template.render(model=model))

    output_folder2 = Path(output_path) / 'output2' / 'public'
    output_folder2.mkdir(parents=True, exist_ok=True)
    template = jinja_env.get_template('frontend/indexHtml.j2')
    folder = output_folder2
    folder.mkdir(parents=True, exist_ok=True)
    file_name = 'index.html'
    output_html_path = output_folder2 / file_name

    with output_html_path.open('w') as f:
        f.write(template.render(model=model))

    output_folder2 = Path(output_path) / 'output2' / 'src'
    output_folder2.mkdir(parents=True, exist_ok=True)
    template = jinja_env.get_template('frontend/reportWebVitals.j2')
    folder = output_folder2
    folder.mkdir(parents=True, exist_ok=True)
    file_name = 'reportWebVitals.js'
    output_html_path = output_folder2 / file_name

    with output_html_path.open('w') as f:
        f.write(template.render(model=model))

    output_folder2 = Path(output_path) / 'output2'
    output_folder2.mkdir(parents=True, exist_ok=True)
    template = jinja_env.get_template('frontend/package-lock.j2')
    folder = output_folder2
    folder.mkdir(parents=True, exist_ok=True)
    file_name = 'package-lock.json'
    output_html_path = output_folder2 / file_name

    with output_html_path.open('w') as f:
        f.write(template.render(model=model))

    output_folder2 = Path(output_path) / 'output2'
    output_folder2.mkdir(parents=True, exist_ok=True)
    template = jinja_env.get_template('frontend/App.j2')
    folder = output_folder2 / 'src'
    folder.mkdir(parents=True, exist_ok=True)
    file_name = 'App.js'
    output_html_path = output_folder2 / 'src' /  file_name

    with output_html_path.open('w') as f:
        f.write(template.render(model=model))

    template = jinja_env.get_template('frontend/Appcss.j2')
    folder = output_folder2 / 'src'
    folder.mkdir(parents=True, exist_ok=True)
    file_name = 'App.css'
    output_html_path = output_folder2 / 'src' / file_name

    with output_html_path.open('w') as f:
        f.write(template.render(model=model))

    template = jinja_env.get_template('frontend/indexcss.j2')
    folder = output_folder2 / 'src'
    folder.mkdir(parents=True, exist_ok=True)
    file_name = 'index.css'
    output_html_path = output_folder2 / 'src' / file_name

    with output_html_path.open('w') as f:
        f.write(template.render(model=model))

    template = jinja_env.get_template('frontend/LoginForm.j2')
    folder = output_folder2 / 'src'/'views'/'login'
    folder.mkdir(parents=True, exist_ok=True)
    file_name = 'LoginForm.js'
    output_html_path = output_folder2 / 'src' /'views'/'login'/ file_name

    with output_html_path.open('w') as f:
        f.write(template.render(model=model))

    template = jinja_env.get_template('frontend/NavBar.j2')
    folder = output_folder2 / 'src' / 'views' / 'components'
    folder.mkdir(parents=True, exist_ok=True)
    file_name = 'NavBar.js'
    output_html_path = output_folder2 / 'src' / 'views' / 'components' / file_name

    with output_html_path.open('w') as f:
        f.write(template.render(model=model))

    #  todo ovde petlja
    for cl in model.classes:
        #front
        template = jinja_env.get_template('frontend/Service.j2')
        file_name = cl.name + 'Service.js'
        folder = output_folder2 / 'src' / 'services'
        folder.mkdir(parents=True, exist_ok=True)
        output_html_path = output_folder2 / 'src' / 'services' / file_name

        with output_html_path.open('w') as f:
            # print(model)
            f.write(template.render(model=model, current_class=cl))

        template = jinja_env.get_template('frontend/ViewComponent.j2')
        file_name = cl.name + 'View.js'
        folder = output_folder2 / 'src' / 'views' / cl.name
        folder.mkdir(parents=True, exist_ok=True)
        output_html_path = output_folder2 / 'src' / 'views' / cl.name / file_name

        with output_html_path.open('w') as f:
            # print(model)
            f.write(template.render(model=model, current_class=cl))

        template = jinja_env.get_template('frontend/DeleteElement.j2')
        file_name = cl.name + 'Delete.js'
        folder = output_folder2 / 'src' / 'views' / cl.name
        folder.mkdir(parents=True, exist_ok=True)
        output_html_path =output_folder2 / 'src' / 'views' / cl.name  / file_name

        with output_html_path.open('w') as f:
            # print(model)
            f.write(template.render(model=model, current_class=cl))

        template = jinja_env.get_template('frontend/EditComponent.j2')
        file_name = 'Edit'+cl.name + '.js'
        folder = output_folder2 / 'src' / 'views' / cl.name
        folder.mkdir(parents=True, exist_ok=True)
        output_html_path = output_folder2 / 'src' / 'views' / cl.name / file_name

        with output_html_path.open('w') as f:
            # print(model)
            f.write(template.render(model=model, current_class=cl))

        template = jinja_env.get_template('frontend/Table.j2')
        file_name = 'Table'+cl.name + '.js'
        folder = output_folder2 / 'src' / 'views' / cl.name
        folder.mkdir(parents=True, exist_ok=True)
        output_html_path = output_folder2 / 'src' / 'views' / cl.name / file_name

        with output_html_path.open('w') as f:
            # print(model)
            f.write(template.render(model=model, current_class=cl))



    #bek
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