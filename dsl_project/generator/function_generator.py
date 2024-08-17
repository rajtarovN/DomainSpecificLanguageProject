from language.model import ClassWithGeters, Operations, ForFunction, Variable, IfStatement, Condition, \
    NewObjectStatement, ShortFunctions, Param
definde_var = {}
param_list = {}

def main_statement_generator(statements, generated_code, def_var, param_li):
    global definde_var, param_list
    if definde_var is not None:
        definde_var = def_var
    if param_list is not None:
        param_list = param_li

    for statement in statements:
        if isinstance(statement, IfStatement):

            generated_code = generate_if_statement(statement, generated_code, def_var, param_list)
        else:
            generated_code = generate_variable_statement(statement.statements[0], generated_code, statement, def_var,param_li)

    return generated_code


def generate_if_statement(statement, generated_code, def_var, param_list ):  # ovde moze biti problema sa get
    generated_code += " if(" + statement.conditions + "){"
    generated_code = main_statement_generator(statement.if_statements, generated_code,def_var, param_list )
    generated_code += "}"
    if statement.elifs is not None:
        i = 0
        for e in statement.elifs:
            generated_code += "else if(" + e + "){" + main_statement_generator(statement.elif_statements[i],
                                                                                 generated_code, param_list, def_var)
            generated_code += "}"
            i += 1
    if statement.else_statements is not None:
        generated_code += "else{" + main_statement_generator(statement.else_statements, generated_code, def_var, param_list) \
                          + "}"

    return generated_code


def generate_variable_statement(statement, generated_code, main_statemant,definde_var, param_list  ):
    type_var = ""
    if generated_code is None:
        generated_code = ""

    if main_statemant is not None:
        type_var = check_type_variable(main_statemant.name, definde_var, param_list)
    if isinstance(statement, NewObjectStatement):
        if type_var != "":
            generated_code += type_var + " " + main_statemant.name + "=" + generate_new_object(
                statement) + ";"
            return generated_code
        generated_code += main_statemant.type + " " + main_statemant.name + "=" + generate_new_object(statement) + ";"
        return generated_code
    elif isinstance(statement, Condition):
        if type_var != "":
            generated_code += type_var + " " + main_statemant.name + "=" + condition_generator(
                statement) + ";"
            return generated_code
        generated_code += main_statemant.type + " " + main_statemant.name + "=" + condition_generator(statement) + ";"
        return generated_code
    elif isinstance(statement, IfStatement):
        name_var = main_statemant.name
        if isinstance(main_statemant.name, ClassWithGeters):
            name_var = generate_chane_code(main_statemant.name, False)
        elif not isinstance(main_statemant.name, str):
            name_var = generated_code.name.name
        if name_var[-1] != "(":
            generated_code += main_statemant.type + " " + name_var + "="
        if main_statemant.type == "int" or main_statemant.type == "float":
            # todo proveri tipove da li takvi budu, i prosledi
            # neku matricu tipova
            generated_code += "0;"
        elif main_statemant.type == "boolean":
            generated_code += "true;"
        else:
            if name_var[-1] != "(":
                generated_code += ";"
            else:
                generated_code += generate_one_line_condition(statement, main_statemant.name, definde_var, param_list) + ";"
        return generated_code
    elif isinstance(statement, Operations):
        if type_var != "":
            if isinstance(main_statemant, ClassWithGeters):
                generated_code += generate_chane_code(main_statemant.name, False)  + generate_operation(
                    statement) + ");"
            else:
                generated_code += type_var + " " + main_statemant.name + "=" + generate_operation(
                    statement) + ";"
            return generated_code
        if main_statemant is not None:
            if isinstance(main_statemant.name, ClassWithGeters):
                generated_code += generate_chane_code(main_statemant.name, False)  + generate_operation(
                    statement) + ");"
            else:
                generated_code += main_statemant.type + " " + main_statemant.name + "=" + generate_operation(statement) + ";"
        else:
            generated_code +=generate_operation(statement)
        return generated_code
    elif isinstance(statement, ForFunction) or isinstance(statement, ShortFunctions):

        new_generated_text = short_func_generator(statement, main_statemant.name, 0) + ";"
        # generated_code += new_generated_text + "" + statement.type + " " + statement.name + "=" + name + ';'
        generated_code += new_generated_text
        return generated_code
    elif isinstance(statement, ClassWithGeters):
        if type_var != "":
            generated_code += type_var + " " + main_statemant.name + "=" + generate_chane_code(
                statement, True) + ";"
            return generated_code
        generated_code += main_statemant.type + " " + main_statemant.name + "=" + generate_chane_code(statement, True) \
                          + ";"
        return generated_code
    elif isinstance(statement, Variable):
        if type_var != "":
            generated_code += str(type_var) + " " + str(main_statemant.name) + "=" + str(statement) + ";"
            return generated_code
        generated_code += str(main_statemant.type) + " " + str(main_statemant.name) + "=" + str(statement) + ";"
        return generated_code
    elif isinstance(main_statemant, Variable) and \
            (isinstance(statement, str) or isinstance(statement, float) or isinstance(statement, int)) and type_var is not None:
        generated_code += type_var + " " + main_statemant.name + "=" + str(statement) + ";"
        return generated_code
    else:
        return generated_code


