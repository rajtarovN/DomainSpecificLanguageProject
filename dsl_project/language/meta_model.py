from textx import TextXSemanticError
from .model import *
from .meta_model_functions import *


def nelly_checker(nelly_model):
    file_parts = nelly_model.split("file")
    clases = []
    enums = []
    files = []
    relations = []
    for fp in file_parts:
        if "[" in fp:
            pass
            # files.append(func_checker(fp))  # mislim da saljem fp, a bilo je part
        else:
            parts = nelly_model.split("}")

            for part in parts:
                if 'class' in part:
                    new_class = class_object_checker(part)
                    if any([True for cl in clases if cl.name == new_class.name]):
                        raise Exception(
                            f'Class with the name {new_class.name} already exists! Name of classes must be unique.')
                    clases.append(new_class)
                elif 'enum' in part:
                    new_enum = enums_checker(part)
                    if any([True for cl in enums if cl.name == new_enum.name]):
                        raise Exception(
                            f'Enum with the name {new_enum.name} already exists! Name of enums must be unique.')
                    enums.append(new_enum)
                elif 'OneToOne' in part or 'OneToMany' in part or 'ManyToOne' in part or 'ManyToMany' in part:
                    new_relation = relations_checker(part)
                    if any([True for relation in relations if isinstance(relation,type(new_relation))]):
                        raise Exception(
                            f'Relation {type(new_relation)} already defined.')
                    relations.append(new_relation)
    check_one_to_many(clases, relations)
    check_existing_class(clases, relations)
    add_relations_to_class(clases, relations)
    if any([True for cl in clases if cl.anotation is not None]):
        add_roles(clases, relations)
    # check_params_types_annotation(clases)
    check_anotations(clases)
    nelly = Model("nelly", clases, enums, files, relations)
    return nelly
    # classPerson{idString;ageint;}enumGender{MALE;FEMALE;OTHER;}classAddress{streetString;cityString;zipCodeString;}OneToOne{PersontoAddress;}OneToMany{PersontoAddress;}ManyToOne{PersontoAddress;}ManyToMany{PersontoAddress;}


def make_anotation(possible_anotation):
    if possible_anotation.startswith("@Bying"):
        return ByingAnotation(possible_anotation.split("(")[1].split(")")[0])
    elif possible_anotation.startswith("@Bill"):
        params_string = possible_anotation.split("(")[1].split(")")[0]
        names = params_string.split(",")
        return BillAnotation(names)
    elif possible_anotation.startswith("@Action"):
        name = possible_anotation.split("@Action")[1]#.split("(")[0]
        #params_string = possible_anotation.split("(")[1].split(")")[0]
        return ActionAnotation(name)#, parse_params(params_string))
    elif possible_anotation.startswith("@Basket"):
        return Anotation("basket")
    elif possible_anotation.startswith("@Item") :
        return Anotation("item")
    return None

def parse_params(part):
    parameters = []
    types = ('String', 'int', 'float', 'boolean', 'byte', 'short', 'long', 'double', 'char') #todo
    for p in part[:-1].split(","):
        found = False
        if ")" in p:
            p = p.split(")")[0]
            found = True
        type_param = next((t for t in types if t in p), p)
        name_param = p.split(type_param)[1]
        if ")" in name_param:
            name_param = name_param.split(")")[0]
        parameters.append(Param(name_param, type_param))
        if found:
            break
    return parameters

def class_object_checker(entity):
    possible_anotation = entity.split('class')[0]
    parts = entity.split('class')[1].split("{")
    attributes = parts[1].split(";")
    types = ('String', 'int', 'float', 'boolean', 'byte', 'short', 'long', 'double', 'char')
    attributes_names = []
    attribute_list = []

    for attr in attributes:
        name_l = [attr.split(type) for type in types if type in attr]
        if len(name_l) > 0:
            if name_l[0][0] != "":
                attributes_names.append(name_l[0][0])
                attribute_list.append(Attribute(name_l[0][0], [type for type in types if type in attr][0]))

    errors = [name for name in attributes_names if attributes_names.count(name) > 1]
    for error in errors:
        raise Exception(f'Attributre with the name {error} already exists! Attributes names must be unique.')
    anotation = None
    if len(possible_anotation)>1:
        anotation = make_anotation(possible_anotation)
    return Class(parts[0], attribute_list, anotation)


def enums_checker(enum):  # enums
    # enumGender:MALEFEMALEOTHER
    name = enum.split("enum")[1].split("{")[0]
    values = enum.split("{")[1].split(";")
    errors = [value for value in values if values.count(value) > 1]
    for error in errors:
        raise Exception(f'Value of enum with the name {error} already exists! Values names must be unique.')
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
        check_couple(list_to, list_couples)
    return list_couples

def check_couple(list_to, list_couples):
    for couple in list_couples:
        if ( (couple[0] == list_to[0] and couple[1] == list_to[1]) or
             (couple[1] == list_to[0] and couple[0] == list_to[1]) ):
            if list_to[2] ==couple[2]:
                raise Exception(f'Relation {couple[0]} {couple[1]} already defined.')
    list_couples.append(list_to)


def add_relations_to_class(clases, relations):
    for relation in relations:
        if isinstance(relation, OneToOne):
            add_relations(relation, clases, 0, "OneToOne", "LAZY", "ALL")
        elif isinstance(relation, OneToMany):
            add_relations(relation, clases, -1, "OneToMany", "LAZY", "ALL")
        elif isinstance(relation, ManyToOne):
            add_relations(relation, clases, 0, "ManyToOne", "LAZY", "ALL")
        else:
            add_relations(relation, clases, -1, "ManyToMany", "LAZY", "ALL")


