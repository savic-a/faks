#!/usr/bin/env python
# coding: utf-8

# # Soft Computing
# 
# ## Vežba 2 - Semantička segmentacija
# 

# Segmentacija slike je proces deljenja slike na više različitih regiona (segmenata). Doprinosi lakšoj analizi i razumevanju slika. Najčešće se koristi za izdvajanje objekata ili granica između objekata na slikama. Segmentacija u stvari predstavlja proces dodeljivanja određene labele svakom pikselu na slici, tako da pikseli koji imaju istu vrednost dele iste karakteristike (ili pripadaju istom objektu). Pronalazi višestruku upotrebu u medicini, autonomnoj vožnji, video nadzoru, itd.
# 
# Na prethodnom terminu smo radili neke osnovne operacije za obradu slike. Upotrebom samo tih operacija za izdvajanje regiona od interesa ne bismo mogli dobiti željene rezultate za konture koje imaju malo dodirnih tačaka.

# ## Watershed

# Česta operacija koja se koristi kada nam same boje ne nose mnogo informacija je konvertovanje iz RGB modela u nijanse sivih (grayscale). Na ovaj način se gubi informacija o boji piksela na slici, ali sama slika postaje mnogo lakša za dalju obradu. Svaka grayscale slika može biti posmatrana kao topografska površina gde pikseli visokog intenziteta označavaju vrhove i brda, dok se oni niskog intenziteta odnose na doline. 
# 
# <img src="images/watershed-flooding-graph.png" width="400">
# 
# Svaku izolovanu dolinu (lokalni minimum) punimo različitom bojom (labele). Kako se nivo vode povećava, u zavisnosti od vrhova (gradijenata) koji su u blizini, voda iz različitih dolina (i različitih boja) će početi da se spaja. Kako bismo ovo izbegli, na mestima gde se voda spaja počinjemo da gradimo barijere. Postupak punjenja vode i izgradnje barijera se ponavlja sve dok svi vrhovi ne budu pod vodom. Na osnovu kreiranih barijera dobijamo segmentiranu sliku. 

# Primena algoritma na ovaj način može dovesti do prekomerne segmentacije, posebno za slike koje u sebi sadrže šum i veliki broj lokalnih minimuma. Unapređenje algoritma koje predstavlja plavljenje topografske površine iz prethodno definisanog skupa markera sprečava prekomernu segmentaciju. Detaljnije objašnjenje je dostupno [ovde](https://people.cmm.minesparis.psl.eu/users/beucher/wtshed.html). 
# 
# [OpenCV biblioteka](https://docs.opencv.org/4.4.0/d7/d1b/group__imgproc__misc.html#ga3267243e4d3f95165d55a618c65ac6e1) sadrži implementaciju marker-based watershed algoritma za koji je potrebno da sami odredimo koje doline će se spojiti, a koje ne:
# 
# ```code
# markers	= cv.watershed(image, markers)
# ```
# 
# Parametar *image* predstavlja RGB sliku čije su vrednosti piksela predstavljene u realnom intervalu (float32).
# Parametar *markers* reprezentuje sliku istih dimenzija kao *image* koja sadrži sledeće oznake:
# 1. poznati objekti su označeni različitim oznakama (label)
# 2. delovi za koje smo sigurni da pripadaju pozadini (background) su označeni drugom bojom 
# 3. delovi za koje nismo sigurni da li pripadaju pozadini (background) ili su deo nekog objekta (foreground) su označeni sa 0.
# 
# Izlaz predstavlja slika koja je dobijena tako što je ulazna slika *markers* ažurirana tako što su granice između objekata postavljene na vrednost -1.

# In[10]:


import numpy as np
import cv2 # OpenCV
import matplotlib
from matplotlib import pyplot as plt


# In[11]:


# iscrtavanje slika u notebook-u
# get_ipython().run_line_magic('matplotlib', 'inline')
# # prikaz vecih slika
# matplotlib.rcParams['figure.figsize'] = 16,12


# Sledeće metode su detaljno objašnjene na prethodnim vežbama.

# In[12]:


def load_image(path):
    return cv2.cvtColor(cv2.imread(path), cv2.COLOR_BGR2RGB)

def image_gray(image):
    return cv2.cvtColor(image, cv2.COLOR_RGB2GRAY)

def image_bin(image_gs):
    height, width = image_gs.shape[0:2]
    image_binary = np.ndarray((height, width), dtype=np.uint8)
    ret, image_bin = cv2.threshold(image_gs, 0, 255, cv2.THRESH_BINARY_INV+cv2.THRESH_OTSU)
    return image_bin

def invert(image):
    return 255-image

def display_image(image, color=False):
    if color:
        plt.imshow(image)
    else:
        plt.imshow(image, 'gray')


# Za početak, učitavamo sliku.

# In[13]:


