import os
import glob

root = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
search = os.path.join(root, 'tests', 'cleaner', 'clean', '**', 'act', 'act.txt')
files = glob.glob(search, recursive=True)
updated = []
skipped = []
for act in files:
    case_dir = os.path.dirname(os.path.dirname(act))
    arrange_dir = os.path.join(case_dir, 'arrange')
    xmls = []
    if os.path.isdir(arrange_dir):
        xmls = [f for f in os.listdir(arrange_dir) if f.lower().endswith('.xml')]
    if not xmls:
        skipped.append((act, 'no xml in arrange'))
        continue
    # read current first line to try to match
    with open(act, 'r', encoding='utf-8') as fh:
        lines = fh.readlines()
    first = lines[0].strip() if lines else ''
    chosen = None
    if first.startswith('load '):
        want = first[5:].strip()
        if want in xmls:
            chosen = want
    if chosen is None:
        # pick first xml
        chosen = xmls[0]
    # compute relative path from repo root, using forward slashes
    rel_case = os.path.relpath(case_dir, root).replace('\\', '/')
    new_first = f'load {os.path.join("tests", "cleaner", "clean", rel_case, "arrange", chosen).replace('\\\\','/')}'
    # ensure slashes
    new_first = new_first.replace('\\', '/')
    # replace first line
    new_lines = [new_first + '\n'] + (lines[1:] if len(lines) > 1 else [])
    if lines and lines[0].strip() == new_first:
        # already correct
        continue
    with open(act, 'w', encoding='utf-8') as fh:
        fh.writelines(new_lines)
    updated.append((act, chosen))

print('Updated files:')
for a, x in updated:
    print(a + ' -> ' + x)
print('\nSkipped:')
for a, reason in skipped:
    print(a + ' : ' + reason)
print(f'\nTotal found: {len(files)}, updated: {len(updated)}, skipped: {len(skipped)}')

