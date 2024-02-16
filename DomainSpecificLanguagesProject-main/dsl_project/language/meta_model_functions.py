from .model import Files, Function, Param, IfStatement, Variable, ForStatement, ReturnStatement, CallFunctionStatement, \
    ShortFunction, NewObjectStatement, CopyName, Operation, Condition, ClassWithGeters
import copy

function_names = []
short_f_names = ["add", "size", "count", "all", "any", "none", "min", "max", "remove", "clear", "index"]
defined_classes = ["User", "Proizvodi", "Porudzbina"]
parameters_list = {}
current_block = ""


def file_checker(text):
    global function_names, parameters_list, current_block
    name, functions_text = text.split("[")
    functions = []
    types = ('String', 'int', 'float', 'boolean', 'byte', 'short', 'long', 'double', 'char')
    for ft in functions_text.split('def')[1:]:  # prvo pronadjem imena svih fja
        parts = ft.split("(")
        ret_type = next((t for t in types if t in parts[0]), "")
        function_names.append(parts[0].split(ret_type)[1])
    num_blocks = 0
    for ft in functions_text.split('def')[1:]:
        num_blocks += 1
        current_block = "" + str(num_blocks)
        parameters_list = {}
        parts = ft.split("(")
        ret_type = next((t for t in types if t in parts[0]), "")
        if ret_type == "":
            raise Exception("Type of function isnt all right")

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
                    raise Exception("Type of param isnt all right")

            name_param = p.split(type_param)[1]
            if ")" in name_param:
                name_param = name_param.split(")")[0]
            parameters.append(Param(name_param, type_param))
            if found:
                break

        name_func = parts[0].split(ret_type)[1]
        for p in parameters:
            parameters_list[p.name] = p
        text1 = "{".join(ft.split("{")[1:])
        if text1.endswith("}]"):
            text1 = text1[:-2]
        else:
            text1 = text1[:-1]
        statements = statements_checker(text1, {})

        functions.append(Function(name_func, statements, copy.deepcopy(parameters), ret_type))

    file1 = Files(name, functions)
    return file1


def copy_name(lines, line, defined_variables):
    global short_f_names
    if "#." in line:
        text = line
        for s in short_f_names:
            if s in text:
                return short_function_checker(line, defined_variables)
        if ".for(" in ";".join(line).split("#")[2]:
            return for_in_statement_checker(lines, line, CopyName(text), defined_variables)
    else:
        text = ";".join(line).split("#")[1]
        return CopyName(text)


def check_if_its_variable(line):
    for i in range(len(line)):
        if i + 1 < len(line):
            if line[i] == "=" and line[i + 1] == "=":
                return False
            elif line[i] == "=" and line[i + 1] != "=":
                return True
    return False

    # var je kljuc, variable je def_v[key]


def find_index_of_operations(line):
    log_simbols = ('&&', '||', '=>','<=','>',  '<',  '==', '!=')
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


def check_is_short_func(line, var, variable, short_f_name, defined_variables, lines, list_statements, num_block,
                        line_num):
    global current_block
    if "=" in line:
        ind = line.find("=")
    else:
        ind = -2
    ind2 = find_index_of_operations(line)
    if line.startswith(var + "." + short_f_name + "("):
        if (ind != -2 and ind < line.find(short_f_name+"(")) or (ind2 != -2 and ind2 < line.find(short_f_name+"(")):
            return False
        list_statements.append(short_function_checker(line, defined_variables))
        return True
    if line.startswith(var + ".") and any([l for l in line.split(".") if l.startswith(short_f_name)]):
        if (ind != -2 and ind < line.find(short_f_name+"(")) or (ind2 != -2 and ind2 < line.find(short_f_name+"(")):
            return False
        list_statements.append(short_function_checker(line, defined_variables))
        return True

    elif line.__contains__(var + ".for(") or (line.startswith(var + ".") and
                                              any([l for l in line.split(".") if l.startswith(short_f_name)])):
        if (ind != -2 and (ind < line.find(".for(") or ind < line.find(short_f_name+"("))) or \
                (ind2 != -2 and ind2 < line.find(short_f_name+"(")):
            return False

        old_curent = current_block
        num_block += 1
        current_block = ":" + str(num_block)
        list_statements.append(
            for_in_statement_checker(lines[line_num:], line, variable, defined_variables))
        current_block = old_curent
        return True
    return False


def check_if_contains_dot(left_side, definde_variables):
    if "." in left_side:
        return left_side.split(".")[0] in definde_variables.keys() or left_side.split(".")[0] in parameters_list.keys()
    return False