img = load_image("images/water_coins.jpg")
display_image(img, color=True)


# Učitanu sliku transformišemo u binarni oblik i primenjujemo morfološke operacije kako bismo uklonili šum.

# In[14]:


img_bin = image_bin(image_gray(img))
# uklanjanje suma
kernel = np.ones((3,3), np.uint8) # strukturni element 3x3 blok
opening = cv2.morphologyEx(img_bin, cv2.MORPH_OPEN, kernel, iterations = 2) # otvaranje
display_image(opening)


# Sve piksele delimo u tri grupe:
# 1. one koji pripadaju pozadini (background)
# 2. one koji pripadaju objektima (foreground) 
# 3. ostale

# Primenom dilacije povećavamo površinu objekata na slici tako da sa sigurnošću znamo da sve ono što predstavlja pozadinu rezultujuće slike, sigurno jeste pozadina. 

# In[15]:


# izdvajanje regiona koji sigurno pripadaju pozadini (background)
sure_bg = cv2.dilate(opening, kernel, iterations=3)
display_image(sure_bg)


# Pošto se objekti dodiruju, za izdvajanje piksela koji sigurno pripadaju novčićima koristimo [distanceTransform](https://docs.opencv.org/4.4.0/d7/d1b/group__imgproc__misc.html#ga8a0b7fdfcb7a13dde018988ba3a43042) metodu uz primenu odgovarajućeg praga. Metoda distanceTransform za svaki piksel (binarne) ulazne slike računa udaljenost od najbližeg piksela koji ima vrednost 0. Izlaz predstavlja slika sa izračunatim udaljenostima za svaki piksel, istih dimenzija kao ulazna slika.

# In[16]:


# izdvajanje regiona koji pripadaju objektima (foreground)
dist_transform = cv2.distanceTransform(opening, cv2.DIST_L2, 5) #  DIST_L2 - Euklidsko rastojanje
display_image(dist_transform)


# In[17]:


# za prag uzimamo 70 percentil najudaljenijih piksela 
ret, sure_fg = cv2.threshold(dist_transform, 0.7 * dist_transform.max(), 255, 0) 
display_image(sure_fg)


# Delove za koje nismo sigurni da li pripadaju pozadini ili objektu dobijamo oduzimanjem ove dve slike.

# In[18]:


# izdvajanje ostalih delova za koje ne znamo da li predstavljaju background ili foreground
sure_fg = np.uint8(sure_fg)
unknown = cv2.subtract(sure_bg, sure_fg)
display_image(unknown)


# ### Kreiranje markera
# 
# Regione za koje znamo da pripadaju ili pozadini ili objektima označavamo bilo kojim pozitivnim (različitim) celim brojevima, dok nepoznatim regionima pridružujemo vrednost 0. 
# 
# OpenCV ima metodu **cv.connectedComponents()** koja pozadinu slike označava sa 0, a ostali brojevi se označavaju celim brojevima počev od 1.

# In[19]:


# marker labelling
ret, markers = cv2.connectedComponents(sure_fg)
# svim vrednostima dodajemo 1 kako pozadina ne bi imala vrednost 0 (ona je rezervisana za nepoznate regione)
markers = markers+1
# oznacavanje nepoznatih regiona sa 0
markers[unknown==255] = 0


# Prikaz rezultata pomocu JET mape boja. Tamnoplava boja odgovara nepoznatim regionima.

# In[20]:


def label2rgb(image):
    cmap = plt.get_cmap('jet', len(np.unique(markers)))
    return cmap(markers)


# In[21]:


rgba_img = label2rgb(markers)
display_image(rgba_img, color=True)


# Sada, sa ovako pripremljenim markerima, možemo primeniti watershed algoritam koji modifikuje ulaznu sliku markera tako što ivice između objekata označava sa -1. 

# In[22]:


markers = cv2.watershed(img, markers)
img[markers == -1] = [255, 0, 0]


# In[23]:


rgba_img = label2rgb(markers)
display_image(rgba_img, color=True)


# Na sledeći način možemo dobiti broj detektovanih objekata (u ovom slučaju, novčića):

# In[24]:


# markers je lista listi, gde je za svaki elemenat "unutrasnjih" listi boja piksela na slici
print(markers)


# In[25]:


# ovom linijom koda dobijamo sve unikatne vrednosti boja u svim "unutrasnjim" listama
unique_colours = {x for l in markers for x in l}
print(unique_colours)


# In[26]:


# -1 nam se odnosi na granice među objektima, ovu boju nećemo računati
# jedan od ovih brojeva se odnosi na pozadinsku boju, pa zbog toga ne računamo još jednu boju
# znači, ukupno treba da oduzmemo 2 boje od svih prisutnih, kako bismo dobili koliko je prisutno unikatnih boja,
# odnosno detektovanih objekata
number_of_coins = len(unique_colours) - 2
print("Broj detektovanih novčića:", number_of_coins)


