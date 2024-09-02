from .model import ForFunction, Function, Param, IfStatement, Variable, ShortFunctions, NewObjectStatement, \
    Operations, Condition, ClassWithGeters, Attribute
import copy
import re

from .meta_model import nelly_checker


function_names = []
short_f_names = ["sumOf", "averageOf", "productOf", "selectIn", "selectTop", "countFrom", "allOf", "anyOf", "noneOf",
                 "min", "max", "for", "add", "sizeOf"]
defined_classes = ["User", "Proizvodi", "Porudzbina", "ListProduct", "Order"]
parameters_list = {}
current_block = ""
parsed_model = None

def func_checker(text):
    global function_names, parameters_list, current_block
    functions_text = text.split("def")
    functions = []
    def_var = {}
    types = ('String', 'int', 'float', 'boolean', 'byte', 'short', 'long', 'double', 'char')
    for ft in functions_text[1:]:  # prvo pronadjem imena svih fja
        parts = ft.split("(")
        function_names.append(parts[0])
    num_blocks = 0
    for ft in functions_text[1:]:
        num_blocks += 1
        current_block = "" + str(num_blocks)
        parameters_list = {}
        parts = ft.split("(")

        parameters = []
        for p in parts[1][:-1].split(","):
            found = False
            if ")" in p:
                p = p.split(")")[0]
                found = True
            type_param = next((t for t in types if t in p), "")
            if type_param == "":
                type_param = next((t for t in defined_classes if t in p), "")
                if type_param == "":
                    raise Exception("Type of param isnt all right "+str(p))

            name_param = p.split(type_param)[1]
            if ")" in name_param:
                name_param = name_param.split(")")[0]
            parameters.append(Param(name_param, type_param))
            if found:
                break

        name_func = parts[0]
        for p in parameters:
            parameters_list[p.name] = p
        text1 = "{".join(ft.split("{")[1:])
        if text1.endswith("}]"):
            text1 = text1[:-2]
        else:
            text1 = text1[:-1]

        statements = statements_checker(text1, def_var)

        functions.append(Function(name_func, statements, copy.deepcopy(parameters)))

    return functions, def_var


def sent_func_checker(text):
    global function_names, parameters_list, current_block, parsed_model
    def_var = {}
    name_func = "make"
    parameters_list["customer"] = 'customer'
    parameters_list["bill"] = 'bill'
    parameters_list["action"] = 'action'
    classes_text, text = text.split("file{")
    parsed_model = nelly_checker(classes_text)
    add_data_to_clases(parsed_model)
    text = text[:-1]
    statements = statements_checker(text, def_var)
    return Function(name_func, statements, copy.deepcopy(parameters_list)), def_var


def add_data_to_clases(model):
    item_class = None
    iwp_class = None
    for cl in model.classes:
        if cl.anotation and cl.anotation.name=="item":
            item_class = cl
            item_class.attributes.append(Attribute("quantity", "int"))
            item_class.attributes.append(Attribute("name", "String")) #item with price basket
        elif cl.anotation and cl.anotation.name=="basket":
            cl.attributes.append(Attribute("quantity", "int"))
        elif cl.anotation and cl.anotation.name=="bill":
            cl.attributes.append(Attribute("totalPrice", "double"))
        elif cl.anotation and cl.anotation.name == "bying":
            iwp_class = cl
            cl.attributes.append(Attribute("currentPrice", "double"))

    for cl in model.classes:
        if cl.anotation and cl.anotation.name=="action":
            model.relations[0].list_couple.append([cl.name, item_class.name])
            model.relations[0].list_couple.append([item_class.name, cl.name])
        elif cl.anotation and cl.anotation.name == "basket":
            model.relations[0].list_couple.append([cl.name, item_class.name])
            model.relations[0].list_couple.append([cl.name, "customer"])
            model.relations[0].list_couple.append([item_class.name, cl.name])
            model.relations[0].list_couple.append(["customer", cl.name])
        elif cl.anotation and cl.anotation.name == "bill":
            model.relations[0].list_couple.append([cl.name, "customer"])
            model.relations[0].list_couple.append([cl.name, iwp_class.name])
            model.relations[0].list_couple.append(["customer", cl.name])
            model.relations[0].list_couple.append([iwp_class.name, cl.name])
        elif cl.anotation and cl.anotation.name == "bying":
            model.relations[0].list_couple.append([cl.name, item_class.name])
            model.relations[0].list_couple.append([item_class.name, cl.name])


