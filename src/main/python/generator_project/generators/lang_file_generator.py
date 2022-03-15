from utils import *


# Formats translations as follows: "very cool_block" -> "Very Cool Block"
def format_translation(prefix: str, translation: str, variant: str) -> str:
    return append_variant(append_prefix(translation, prefix, False), variant).replace("_", " ").title()


# Entrypoint for the program, making sure the file itself was run directly
def run(rules: Path, output: Path):
    # Load the contents of the JSON file into memory
    data = json.load(open(rules))
    translations = data["translations"]
    lang = data["language"]
    del data

    # Dict that will host our translations
    lang_dict = dict()

    for translation in translations:
        for k, content in translation["keys"].items():
            for variant in translation["variants"]:
                prefix = get_prefix(content)
                final_content = format_translation(prefix, content["translation"], variant)
                new_key = build_name(prefix, k, variant)
                lang_dict[new_key] = final_content

    # Serialize the data into JSON form
    write_json(output, "lang", lang, lang_dict)
