class Model(object):
    def __init__(self,  name, classes, enums, files, relations):
        self.name = name
        self.classes = classes
        self.enums = enums
        self.files = files
        self.relations = relations

    def __str__(self):
        return self.name


class Class(object):
    def __init__(self, name, attributes, anotation, extends_user=False, role=None):#parent, name, attributes):
        # self.parent = parent
        self.name = name
        self.attributes = attributes
        self.reference_properties = []
        self.anotation = anotation
        self.extends_user = extends_user
        self.role = role

    def add_ref_property(self, property):
        self.reference_properties.append(property)

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
    def __init__(self, list_couple):
        self.list_couple = list_couple

    def __str__(self):
        s = "One to One:"
        for c in self.list_couple:
            if isinstance(c, list):  # Use isinstance() for type checking
                for co in c:
                    s += str(co) + " "  # Convert co to string before concatenation
            else:
                s += str(c)
        return s

class OneToMany(object):
    def __init__(self, list_couple):
        self.list_couple = list_couple

    def __str__(self):
        s = "One to many"
        for c in self.list_couple:
            if isinstance(c, list):  # Use isinstance() for type checking
                for co in c:
                    s += str(co) + " "  # Convert co to string before concatenation
            else:
                s += str(c)
        return s


class ManyToOne(object):
    def __init__(self, list_couple):
        self.list_couple = list_couple

    def __str__(self):
        s = "Many to One"
        for c in self.list_couple:
            if isinstance(c, list):  # Use isinstance() for type checking
                for co in c:
                    s += str(co) + " "  # Convert co to string before concatenation
            else:
                s += str(c)
        return s


class ManyToMany(object):
    def __init__(self, list_couple):
        self.list_couple = list_couple

    def __str__(self):
        s = "Many to Many"
        for c in self.list_couple:
            if isinstance(c, list):  # Use isinstance() for type checking
                for co in c:
                    s += str(co) + " "  # Convert co to string before concatenation
            else:
                s += str(c)
        return s

class LinkProperty:
    def __init__(self, name, type_p, upper, cardinality, fetch_type, cascade_type, mapped_by, as_part):
        self.name = name
        self.type = type_p
        self.upper = upper
        self.cardinality = cardinality
        self.fetch_type = fetch_type
        self.cascade_type = cascade_type
        self.mapped_by = mapped_by
        self.as_part = as_part
    def __str__(self):
        return "ttt "+self.as_part

class Anotation(object):
    def __init__(self, name):
        self.name = name

class ByingAnotation(Anotation):
    def __init__(self, how_much):
        super().__init__("bying")
        self.how_much = how_much

class BillAnotation(Anotation):
    def __init__(self, call_actions):
        super().__init__("bill")
        self.call_actions = call_actions

class ActionAnotation(Anotation):
    def __init__(self, name_f ):#params
        super().__init__("action")
        self.name_f = name_f
        # self.params = params




class Function(object):
    def __init__(self, name, statements, params):
        self.statements = statements
        self.params = params
        self.name = name

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


class Variable(object): #neide ovo
    def __init__(self, name, type, value, statements):
        self.name = name
        self.type = type
        self.value = value
        self.statements = statements


class ShortFunctions(Statement):
    def __init__(self, posible_list, name, where_part, as_part, value, number, formula_part):
        super().__init__()
        self.name = name # da li je add remve...
        self.posible_list = posible_list
        self.where_part = where_part
        self.as_part = as_part
        self.value = value
        self.number = number
        self.formula_part = formula_part


class ForFunction(ShortFunctions):
    def __init__(self, posible_list, name, where_part, as_part, value, number, formula_part, set_operation, for_func, short_func):
        super().__init__(posible_list, name, where_part, as_part, value, number, formula_part)
        self.set_operation = set_operation
        self.for_func = for_func
        self.short_func = short_func


class IfStatement(Statement):
    def __init__(self, conditions, if_statements, elifs, elif_statements, else_statements):
        super().__init__()
        self.conditions = conditions
        self.if_statements = if_statements
        self.elifs = elifs
        self.elif_statements = elif_statements
        self.else_statements = else_statements

class NewObjectStatement(Statement):
    def __init__(self, class_name, args):
        super().__init__()
        self.class_name = class_name
        self.args = args


class Operations(Statement):
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

class ClassWithGeters():
    def __init__(self, variable, list_geters):
        if isinstance(variable, Variable):
            self.name = variable.name
        else:
            self.name = variable
        self.variable = variable
        self.list_geters = list_geters

internal_classes = [
    Model,
    Class,
    Attribute,
    Enum,
    OneToOne,
    OneToMany,
    ManyToOne,
    ManyToMany,
    Function,
    Param,
    Statement,
    Variable,
    ShortFunctions,
    IfStatement,
    Operations,
    Anotation
]