def generate_new_object(statement):
    generated_code = "new " + statement.class_name + "(" + generate_arguments(statement.args) + ")"
    return generated_code


def condition_generator(statement):
    negation = ""
    if statement.negation is not None:
        negation = "!"
    if isinstance(statement.left_side, ClassWithGeters):
        ls = generate_chane_code(statement.left_side, True)
    if isinstance(statement.left_side, Condition):
        ls = condition_generator(statement.left_side)
    else:
        ls = statement.left_side.name

    if isinstance(statement.right_side, ClassWithGeters):
        rs = generate_chane_code(statement.right_side, True)
    elif isinstance(statement.right_side, tuple):
        rs = str(statement.right_side[0])
    elif statement.right_side is None:
        rs = ""
    else:
        rs = statement.right_side.name
    operator = statement.operator
    if operator is None:
        operator = ""
    generated_code = negation + ls + operator + rs
    return generated_code


def generate_one_line_condition(statement, name, definde_var, param_list):
    # order.totalPrice=number>3?order.totalPrice-minorder.productsas:order.price;
    generated_code = "if(" + statement.conditions + "){"
    if isinstance(name, ClassWithGeters):
        name = generate_chane_code(name, False)
    elif isinstance(name, Param) or isinstance(name, Variable):
        name = name.name
    if name[-1] == "(": #ovdee
        # saving_value = "double savingVal = "
        for stmt in statement.if_statements:
            generated_code += generate_variable_statement(stmt, name, None, definde_var,param_list)
        generated_code += ");}"
    else:
        generated_code += name + " = " + generate_variable_statement(statement.if_statements, "", None, definde_var,param_list) + "}"
    if statement.else_statements is not None:
        if name[-1] == "(":
            generated_code += "else{" + generate_variable_statement(statement.else_statements[0], name, None, definde_var,param_list) + ");}"
        else:
            generated_code += "else{" + name + " = " + generate_variable_statement(statement.else_statements[0], name,
                                                                             None, definde_var, param_list) + ";}"

    return generated_code


def generate_operation(statement):
    def process_side(side):
        if isinstance(side, ClassWithGeters):
            return generate_chane_code(side, True)
        if isinstance(side, Variable):
            return side.name
        if isinstance(side, Param):
            return side.name
        if isinstance(side, tuple):
            return str(side[0])
        return str(side)

    left_side = process_side(statement.left_side)
    if isinstance(left_side, list) or isinstance(left_side, tuple):
        generated_code = left_side[0] + statement.operator
    else:
        generated_code = left_side + statement.operator
    right_side = statement.right_side[0]
    while isinstance(right_side, Operations):
        generated_code += process_side(right_side.left_side) + right_side.operator
        right_side = right_side.right_side[0]

    if isinstance(right_side, list):
        right_side = right_side[0]
    generated_code += process_side(right_side)
    return generated_code