def statements_checker(text, defined_variables):
    global short_f_names, current_block
    num_block = 0
    lines = text.split(";")
    line_num = 0
    list_statements = []
    while line_num < len(lines):

        line = lines[line_num]
        if line == "" or line == ")" or line == "}":
            line_num += 1
            continue
        if any(line.startswith(n) for n in short_f_names):
            cur_b = current_block
            list_statements.append(short_function_checker(line, defined_variables))
            current_block = cur_b
            line_num += 1
            continue
        found = False
        for cl in defined_classes:# new obj
            if "'" in cl:
                cl = cl[1:-1]
            if line.lower().startswith("new" + cl.lower() + "("):
                list_statements.append(new_object_checker(line, cl, defined_variables))
                found = True
                break
        if found:
            line_num += 1
            continue

        if line.startswith("if"):
            old_curent = current_block
            num_block += 1
            current_block = ":" + str(num_block)
            list_statements.append(if_statement_checker(lines[line_num:], line, defined_variables))

            text = ";".join(lines[line_num:])
            index_in_text = find_bracket(text, "{")
            index_to_break = find_index(text[index_in_text + 1:])
            lines = text[index_in_text + 1 + index_to_break:].split(";")
            current_block = old_curent
            continue
        elif check_if_its_variable(line):  # preostaje da je samo variabla
            if "apply" in line:
                list_statements.append(variable_checker(line, defined_variables, "apply"))
            else:
                list_statements.append(variable_checker(line, defined_variables))
        elif "?" in line:  # preostaje samo ? da znaci da je uslov
            list_statements.append(one_line_condition_checker(line, defined_variables))
        elif check_if_condition_or_operation(line, defined_variables, True):
            list_statements.append(condition_checker(line, defined_variables))  # condition
        elif check_if_condition_or_operation(line, defined_variables, False):
            list_statements.append(operation_checker(line, defined_variables))  # condition
        elif check_if_string_value(line):
            list_statements.append(line)
        elif check_type(line)[0] is not None:
            list_statements.append(check_type(line)[1])
        elif all(char == '}' for char in line):
            line_num += 1
            continue
        elif line in defined_variables.keys():
            if is_var_in_parent_block(defined_variables[line]):
                found_var = find_var_in_defined_variables(line, defined_variables)
                list_statements.append(found_var)
            else:
                raise Exception("Variable is not defined in this block " + line)
        elif line in parameters_list.keys():
            list_statements.append(parameters_list[line])
        elif check_if_contains_dot(line, defined_variables):
            part = line.split(".")[0]
            new_parts = line.split(".")[1:]
            if part in defined_variables.keys():
                v = None
                if is_var_in_parent_block(defined_variables[part][1]):
                    found_var = find_var_in_defined_variables(part, defined_variables)
                    find_all_chain_getters(found_var, new_parts)
                    v = ClassWithGeters(found_var, new_parts)
            else:
                find_all_chain_getters(parameters_list[part], new_parts)
                v = ClassWithGeters(parameters_list[part], new_parts)
            list_statements.append(v)
        else:
            raise Exception("Nothing found " + str(line))
        line_num += 1
    return list_statements


def short_function_checker(line, defined_variables):
    global current_block
    name = [i for i in short_f_names if line.startswith(i)][0]
    rest_line = line.split(name)[1]
    name_func_with_posible_list = ["sumOf", "averageOf", "productOf", "selectIn",  "countFrom", "allOf",
                                   "anyOf", "noneOf", "min", "max",  "sizeOf"]
    if name in name_func_with_posible_list:
        value, rest_line = check_posible_list(rest_line, defined_variables)
        value_as, value_where = None, None
        formula_part = None
        if rest_line.startswith("as:"):
            if isinstance(value, ClassWithGeters):
                type_value = "List"
                if value.list_geters[-1]=="item":
                    type_value+="Item"
                value_as, rest_line = check_as_part(rest_line[3:], defined_variables, "ListItem")
            else:
                value_as, rest_line = check_as_part(rest_line[3:], defined_variables, value.type)
            if rest_line.startswith("formula"):
                formula_part, rest_line = for_func_formula(rest_line, defined_variables)
        if rest_line.startswith("where"):
            value_where, rest_line = check_where_part(rest_line, defined_variables)
        return ShortFunctions(value, name, value_where, value_as, None, None, formula_part)
    elif name == "add":
        rl = rest_line
        first_part, rest_line = find_part_function(rest_line, defined_variables)
        if len(rest_line) == 0:
            in_index = rl.rfind("in:")
            rest_line = rl[in_index + 3:]
        else:
            rest_line = rest_line[3:]
        posible_list, rest_line = check_posible_list(rest_line, defined_variables)
        return ShortFunctions(posible_list, name, None, None, first_part, None, None)
    elif name == "selectTop":
        number = rest_line.split("of")[0]
        value_posible, rest_line = check_posible_list(rest_line.split("of")[1], defined_variables)
        if isinstance(value_posible, ClassWithGeters):
            type_value = "List"
            if value_posible.list_geters[-1] == "item":
                type_value += "Item"
            value_as, rest_line = check_as_part(rest_line[3:], defined_variables, type_value)
        else:
            value_as, rest_line = check_as_part(rest_line[3:], defined_variables, value_posible.type)
        value_where, rest_line = check_where_part(rest_line, defined_variables)
        return ShortFunctions(value_posible, name, value_where, value_as, None, number, None)
    else:
        line = line[3:]
        list_name, rest_line = for_func_first_part(line[:line.find("as:")], defined_variables), line[line.find("as:"):]
        as_part, rest_line = for_func_as_part(rest_line, defined_variables, list_name)
        for_function = None

        if rest_line.startswith("sumFrom") and not rest_line.startswith("sumOf"):
            for_function, rest_line = "sumFrom", rest_line[7:]
        elif rest_line.startswith("averageFrom"):
            for_function, rest_line = "averageFrom", rest_line[7:]
        elif rest_line.startswith("productFrom"):
            for_function, rest_line = "productFrom", rest_line[7:]
        cur_b = current_block
        sf_for = short_function_checker(rest_line, defined_variables)
        current_block = cur_b
        obj = ForFunction(list_name[0], "for", None, as_part, None, None, None, None, for_function, sf_for)
        return obj