def check_if_condition_or_operation(line, definde_variables, condition):
    if condition:
        simbols = ('&&', '||', '=>','<=','>',  '<',  '==', '!=')
    else:
        simbols = ('+', '-', '*', '/', '%', '^')

    for s in simbols:
        rr = line.split(s)[0]
        if s in line:
            left_side = line.split(s)[0]
            if left_side in definde_variables.keys():
                if is_var_in_parent_block(definde_variables[left_side][1]):
                    return True
            elif line.split(s)[0] in definde_variables.keys():
                if is_var_in_parent_block(line.split(s)[0][1:]):
                    return True
            elif left_side in parameters_list.keys() or check_if_string_value(left_side) or \
                    check_type(left_side)[1] or check_if_contains_dot(left_side, definde_variables):
                return True
    return False


def statements_checker(text, defined_variables):
    global short_f_names, current_block
    num_block = 0
    lines = text.split(";")
    line_num = 0
    list_statements = []
    while line_num < len(lines):

        line = lines[line_num]
        call_f_s = False  # call
        if line == "" or line == ")" or line == "}":
            line_num += 1
            continue
        for name in function_names:  # poyiv posdtojece fje, fali mi za poziv iy repoa, mozda neka oznaka ya to
            if line.startswith(name + "("):
                list_statements.append(call_functon_checker(line, name, defined_variables))
                call_f_s = True
                break
        if call_f_s:
            line_num += 1
            continue
        found = False
        for var in defined_variables.keys():  # short
            for short_f_name in short_f_names:
                found_var = find_var_in_defined_variables(var, defined_variables)
                if check_is_short_func(line, var, found_var, short_f_name, defined_variables, lines,
                                       list_statements, num_block, line_num):
                    text = ";".join(lines[line_num:])
                    index_in_text = find_bracket(text, "(")
                    lines = text[index_in_text + 1:].split(";")
                    line_num = 0
                    found = True
                    break
            if found:
                break
                #ovo ne radi za p.ukupnaCena=p.ukupnaCena-p.prooizvodi.min(cena);
        for var in parameters_list.keys():  # short
            for short_f_name in short_f_names:
                if check_is_short_func(line, var, parameters_list[var], short_f_name, defined_variables, lines,
                                       list_statements, num_block, line_num):
                    found = True
                    text = ";".join(lines[line_num:])
                    index_in_text = find_bracket(text, "(")
                    print(text)
                    print(index_in_text)
                    lines = text[index_in_text + 1:].split(";")
                    line_num = 0
                    break
            if found:
                break
        if found:
            continue

        found = False
        for cl in defined_classes:  # new obj
            if line.startswith("new" + cl.upper() + "("):
                list_statements.append(new_object_checker(line, cl, defined_variables))
                found = True
                break
        if found:
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
        elif line.startswith("for"):
            old_curent = current_block
            num_block += 1
            current_block = ":" + str(num_block)
            list_statements.append(for_statement(lines[line_num:], line, defined_variables))
            text = ";".join(lines[line_num:])
            index_in_text = find_bracket(text, "{")
            lines = text[index_in_text + 1:].split(";")
            line_num = 0
            current_block = old_curent
            continue
        elif line.startswith("return"):
            list_statements.append(return_checker(line, defined_variables))
        elif line.startswith("#") and line.count("#") == 2:
            list_statements.append(copy_name(lines, line, defined_variables))
            text = ";".join(lines).split("#")[2]
            if text.startswith("."):
                ind = find_bracket(text, "(")
                text = text[ind + 1:]

            lines = text.split(";")
            line_num = 0
            continue
        elif check_if_its_variable(line):  # preostaje da je samo variabla
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
                if is_var_in_parent_block(defined_variables[part]):
                    found_var = find_var_in_defined_variables(part, defined_variables)
                    v = ClassWithGeters(found_var, new_parts)
            else:
                v = ClassWithGeters(parameters_list[part], new_parts)
            list_statements.append(v)
        else:
            raise Exception("Nothing found" + str(line))
        line_num += 1
    return list_statements