def short_func_generator(statement, name, block=0, inter_code=""):
    if isinstance(statement, ShortFunctions):
        if statement.name == "for":
            return generate_for_func(statement, name)
        elif statement.name == "add":
            return generate_add_func(statement, block)
        elif statement.name == "sizeOf":
            return generate_size_func(statement, block)
        elif statement.name == "countFrom":
            return generate_count_in_func(statement, name, block, "")
        elif statement.name == "sumOf":
            return generate_sap_func(statement, name, block, "", "+")
        elif statement.name == "averageOf":
            return generate_sap_func(statement, name, block, "", "a")
        elif statement.name == "productOf":
            return generate_sap_func(statement, name, block, "", "*")
        elif statement.name == "allOf":
            return generate_all_any_none_func(statement, name, block, "true", "not", "false")
        elif statement.name == "anyOf":
            return generate_all_any_none_func(statement, name, block, "false", "", "true")
        elif statement.name == "noneOf":
            return generate_all_any_none_func(statement, name, block, "true", "", "false")
        elif statement.name == "selectIn":
            return generate_select_in_top_func(statement, name, block, "")
        elif statement.name == "selectTop":
            return generate_select_in_top_func(statement, name, block, "")
        elif statement.name == "min":
            return generate_min_max_func(statement, name, block, "<")
        elif statement.name == "max":
            return generate_min_max_func(statement, name, block, ">")
    return ""


def generate_sap_func(statement, name, block=0, id="", operation="a"):

    is_avg = operation == "a"
    if is_avg:
        operation = "+"
    new_var = "new_var" + str(block)
    generated_code = "double " + new_var + " = 0;"
    possible_list = statement.posible_list
    if isinstance(possible_list, ClassWithGeters):
        possible_list = generate_chane_code(possible_list, True)
    elif isinstance(possible_list, Variable) or isinstance(possible_list, Param):
        possible_list = possible_list.name
    elif not isinstance(possible_list, str):
        generated_code += "List<Object>  " + new_var + "1 = new ArrayList<>();"
        generated_code += generate_select_in_top_func(possible_list, new_var + "1", block + 1, "")
        possible_list = new_var + "1"

    # sumOf productsOnAction as: e usingFormula e.price*0.5*e.kolicina where e.id==o.id
    as_part =statement.as_part
    if isinstance(statement.as_part, ClassWithGeters):
        as_part = generate_chane_code(statement.as_part, True)
    elif isinstance(statement.as_part, Variable) or isinstance(statement.as_part, Param):
        as_part = statement.as_part.name

    generated_code += "for(Object " + as_part + " : " + possible_list + " ){"
    if statement.where_part is not None:
        generated_code += "if(" + condition_generator(statement.where_part) + "){"

    gen_code = as_part
    if statement.formula_part is not None:
        gen_code = generate_operation(statement.formula_part)
    generated_code += new_var + " = " + new_var + operation + "(" + gen_code + ")"

    if statement.where_part is not None:
        generated_code += "}"
    generated_code += "}"  # ovo je za for
    if is_avg:
        generated_code +="double " + name + "=" + new_var + "/" + possible_list + ".size();"
    else:
        generated_code += "double " + name + "=" + new_var + ";"
    return generated_code


def generate_select_in_top_func(statement, name, block=0, id=""):
    new_var = "new_var" + str(block)
    generated_code = "List<Object> " + new_var + " = new ArrayList<>;"
    possible_list = statement.posible_list
    if isinstance(possible_list, ClassWithGeters):
        possible_list = generate_chane_code(possible_list, True)
    elif not isinstance(possible_list, str):
        generated_code += "List<Object>  " + new_var + "1 = new ArrayList<>();"
        generated_code += generate_select_in_top_func(possible_list, new_var + "1", block + 1, "")
        possible_list = new_var + "1"
    as_part = statement.as_part
    if isinstance(statement.as_part, ClassWithGeters):
        as_part = generate_chane_code(statement.as_part, True)
    elif isinstance(statement.as_part, Variable) or isinstance(statement.as_part, Param):
        as_part = statement.as_part.name

    generated_code += "for(Object " + as_part + " : " + possible_list + " ){"
    if statement.where_part is not None:
        generated_code += "if(" + condition_generator(statement.where_part) + "){"

    gen_code = as_part
    generated_code += new_var + ".add(" + gen_code + ");"

    if statement.where_part is not None:
        generated_code += "}"
    generated_code += "}"  # ovo je za for
    if statement.number is not None:
        generated_code += "List<Object> " + name + "=" + new_var + ".stream().limit(" + statement.number + \
                          ").collect(Collectors.toList());"
        return generated_code
    generated_code +="List<Object> " + name + "=" + new_var + ";"
    return generated_code