def for_func_formula(rest_line, defined_variables):
    ind_of_func = [x for x in [rest_line.find("where"), rest_line.find("sumFrom"), rest_line.find("averageFrom"),
                               rest_line.find("productFrom")] if x != -1][0]
    r_l, r_line = rest_line[:ind_of_func], rest_line[ind_of_func:]
    r_l = r_l[7:]
    operation = operation_checker(r_l, defined_variables)
    return operation, r_line


def for_func_as_part(rest_line, defined_variables, types=None):
    global current_block
    indexes = [rest_line.find("formula"), rest_line.find("sumFrom"), rest_line.find("averageFrom"),
               rest_line.find("productFrom")]
    indexes.extend([rest_line.find(name) for name in short_f_names])
    new_indexes = [x for x in indexes if x != -1]
    first_op = min(new_indexes)
    new_rest_line = rest_line[first_op:]
    as_part = rest_line[:rest_line.find(new_rest_line)][3:]
    checked = id_or_chain(as_part, defined_variables, None, new_rest_line)
    if checked[0] is None:
        if check_if_contains_dot(as_part, defined_variables):
            name = as_part.split(".")[0]
            find_all_chain_getters(name, as_part.split(".")[1:])
            return ClassWithGeters(name, as_part.split(".")[1:]), new_rest_line
        else:
            if types is not None:
                as_part_type =  print(types[0].list_geters[-1])
            else:
                if checked[0] is not None and isinstance(checked[0].name, str):
                    as_part_type = checked[0].name
                elif checked[0] is None:
                    as_part_type = as_part
                else:
                    as_part_type = checked[0].name.type
            new_var = Variable(as_part, as_part_type, None, None), new_rest_line
            current_block = current_block + ":" + str(1)
            defined_variables[as_part] = [[new_var[0]], [current_block]]
            return new_var
    else:
        return None, None


def for_func_first_part(rest_line, defined_variables):
    global current_block
    index = rest_line.find("as:")
    if check_if_contains_dot(rest_line, defined_variables):
        name = rest_line.split(".")[0]
        if name in defined_variables.keys():
            if is_var_in_parent_block(defined_variables[name][1]):
                found_var = find_var_in_defined_variables(name, defined_variables)
                find_all_chain_getters(found_var, rest_line.split(".")[1:])
                obj = ClassWithGeters(found_var, rest_line.split(".")[1:]), rest_line[index:]
                return obj
        elif name in parameters_list.keys():
            find_all_chain_getters(parameters_list[name], rest_line.split(".")[1:])
            return ClassWithGeters(parameters_list[name], rest_line.split(".")[1:]), rest_line[index:]
    elif rest_line in defined_variables.keys():
        if is_var_in_parent_block(defined_variables[rest_line][1]):
            return find_var_in_defined_variables(rest_line, defined_variables), rest_line[index:]
    elif rest_line in parameters_list.keys():
        return parameters_list[rest_line], rest_line[index:]
    else:
        raise Exception(f"Variable not defined {rest_line}")


def check_posible_list(rest_line, defined_variables):
    global current_block
    index = rest_line.rfind("where")
    if rest_line.startswith("selectIn"):
        number_select = rest_line.count("selectIn")
        number_where = rest_line.count("where")
        if number_where == number_select:
            cur_b = current_block
            res = short_function_checker(rest_line, defined_variables)
            current_block = cur_b
            return res, ""
        else:
            cur_b = current_block
            res = short_function_checker(rest_line[index:], defined_variables)
            current_block = cur_b
            return res, rest_line[index:]
    if "as:" in rest_line:
        sending_rest_line = rest_line[:rest_line.find("as:")],
    else:
        sending_rest_line=rest_line,
    is_id = id_or_chain(sending_rest_line, defined_variables, index)
    if is_id[0] is None:
        raise Exception(f"Posible list {sending_rest_line} not defined.")
    if isinstance(is_id[0], ClassWithGeters):
        find_all_chain_getters(is_id[0].name, is_id[0].list_geters)
    return is_id[0], rest_line[rest_line.find("as:"):]


