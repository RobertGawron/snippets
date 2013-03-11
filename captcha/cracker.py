import sys, os
import math
import string
import Image
import PIL.ImageChops

if __name__=="__main__":
    input = sys.argv[1]
    base = Image.open(input).convert('L')

    class Fit:
        letter = None
        difference = 0 

    best = Fit()

    for letter in string.lowercase:
        current = Fit()
        current.letter = letter

        sample_path = os.path.join('samples', letter + '.png')
        sample = Image.open(sample_path).convert('L').resize(base.size)
        difference = PIL.ImageChops.difference(base, sample)
        
        for x in range(difference.size[0]):
            for y in range(difference.size[1]):
                current.difference += difference.getpixel((x, y))

        print current.letter, current.difference
        if not best.letter or best.difference > current.difference:
            best = current


    print best.letter