def operation_checker(line, defined_variables):
    operations = ['-', '*', '/', '%', '^', '+']
    parsed_line = parse_line(line, operations)
    left_side = parsed_line[0]
    # todo podizati greske
    ls = ""
    for sfn in short_f_names:
        if sfn in left_side:
            ls = short_function_checker(left_side, defined_variables)
    if left_side in defined_variables.keys():
        if is_var_in_parent_block(defined_variables[left_side][1]):
            found_var = find_var_in_defined_variables(left_side, defined_variables)
            ls = found_var
    elif left_side in parameters_list.keys():
        ls = parameters_list[left_side]
    elif left_side.startswith("#") and left_side.endswith("#"):
        ls = CopyName(left_side)
    elif "(" in left_side and ")" in left_side:
        for n in function_names:
            if left_side.startswith(n):
                ls = call_functon_checker(left_side, n, defined_variables)
                break
    elif check_if_contains_dot(left_side, defined_variables):
        lef = left_side.split(".")[0]
        left_s = left_side.split(".")[1:]
        if lef in defined_variables.keys():
            if is_var_in_parent_block(defined_variables[lef][1]):
                found_var = find_var_in_defined_variables(lef, defined_variables)
                ls = ClassWithGeters(found_var, left_s)
        elif lef in parameters_list.keys():
            ls = ClassWithGeters(parameters_list[lef], left_s)
    elif check_if_string_value(left_side):
        ls = left_side
    else:
        ct_value = check_type(left_side)
        if ct_value[0] is not None:
            ls = ct_value[1], None
    operator = parsed_line[1]
    right_side = operator.join(line.split(operator)[1:])
    result_right_side = None
    if right_side in defined_variables.keys():
        if is_var_in_parent_block(defined_variables[right_side][1]):
            found_var = find_var_in_defined_variables(right_side, defined_variables)
            result_right_side = found_var
    elif right_side in parameters_list.keys():
        result_right_side = parameters_list[right_side]
    else:
        print(right_side)
        result_right_side = statements_checker(right_side, defined_variables)
    return Operation(ls, operator, result_right_side)


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
        right_side = check_one_condition(values[2], defined_variables)
    else:
        operator = None
        right_side = None

    return Condition(left_operator, operator, right_side, negation)


def return_from_def_var_or_parameter_list(first_part, defined_variables):
    if first_part in defined_variables.keys():
        if is_var_in_parent_block(defined_variables[first_part][1]):
            return find_var_in_defined_variables(first_part, defined_variables)
    elif first_part in parameters_list.keys():
        return parameters_list[first_part]


def check_part_condition(first_part, defined_variables):
    for sfn in short_f_names:
        if sfn in first_part:
            ls = short_function_checker(first_part, defined_variables)
            return ls
    ls = None

    if first_part in defined_variables.keys() or first_part in parameters_list.keys():
        ls = return_from_def_var_or_parameter_list(first_part, defined_variables)
    elif first_part.startswith("#") and first_part.endswith("#"):
        ls = CopyName(first_part)
    elif "(" in first_part and ")" in first_part:
        for n in function_names:
            if first_part.startswith(n):
                ls = call_functon_checker(first_part, n, defined_variables)
                break
    elif check_if_contains_dot(first_part, defined_variables):
        first_part = first_part.split(".")[0]
        if first_part in defined_variables.keys() or first_part in parameters_list.keys():
            ls = ClassWithGeters(return_from_def_var_or_parameter_list(first_part, defined_variables), first_part.split(".")[1:])
    elif check_if_string_value(first_part):
        ls = first_part
    else:
        ct_value = check_type(first_part)
        if ct_value[0] is not None:
            ls = ct_value[1], None
    return ls


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


def one_line_condition_checker(line, defined_variables):
    left_side, right_side = line.split("?")
    if_statement, else_statement = None, None

    if ":" in right_side:
        if_statement, else_statement = right_side.split(":")
        else_statement = check_if_statement(else_statement, defined_variables)
    else:
        if_statement = right_side

    if_statement = check_if_statement(if_statement, defined_variables)

    return IfStatement(left_side, if_statement, None, None, else_statement)


def check_if_statement(if_statement, defined_variables):
    if if_statement in defined_variables.keys():
        if is_var_in_parent_block(defined_variables[if_statement][1]):
            found_var = find_var_in_defined_variables(if_statement, defined_variables)
            if_statement = found_var
    elif if_statement in parameters_list.keys():
        if_statement = parameters_list[if_statement][0]
    elif check_if_contains_dot(if_statement, defined_variables):
        part = if_statement.split(".")[0]
        parts = if_statement.split(".")[1:]
        if is_var_in_parent_block(defined_variables[part][1]):
            found_var = find_var_in_defined_variables(part, defined_variables)
            if_statement = ClassWithGeters(found_var, parts)
        elif part in parameters_list.keys():
            if_statement = ClassWithGeters(parameters_list[part][0], parts)
    elif check_type(if_statement)[0] is not None:
        if_statement = check_type(if_statement)[1]
    elif check_if_string_value(if_statement) and not if_statement.__contains__("+"):
        if_statement = if_statement  # ostaje string
    else:
        if_statement = statements_checker(if_statement, defined_variables)

    return if_statement


