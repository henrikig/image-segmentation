import os
from file_reader import read_image
from file_reader import read_text_file
import re

path = os.path.dirname(os.path.abspath(__file__))

optimalFolder = path + "/Optimal_Segmentation_Files/"  # you may have to specify the complete path
studentFolder = path + "/Student_Segmentation_Files"  # you may have to specify the complete path
colorValueSlackRange = 40
blackValueThreshold = 100  # colors below 100 is black
pixelRangeCheck = 4
checkEightSurroundingPixels = True


def atoi(text):
    return int(text) if text.isdigit() else text


def natural_keys(text):
    return [atoi(c) for c in re.split(r'(\d+)', text)]


def read_files_from_folder(directory):
    all_files = []
    for filename in sorted(os.listdir(directory), key=natural_keys):
        if filename.endswith(".jpg") or filename.endswith(".png"):
            filename = os.path.join(directory, filename)
            all_files.append(read_image(filename))
        elif filename.endswith(".txt"):
            filename = os.path.join(directory, filename)
            all_files.append(read_text_file(filename))
    return all_files


def compare_pics(studentPic, optimalSegmentPic):
    # for each pixel in studentPic, compare to corresponding pixel in optimalSegmentPic
    global colorValueSlackRange
    global checkEightSurroundingPixels
    global pixelRangeCheck

    height, width = studentPic.shape

    counter = 0  # counts the number of similar pics
    number_of_black_pixels = 0
    for w in range(width):
        for h in range(height):
            # if any pixel nearby or at the same position is within the range, it counts as correct
            color1 = studentPic[h][w]
            color2 = optimalSegmentPic[h][w]
            if color1 < blackValueThreshold:
                # black color
                number_of_black_pixels += 1
                if int(color1) == int(color2):
                    counter += 1
                    continue
                elif checkEightSurroundingPixels:
                    # check surroundings
                    correct_found = False
                    for w2 in range(w - pixelRangeCheck, w + pixelRangeCheck + 1):
                        if correct_found:
                            break
                        for h2 in range(h - pixelRangeCheck, h + pixelRangeCheck + 1):
                            if 0 <= w2 < width and 0 <= h2 < height:

                                color2 = optimalSegmentPic[h2][w2]
                                if color1 - colorValueSlackRange < color2 < colorValueSlackRange + color1:
                                    correct_found = True
                                    counter += 1
                                    break

    return counter / max(number_of_black_pixels, 1)


def main():
    image_name = input("Please specify image name: ")
    optimal_files = read_files_from_folder(optimalFolder + image_name)
    student_files = read_files_from_folder(studentFolder)
    total_score = 0
    global_high = 0
    global_high_idx = 0
    for i, student in enumerate(student_files):
        highest_score = 0
        for opt in optimal_files:
            result1 = compare_pics(opt, student)
            result2 = compare_pics(student, opt)
            # print("PRI 1: %.2f" % result1)
            # print("PRI 2: %.2f" % result2)
            result = min(result1, result2)
            highest_score = max(highest_score, result)
        total_score += highest_score
        a = highest_score * 100
        global_high = max(a, global_high)
        global_high_idx = i + 1 if global_high == a else global_high_idx
        print(f'{i+1}: Score: {a:.2f}%')
    a = total_score / len(student_files) * 100
    print()
    print("Total Average Score: %.2f" % a + "%")
    print(f'The best image is #{global_high_idx} with score {global_high:.2f}%')


main()
