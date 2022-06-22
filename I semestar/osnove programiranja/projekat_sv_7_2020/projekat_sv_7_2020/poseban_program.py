from datetime import date, datetime
import recepcioneri

def odradjen_ili_zakazan():
    datum = date.today()
    recnik_termina = recepcioneri.citanje_iz_fajla()
    for value in recnik_termina.values():
        fajl_datum = datetime.strptime(value[2], '%Y-%m-%d').date()
        if fajl_datum < datum and value[9] != 'brisanje':
            value[9] = 'odrađen'
        elif fajl_datum == datum and value[9] != 'brisanje':
            fajl_vreme = datetime.strptime(value[3], '%H:%M').time()
            if fajl_vreme <= datetime.today().time():
                value[9] = 'odrađen'
            else:
                value[9] = 'zakazan'
        elif fajl_datum > datum and value[9] != 'brisanje':
            value[9] = 'zakazan'
    recepcioneri.pisanje_u_fajl(recnik_termina)


