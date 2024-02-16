from textx import TextXSemanticError
from .model import *
from .meta_model_functions import *
def nelly_checker(nelly_model):
    file_parts = nelly_model.split("file")
    clases =[]
    enums =[]
    files = []
    for fp in file_parts:
        if "[" in fp:
            files.append(file_checker(part))
        else:
            parts = nelly_model.split("}")

            for part in parts:
                if 'class' in part:
                    clases.append(class_object_checker(part))
                elif 'enum' in part:
                    enums.append(enums_checker(part))

    nelly = Nelly("nelly", clases, enums, files)
    return nelly
    # classPerson{idString;ageint;}enumGender{MALE;FEMALE;OTHER;}classAddress{streetString;cityString;zipCodeString;}OneToOne{PersontoAddress;}OneToMany{PersontoAddress;}ManyToOne{PersontoAddress;}ManyToMany{PersontoAddress;}
def class_object_checker(entity):
    parts = entity.split('class')[1].split("{")
    #classPerson:idStringageint
    attributes = parts[1].split(";")
    types = ('String', 'int', 'float', 'boolean', 'byte', 'short',  'long',  'double', 'char')
    attributes_names = []
    attribute_list = []
    
    for attr in attributes:
        name_l = [attr.split(type) for type in types if type in attr]
        if len(name_l)>0:
            if name_l[0][0]!="":
                attributes_names.append(name_l[0][0])
                attribute_list.append(Attribute(name_l[0][0], [type for type in types if type in attr][0]))

    errors = [name for name in attributes_names if attributes_names.count(name) > 1]
    for error in errors:
        raise TextXSemanticError(f'Attributre with the name {error} already exists! Attributes names must be unique.')
    return Class(parts[0],attribute_list)

def enums_checker(enum): #enums
    # enumGender:MALEFEMALEOTHER
    name = enum.split("enum")[1].split("{")[0]
    values = enum.split("{")[1].split(";")
    errors = [value for value in values if values.count(value) > 1]
    for error in errors:
        raise TextXSemanticError(f'Value of enum with the name {error} already exists! Values names must be unique.')
    return Enum(name, values)

def relations_checker(relation):
    pass
    # print("relation\n")
    # print(relation)
    # print("relation\n")