# file reader: reads file name and returns 2d array
from PIL import Image
import numpy as np


def read_image(filename):
	im = Image.open(filename)
	pix = im.load()
	width, height = im.size
	data = fix_data(list(im.getdata()))
	# get pixel (x,y) by pixel_values[width*y+x]
	try:
		return np.array(data).reshape((height, width))
	except:
		raise Exception("Mo")


def fix_data(data):
	# if data contains tuples, then replace every tuple with the first number in the tuple
	check = type(data[0])
	if check == tuple:
		for i in range(len(data)):
			data[i] = data[i][0]
	return data


def read_text_file(filename):
	width = 0
	height = 0
	im = np.array([])
	file = open(filename, "r")
	for line in file:
		im_line = line.strip().split(",")
		width = len(im_line)
		# print(width)
		for i in range(len(im_line)):
			im_line[i] = int(im_line[i])
		im = np.append(im, np.array(im_line))
		height += 1
	# print(height)
	try:
		im.reshape((width,height))
	except:
		raise Exception('\n\nSome error with the shape of the .txt image file \n\n')
	return im.reshape((width,height))