def check_as_part(rest_line, defined_variables, type_as):
    rest_line, last_part = rest_line, ""
    if "formula" in rest_line:
        last_part = "formula"+rest_line.split("formula")[1]
        rest_line = rest_line.split("formula")[0]
    elif "where" in rest_line:
        last_part = "where"+rest_line.split("where")[1]
        rest_line = rest_line.split("where")[0]

    if check_if_condition_or_operation(rest_line, defined_variables, False):
        return operation_checker(rest_line, defined_variables), last_part
    id_n, rl = id_or_chain(rest_line, defined_variables, None, last_part)
    if type_as.lower().startswith("list"):
        type_as = type_as[4:]
    if id_n is None:
        if "." in rest_line:
            var = Variable(rest_line.split(".")[0], type_as, None, None)
            find_all_chain_getters(var, rest_line.split(".")[1:])
            clas = ClassWithGeters(var, rest_line.split(".")[1:])
            defined_variables[rest_line.split(".")[0]] = [[clas], [current_block]]
            return clas, last_part
        else:
            var = Variable(rest_line, type_as, None, None)
            defined_variables[rest_line] = [[var], [current_block]]
            return var, last_part
    return id_n, last_part


def check_where_part(rest_line, defined_variables):
    global current_block
    rest_line = rest_line[5:]
    if rest_line.startswith("any") or rest_line.startswith("none") or rest_line.startswith("all"):
        cur_b = current_block
        value = short_function_checker(rest_line, defined_variables)
        current_block = cur_b
        return value, ""
    elif check_if_condition_or_operation(rest_line, defined_variables, True):
        return check_one_condition(rest_line, defined_variables), ""
    return None, None


def find_part_function(rest_line, defined_variables):
    in_index = rest_line.rfind("in:")
    last_part = rest_line[in_index+3:]
    rest_line = rest_line[:in_index]
    # ( ChaneId | ID)
    ct = check_type(rest_line)
    if ct[0] is not None:
        return ct[1], last_part
    elif check_if_string_value(rest_line):
        return rest_line, last_part
    return id_or_chain(rest_line, defined_variables, in_index)


def id_or_chain(rest_line, defined_variables, in_index=None, return_line=""):
    if in_index is not None:
        return_line = rest_line[in_index:]
    if isinstance(rest_line, tuple):
        rest_line = rest_line[0]
    if check_if_contains_dot(rest_line, defined_variables):
        name = rest_line.split(".")[0]
        if name in defined_variables.keys():
            if is_var_in_parent_block(defined_variables[name][1]):
                found_var = find_var_in_defined_variables(name, defined_variables)
                find_all_chain_getters(found_var, rest_line.split(".")[1:])
                return ClassWithGeters(found_var, rest_line.split(".")[1:]), return_line
        elif name in parameters_list.keys():
            find_all_chain_getters(parameters_list[name], rest_line.split(".")[1:])
            return ClassWithGeters(parameters_list[name], rest_line.split(".")[1:]), return_line
    elif rest_line in defined_variables.keys():
        if is_var_in_parent_block(defined_variables[rest_line][1]):
            return find_var_in_defined_variables(rest_line, defined_variables), return_line
    elif rest_line in parameters_list.keys():
        return parameters_list[rest_line], return_line
    return None, None


def condition_checker(line, defined_variables):
    parts = parse_line(line, ["&&", "||"])

    first_part = parts[0]
    condition1 = check_one_condition(first_part, defined_variables)
    if len(parts) > 1:
        operator = parts[1]
        right_side = condition_checker(line.split(first_part + operator)[1], defined_variables)
    else:
        operator = None
        right_side = None
    return Condition(condition1, operator, right_side)


