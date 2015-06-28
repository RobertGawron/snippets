#!/usr/bin/env python3
import sys
import string
from PIL import Image

def save_image(image, template, output_file):
    data = template.replace('IMAGE', image)
    output = open(output_file, 'w')
    output.write(data)

def parse_image(image):
    buffer = '<table>'
    for j in range(image.size[1]):
        buffer += '<tr>'
        for i in range(image.size[0]):
            color = "rgb(%d, %d, %d)" % image.getpixel((i, j))
            buffer += "<td style=\"background:%s\"></td>" % color
        buffer += '</tr>'
    return buffer + '</table>'

if __name__=="__main__":
    image_name = sys.argv[1]
    template = open('template.html', 'r').read()
    image = Image.open(image_name).convert('RGB')
    save_image(parse_image(image), template, 'output.html')
