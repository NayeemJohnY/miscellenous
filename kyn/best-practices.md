## Pylint RC (.pylintrc)

[DESIGN]
max-attributes=15
max-args=10
max-parents=10
max-positional-arguments=10

[MESSAGES CONTROL]
disable= wildcard-import, unused-wildcard-import, too-many-lines

[FORMAT]
max-line-length=115

## Scripting Best Practices

- Write your code in own way. But keep align with framework and [The Zen of python](https://peps.python.org/pep-0020/#the-zen-of-python).

- Avoid using global variables. Use global variables only for constants that will never change once assigned. Since in performance mode multiple threads tries to access and modify the same.
  
- Try to reduce the usage of direct assert statements. As screenshots are not taken on assertion failures. So debug of failure due to assertion is not straightforward. Instead of adding direct assert statements, Try to incorporate assertion in element locator. As a result if assertion fails, i.e.,(Issue with the locator) then the script will capture screenshot for debug.

- Add stop_on_fail = True with selenium functions to handle hard Assertions that need to validate and fail/stop the execution.

- Tried to avoid sleep waits in the PR. Check alternative way of handling the intermittent issues. In worst case, let your script fail for the intermittent issue.

- Add loading page validations in timed event, optionally it can include the click in timed event but make sure element already available before going to timed event i.e., timed event should not include elements to click which is not available, This way we can exclude the wait time before the operation occurs

- Once scripting is done, Before rasing PR or pushing code, Do organize the imports, format the code. Add doc strings, add comment for specific line of code which need a explanation and check the code for linting issues/suggestions.

- Use pylint library (command line) or use the PyCharm plugins listed below for better scripting. Refer links of How to set up PyCharm for better scripting:
  - To Format code: [https://www.jetbrains.com/help/pycharm/reformat-and-rearrange-code.html#tabs_and_indents](https://www.jetbrains.com/help/pycharm/reformat-and-rearrange-code.html#tabs_and_indents)

  - To Organize Imports [https://www.jetbrains.com/help/pycharm/creating-and-optimizing-imports.html#optimize-imports](https://www.jetbrains.com/help/pycharm/creating-and-optimizing-imports.html#optimize-imports)
  
  - To identify and fix code issues using pylint [https://github.com/leinardi/pylint-pycharm](https://github.com/leinardi/pylint-pycharm)