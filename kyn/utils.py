# Test Picker
def test_picker(test_type=None, only_desktop=False, mobile_only=False):
    """Decorator function to execute a Test based on the test type

    Args:
        test_type (str): Test type. Defaults to None. func / perf
        only_desktop (bool): True if the task should be skipped in mobile mode. Defaults to False.
        mobile_only (bool): True if the task should be run only when mobile mode is true. Defaults to False.
    """
    def test_wrapper(func):
        def inner(*args, **kwargs):
            current_test_type =  get_test_type() # func /perf
            mobile_mode = is_mobile_mode()

            if test_type and test_type != current_test_type:
                logger.warning(
                    "Skipping this task. Current test type is not '%s'", test_type)
                return

            if (not mobile_mode) and mobile_only:
                logger.warning(
                    "Skipping this task. 'mobile_only' flag is True but not in mobile mode")
                return

            if mobile_mode and only_desktop:
                logger.warning(
                    "Skipping this task. 'only_desktop' flag is True in mobile mode")
                return

            func(*args, **kwargs)
        inner.__name__ = func.__name__
        return inner

    return test_wrapper

class Test:

    @test_picker("perf")
    def test_1(self):
        print("Test Me")



def wait_and_poll_function(
        max_wait_time, poll_interval, poll_func, *args, **kwargs):
    """Wait and poll (execute the poll function) in the poll interval
    until the specified poll function returns true or timed out

    Args:
        max_wait_time (float): Max Wait Time in seconds
        poll_interval (float): Poll Interval in seconds
        poll_func (func): callable to be executed (polled)
        *args: arguments to be used when calling poll_func
        **kwargs: Arbitrary keyword args used for calling poll_func

    Returns:
        bool: Return True if condition is met, False if timed out
    """
    start_time = time.time()
    logger.info(
        'Waiting for expected condition. Max Wait Time: "%s" seconds.', max_wait_time)
    while True:
        logger.info(
            'Polling for expected condition. After Delay: "%s" seconds.', poll_interval)
        time.sleep(poll_interval)
        condition = poll_func(*args, **kwargs)
        timed_out = (time.time() - start_time) > max_wait_time
        if condition:
            return True
        if timed_out:
            return False


def prepare_document(*src_file_relative_paths, new_file_name=None, increase_size=False, file_ext=None):
    """Prepare document for test

    Args:
        src_file_relative_paths (str | tuple): Source file Relative paths
        new_file_name (str, optional): New(Updated) File Name. Defaults to None.
        increase_size (bool, optional): To increase file size. Defaults to False.
        file_ext (str, optional): To modify file type(extension). Defaults to None.

    Returns:
        tuple: Prepared document name and filepath
    """
    src_file_path = os.path.join(os.getcwd(), *src_file_relative_paths)
    if file_ext is None:
        _, file_ext = os.path.splitext(src_file_path)
    if new_file_name is None:
        new_file_name = f'document_{random.get_seed()}{file_ext}'
    new_file_path = os.path.join(os.getcwd(), new_file_name)
    shutil.copyfile(src_file_path, new_file_path)
    if increase_size:
        with open(new_file_path, "a+", encoding="utf-8") as file:
            file.write("increase_size" * (1024*1024*10))
    return new_file_name, new_file_path


@contextmanager
def log_step(name:str):
    """Context Manger to log step

    Args:
        name (str): Name of the step
    """
    try:
        logger.info("<========= Started %s =======>", name)
        yield
    finally:
        logger.info("<========= Completed %s =======>", name)


def retry_on_exception(*exceptions):
    """Decorator function to retry on exceptions
    such as StaleElementReferenceException, TimeoutException and any additional exception provided

    Args:
        exceptions (Exception, optional): Additional exception classes to be caught and retried
    """
    def wrapper(func):
        def inner(*args, **kwargs):
            try:
                return func(*args, **kwargs)
            except (StaleElementReferenceException, TimeoutException, *exceptions) as e:
                logger.warning(
                    "'%s' occurred. Message: '%s'. Retrying...", type(e).__name__, getattr(e, 'msg', 'NO-MSG'))
                time.sleep(2)
                return func(*args, **kwargs)
        return inner

    return wrapper



def wait_for_window_to_auto_close(driver, windows_count):
    """Wait for Window to Auto Close and windows count reduced

    Args:
        driver (webdriver):  selenium webdriver
        windows_count (int): total windows opened

    Returns:
        bool: `True` if Window is auto closed and windows count is reduced else `False`
    """
    logger.info("Waiting for windows to get auto close")
    try:
        return WebDriverWait(driver, 60, 2, ignored_exceptions=(NoSuchWindowException)).until(
            lambda driver: len(driver.window_handles) < windows_count, "Window is not get auto closed")
    except TimeoutException:
        logger.info(
            "TimeoutException occurred on waiting for windows to get auto close")
        return False

curr_time
query_str = ''
if curr_time:
    query_str += 'receivedDateTime ge ' + datetime.isoformat(
        curr_time - timedelta(minutes=15)).replace("+00:00", "Z")
    query_str += ' and receivedDateTime le ' + datetime.isoformat(
        curr_time + timedelta(minutes=20)).replace("+00:00", "Z")
else:
    query_str += 'receivedDateTime ge 0001-01-01T00:00:00Z'

query_str += f" and contains(from/emailAddress/address,'{sender}') "
query_str += f" and contains(subject, '{subject}')"


query = {'name': 'a'}
if attrs:
    query.update({'attrs': attrs})
if text_in_href:
    query.update({'href': re.compile(text_in_href)})
if link_text:
    query.update({'text': re.compile(link_text)})

# https://drive.google.com/file/d/1Mn-uP8Y3xo-BK4eRn3BW3uSbWH69iq-7/view?usp=drive_link
# https://drive.google.com/file/d/1WzVFbZdBSMRD14hNFEfb0G2KO1nnMOow/view?usp=drive_link


