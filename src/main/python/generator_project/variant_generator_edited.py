import json

from pathlib import Path
from itertools import product
from utils import append_underscore_if_not_empty


# Builds a dictionary representing all possible blockstate permutations based on the rules file provided
def collect_permutations(rules: list) -> dict:
    perm_dict = dict()
    for rule in rules:
        if "when" not in rule:
            continue
        conditions = rule["when"]
        for k, v in conditions.items():
            if k not in perm_dict:
                perm_dict[k] = set()
            # If our value is a list, add all values rather than the list in-and-of-itself
            if isinstance(v, list):
                perm_dict[k].update(v)
            else:
                perm_dict[k].add(v)
    return perm_dict


# Checks whether the blockstate permutation matches the requirements for a rule
def check_rule(rule: dict, permutation: dict) -> bool:
    # If there is no condition for this rule's application, simply return true
    if "when" not in rule:
        return True
    # Otherwise, check each requirement, returning false if any fails to be met
    for k, v in rule["when"].items():
        # If the value is a list, treat it as "or"-like
        if isinstance(v, list):
            return any([x == permutation[k] for x in v])
        # Otherwise, treat it as a single element
        elif v != permutation[k]:
            return False
    return True


# A map of defined operations which can be applied to blockstate values
op_map = {
    "set": (lambda i, n: n),
    "append": (lambda i, n: f"{i}{n}"),
    "shift": (lambda i, n: i + n),
    "multiply": (lambda i, n: i * n),
    "replace": (lambda i, n: replace_op(i, n)),
    "swap": (lambda i, n: swap_op(i, n)),
}


def replace_op(old: str, replacements: dict) -> str:
    new_str = old
    for k, v in replacements.items():
        new_str = new_str.replace(k, v)
    return new_str


def swap_op(old: str, replacements: dict) -> str:
    new_str = old
    for k, v in replacements.items():
        if k in new_str:
            new_str = new_str.replace(k, v)
        elif v in new_str:
            new_str = new_str.replace(v, k)
    return new_str


# replaces every instance of <variant> within the input string
def replace_variant(string, variant: str) -> str:
    if isinstance(string, str):
        new_str = string
        new_str.replace("<variant>", variant)
        return new_str
    else:
        return string


# Apply a rule to a specified permutation's blockstate
def apply_rule(rule: dict, permutation: dict, blockstate: dict) -> dict:
    # Return early if the rule should not be applied to this permutation
    if not check_rule(rule, permutation):
        return blockstate
    # Otherwise, apply the rules effects to the blockstate
    for op, contents in rule["apply"].items():
        for k, n in contents.items():
            i = blockstate.get(k, None)
            blockstate[k] = op_map[op](i, n)
    
    return blockstate


# Generates the blockstate key for the specified permutation of attributes
def generate_key(attributes: dict) -> str:
    components = []
    for k, v in attributes.items():
        s = f"{k}={v}"
        components.append(s)
    return ",".join(components)


# Entrypoint for the program, making sure the file itself was run directly
def run(rules: Path, output: Path):
    # Load the contents of the JSON file into memory
    data = json.load(open(rules))
    rules = data["rules"]
    variants = data["variants"]
    names = data["names"]
    del data

    for variant in variants:
        # Generate the list of unique values, representing all blockstate permutations
        perm_dict = collect_permutations(rules)
        perm_keys = perm_dict.keys()

        # Iterate through all valid permutations, applying the rules to each of them in turn
        blockstate_dict = dict()

        # replace variant keywords with the desired data
        set_variant = (lambda s: s.replace("<variant>", variant))

        for name, prefix in names.items():
            # replace prefix keywords with the desired data
            set_prefix = (lambda n: n.replace("<prefix>", append_underscore_if_not_empty(prefix)))

            # replace all keywords with desired data
            set_keywords = (lambda n: set_variant(set_prefix(n)))

            for p in product(*perm_dict.values()):
                # Generate the attribute dictionary for ease of use
                attrib_dict = dict()
                for i, v in enumerate(perm_keys):
                    attrib_dict[v] = p[i]

                # Initialize the blockstate dictionary
                blockstate_val = dict()

                # Apply each of the rules in the rules file to the blockstate
                for r in rules:
                    blockstate_val = apply_rule(r, attrib_dict, blockstate_val)

                # Generate the key for the blockstate generated prior
                blockstate_key = generate_key(attrib_dict)

                # Add the new blockstate to the blockstate dictionary
                blockstate_val["model"] = set_keywords(blockstate_val["model"])
                blockstate_dict[blockstate_key] = blockstate_val

            # Create the dictionary we intend to serialize into JSON
            final_dict = {"variants": blockstate_dict}

            # Serialize the data into JSON form
            # first, set the variants and prefixes if there are any
            new_name = set_keywords(name)
            # build output path
            output_path = output / "output" / f"{new_name}.json"
            # create directories and files
            output_path.parent.mkdir(parents=True, exist_ok=True)
            json.dump(final_dict, indent=4, fp=open(output_path, 'w'))
            print(f"File created: {output_path.name}")
