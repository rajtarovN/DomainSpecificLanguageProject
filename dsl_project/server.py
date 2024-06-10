import os
from flask import Flask, request, jsonify
from textx import metamodel_from_file, scoping
from language.model import internal_classes
from language.meta_model import nelly_checker
from language.meta_model_functions import sent_func_checker
from generator.function_generator import main_statement_generator
import traceback
app = Flask(__name__)

def create_metamodel():
    grammar_path = os.path.join(os.path.dirname(__file__), 'model.tx')
    object_checkers = {
        'Model': sent_func_checker,
    }
    builtin_models_repository = scoping.ModelRepository()
    metamodel = metamodel_from_file(grammar_path, classes=internal_classes, global_repository=True)
    metamodel.register_obj_processors(object_checkers)
    return metamodel

@app.route('/api/metamodel', methods=['GET'])
def get_metamodel():
    metamodel = create_metamodel()
    return jsonify({"message": "Metamodel initialized", "metamodel": str(metamodel)})

@app.route('/api/process', methods=['POST'])
def process_model():
    data = request.json
    if not data or 'model_content' not in data:
        return jsonify({"error": "Invalid input"}), 400

    model_content = data['model_content']
    # classes_names = data['classes_names']
    try:
        metamodel = create_metamodel()
        model = metamodel.model_from_str(model_content)
        param_li = {}
        param_li["person"] = 'person'
        param_li["bill"] = 'bill'
        output_txt =main_statement_generator(model[0].statements, "", model[1], param_li)
        result = {
            "message": "Model processed successfully",
            "model": output_txt
        }
        return jsonify(result)
    except Exception as e:
        traceback.print_exc()
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)


# {
#     "model_content": "{int order = 5;int productsOnAction=8;int number apply for order.products as: o sumOf productsOnAction as: e formula e.price/2*e.kolicina where e.id==o.id;}"
# }