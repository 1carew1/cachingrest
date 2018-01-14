from os import listdir
from os.path import isfile, join
import requests
import random

# Constants
endpoint_url = "http://localhost:8080/api/v1/classify"
mypath="../images/"

onlyDirs = [f for f in listdir(mypath) if not isfile(join(mypath, f))]
imagePosition = 0
imageLocations = []
for smallDir in onlyDirs :
    nextDir = mypath + smallDir
    for imageLocation in listdir(nextDir) :
        if isfile(join(nextDir, imageLocation)) :
            imageLocations.insert(imagePosition, nextDir + "/" + imageLocation)
            imagePosition = imagePosition + 1
# Obtain a random image a post to the endpoint
#random_photo = random.choice(imageLocations)

# Send each image to the endpoint
for imageLocation in imageLocations :
    files = {'file': ("file", open(imageLocation, 'r'))}
    try :
        response = requests.post(endpoint_url, files=files)
        print(imageLocation)
        print(response.content)
    except :
        print("Issue Posting : " + imageLocation + " to " + endpoint_url)
