import os
from .language.model import internal_classes
from .language.meta_model import *


from textx import metamodel_from_file,scoping, language


@language('model', '*.add')
def model_language():
    grammar_path = os.path.join(os.path.dirname(__file__), 'model.tx')
    object_checkers = {
        'Nelly': nelly_checker,
    }
    builtin_models_repository = scoping.ModelRepository()
    metamodel = metamodel_from_file(grammar_path, classes=internal_classes, #builtin_models=builtin_models_repository,
                                    global_repository=True)
    metamodel.register_obj_processors(object_checkers)
    return metamodel