def new_object_checker(line, cl, defined_variables):
    string_args = line.split("(")[1][:-1].split(",")
    arguments = arguments_checker(string_args, defined_variables)
    return NewObjectStatement(cl, arguments)


def for_in_statement_checker(lines, line, var, defined_variables):
    # todo i ovde ce trebati
    list_name = var
    iterator = line.split("for(")[1].split("->")[0].split("=")[0]
    text = ";".join(lines)
    length_first_line = len(line.split("{")[0])

    index_in_text = find_bracket(text, "{")
    statements = statements_checker(text[length_first_line:index_in_text + 1][1:-1], defined_variables)
    if isinstance(var, str):
        if check_if_contains_dot(list_name, defined_variables):
            name = list_name.split(".")[0]
            if name in defined_variables.keys():
                if is_var_in_parent_block(defined_variables[name]):
                    list_atr = list_name
                    found_var = find_var_in_defined_variables(name, defined_variables)
                    list_name = ClassWithGeters(found_var, list_name.split(".")[1:])
            elif name in parameters_list.keys():
                list_atr = list_name
                list_name = ClassWithGeters(parameters_list[name], list_name.split(".")[1:])
    return ForStatement(iterator, None, None, statements, list_name)


def return_checker(line, defined_variables):
    global function_names
    right_side = line.split("return")[1]
    for name in function_names:
        if right_side.startswith(name + "("):
            statement = call_functon_checker(right_side, name, defined_variables)
            return ReturnStatement(None, statement)
    if right_side.startswith("#") and right_side.endswith("#"):
        return CopyName(right_side)
    elif right_side in defined_variables.keys():
        if is_var_in_parent_block(defined_variables[right_side][1]):
            found_var = find_var_in_defined_variables(right_side, defined_variables)
            return ReturnStatement(found_var, None)
    elif check_if_contains_dot(right_side, defined_variables):
        part = right_side.split(".")[0]
        parts = right_side.split(".")[1:]
        if is_var_in_parent_block(defined_variables[part][1]):
            found_var = find_var_in_defined_variables(part, defined_variables)
            return ReturnStatement(ClassWithGeters(found_var, parts), None)
        if is_var_in_parent_block(parameters_list[part][1]):
            return ReturnStatement(ClassWithGeters(parameters_list[part][0], parts), None)
    elif right_side in parameters_list.keys():
        return ReturnStatement(parameters_list[right_side], None)
    if check_if_string_value(right_side) and not right_side.__contains__("+"):
        return ReturnStatement(right_side, None)
    ct_value = check_type(right_side)
    if ct_value[0] is not None:
        return ReturnStatement(ct_value[1], None)

    statement = statements_checker(right_side, defined_variables)
    return ReturnStatement(None, statement)


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
    old_text = ";".join(lines)[len(condition) - 3 + index_in_text:]
    text = old_text
    while "elif" in text[:4]:
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

    return IfStatement(condition, list_statements_if, elif_conditions, elif_statements, else_statements)


def is_var_in_parent_block(list_blocks):
    for b in list_blocks:
        if current_block[1:].startswith(b) or current_block.startswith(b):
            return True
    return False


def variable_checker(line,
                     defined_variables):  # obavezno dodati u recnik variablu, podici gresku ako postji i ako ne postoji
    types = ('String', 'int', 'float', 'boolean', 'byte', 'short', 'long', 'double', 'char')
    var_type = next((t for t in types if line.startswith(t)), "")
    if var_type == "" and (
            line.split("=")[0] not in defined_variables.keys() and line.split("=")[0] not in parameters_list.keys() and
            "." not in line.split("=")[0]):
        return line
    if var_type != "":
        right_side = "=".join(line.split(var_type)[1].split("=")[1:])
    else:
        right_side = "=".join(line.split("=")[1:])

    value = check_type(right_side)
    if value[0] is not None:
        value = value[1]
    else:
        if check_if_string_value(right_side):
            value = right_side

    if var_type != "":
        name = line.split(var_type)[1].split("=")[0]
        if name in parameters_list.keys():
            raise Exception("already defined variable in this block" + name)
        elif name in defined_variables.keys():
            for b in defined_variables[name]:
                if b == current_block or is_var_in_parent_block(defined_variables[name][1]):
                    raise Exception("already defined variable in this block" + name)
    else:
        if "." in line.split("=")[0] and check_if_contains_dot(line.split("=")[0], defined_variables):
            name = line.split("=")[0].split(".")[0]
            rs_list = line.split("=")[0].split(".")[1:]
            if name in defined_variables.keys():
                if is_var_in_parent_block(defined_variables[name][1]):
                    found_var = find_var_in_defined_variables(name, defined_variables)
                    name = ClassWithGeters(found_var, rs_list)
            elif name in parameters_list.keys():
                name = ClassWithGeters(parameters_list[name], rs_list)
        else:
            name = line.split("=")[0]
            if name not in defined_variables.keys() or name in parameters_list.keys():
                raise Exception("variable is not defined" + name)
            elif name in defined_variables.keys():
                found = False
                for b in defined_variables[name]:
                    if b == current_block or is_var_in_parent_block(defined_variables[name][1]):
                        # ako mi se ne nalazi u sadasnjem ili roditeljskom bloku
                        found = True
                        break
                if not found:
                    d = current_block
                    raise Exception("variable is not defined" + name)
    statements = statements_checker(right_side, defined_variables)
    var = Variable(name, var_type, value, statements)
    defined_variables[name] = [[var], [current_block]]
    return var


