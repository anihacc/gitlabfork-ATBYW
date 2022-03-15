from itertools import product

import json

log_types = ["oak", "birch", ...]  # Fill as desired
axis = ["x", "y", "z"]
placement = ["top", "bottom"]

# For every unique combination of log type, orientation, and placement position...
multipart_list = []
for t, a, p in product(log_types, axis, placement):
    # Generate our initial multipart dict with the "when" condition pre-built
    multipart_dict = {"when": {p: a, "log_type": t}}
    apply_dict = {}

    # Identify the orientation model which we should (vertical or horizontal)
    orient = None
    if a == "y":
        orient = "vertical"
    else:
        orient = "horizontal"
        # Identify whether a rotation should occur as well
        if a == "z":
            apply_dict["y"] = 90

    # Insert a dynamically generated model name into the apply dict
    apply_dict["model"] = f"block/double_{p}_{orient}_{t}"  # !!Modify this to your prefered naming scheme!!

    # Insert the prior dict into our multipart
    multipart_dict["apply"] = apply_dict

    # Save the data to our list of elements
    multipart_list.append(multipart_dict)

# Build the final to-be-saved dict
out_dict = {"multipart": multipart_list}

# Save the result
json.dump(out_dict, indent=4, fp=open("../tmp.json", "w"))
