import random, time


class Random():
    """Random Class to get random boolean, random int and random item/items from specified iterable"""

    def get_seed(self):
        """Generate seed based on current timestamp"""
        return int(time.time())

    def get_bool(self):
        """Get Random boolean value"""
        return bool(random.getrandbits(1))  # nosec

    def get_int(self, min_bound, max_bound):
        """Get Random integer value between specified min and max bound

        Args:
            min_bound (int): Minimum bound
            max_bound (int): Maximum bound

        Returns:
            int: Random integer value between specified min and max bound
        """
        if max_bound < min_bound:
            return min_bound
        return random.randint(min_bound, max_bound)  # nosec

    def exclude_items(self, iterable, *exclude_items):
        """Remove items from the iterable

        Args:
            iterable (Any): Iterable
            exclude_items (Any, optional): items to be removed

        Returns:
            list: List with items exclude items removed
        """
        return [item for item in list(iterable) if item not in exclude_items]

    def get_item(self, iterable, *exclude_item):
        """Get random single item from iterable

        Args:
            iterable (Any): Iterable
            exclude_items (any, optional): tuple of items to be removed

        Returns:
            Any : Single Item from iterable
        """
        items = self.exclude_items(iterable, *exclude_item)
        if not items:
            return items
        return random.choice(items)  # nosec

    def get_items(self, iterable, limit, *exclude_item, retain_order=False):
        """Get random multiple unique items from iterable specified by limit

        Args:
            iterable (Any): Iterable
            limit (int): Number of items to return
            exclude_items (Any, optional): tuple of items to be removed
            retain_order (bool, optional): To Retain Order of items. Defaults to False.

        Returns:
            list: List of unique items from the iterable specified by limit
        """
        items = self.exclude_items(iterable, *exclude_item)
        if not items:
            return items
        limit = len(items) if limit > len(items) else limit
        items = random.sample(items, k=limit)  # nosec
        if retain_order:
            items.sort(key=list(iterable).index)
        return items

    def shuffle(self, iterable):
        """To shuffle the iterable items

        Args:
            iterable (Any): Iterable

        Returns:
            Any: Shuffled iterable
        """
        if not (iterable is not None and len(iterable)):
            return iterable
        items = list(iterable)
        random.shuffle(items)
        return items  # nosec


random = Random()