def operation_checker(line, defined_variables):
    global current_block
    operations = ['-', '*', '/', '%', '^', '+']
    parsed_line = parse_line(line, operations)
    left_side = parsed_line[0]
    ls = ""
    for sfn in short_f_names:
        if sfn in left_side:
            cur_b = current_block
            ls = short_function_checker(left_side, defined_variables)
            current_block = cur_b
    if left_side in defined_variables.keys():
        if is_var_in_parent_block(defined_variables[left_side][1]):
            found_var = find_var_in_defined_variables(left_side, defined_variables)
            ls = found_var
            if ls.type not in ["int","float", "double","short"]:
                raise Exception(f"Type {ls.type} of variable {ls.name} not suports arithmetic operation")
    elif left_side in parameters_list.keys():
        ls = parameters_list[left_side]
        if ls.type not in ["int", "float", "double", "short"]:
            raise Exception(f"Type {ls.type} of variable {ls.name} not suports arithmetic operation")
    # elif left_side.startswith("#") and left_side.endswith("#"):
    #     ls = CopyName(left_side)
    elif check_if_contains_dot(left_side, defined_variables):
        lef = left_side.split(".")[0]
        left_s = left_side.split(".")[1:]
        if lef in defined_variables.keys():
            if is_var_in_parent_block(defined_variables[lef][1]):
                found_var = find_var_in_defined_variables(lef, defined_variables)
                find_all_chain_getters(found_var, left_s)
                ls = ClassWithGeters(found_var, left_s)
                check_type_for_arithmetic_logical_operation(ls, defined_variables)
        elif lef in parameters_list.keys():
            find_all_chain_getters(parameters_list[lef], left_s)
            ls = ClassWithGeters(parameters_list[lef], left_s)
            check_type_for_arithmetic_logical_operation(ls, defined_variables)
    elif check_if_string_value(left_side):
        ls = left_side
    else:
        ct_value = check_type(left_side)
        if ct_value[0] is not None:
            ls = ct_value[1], None
            if ct_value[0]=="bool":
                raise Exception("Arithmetic operation doesnt support boolean value")
    operator = parsed_line[1]
    right_side = operator.join(line.split(operator)[1:])
    result_right_side = None
    if right_side in defined_variables.keys():
        if is_var_in_parent_block(defined_variables[right_side][1]):
            found_var = find_var_in_defined_variables(right_side, defined_variables)
            result_right_side = found_var
            if result_right_side.type not in ["int","float", "double","short"]:
                raise Exception(f"Type {result_right_side.type} of variable {result_right_side.name} not suports arithmetic operation")
    elif right_side in parameters_list.keys():
        result_right_side = parameters_list[right_side]
        if (not isinstance(result_right_side, str)) and result_right_side.type not in ["int", "float", "double", "short"]:
            raise Exception(
                f"Type {result_right_side.type} of variable {result_right_side.name} not suports arithmetic operation")
        if isinstance(result_right_side, str) and result_right_side not in ["int", "float", "double", "short"]:
            raise Exception(
                f"Type {result_right_side} of variable {right_side} not suports arithmetic operation")
    else:
        result_right_side = statements_checker(right_side, defined_variables)
    return Operations(ls, operator, result_right_side)


def one_line_condition_checker(line, defined_variables):
    left_side, right_side = line.split("?")
    if_statement, else_statement = None, None

    if "or:" in right_side:
        if_statement, else_statement = right_side.split("or:")
        else_statement = return_if_statement(else_statement, defined_variables)
    else:
        if_statement = right_side

    if_statement = return_if_statement(if_statement, defined_variables)
    extract_operands_conditions(left_side, defined_variables)
    return IfStatement(left_side, if_statement, None, None, else_statement)


def return_if_statement(if_statement, defined_variables):
    if_statement = statements_checker(if_statement, defined_variables)
    return if_statement


def variable_checker(line,
                     defined_variables, spliting_char="="):
    types = ('String', 'int', 'float', 'boolean', 'byte', 'short', 'long', 'double', 'char')
    var_type = next((t for t in types if line.startswith(t)), "")
    if var_type == "" and (
            line.split(spliting_char)[0] not in defined_variables.keys() and line.split(spliting_char)[0] not in
            parameters_list.keys() and "." not in line.split(spliting_char)[0]):
        raise Exception(f"Variable {line.split(spliting_char)[0]} not defined.")
    if var_type != "":
        right_side = spliting_char.join(line.split(var_type)[1].split(spliting_char)[1:])
    else:
        right_side = spliting_char.join(line.split(spliting_char)[1:])

    value = check_type(right_side)
    if value[0] is not None:
        value = value[1]
    else:
        if check_if_string_value(right_side):
            value = right_side

    if var_type != "":
        name = line.split(var_type)[1].split(spliting_char)[0]
        if name in parameters_list.keys():
            raise Exception("already defined variable in this block" + name)
        elif name in defined_variables.keys():
            for b in defined_variables[name]:
                if b == current_block or is_var_in_parent_block(defined_variables[name][1]):
                    raise Exception("already defined variable in this block" + name)
    else:
        if "." in line.split(spliting_char)[0] and check_if_contains_dot(line.split(spliting_char)[0],
                                                                         defined_variables):
            name = line.split(spliting_char)[0].split(".")[0]
            rs_list = line.split(spliting_char)[0].split(".")[1:]
            if name in defined_variables.keys():
                if is_var_in_parent_block(defined_variables[name][1]):
                    found_var = find_var_in_defined_variables(name, defined_variables)
                    find_all_chain_getters(found_var, rs_list)
                    name = ClassWithGeters(found_var, rs_list)
            elif name in parameters_list.keys():
                find_all_chain_getters(parameters_list[name], rs_list)
                name = ClassWithGeters(parameters_list[name], rs_list)
        else:
            name = line.split(spliting_char)[0]
            if "." in name:
                name = name.split(".")[0]
            if name not in defined_variables.keys() or name in parameters_list.keys():
                raise Exception("variable is not defined "+name)
            elif name in defined_variables.keys():
                found = False
                for b in defined_variables[name]:
                    if b == current_block or is_var_in_parent_block(defined_variables[name][1]):
                        # ako mi se ne nalazi u sadasnjem ili roditeljskom bloku
                        found = True
                        break
                if not found:
                    raise Exception("variable is not defined" + name)
    statements = statements_checker(right_side, defined_variables)
    var = Variable(name, var_type, value, statements)
    defined_variables[name] = [[var], [current_block]]
    return var


