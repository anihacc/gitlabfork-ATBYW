def append_underscore_if_not_empty(string: str):
    if string != "":
        return f"{string}_"
    else:
        return ""
