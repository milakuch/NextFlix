from simple_image_download import simple_image_download as simp
import pandas as pd
response = simp.simple_image_download

data = pd.read_csv('shows_fordatabase.csv', header=None)
# txt = open("URLs.txt")
print(data)
lst = data[7]

urls = []
# lst=['avengers']
count=0
for rep in lst:
 # response().download(rep +'poster' , 1)
 url = str(response().urls(rep+' poster netflix',1,extensions={'.jpg','.png'}))
 img = url.removeprefix("['")
 img = img.removesuffix("']")
 print(img)
 urls.append(img)
 # txt.writelines(img)

 count+=1
 print(count)


data['8'] = urls
data.to_csv('urls.csv', index=False, header=False)



# data = pd.read_csv('urls.csv', header=None)
#
# lst = data[8]
#
# urls = []
#
# for url in lst:
#  img = url.removeprefix("['")
#  img = img.removesuffix("']")
#  print(img)
#  urls.append(img)
#
# data[8] = urls
# data.to_csv('urls.csv', index=False, header=False)