def find_var_in_defined_variables(name, defined_variables):
    variables = defined_variables[name][0]
    blocks = defined_variables[name][1]
    ind = 0
    for b in blocks:
        if current_block[1:].startswith(b) or current_block.startswith(b):
            return variables[ind]
        ind += 1


def new_object_checker(line, cl, defined_variables):
    string_args = line.split("(")[1][:-1].split(",")
    arguments = arguments_checker(string_args, defined_variables)
    return NewObjectStatement(cl, arguments)


def arguments_checker(string_args, defined_variables):
    arguments = []
    for arg in string_args:
        if arg in defined_variables.keys():
            if is_var_in_parent_block(defined_variables[arg][1]):
                found_var = find_var_in_defined_variables(arg, defined_variables)
                arguments.append(found_var)
            continue
        if arg in parameters_list.keys():
            arguments.append(parameters_list[arg])
            continue
        if check_if_contains_dot(arg, defined_variables):
            a = arg.split(".")[0]
            if is_var_in_parent_block(defined_variables[a][1]):
                found_var = find_var_in_defined_variables(a, defined_variables)
                arguments.append(found_var)
                continue
            elif a in parameters_list.keys():
                find_all_chain_getters(parameters_list[a], arg.split(".")[1:])
                arguments.append(ClassWithGeters(parameters_list[a], arg.split(".")[1:]))
                continue
        elif check_if_string_value(arg):
            arguments.append(arg)
            continue
        ct_value = check_type(arg)
        if ct_value[0] is not None:
            arguments.append(ct_value[1])
        else:
            arguments.append(statements_checker(arg, defined_variables))
    return arguments


def is_var_in_parent_block(list_blocks):
    for b in list_blocks:
        if current_block[1:].startswith(b) or current_block.startswith(b):
            return True
    return False


def check_if_condition_or_operation(line, definde_variables, condition):
    if condition:
        simbols = ('&&', '||', '=>', '<=', '>',  '<',  '==', '!=')
    else:
        simbols = ('+', '-', '*', '/', '%', '^')

    for s in simbols:
        # rr = line.split(s)[0]
        if s in line:
            left_side = line.split(s)[0]
            if left_side.startswith("!"):
                left_side = left_side[1:]
            if check_if_contains_dot(left_side, definde_variables):
                left_side = left_side.split(".")[0]
            if left_side in definde_variables.keys():
                if is_var_in_parent_block(definde_variables[left_side][1]):
                    return True
            elif line.split(s)[0] in definde_variables.keys():
                if is_var_in_parent_block(line.split(s)[0][1:]):
                    return True
            elif left_side in parameters_list.keys() or check_if_string_value(left_side) or \
                    check_type(left_side)[1] or check_if_contains_dot(left_side, definde_variables):
                #  ovo ipak mozda drugacije jer cu imati vec definisane
                return True
            raise Exception(f"Variable {left_side} in check is not defined")
    return False


def check_if_its_variable(line):
    if "apply" in line:
        return True
    for i in range(len(line)):
        if i + 1 < len(line):
            if line[i] == "=" and line[i + 1] == "=":
                return False
            elif line[i] == "=" and line[i + 1] != "=":
                return True
    return False


def if_statement_checker(lines, line, defined_variables):  # ovde bi trebalo podizati greske i slicno
    if lines[0].startswith("{"):
        lines[0] = lines[0][1:]
    if line.startswith("{"):
        line = line[1:]
    condition = line.split("{")[0][2:]  # mislim da uslov uglavnom mozemo nalepiti
    text = ";".join(lines)
    index_in_text = find_bracket(text, "{")
    text = text[len(condition) + 3:index_in_text]
    list_statements_if = statements_checker(text, defined_variables)
    elif_conditions = []
    elif_statements = []
    else_statements = []
    old_text = 'e'+";".join(lines)[len(condition) - 3 + index_in_text:]
    text = old_text
    if text[1] == "}":
        text = text[2:]
    while "elif" in text[:6]:
        el_condition = text.split("elif")[1].split("{")[0]
        if el_condition in elif_conditions:
            raise Exception("You have two same elif conditions")
        elif_conditions.append(el_condition)
        new_index_in_text = find_bracket(text.split("elif")[1], "{")
        new_old_text = copy.deepcopy(text)
        text = text.split("elif")[1][:new_index_in_text].split("{")[1]
        elif_statements.append(statements_checker(text, defined_variables))  # lista listi

        text = new_old_text[find_bracket(new_old_text, "{"):]
        if text.startswith("}"):
            text = text[1:]

    if "else" in text[:6]:
        closing_else_index_in_text = find_bracket(text, "{")
        else_statements = statements_checker(text[:closing_else_index_in_text].split("else{")[1], defined_variables)
    extract_operands_conditions(condition, defined_variables)
    for elif_part in elif_conditions:
        extract_operands_conditions(elif_part, defined_variables)
    return IfStatement(condition, list_statements_if, elif_conditions, elif_statements, else_statements)


