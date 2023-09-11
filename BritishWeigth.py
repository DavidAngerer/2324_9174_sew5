class BritishWeight:
    def __init__(self, stone:int, pounds:int):
        self._pounds = stone * 14 + pounds

    def __str__(self):
        return f'{str(int(self._pounds/14)) + " st " if self._pounds > 14 else ""}' \
               f'{str(int(self._pounds%14)) + " lb " if self._pounds % 14 != 0 else "0 lb" if self._pounds == 0 else ""}'