def add_relations(relation, clases, upper, cardinality, fetch_type, cascade_type):
    for link in relation.list_couple:
        for cl in clases:
            if link != "":
                if link[0] == cl.name:
                    second_class = [c.name for c in clases if c.name == link[1]][0]
                    if len(link) == 3:
                        if cardinality == "ManyToOne":
                            mapped_by = None
                        elif cardinality == "OneToMany":
                            mapped_by = ""
                        else:
                            mapped_by = cl.name.lower()
                        cl.add_ref_property(
                            LinkProperty(link[1], second_class, upper, cardinality, fetch_type, cascade_type, mapped_by,
                                         link[2]))  # todo
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
                    if cardinality == "OneToOne":
                        second_class_class.add_ref_property(
                            LinkProperty(link[0], cl, 0, "OneToOne", fetch_type, cascade_type, None, "*"))
                    elif cardinality == "OneToMany":
                        second_class_class.add_ref_property(
                            LinkProperty(link[0], cl, 0, "ManyToOne", fetch_type, cascade_type, None, ""))
                    elif cardinality == "ManyToOne":
                        if len(link) == 3:
                            mapped_by = link[2]
                        else:
                            mapped_by = None
                        second_class_class.add_ref_property(
                            LinkProperty(link[0], cl, -1, "OneToMany", fetch_type, cascade_type, mapped_by, ""))
                    elif cardinality == "ManyToMany":
                        second_class_class.add_ref_property(
                            LinkProperty(link[0], cl, -1, "ManyToMany", fetch_type, cascade_type,
                                         second_class_class.name.lower() + "_" + link[0].lower(), ""))

def check_params_types_annotation(classes):
    types = ('String', 'int', 'float', 'boolean', 'byte', 'short', 'long', 'double', 'char')
    names_classes = [cl.name for cl in classes]
    for cl in classes:
        if cl.anotation is not None:
            if cl.anotation.name == "action":
                for p in cl.anotation.params:
                    if p.name == "":
                        if p.type.startswith("List<"):
                            type_p = p.type
                            p.type = p.type.split(">")[0]+">"
                            p.name = type_p.split(">")[1]
                            list_type =type_p[5:].split(">")[0]
                            if list_type not in types and list_type not in names_classes:
                                raise Exception("Type "+list_type+"doesnt exist")
                        else:
                            found = False
                            for name in names_classes:
                                if p.type.startswith(name):
                                    name_p = p.type.split(name)[1]
                                    p.type= name
                                    p.name = name_p
                                    found = True
                                    break
                            if not found:
                                raise Exception("Type " + p.type + "doesnt exist")


def add_roles(clases, relations):
    seller = Class("Seller", [], None, True, 'SELLER')
    admin = Class("Admin", [], None, True, 'ADMIN')
    customer = Class("Customer", [], None, True, 'CUSTOMER')
    clases.extend([seller, admin, customer])
    for clas in clases:
        if clas.anotation is not None:
            if clas.anotation.name == "basket":
                clas.add_ref_property(
                    LinkProperty(customer.name, customer, 0, "OneToOne", 'LAZY', 'ALL', None, "customer"))
                customer.add_ref_property(
                    LinkProperty(clas.name, clas, 0, "OneToOne", 'LAZY', 'ALL', "customer", "basket"))
            elif clas.anotation.name == "bill":
                clas.add_ref_property(
                    LinkProperty(customer.name, customer, 0, "ManyToOne", 'LAZY', 'ALL', None ,"customer"))
                customer.add_ref_property(
                    LinkProperty(clas.name, clas, -1, "OneToMany", 'LAZY', 'ALL', None, "bill"))

def check_existing_class(clases, relations):
    for relation in relations:
        for couple in relation.list_couple:
            if not any([True for cl in clases if cl.name == couple[0] or cl.name == couple[1] ]):
                raise Exception(f'Class name {couple[1]} or {couple[0]} not defined ')


def check_one_to_many(clases, relations):
    one_to_many = [relation for relation in relations if isinstance(relation, OneToMany )]
    many_to_one = [relation for relation in relations if isinstance(relation, ManyToOne )]
    if len(one_to_many)>0:
        one_to_many =one_to_many[0]
    else:
        return
    if len(many_to_one)>0:
        many_to_one =many_to_one[0]
    else:
        return
    for otm in one_to_many.list_couple:
        print(type(many_to_one))
        for mto in many_to_one:
            if ((otm[0] == mto[0] and otm[1] == mto[1]) or
                    (otm[1] == mto[0] and otm[0] == mto[1])):
                if mto[2] == otm[2]:
                    raise Exception(f'Relation {otm[0]} {otm[1]} already defined.')

def check_anotations(classes):
    if any([True for cl in classes if cl.anotation is not None]):
        model_anotation = [cl.anotation.name for cl in classes if cl.anotation is not None]
        list_anotation = ['bill', 'bying', 'action', 'basket', 'item']
        for m in model_anotation:
            if model_anotation.count(m)>1:
                raise Exception(f'Multiply anotation definition {m}.')
        for antation in list_anotation:
            if antation.lower() not in model_anotation:
                raise Exception(f'Missing anotation  {antation}.')