def find_bracket(text, opening_bracket):
    stack = []
    if opening_bracket == "{":
        for i, char in enumerate(text):
            if char == opening_bracket:
                stack.append(i)
            elif char == '}':
                if not stack:
                    return None
                stack.pop()

                if not stack:
                    return i
    else:
        for i, char in enumerate(text):
            if char == opening_bracket:
                stack.append(i)
            elif char == ')':
                if not stack:
                    return None
                stack.pop()

                if not stack:
                    return i
    return None


def find_index(text):
    new_index = 0
    if text.startswith("}"):
        text = text[1:]
    while text.startswith("elif"):
        num_of_bracket = find_bracket(text, "{")
        new_index = new_index + num_of_bracket
        text = text[num_of_bracket:]
        if text.startswith("}"):
            new_index += 1
            text = text[1:]

    if text.startswith("else"):
        new_index = new_index + find_bracket(text, "{")
    return new_index + 1


def check_type(value):
    try:
        int_value = int(value)
        return "int", int_value
    except ValueError:
        try:
            float_value = float(value)
            return "float", float_value
        except ValueError:
            if value.lower() == 'true':
                return "bool", True
            elif value.lower() == 'false':
                return "bool", False
            else:
                return (None, None)


def check_if_string_value(value):
    if (value.startswith("'") and value.endswith("'") and value.count("'") == 2) or (
            value.startswith('"') and value.endswith('"') and value.count('"') == 2):
        return True
    else:
        return False


def parse_line(line, operations):
    elements = [line]
    new_elements = []
    for op in operations:
        for e in elements:
            if op in e:
                splited = e.split(op)
                numb = 0
                for el in splited:
                    if el != "":
                        new_elements.append(el)
                        if numb + 1 != len(splited):
                            new_elements.append(op)
                    numb += 1
            else:
                new_elements.append(e)
        elements = new_elements
        new_elements = []
        # a / b - a + b
    return elements


def find_index_of_operations(line):
    log_simbols = ('&&', '||', '=>', '<=', '>',  '<',  '==', '!=')
    artm_simbols = ('+', '-', '*', '/', '%', '^')
    ind = -2
    for ls in log_simbols:
        if ls in line:
            ind = line.find(ls)
            line = line.split(ls)[0]

    for ar in artm_simbols:
        if ar in line:
            ind = line.find(ar)
            line = line.split(ar)[0]

    return ind


def check_if_contains_dot(left_side, definde_variables):
    if "." in left_side:
        return left_side.split(".")[0] in definde_variables.keys() or left_side.split(".")[0] in parameters_list.keys()
    return False


def check_one_condition(first_part, defined_variables):
    negation = None
    if first_part[0] == "!":
        first_part = first_part[1:]
        negation = True

    operators = ['==', '!=', '>=', '<=', '>', '<']

    values = parse_line(first_part, operators)
    left_operator = check_part_condition(values[0], defined_variables)

    if len(values) > 1:
        operator = values[1]
        right_side = check_part_condition(values[2], defined_variables)
    else:
        operator = None
        right_side = None
    check_type_for_arithmetic_logical_operation(left_operator, defined_variables, False)
    return Condition(left_operator, operator, right_side, negation)


def check_part_condition(first_part, defined_variables):
    global current_block
    for sfn in short_f_names:
        if sfn in first_part:
            cur_b = current_block
            ls = short_function_checker(first_part, defined_variables)
            current_block = cur_b
            return ls
    ls = None
    if first_part in defined_variables.keys() or first_part in parameters_list.keys():
        ls = return_from_def_var_or_parameter_list(first_part, defined_variables)
    elif check_if_contains_dot(first_part, defined_variables):
        second_part = first_part.split(".")[1:]
        first_part = first_part.split(".")[0]
        if first_part in defined_variables.keys() or first_part in parameters_list.keys():
            res = return_from_def_var_or_parameter_list(first_part, defined_variables)
            find_all_chain_getters(res, second_part)
            ls = ClassWithGeters(res, second_part)
    elif check_if_string_value(first_part):
        ls = first_part
    else:
        ct_value = check_type(first_part)
        if ct_value[0] is not None:
            ls = ct_value[1], None
    return ls


