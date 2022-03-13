import variant_generator_edited as blockstate_variant_generator
import multipart_generator as blockstate_multipart_generator
import os
import json

from pathlib import Path

blockstate_variant = "blockstate_variant"
blockstate_multipart = "blockstate_multipart"
block_model = "model_block"
item_model = "model_item"


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
            check_type(rule, output, blockstate_multipart, blockstate_multipart_generator.run)


