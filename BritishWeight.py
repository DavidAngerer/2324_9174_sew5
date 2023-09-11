"""
@author David Angelo
"""

class BritishWeight:
    pass


class BritishWeight:
    """
    >>> weigth_one = BritishWeight(12,10)
    >>> weigth_one
    12 st 10 lb
    >>> print(weigth_one)
    12 st 10 lb
    >>> weigth_two = BritishWeight(0,12)
    >>> print(weigth_two)
    12 lb
    >>> weigth_one + weigth_two
    13 st 8 lb
    >>> weigth_two + BritishWeight(0, -1)
    11 lb
    """
    def __init__(self, stone:int, pounds:int):
        self._pounds = stone * 14 + pounds

    def __str__(self):
        return f'{str(int(self._pounds/14)) + " st " if self._pounds > 14 else ""}' \
               f'{str(int(self._pounds%14)) + " lb" if self._pounds % 14 != 0 else "0 lb" if self._pounds == 0 else ""}'

    def __repr__(self):
        return f'{str(int(self._pounds / 14)) + " st " if self._pounds > 14 else ""}' \
               f'{str(int(self._pounds % 14)) + " lb" if self._pounds % 14 != 0 else "0 lb" if self._pounds == 0 else ""}'

    @property
    def pounds(self) -> int:
        """
        returns how many pounds it is
        :return:
        """
        return self._pounds

    @property
    def stone_and_pounds(self) -> tuple[int,int]:
        """
        :return: How many stones and pounds as a tuple
        """
        return int(self._pounds/14), self._pounds % 14

    def __add__(self, other:BritishWeight):
        return BritishWeight(0,self._pounds + other.pounds)
