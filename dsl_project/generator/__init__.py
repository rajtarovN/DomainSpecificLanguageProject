from .generator import generate
from textx import generator

from os.path import dirname


@generator("model", "txt")
def field_generator(metamodel, model, output_path, overwrite, debug):
   # overwrite = true, debug = True
    tx_filename = model._tx_filename
    output_dir = output_path if output_path else dirname(tx_filename)
    generate(output_dir)