def generate_count_in_func(statement, name, block=0, id=""):
    new_var = "new_var" + str(block)
    generated_code = "int " + new_var + " = 0;"
    possible_list = statement.posible_list
    if isinstance(possible_list, ClassWithGeters):
        possible_list = generate_chane_code(possible_list, True)
    elif isinstance(possible_list, Variable):
        possible_list = possible_list.name
    elif isinstance(possible_list, Param):
        # generated_code += "int " + new_var + "1 = 0;"
        possible_list = possible_list.name
    elif not isinstance(possible_list, str) and not isinstance(possible_list, Param):
        generated_code += "List<Object>  " + new_var + "1 = new ArrayList<>();"
        generated_code += generate_select_in_top_func(possible_list, new_var + "1", block + 1, "")
        possible_list = new_var + "1"
    as_part = statement.as_part
    if isinstance(statement.as_part, Variable):
        as_part = statement.as_part.name
    elif isinstance(statement.as_part, ClassWithGeters):
        as_part = generate_chane_code(statement.as_part, True)
    generated_code += "for(Object " + as_part + " : " + possible_list + " ){"

    if statement.where_part is not None:
        generated_code += "if(" + condition_generator(statement.where_part) + "){"

    generated_code += new_var + " = " + new_var + "+1;"

    if statement.where_part is not None:
        generated_code += "}"
    generated_code += "}"
    if name != "":
        generated_code += "int " + name + "=" + new_var + ";"
    return generated_code


# all true not flse, any false "" true, none true "" false
def generate_all_any_none_func(statement, name, block=0, first="true", not_op="", second="false"):
    new_var = "new_var" + str(block)
    generated_code = "boolean " + new_var + " = " + first + ";"
    possible_list = statement.posible_list
    if isinstance(possible_list, ClassWithGeters):
        possible_list = generate_chane_code(possible_list, True)
    elif not isinstance(possible_list, str):
        generated_code += "List<Object>  " + new_var + "1 = new ArrayList<>();"
        generated_code += generate_select_in_top_func(possible_list, new_var + "1", block + 1, "")
        possible_list = new_var + "1"
    as_part = statement.as_part
    if isinstance(statement.as_part, ClassWithGeters):
        as_part = generate_chane_code(statement.as_part, True)
    elif isinstance(statement.as_part, Param) or isinstance(statement.as_part, Variable):
        as_part = statement.as_part.name
    generated_code += "for(Object " + as_part + " : " + possible_list + " ){"

    generated_code += "if(" + not_op + "(" + condition_generator(statement.where_part) + ")){"
    generated_code += new_var + " = " + second + ";break;}}"

    generated_code += name + "=" + new_var + ";"
    return generated_code


def generate_min_max_func(statement, name, block=0, max_min=">"):
    new_var = "new_var" + str(block)
    generated_code = ""
    possible_list = statement.posible_list
    if isinstance(possible_list, ClassWithGeters):
        possible_list = generate_chane_code(possible_list, True)
    elif not isinstance(possible_list, str):
        generated_code += "List<Object>  " + new_var + "1 = new ArrayList<>();"
        generated_code += generate_select_in_top_func(possible_list, new_var + "1", block + 1, "")
        possible_list = new_var + "1"

    as_part = statement.as_part
    if isinstance(as_part, ClassWithGeters):
        as_part = generate_chane_code(as_part, True)
        generated_code += "if(" + possible_list + ".size()>0){Object " + new_var + " = " + possible_list + \
                          ".get(0);}else{Object " + new_var + " = null;}"
        generated_code += "for(Object " + statement.as_part.name + " : " + possible_list + " ){"
    else:
        generated_code += "if(" + possible_list + ".size()>0){double " + new_var + " = " + possible_list + \
                          ".get(0);}else{Object " + new_var + " = null;}"
        generated_code += "for(Object " + as_part + " : " + possible_list + " ){"
    if statement.where_part is not None:
        generated_code += "if(" + condition_generator(statement.where_part) + "){"

    # generated_code += new_var + "if(" + as_part + max_min + new_var + "){" todo pogledaj ovo
    generated_code += "if(" + as_part + max_min + new_var + "){"
    if isinstance(statement.as_part, ClassWithGeters):
        generated_code += new_var + " = " + statement.as_part.name + ";}}"
    else:
        generated_code += new_var + " = " + as_part + ";}}"
    generated_code += name + "=" + new_var + ";"
    return generated_code


