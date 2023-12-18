# Why 31: An exploration into Java Hashing Algorithms

## Overview
In order to retrieve Strings in Java while maintaining an O(1) time complexity we need Hash Tables, which means we need an efficient way to hash strings. But what makes Java's implementation of a String hashing algorithm the most efficient? This project explores that question.

## Problem statement
### Hash Table Overview
Hash tables are a data structure that utilizes hashing functions to place and retrieve values in their internal array in O(1) time. 

### What Makes a Good Hashing Algorithm?
A good hashing algorithm has three properties:
1. It will produce the same output given the same input, in other words, it is a function. Since Strings are immutable in Java this allows us to cache the value for even quicker computation.
1. The computation time is as low as possible. Having an O(1) data structure does not do us much good if that one operation takes hours to compute.
1. The function will distribute the values allowing for minimal collisions.

### Java's Hashing Algorithm
According to the Java documentation, the hashing algorithm for Strings does the following:

```
Returns a hash code for this string. The hash code for a String object is computed as
s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]

using int arithmetic, where s[i] is the ith character of the string, n is the length of the string, and ^ indicates exponentiation.
```

### The Goal
This part of the documentation naturally leads to the question why would you choose 31, a seemingly arbitrary integer, as the base of this exponent? This project will implement several classes in Java that allow for efficient data collection about the performance of several different bases and then use Juypiter notebooks to process and visualize that data in order to explain why 31 was the most efficient base.



## Outline
### Technologies
This project primarily uses Java with Maven and the OpenCSV library. Data analysis is done in Python using Juypiter Notebooks.

### Usage
#### Visualization
Navigate to the [Visualization Notebook](https://github.com/LanderDuncan/Why31/blob/main/visualization.ipynb) to see the code in action!
#### Extension
1. Clone the repo
```
git clone https://github.com/LanderDuncan/Why31.git
```
1. Install the Maven packages
```
cd stringhashing
mvn clean install
```
You can now use the Java classes to experiment with Java's String hashing function on your own!
## License
Distributed under the MIT License. See [LICENSE.txt](https://github.com/LanderDuncan/Why31/blob/main/LICENSE.txt) for more information.

## Acknowledgments
The word list was sourced from this [Github Repo](https://github.com/first20hours/google-10000-english) which sourced the data from the Google Web Trillion Word Corpus and is distributed under the LDC license.