class Nelly(object):
    def __init__(self,  name, classes, enums, files):
        self.name = name
        self.classes = classes
        self.enums = enums
        self.files = files

    def __str__(self):
        return self.name
class Class(object):
    def __init__(self, name, attributes):#parent, name, attributes):
        # self.parent = parent
        self.name = name
        self.attributes = attributes

    def __str__(self):
        return self.name

class Attribute(object):
    def __init__(self, name, type):#parent, name, type):
        # self.parent = parent
        self.name = name
        self.type = type

    def __str__(self):
        return self.name

class Enum(object):
    def __init__(self, name, values):
        # self.parent = parent
        self.name = name
        self.values = values


    def __str__(self):
        return self.name


class OneToOne(object):
    def __init__(self, **kwargs):
        print("oo\n")
        print(kwargs)
        # self.firstName = firstName
        # self.secondName = secondName

    def __str__(self):
        return self.firstName

class OneToMany(object):
    def __init__(self, **kwargs):
        print("om\n")
        print(kwargs)
        # self.firstName = firstName
        # self.secondName = secondName

    def __str__(self):
        return self.firstName

class ManyToOne(object):
    def __init__(self, **kwargs):
        print("mo\n")
        print(kwargs)
        # self.firstName = firstName
        # self.secondName = secondName

    def __str__(self):
        return self.firstName

class ManyToMany(object):
    def __init__(self, **kwargs):
        print("enum\n")
        print(kwargs)
        # self.firstName = firstName
        # self.secondName = secondName

    def __str__(self):
        return self.firstName

class Files(object):
    def __init__(self, name,functions):
        self.functions = functions
        self.name = name

    def __str__(self):
        return self.name

class Function(object):
    def __init__(self, name, statements, params, return_type):
        self.statements = statements
        self.params = params
        self.name = name
        self.return_type =return_type

    def __str__(self):
        return self.name

class Param(object):
    def __init__(self, name, type):
        self.name = name
        self.type = type

    def __str__(self):
        return self.name
class Statement(object):
    def __init__(self):
        pass
class ForStatement(Statement): #ovo moze za oba fora
    def __init__(self, iterator, condition, increment, statements, list_name):
        super().__init__()
        self.iterator = iterator
        self.condition = condition
        self.increment = increment
        self.statements = statements
        self.list_name = list_name

class Variable(object): #neide ovo
    def __init__(self, name, type, value, statements):
        self.name = name
        self.type = type
        self.value = value
        self.statements = statements

class ShortFunction(Statement):
    def __init__(self, list_name, name, condition, value): #value moze da bude i drugo ono posible
        super().__init__()
        self.name = name # da li je add remve...
        self.list_name = list_name
        self.condition = condition
        self.value = value

class IfStatement(Statement):
    def __init__(self, conditions, if_statements, elifs, elif_statements, else_statements):
        super().__init__()
        self.conditions = conditions
        self.if_statements = if_statements
        self.elifs = elifs
        self.elif_statements = elif_statements
        self.else_statements = else_statements

class CallFunctionStatement(Statement):
    def __init__(self, f_name, arguments):
        super().__init__()
        self.arguments= arguments
        self.f_name = f_name

class ReturnStatement(Statement):
    def __init__(self, value, command):
        super().__init__()
        self.command = command
        self.value = value

class NewObjectStatement(Statement):
    def __init__(self, class_name, args):
        super().__init__()
        self.class_name = class_name
        self.args = args

class Operation(Statement):
    def __init__(self, left_side, operator, right_side):
        super().__init__()
        self.left_side = left_side
        self.operator = operator
        self.right_side = right_side

class Condition(Statement):
    def __init__(self, left_side, operator, right_side, negation=None):
        super().__init__()
        self.negation = negation
        self.left_side = left_side
        self.operator = operator
        self.right_side = right_side

class CopyName(Statement):
    def __init__(self, name):
        super().__init__()
        self.name = name

class ClassWithGeters():
    def __init__(self, variable, list_geters):
        self.variable = variable
        self.list_geters = list_geters

internal_classes = [
    Nelly,
    Class,
    Attribute,
    Enum,
    OneToOne,
    OneToMany,
    ManyToOne,
    ManyToMany,
    Files,
    Function,
    Param,
    Statement,
    ForStatement,
    Variable,
    ShortFunction,
    IfStatement,
    CallFunctionStatement,
    ReturnStatement,
    Operation,
    CopyName,
    Operation,
    ClassWithGeters
]