def generate_size_func(statement, block=0):
    pl = statement.posible_list
    generated_code = ""
    if isinstance(pl, ClassWithGeters):
        return generate_chane_code(pl, True) + ".size()"
    elif not isinstance(pl, str):
        generated_code += "List<Object>  " + "new_var1 = new ArrayList<>();"
        generated_code += generate_select_in_top_func(pl, "new_var1", block + 1, "")
    return pl + ".size()"


# Add:        'add' 		  (ChaneId|ID)   'in' PosibleLists ( WherePart)?;

def generate_add_func(statement, block=0):
    possible_list = statement.posible_list
    generated_code = ""
    new_var = "new_var" + str(block)
    if isinstance(possible_list, ClassWithGeters):
        possible_list = generate_chane_code(possible_list, True)
    if isinstance(possible_list, Variable) or isinstance(possible_list, Param):
        possible_list = possible_list.name
    elif not isinstance(possible_list, str) and possible_list is not None:
        generated_code += "List<Object>  " + new_var + "1 = new ArrayList<>();"
        generated_code += generate_select_in_top_func(possible_list, new_var + "1", block + 1, "")
        possible_list = new_var + "1"

    value = statement.value
    if isinstance(value, ClassWithGeters):
        value = generate_chane_code(value, True)
    if isinstance(value, Variable) or isinstance(value, Param):
        value = value.name
    generated_code += possible_list + ".add(" + value + ");"
    return generated_code


def generate_for_func(statement, name, block=0):  # todo ovo prodji ponovo, nije dobro
    # todo for ne moze da ima select in za posible list, alid rugi mgu
    generated_code = ""
    possible_list = statement.posible_list
    if isinstance(possible_list, tuple) or isinstance(possible_list, list):
        possible_list = possible_list[0]
    if isinstance(possible_list, ClassWithGeters):
        possible_list = generate_chane_code(possible_list, True)

    as_part = statement.as_part
    if isinstance(as_part, ClassWithGeters):
        as_part = generate_chane_code(as_part, True)

    new_var = ""
    if statement.for_func is not None:
        init_value = 0
        if statement.for_func == "product":
            init_value = 1

        new_var = "new_var" + str(block)
        generated_code += "double " + new_var + " = " + str(init_value) + ";"

    generated_code += "for(Object " + statement.as_part.name + " : " + possible_list + " ){"
    inter_code = ""
    if statement.for_func is not None:

        if statement.for_func == "product":
            inter_code = new_var + " = " + new_var + "*"
        elif statement.for_func == "sum":
            inter_code = new_var + " = " + new_var + "+"
        else:
            inter_code = new_var + " = " + new_var + "*"
    generated_code += short_func_generator(statement.short_func, "", block + 1, inter_code) + "}"

    return generated_code


def generate_chane_code(statement, get_or_set):
    if not get_or_set and len(statement.list_geters) == 1:
        generated_code = statement.name + ".set"
    else:
        generated_code = statement.name + ".get"
    for get_part in statement.list_geters:
        generated_code += get_part[0].capitalize() + get_part[1:]
        if (not get_or_set) and statement.list_geters[-1] == get_part:
            generated_code += "("
        else:
            generated_code += "()"
        if statement.list_geters.index(get_part) != len(statement.list_geters) - 1:
            if not get_or_set and len(statement.list_geters) - 2 == statement.list_geters.index(get_part):
                generated_code += statement.name + ".set"
                continue
            generated_code += ".get"
    return generated_code


def generate_arguments(arguments):
    generated = ""
    i = 0
    for arg in arguments:
        if isinstance(arg, ClassWithGeters):
            generated += generate_chane_code(arg, True)
        elif isinstance(arg, list):
            generated += generate_arguments(arg)
        else:
            generated += arg
        if i == len(arguments) - 1:
            break
        generated += ","
        i += 1
    return generated


def check_type_variable(name,  definde_var, param_list):
    if name in param_list.keys():
        if isinstance(param_list[name],list):
            return param_list[name][0][0].type
        else:
            return param_list[name].type
    if name in definde_var:
        if isinstance(definde_var[name], list):
            return definde_var[name][0][0].type
        return definde_var[name][0][0].type  # todo
    return ""
