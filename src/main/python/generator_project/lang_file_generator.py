from utils import *


# Entrypoint for the program, making sure the file itself was run directly
def run(rules: Path, output: Path, sub_folder: str):
    # Load the contents of the JSON file into memory
    data = json.load(open(rules))
    models = data["models"]
    variants = data["variants"]
    del data

    for variant in variants:
        for model in models:
            for name, prefix in model["names"].items():
                model_dict = dict()
                model_dict["parent"] = build_name(prefix, model["parent"], variant)

                # Serialize the data into JSON form
                file_name = build_name(prefix=prefix, name=name, variant=variant)
                write_json(output, f"model/{sub_folder}", file_name, model_dict)