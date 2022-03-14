import variant_generator as blockstate_variant_generator
import model_child_generator as child_model_generator
import os
import json

from pathlib import Path

blockstate_variant = "blockstate_variant"
blockstate_multipart = "blockstate_multipart"
block_model_template = "block_model_template"
item_model_template = "item_model_template"
language_file = "language_file"


def check_type(rules_file: Path, output_file: Path, expected_type: str, generator) -> bool:
    data = json.load(open(rules_file))
    file_type = data["type"]
    del data
    if file_type == expected_type:
        generator(rules_file, output_file)
        return True


if __name__ == '__main__':
    output = Path(os.getcwd())
    for rule in (Path(os.getcwd()) / "rules").glob("**/*.json"):
        if not rule.parent.name == "disabled":
            check_type(rule, output, blockstate_variant, blockstate_variant_generator.run)
            check_type(rule, output, block_model_template, (lambda r, o: child_model_generator.run(r, o, "block")))
            check_type(rule, output, item_model_template, (lambda r, o: child_model_generator.run(r, o, "item")))


