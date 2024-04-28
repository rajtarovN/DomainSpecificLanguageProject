from textx import TextXSemanticError
from .model import *
from .meta_model_functions import *
def nelly_checker(nelly_model):
    file_parts = nelly_model.split("file")
    clases =[]
    enums =[]
    files = []
    relations = []
    for fp in file_parts:
        if "[" in fp:
            files.append(func_checker(fp)) #mislim da saljem fp, a bilo je part
        else:
            parts = nelly_model.split("}")

            for part in parts:
                if 'class' in part:
                    clases.append(class_object_checker(part))
                elif 'enum' in part:
                    enums.append(enums_checker(part))
                elif 'OneToOne' in part or 'OneToMany' in part or 'ManyToOne' in part or 'ManyToMany' in part:
                    relations.append(relations_checker(part))
    add_relations_to_class(clases, relations)
    nelly = Model("nelly", clases, enums, files, relations)
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
    if relation.startswith("OneToOne"):
        list_couples = find_couples(relation.split("{")[1])
        return OneToOne(list_couples)
    elif relation.startswith("OneToMany"):
        list_couples = find_couples(relation.split("{")[1])
        return OneToMany(list_couples)
    elif relation.startswith("ManyToOne"):
        list_couples = find_couples(relation.split("{")[1])
        return ManyToOne(list_couples)
    else:
        list_couples = find_couples(relation.split("{")[1])
        return ManyToMany(list_couples)


def find_couples(tekst):
    list_links = tekst.split(";")
    list_couples = []
    for link in list_links:
        as_part = ""
        if "as:" in link:
            link, as_part = link.split("as:")
        list_to = link.split("to:")
        list_to.append(as_part)
        list_couples.append(list_to)
    return list_couples

def add_relations_to_class(clases, relations):
    for relation in relations:
        if isinstance(relation, OneToOne):
            add_relations(relation, clases, 0,"OneToOne", "LAZY", "ALL")
        elif isinstance(relation, OneToMany):
            add_relations(relation, clases, -1,"OneToMany", "LAZY", "ALL")
        elif isinstance(relation, ManyToOne):
            add_relations(relation, clases, 0,"ManyToOne", "LAZY", "ALL")
        else:
            add_relations(relation, clases, -1,"ManyToMany", "LAZY", "ALL")

def add_relations(relation, clases,upper, cardinality, fetch_type, cascade_type):
    for link in relation.list_couple:
        for cl in clases:
            if link!="":
                if link[0] == cl.name:
                    second_class = [c.name for c in clases if c.name == link[1]][0]
                    if len(link)==3:
                        if cardinality == "ManyToOne":
                            mapped_by =None
                        elif cardinality == "OneToMany":
                            mapped_by = ""
                        else:
                            mapped_by = cl.name.lower()
                        cl.add_ref_property(LinkProperty(link[1], second_class, upper, cardinality, fetch_type, cascade_type, mapped_by, link[2]))  # todo
                    else:
                        if cardinality == "ManyToOne":
                            mapped_by = None
                        elif cardinality == "OneToMany":
                            mapped_by = ""
                        else:
                            mapped_by = cl.name.lower()
                        cl.add_ref_property(
                            LinkProperty(link[1], second_class, upper, cardinality, fetch_type, cascade_type, mapped_by,
                                         ""))
                    second_class_class = [c for c in clases if c.name == link[1]][0]
                    if cardinality=="OneToMany":
                        second_class_class.add_ref_property(LinkProperty(link[0], cl, 0,"ManyToOne", fetch_type, cascade_type, None, ""))
                    elif cardinality=="ManyToOne":
                        if len(link)==3:
                            mapped_by = link[2]
                        else:
                            mapped_by = None
                        second_class_class.add_ref_property(LinkProperty(link[0], cl, -1, "OneToMany", fetch_type, cascade_type,  mapped_by, ""))
                    elif cardinality == "ManyToMany":
                        second_class_class.add_ref_property(
                            LinkProperty(link[0], cl,-1, "ManyToMany",  fetch_type, cascade_type,  second_class_class.name.lower()+"_"+link[0].lower(), ""))