def call_functon_checker(right_side, name, defined_variables):
    string_args = right_side.split("(")[1][:-1].split(",")
    arguments = arguments_checker(string_args, defined_variables)
    return CallFunctionStatement(name, arguments)


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


def short_function_checker(line, defined_variables):
    if line.startswith("#"):
        list_name = CopyName(line.split("#")[1])

        parts = line.split("#")[2].split(".")
    else:
        parts = line.split("(")[0].split(".")
        list_name = ""
        for name in parameters_list.keys():
            if name == parts[0]:
                list_name = name
        for name in defined_variables.keys():  # poyiv posdtojece fje, fali mi za poziv iy repoa, mozda neka oznaka ya to
            if is_var_in_parent_block(defined_variables[name][1]):
                if name == parts[0]:
                    list_name = name
        if list_name == "":
            raise Exception("list name of function is empty string")
    func_name = parts[-1]
    if "(" in func_name:
        func_name = func_name.split("(")[0]
    value = None
    condition = None
    if func_name == "min" or func_name == "max":
        value = line.split("(")[1][:-1]
    elif func_name == "count" or func_name == "all" or func_name == "any" or func_name == "none":
        condition = line.split("(")[1][:-1]
    else:
        v = check_type(line.split("(")[1][:-1])
        if check_if_string_value(line.split("(")[1][:-1]):
            value = line.split("(")[1][:-1]
        elif v[0] is not None:
            value = v[1]
        else:
            condition = statements_checker(line.split("(")[1].split(")")[0], defined_variables)
            value = condition
    if not line.startswith("#") and len(line.split("(")[0].split(".")) > 2:
        print(line.split("(")[0].split(".")[1:])
        return ShortFunction(list_name, ClassWithGeters(func_name, line.split("(")[0].split(".")[1:]), condition, value)
    return ShortFunction(list_name, func_name, condition, value)


def check_if_string_value(value):
    if (value.startswith("'") and value.endswith("'") and value.count("'") == 2) or (
            value.startswith('"') and value.endswith('"') and value.count('"') == 2):
        return True
    else:
        return False


def for_statement(lines, line, defined_variables):
    first_line = line.split("for")[1].split("{")[0]
    parts = first_line.split(",")

    if parts[0].startswith("int"):
        typs = "int"
    else:
        typs = "float"
    name = parts[0].split("=")[0].split(typs)[1]
    value = parts[0].split("=")[1]
    found = None
    if name in defined_variables.keys():
        if is_var_in_parent_block(defined_variables[name][1]):
            raise Exception("Iterator already exists" + name)
        else:
            defined_variables[name][1].append(current_block)
            iterator_v = Variable(name, typs, value, None)
            defined_variables[name][0].append(iterator_v)
            found = iterator_v
    elif name in parameters_list.keys():
        raise Exception("Iterator already exists " + name)
    else:
        iterator_v = Variable(name, typs, value, None)
        defined_variables[name] = [[iterator_v], [current_block]]
        found = iterator_v

    if found is not None:
        iterator_v = found
    else:
        iterator_v = Variable(name, typs, value, None)
        defined_variables[name] = [[iterator_v], [current_block]]

    conditions = parts[1]
    increment = variable_checker(parts[2], defined_variables)
    text = ";".join(lines)
    length_first_line = len(line.split("{")[0])
    index_in_text = find_bracket(text, "{")
    statements = statements_checker(text[length_first_line + 1:index_in_text], defined_variables)

    return ForStatement(iterator_v, conditions, increment, statements, None)


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


def find_var_in_defined_variables(name, defined_variables):
    variables = defined_variables[name][0]
    blocks = defined_variables[name][1]
    ind = 0
    for b in blocks:
        if current_block[1:].startswith(b) or current_block.startswith(b):
            return variables[ind]
        ind += 1
