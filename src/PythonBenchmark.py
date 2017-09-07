from time import time, sleep
import random
import hashlib
import re
import urllib2

def stringBenchmark():
    for i in range(1500):
        validchar = 'abcdefghijklmnopqrstuvwxyz\\s0123456789-"\''
        testString = ''.join((random.choice(validchar) for i in range(500)))
        re.sub('\\w\\s', ' ', testString)
        testString = testString + ''.join((random.choice(validchar) for i in range(55)))

def benchmark():
    sum = 0
    for i in range(10):
        starttime = time()
        stringBenchmark()
        endtime = time()
        sum += endtime - starttime

    print 'Python Benchmark Score : ' + str(sum)[:5]


if __name__ == '__main__':
    benchmark()
