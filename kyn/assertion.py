"""Assertion Helper"""
import traceback

class Assertion:
    """Assertion Helper class. It does hard assertions (i.e, stop on assertion failures)
    """

    def is_equals(self, expected, actual, field):
        """Assert the values are equal

        Args:
            expected : Expected value
            actual : Actual value
            field (str): Assertion field name
        """
        self._assert_and_log(expected == actual,
                             f"{field} -> Expected: '{expected}' | Actual: '{actual}'")

    def is_empty(self, value, field):
        """Assert the object is empty

        Args:
            value : Value/object to assert as empty
            field (str):  Assertion field name
        """
        self._assert_and_log(
            not value, f"{field} -> Expected: value should be 'Empty' | Actual: '{value}'")

    def is_true(self, value, field):
        """Assert the value is True

        Args:
            value : Value to assert as true
            field (str):  Assertion field name
        """
        self._assert_and_log(
            value, f"{field} -> Expected: 'True' | Actual: '{value}'")

    def is_false(self, value, field):
        """Assert the value is False

        Args:
            value : Value to assert as true
            field (str):  Assertion field name
        """
        self._assert_and_log(
            not value, f"{field} -> Expected: 'False' | Actual: '{value}'")

    def is_in(self, value, in_value, field):
        """Assert the value is in the in_value

        Args:
            value : Expected value to be in 
            in_value : value/object to checked for specified value
            field (str):  Assertion field name
        """
        self._assert_and_log(value in in_value,
                             f"{field} -> Expected: '{value}' is in '{in_value}'")

    def _assert_and_log(self, condition, message):
        """To assert and log the message

        Args:
            condition : Condition (statements) to assert
            message (str): Message

        Raises:
            ex: Raise an AssertionError if assertion fails
        """
        try:
            assert condition  # nosec
            logger.info("Assertion Pass: %s", message)
        except AssertionError as ex:
            logger.error("Assertion Fail: %s", message)
            raise ex


assertion = Assertion()


class SoftAssertion(Assertion):
    """Assertion class to handle Soft Assertion and fails when assert_all is called
    """

    def __init__(self):
        self.failure_list = []

    def assert_all(self):
        """Log All the Assertion Failure traces if any assertions had failed


        Raises:
            AssertionError: Raises an AssertionError if any assertions had failed
        """
        if self.failure_list:
            failure_trace = ""
            for (file, line, func, context) in self.failure_list:
                failure_trace += f"[{file}:{line}][{func}()] -> {context}\n"
            raise AssertionError(
                f"Assertion Failures in following context:\n{failure_trace}")

    def _assert_and_log(self, condition, message):
        """To assert and log the message

        Args:
            condition : Condition (statements) to assert
            message (str): Message
        """
        try:
            assert condition  # nosec
            logger.info("(Soft) Assertion Pass: %s", message)
        except AssertionError:
            logger.error("(Soft) Assertion Fail: %s", message)
            self.failure_list.append(
                traceback.extract_stack()[-3])