def return_from_def_var_or_parameter_list(first_part, defined_variables):
    if first_part in defined_variables.keys():
        if is_var_in_parent_block(defined_variables[first_part][1]):
            return find_var_in_defined_variables(first_part, defined_variables)
    elif first_part in parameters_list.keys():
        return parameters_list[first_part]


def extract_operands_conditions(expression, defined_variables):
    pattern = r'\b\w+\b'
    matches = re.findall(pattern, expression)
    first_elem = ""
    for element in matches:
        if first_elem + "."+element in expression:
            element = first_elem + "."+element
        if check_part_condition(element, defined_variables) is None and check_type(element)[0] is None:
            raise Exception(f"Variable {element} not defined in {expression}")
        first_elem = element


def check_is_class_exist(class_name, model_cl):
    if model_cl is not None:
        for model_class in model_cl.classes:
            if isinstance(class_name, str) and isinstance(model_class, str):
                if model_class[1:-1].lower() == class_name[1:-1].lower():
                    return model_class
                continue
            if isinstance(class_name, str):
                if model_class.name[1:-1].lower() == class_name[1:-1].lower():
                    return model_class
                continue
            if isinstance(model_class, str):
                if model_class[1:-1].lower() == class_name[1:-1].lower():
                    return model_class
                continue
            if isinstance(class_name, Variable):
                if model_class.name[1:-1].lower() == class_name.type[1:-1].lower()\
                        or model_class.name.lower() == class_name.type.lower():
                    return model_class
                continue
            if model_class.name[1:-1].lower() == class_name.name[1:-1].lower():
                return model_class
    return None


def find_all_chain_getters(clas_name, list_geters):
    global parsed_model
    if isinstance(clas_name, ClassWithGeters):
        found_name = check_is_class_exist(clas_name.variable, parsed_model)
    else:
        found_name = check_is_class_exist(clas_name, parsed_model)
    i = 0
    if found_name is not None:
        for geter in list_geters:
            if list_geters.index(geter)!=0:
                geter_before = list_geters[list_geters.index(geter)-1]
                found_name = check_is_class_exist(geter_before, parsed_model)
            found = False
            for atribute in found_name.attributes:
                if atribute.name[1:-1] == geter:
                    found = True
                    break
                elif atribute.name == geter:
                    found = True
                    break
            if not found:

                for relations in parsed_model.relations:
                    if isinstance(relations, list):
                        if relations[0].lower() == found_name.name.lower() and relations[1].lower() == geter.lower():
                            found = True
                        break
                    for link in relations.list_couple:
                        if link[0][1:-1].lower() == found_name.name.lower() and link[1][1:-1].lower() == geter.lower():
                            found = True
                            break
                        if link[0].lower() == found_name.name.lower() and link[1].lower() == geter.lower():
                            found = True
                            break
                    if found:
                        break
            if geter=="id":
                return True
            if found and len(list_geters)-1 < i:
                raise Exception(f"You inputed more than expected geters {list_geters}.")
            if not found:
                raise Exception(f"Geter {geter} not found. ({clas_name} {list_geters})")
        i += 1
    else:
        raise Exception(f"Class {clas_name.name} doesnt exist.")


def check_type_for_arithmetic_logical_operation(class_with_geters, defined_variables, arithmetic=True):
    global parsed_model
    if isinstance(class_with_geters.name, str):
        try:
            found_name = check_is_class_exist(defined_variables[class_with_geters.name][0][0], parsed_model)
        except KeyError:
            try:
                found_name = check_is_class_exist(parameters_list[class_with_geters.name], parsed_model)
            except:
                raise Exception(f"therre is no element {class_with_geters.name}")
    elif isinstance(class_with_geters.name, ClassWithGeters):
        found_name = check_is_class_exist(class_with_geters.name.variable.type, parsed_model)
    else:
        found_name = check_is_class_exist(class_with_geters.name.type, parsed_model)
    i = 0
    types = ["int","float", "double","short"]
    if not arithmetic:
        types = ["boolean"]
    if found_name is not None:
        for geter in class_with_geters.list_geters:
            found = False
            for atribute in found_name.attributes:
                if atribute.name[1:-1] == geter and atribute.type not in types and len(class_with_geters.list_geters) - 1 == i:
                    raise Exception(f"Type of {geter} not suports arithmetic operation")
            if not found:
                for ref_prop in found_name.reference_properties:
                    if isinstance(ref_prop.name, str):
                        if ref_prop.name == geter and ref_prop.type not in types and len(
                                class_with_geters.list_geters) - 1 == i:
                            raise Exception(f"Type of {geter} not suports arithmetic operation")
                    else:
                        if ref_prop.name.name == geter and ref_prop.name.type not in types and len(class_with_geters.list_geters) - 1 == i:
                            raise Exception(f"Type of {geter} not suports arithmetic operation")

            i += 1
