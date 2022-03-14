import json

from pathlib import Path


# append underscore to end of string if the string isn't empty
def append_underscore(string: str):
    if string != "":
        return f"{string}_"
    else:
        return ""


# replace variant keywords with the desired data
def append_variant(name: str, variant: str) -> str:
    return name.replace("<variant>", variant)


# replace prefix keywords with the desired data
def append_prefix(name: str, prefix: str, add_underscore: bool) -> str:
    replacement = prefix
    if add_underscore:
        replacement = append_underscore(prefix)
    return name.replace("<prefix>", replacement)


# replace all keywords with desired data
def build_name(prefix: str, name: str, variant: str) -> str:
    new_name = append_prefix(name, prefix, True)
    return append_variant(new_name, variant)


# Serialize the data into JSON form
def write_json(output_path: Path, sub_folder: str, name: str, content: dict):
    # build output path
    new_output_path = output_path / "output" / sub_folder / f"{name}.json"
    # create directories and files
    new_output_path.parent.mkdir(parents=True, exist_ok=True)
    json.dump(content, indent=4, fp=open(new_output_path, 'w'))
    print(f"File created: {new_output_path.name}")


# get the prefix, return empty if not present
def get_prefix(content: dict) -> str:
    prefix = ""
    if "prefix" in content:
        prefix = content["prefix"]
    return prefix

