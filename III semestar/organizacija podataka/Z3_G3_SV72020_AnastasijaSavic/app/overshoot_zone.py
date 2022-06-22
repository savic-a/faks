from app.binary_file import BinaryFile
from app.record import Record
from app.constants import *


class OvershootZone(BinaryFile):
    def __init__(self, filename, record, blocking_factor, empty_key=-1):
        BinaryFile.__init__(self, filename, record, blocking_factor, empty_key)

    def init_file(self):
        with open(self.filename, "wb") as f:
            f.seek(4, 0)  # preskacemo zaglavlje
            # inicijalizacija datoteke podrazumeva unos bloka koji sadrzi prazne slogove
            block = self.blocking_factor * [self.get_empty_rec()]
            self.write_block(f, block)

    def write_header(self, rec):
        self.record = Record(ATTRIBUTES_2, FMT_2, CODING)
        binary_data = self.record.dict_to_encoded_values(rec)
        with open(self.filename, "wb") as f:
            f.write(binary_data)
        self.record = Record(ATTRIBUTES_Z, FMT, CODING)

    def read_header(self):
        self.record = Record(ATTRIBUTES_2, FMT_2, CODING)
        with open(self.filename, "rb+") as file:
            binary_data = file.read(4)          # 4 jer je integer
        header = self.record.encoded_tuple_to_dict(binary_data)
        self.record = Record(ATTRIBUTES_Z, FMT, CODING)
        return header

    def update_header(self, new_address):
        header = self.read_header()
        header['address'] = new_address
        self.record = Record(ATTRIBUTES_2, FMT_2, CODING)
        binary_data = self.record.dict_to_encoded_values(header)
        with open(self.filename, "rb+") as file:
            file.seek(4)  # preskacemo zaglavlje
            file_content = file.read()
            file.seek(0)
            file.write(binary_data + file_content)  # upisujemo novo zaglavlje
        self.record = Record(ATTRIBUTES_Z, FMT, CODING)

    def insert_record(self, rec):
        # provera da li vec postoji slog sa zadatim id-jem - svakom unosu prethodi pretraga!
        if self.find_by_id(rec.get("id")):
            return

        with open(self.filename, "rb+") as f:
            f.seek(-self.block_size, 2)  # citamo poslednji blok
            block = self.read_block(f)

            for i in range(self.blocking_factor):
                if block[i].get("id") == self.empty_key:  # trazimo prvi prazan slog
                    block[i] = rec
                    break

            i += 1

            if i == self.blocking_factor:  # provera da li smo popunili trenutni blok ili ne
                f.seek(-self.block_size, 1)
                self.write_block(f, block)
                block = self.blocking_factor * [self.get_empty_rec()]
                self.write_block(f, block)
            else:
                block[i] = self.get_empty_rec()
                f.seek(-self.block_size, 1)
                self.write_block(f, block)

    def __is_last(self, block):
        for i in range(self.blocking_factor):  # da li blok sadrzi neki slog koji je prazan?
            if block[i].get("id") == self.empty_key:
                return True
        return False

    def print_file(self):
        i = 0
        with open(self.filename, "rb+") as f:
            f.seek(4, 0)   # preskacemo zaglavlje TODO
            while True:
                block = self.read_block(f)

                if not block:
                    break

                i += 1
                print("Block {}".format(i))
                self.print_block(block)

    def find_by_id(self, id):
        i = 0
        with open(self.filename, "rb") as f:
            f.seek(4, 0)  # preskacemo zaglavlje TODO
            while True:
                block = self.read_block(f)

                # for j in range(self.blocking_factor):
                if block[0].get("id") == id:
                    return i, 0
                    # ukoliko smo naisli na prazan slog - stigli smo do kraja datoteke
                elif block[0].get("id") == self.empty_key:
                    return None

                i += 1

    def delete_by_id(self, id):
        found = self.find_by_id(id)

        if not found:
            return

        block_idx = found[0]
        rec_idx = found[1]

        with open(self.filename, "rb+") as f:
            while True:
                f.seek(block_idx * self.block_size)
                block = self.read_block(f)

                i = rec_idx
                # brisemo block[rec_idx] tako sto sve nakon njega pomeramo za jedno mesto ka levo
                while i < self.blocking_factor - 1:
                    block[i] = block[i + 1]
                    i += 1

                if self.__is_last(block):  # ako je poslednji blok - upisemo novi sadrzaj i zavrsavamo
                    f.seek(-self.block_size, 1)
                    self.write_block(f, block)
                    break
                # ako nije poslednji blok, onda pomeranje mora da se nastavi i u narednim blokovima,
                # dok ne stignemo do poslednjeg
                next_block = self.read_block(f)
                block[self.blocking_factor - 1] = next_block[0]  # poslednji iz trenutnog bloka = prvi iz narednog bloka
                f.seek(-2 * self.block_size, 1)
                self.write_block(f, block)

                block_idx += 1
                rec_idx = 0

    def get_empty_rec(self):
        return {'id': self.empty_key, 'vehicle code': '', 'date and time': '', 'class': '', 'passengers':
                self.empty_key, 'address': 0}