# In[27]:


display_image(img, color=True)


# ## K-means

# ### Podsećanje
# 
# K-means je model nenadgledanog mašinskog učenja koji se koristi za grupisanje podataka u K različitih grupa.
# 
# <img src="images/kmeans.gif"/>

# U OpenCV postoji implementirana metoda [kmeans](https://docs.opencv.org/4.4.0/d5/d38/group__core__cluster.html#ga9a34dc06c6ec9460e90860f15bcd2f88) koja ima sledeće ulazne parametre:
# * **samples** - uzorci na osnovu kojih će se vršiti klasterovanje, podaci moraju biti u obliku matrice ***koja sadrži np.float32 vrednosti gde svaka kolona predstavlja jedno obeležje (feature)***
# * **K** - broj klastera
# * **bestLabels** - parametar koji predstavlja i izlaz, niz celih brojeva koji čuvaju vrednost indeksa centra za svaki uzorak
# * **criteria** - kriterijum zaustavljanja, maksimalan broj iteracija ili željena tačnost
# * **attempts** - broj ponavljanja algoritma sa različitom inicijalizacijom početnih oznaka
# * **flags** - oznaka koja može imati jednu od vrednosti: KMEANS_RANDOM_CENTERS, KMEANS_PP_CENTERS, KMEANS_USE_INITIAL_LABELS 
# 
# Kao izlaz vraća tri vrednosti: kompaktnost (zbir kvadratnog rastojanja svake tačke od odgovarajućeg klastera), niz oznaka za svaki piksel i centre klastera.

# ### Početak implementacije
# 
# Za početak, učitavamo sliku.

# In[28]:


image = load_image('images/nature.PNG')
display_image(image, color=True)


# Kako *kmeans* na ulazu zahteva matricu *float32* vrednosti u kojoj svaka kolona predstavlja jedno obeležje, moramo sliku prebaciti u željeni oblik. Slika u boji ima tri obeležja: R, G i B, što znači da je moramo prebaciti u niz veličine Mx3, gde M predstavlja ukupan broj piksela. 

# In[29]:


# sliku konvertujemo u 2D niz piksela sa po 3 vrednosti (RGB)
pixel_values = image.reshape((-1,3)) 
# konvertujemo sve u float32
pixel_values = np.float32(pixel_values)
print(pixel_values.shape)


# Definišemo kriterijum zaustavljanja i broj klastera K i pozivamo metodu. 
# 
# Kriterijum zaustavljanja predstavlja tuple od 3 vrednosti (type, max_iter, epsilon). Tip zaustavljanja može uzeti jednu od tri vrednosti:
# * cv.TERM_CRITERIA_EPS - algoritam se zaustavlja ako je postignuta određena tačnost, epsilon
# * cv.TERM_CRITERIA_MAX_ITER - algoritam se zaustavlja nakon određenog broja iteracija, max_iter
# * cv.TERM_CRITERIA_EPS + cv.TERM_CRITERIA_MAX_ITER - algoritam se zaustavlja nakon što je ispunjen bilo koji od navedenih uslova

# In[30]:


criteria = (cv2.TERM_CRITERIA_EPS + cv2.TERM_CRITERIA_MAX_ITER, 100, 0.2) # poslednja 2 parametra menjati u zavisnnosti od slike
k = 3
_, labels, (centers) = cv2.kmeans(pixel_values, k, None, criteria, 10, cv2.KMEANS_RANDOM_CENTERS)


# Nakon klasterovanja, svakom pikselu pridružujemo vrednost centra klastera kojem pripada, a potom sliku vraćamo u originalni oblik.

# In[31]:


# konvertujemo vrednosti u 8-bitne
centers = np.uint8(centers)
# mapiramo sve piksele na boju koja odgovara centru klastera
segmented_data = centers[labels.flatten()]
# vraćamo u dimenzije slike
segmented_image = segmented_data.reshape((image.shape))
display_image(segmented_image, color=True)


# Kako bismo utvrdili koji klaster se odnosi na koji deo, možemo te piksele prikazati u crnoj boji.

# In[34]:


# piksele klastera označenim brojem 2 pretvaramo u crne
masked_image = np.copy(image)
# sliku konvertujemo u vektor piksela dimenzija Mx3
masked_image = masked_image.reshape((-1, 3))
# biramo koji klaster želimo da izdvojimo
cluster = 2 # 0, 1, 2 - u zavisnosti od povrsine koju zelimo da obojimo
labels = labels.flatten()
masked_image[labels == cluster] = [0, 0, 0]
# vraćamo sliku u početni oblik
masked_image = masked_image.reshape(image.shape)
display_image(masked_image)


