import json

from pathlib import Path
from itertools import product
from utils import append_underscore_if_not_empty


# A map of defined operations which can be applied to blockstate values
op_map = {
    "set": (lambda i, n: n),
    "append": (lambda i, n: f"{i}{n}"),
    "shift": (lambda i, n: i + n),
    "multiply": (lambda i, n: i * n),
}


# replaces every instance of <variant> within the input string
def replace_variant(string, variant: str) -> str:
    if isinstance(string, str):
        new_str = string
        new_str.replace("<variant>", variant)
        return new_str
    else:
        return string


# get available states from rule & default_apply
def get_available_states(rule: dict, default_apply: dict, available_states: dict, state):
    for op, contents in rule["apply"].items():
        for key, value in contents.items():
            if key == "model":
                for k, v in default_apply.items():
                    available_states[state] = op_map[op](v, value)


# Checks whether the blockstate permutation matches the requirements for a rule
def check_rule(rule: dict, available_states: dict, default_apply: dict):
    # if the rule has no "when" then it must be the default one, apply special treatment
    if "when" not in rule:
        for op, contents in rule["apply"].items():
            for k, n in contents.items():
                default_apply[k] = op_map[op](None, n)
    else:
        # iterate through the condition
        for key, value in rule["when"].items():
            # If our value is a list, add all values rather than the list in-and-of-itself
            if isinstance(value, list):
                for v in value:
                    get_available_states(rule, default_apply, available_states, v)
            else:
                get_available_states(rule, default_apply, available_states, value)


# creates a dictionary of all {model: [state1, state2..]}
# intended for use later in multipart OR syntax -> when: {state1|state2|...}
def add_models_to_states(available_states: dict):
    model_states = dict()
    for state, model in available_states.items():
        if model not in model_states:
            model_states[model] = set()
            model_states[model].add(state)
        else:
            model_states[model].add(state)
    return model_states


# Entrypoint for the program, making sure the file itself was run directly
# TODO: finish this mess
def run(rules: Path, output: Path):
    # Load the contents of the JSON file into memory
    data = json.load(open(rules))
    rules = data["rules"]
    variants = data["variants"]
    names = data["names"]
    del data

    available_states = dict()
    default_apply = dict()
    model_states = dict()

    # iterate through rules
    for rule in rules:
        # populate available states
        check_rule(rule, available_states, default_apply)

    # collect the present model's states & print them for debug
    for model, states in add_models_to_states(available_states).items():
        state_string = "|".join(states)
        model_states[model] = state_string

    print(model_states)
    print(default_apply)

    for variant in variants:

        # replace <variant> keywords with the current variant
        set_variant = (lambda s: s.replace("<variant>", variant))

        for name, prefix in names.items():
            # replace prefix keywords with the desired data
            set_prefix = (lambda n: n.replace("<prefix>", append_underscore_if_not_empty(prefix)))

            # replace all keywords with desired data
            set_keywords = (lambda n: set_variant(set_prefix(n)))

            # Serialize the data into JSON form
            # first, set the variants if there are any
            new_name = set_keywords(name)
            # build output path
            output_path = output / "output" / f"{new_name}.json"
            # create directories and files
            output_path.parent.mkdir(parents=True, exist_ok=True)
            # json.dump(final_dict, indent=4, fp=open(output_path, 'w'))
            # print(f"File created: {output_path.name}")
