import textwrap

# Dodatni napredni bonus zadatak: Pogledajte sadrza csv fajla SalesJan2009. Treba da se za svaku drzavu nadje najskuplja transakcija za svaku od kreditnih kartica:
# Primer resenja:
# Amerika - [visa-1200,Mastercard-3420,dina-1234,amex-3452]
# Srbija - [visa-1111, mastercard-2341,dina-1167]

# Pomocna funkcija za pretvaranje hash mape (dict) u listu parova. Trebace vam u map_implementatipon funkciji
def dict_to_list_of_tuples(dict):
    list_of_tuples = []

    #Ako imate python 3 umesto dict.iteritems() treba da stavite dict.items()
    for key, value in dict.items():
        list_of_tuples.append((key,value))

    return list_of_tuples

# Treba da vrati list key-value parova (u pythonu to moze biti lista tuple-ova)
# Data argument ce biti string 
def map_function_implementation(data):
    words_dict = {}
    list_of_words = data.split(" ") 
    
    for word in list_of_words:
        if word in words_dict.keys():
            words_dict[word] += 1
        else:
            words_dict[word] = 1

    return dict_to_list_of_tuples(words_dict)

def shuffle_implementation(data):
    sorted_by_first = sorted(data, key=lambda tup: tup[0])
    return sorted_by_first  

# Uzima listu key-value parova, treba ba vrati listu key-value parova
def reduce_function_implementation(data):
    reduce_dict = {}

    for pair in data:
        if pair[0] in reduce_dict.keys():
            reduce_dict[pair[0]] += pair[1]
        else: 
            reduce_dict[pair[0]] = pair[1]

    return dict_to_list_of_tuples(reduce_dict)

if __name__=="__main__":
    with open("./data/dq.txt") as f:
        data = f.read()

    # Podela fajla na 4 manje delove koji ce se 'paralelno' obradjivati
    data_chunks=textwrap.wrap(data,len(data)/4)

    returned_data=[]
    for chunk in data_chunks:
        ret_word_count = map_function_implementation(chunk)
        # Spajamo sve sto nam vrati svaka 'instanca' map funkcije u jednu list
        returned_data.extend(map_function_implementation(chunk))
    
    # final_result = reduce_function_implementation(returned_data)
    # print(len(returned_data))
    # print(len(final_result))

    # Sta ako hocemo da pokrenemo 2 instance reduce funkcije.
    # Hint: nece. Sto, i kako prepraviti kod da vrati pravilan rezultat
    # Kod ispod otkomentarisati kad uradite map i reduce funkcije
    returned_data = shuffle_implementation(returned_data)   # sorting

    middle = len(returned_data)//2
    while(returned_data[middle][0] == returned_data[middle+1][0]):
        middle += 1

    first_half = returned_data[:middle]   # first half 
    second_half = returned_data[middle:]  # second half

    final_result = reduce_function_implementation(first_half)
    final_result.extend(reduce_function_implementation(second_half))
    print(len(final_result))