# ## Zadaci
# 
# 1. Na slici sa novčićima (**images/coins.jpg**), prebrojati koliko ima novčića
# 
# 2. Na slici sa crvenim krvnim zrncima (**images/bloodcells.jpg**), prebrojati koliko ima crvenih krvnih zrnaca.
# 
# 3. Na slici sa tabletama (**images/tablets.jpg**) prebrojati koliko ima tableta
# 
# 4. Na slike sa kafom (**images/coffee.gif**) prebrojati koliko ima zrna kafe.    
# 
# 5. Na slici sa kruškama (**images/pears.png**) prebrojati koliko ima krušaka.  
# 
# 6. Sa slike plaže (**images/beach.jpeg**) izdvojiti more
# 
# 7. Sa slike gde se nalazi konj (**images/horse.jpg**) izdvojiti konja i livadu
# 

# In[72]:


# 1. zadatak  # 3 iteracije dilacije, pa 3 iteracije erozije 
# ucitavanje slike
coins = load_image("images/coins.jpg")
# display_image(coins, color=True)
coins_bin = image_bin(image_gray(coins))

# dilacija
kernel = cv2.getStructuringElement(cv2.MORPH_CROSS, (3,3)) # MORPH_ELIPSE, MORPH_RECT
plt.figure()
scale_image = cv2.dilate(coins_bin, kernel, iterations=3)
# display_image(scale_image_d)

# erozija
# kernel = np.ones((3, 3))
# scale_image = cv2.erode(scale_image_d, kernel, iterations=3)

dist_coins_transform = cv2.distanceTransform(scale_image, cv2.DIST_L2, 5)

# izdvajanje regiona koji sigurno pripadaju pozadini (background)
sure_bg = cv2.dilate(opening, kernel, iterations=3)
# display_image(sure_bg)

ret, sure_fg = cv2.threshold(dist_coins_transform, 0.7 * dist_transform.max(), 255, 0) 
# display_image(sure_fg)

# izdvajanje ostalih delova za koje ne znamo da li predstavljaju background ili foreground
sure_fg = np.uint8(sure_fg)
unknown = cv2.subtract(sure_bg, sure_fg)
# display_image(unknown)

# marker labelling
ret, markers = cv2.connectedComponents(sure_fg)
# svim vrednostima dodajemo 1 kako pozadina ne bi imala vrednost 0 (ona je rezervisana za nepoznate regione)
markers = markers+1
# oznacavanje nepoznatih regiona sa 0
markers[unknown==255] = 0

rgba_img = label2rgb(markers)
# display_image(rgba_img, color=True)

markers = cv2.watershed(coins, markers)
coins[markers == -1] = [255, 0, 0]

rgba_img = label2rgb(markers)
# display_image(rgba_img, color=True)

unique_colours = {x for l in markers for x in l}
number = len(unique_colours) - 2
print(number)


# In[85]:


# 2. zadatak
# ucitavanje slike
blood = load_image("images/bloodcells.jpg")
blood_bin = image_bin(image_gray(blood))
display_image(blood_bin)

# dilacija
kernel = cv2.getStructuringElement(cv2.MORPH_CROSS, (3,3)) # MORPH_ELIPSE, MORPH_RECT
dilate_img = cv2.dilate(blood_bin, kernel, iterations=5)
# display_image(dilate_img)

# zatvaranje
kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (5, 5))
img_dil = cv2.dilate(dilate_img, kernel, iterations=1)
img_close = cv2.erode(img_dil, kernel, iterations=1)
# plt.imshow(img_close, 'gray')

dist_coins_transform = cv2.distanceTransform(img_close, cv2.DIST_L2, 5)

# izdvajanje regiona koji sigurno pripadaju pozadini (background)
sure_bg = cv2.dilate(img_close, kernel, iterations=3)
# display_image(sure_bg)

ret, sure_fg = cv2.threshold(dist_coins_transform, 0.7 * dist_transform.max(), 255, 0) 
# display_image(sure_fg)

# izdvajanje ostalih delova za koje ne znamo da li predstavljaju background ili foreground
sure_fg = np.uint8(sure_fg)
unknown = cv2.subtract(sure_bg, sure_fg)
# display_image(unknown)

# marker labelling
ret, markers = cv2.connectedComponents(sure_fg)
# svim vrednostima dodajemo 1 kako pozadina ne bi imala vrednost 0 (ona je rezervisana za nepoznate regione)
markers = markers+1
# oznacavanje nepoznatih regiona sa 0
markers[unknown==255] = 0

rgba_img = label2rgb(markers)
# display_image(rgba_img, color=True)

markers = cv2.watershed(blood, markers)
blood[markers == -1] = [255, 0, 0]

rgba_img = label2rgb(markers)
# display_image(rgba_img, color=True)

unique_colours = {x for l in markers for x in l}
number = len(unique_colours) - 2
print(number)


# In[ ]:





# In[ ]:




