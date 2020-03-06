# DateManipulation
Program accepts 2 arguments Start time and end time in below format
HH:MM:SS

In case of missing arguments it will assume the input as 16:00:00 and 17:00:00

It will calculate the number of occurrences where hours and minutes are having same digits between the given time.

Assumptions:
1.	From the given statement "the amount of times where only 2 digits appear in every possible combination" – Here I assume we need to match only hours & minutes but not seconds since the given example is ignoring seconds and minutes similarity.
2.	Assuming seconds can have zero and still considered to be similar
