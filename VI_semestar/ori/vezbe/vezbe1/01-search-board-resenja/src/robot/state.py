# from abc import *


# class State(object):
#     """
#     Apstraktna klasa koja opisuje stanje pretrage.
#     """

#     @abstractmethod
#     def __init__(self, board, parent=None, position=None, goal_position=None, checkpoints=None, teleports=None, teleport=False):
#         """
#         :param board: Board (tabla)
#         :param parent: roditeljsko stanje
#         :param position: pozicija stanja
#         :param goal_position: pozicija krajnjeg stanja
#         :return:
#         """
#         self.board = board
#         self.parent = parent  # roditeljsko stanje

#         if checkpoints == None:
#             self.checkpoints = tuple(board.find_all_positions(self.get_checkpoint_code()))
#         else:
#             self.checkpoints = checkpoints

#         self.teleport = teleport
#         if teleports == None:
#             self.teleports = tuple(board.find_all_positions(self.get_teleport_code()))
#         else:
#             self.teleports = teleports

#         if self.parent is None:  # ako nema roditeljsko stanje, onda je ovo inicijalno stanje
#             self.position = board.find_position(self.get_agent_code())  # pronadji pocetnu poziciju
#             self.goal_position = board.find_position(self.get_agent_goal_code())  # pronadji krajnju poziciju
#         else:  # ako ima roditeljsko stanje, samo sacuvaj vrednosti parametara
#             self.position = position
#             self.goal_position = goal_position
#         self.depth = parent.depth + 1 if parent is not None else 1  # povecaj dubinu/nivo pretrage

#     def get_next_states(self):
#         new_positions = self.get_legal_positions()  # dobavi moguce (legalne) sledece pozicije iz trenutne pozicije
#         next_states = []
#         # napravi listu mogucih sledecih stanja na osnovu mogucih sledecih pozicija
#         for new_position in new_positions:
#             next_state = self.__class__(self.board, self, new_position, self.goal_position, self.checkpoints, self.teleports, self.teleport)
#             next_states.append(next_state)
#         return next_states


#     @abstractmethod
#     def get_agent_code(self):
#         """
#         Apstraktna metoda koja treba da vrati kod agenta na tabli.
#         :return: str
#         """
#         pass

#     @abstractmethod
#     def get_agent_goal_code(self):
#         """
#         Apstraktna metoda koja treba da vrati kod agentovog cilja na tabli.
#         :return: str
#         """
#         pass

#     @abstractmethod
#     def get_checkpoint_code(self):
#         """
#         Apstraktna metoda koja treba da vrati kod checkpoint-a na tabli.
#         :return: str
#         """
#         pass

    
#     @abstractmethod
#     def get_teleport_code(self):
#         """
#         Apstraktna metoda koja treba da vrati kod teleporta-a na tabli.
#         :return: str
#         """
#         pass

#     @abstractmethod
#     def get_legal_positions(self):
#         """
#         Apstraktna metoda koja treba da vrati moguce (legalne) sledece pozicije na osnovu trenutne pozicije.
#         :return: list
#         """
#         pass

#     @abstractmethod
#     def is_final_state(self):
#         """
#         Apstraktna metoda koja treba da vrati da li je treuntno stanje zapravo zavrsno stanje.
#         :return: bool
#         """
#         pass

#     @abstractmethod
#     def unique_hash(self):
#         """
#         Apstraktna metoda koja treba da vrati string koji je JEDINSTVEN za ovo stanje
#         (u odnosu na ostala stanja).
#         :return: str
#         """
#         pass
    
#     @abstractmethod
#     def get_cost_estimate(self):
#         """
#         Apstraktna metoda koja treba da vrati procenu cene
#         (vrednost heuristicke funkcije - h(n)) za ovo stanje.
#         Koristi se za vodjene pretrage.
#         :return: float
#         """
#         pass
    
#     @abstractmethod
#     def get_current_cost(self):
#         """
#         Apstraktna metoda koja treba da vrati stvarnu trenutnu cenu za ovo stanje, odnosno g(n)
#         Koristi se za vodjene pretrage.
#         :return: float
#         """
#         pass


# class RobotState(State):

    
#     def __init__(self, board, parent=None, position=None, goal_position=None, checkpoints=None, teleports=None, teleport=False):
#         super(self.__class__, self).__init__(board, parent, position, goal_position, checkpoints, teleports, teleport)
#         # posle pozivanja super konstruktora, mogu se dodavati "custom" stvari vezani za stanje

#         # Akumuliranje cene u stanju
#         if self.parent == None:
#             self.cost = 0
#         else:
#             self.cost = self.parent.cost + 1

#         # TODO 5: prosiriti stanje sa informacijom da li je robot pokupio kutiju
#         # TODO 6: prosiriti stanje sa informacijom o preostalim kutijama
        
#         if self.position in self.checkpoints:
#             self.checkpoints = tuple([c for c in self.checkpoints if c != self.position])
        
#         # TODO 7: prosiriti stanje sa informacijom o teleportovanju
#         if self.teleport:
#             self.teleport = False
#             self.teleports = tuple([t for t in self.teleports if t != self.position])
#         elif self.position in self.teleports:
#             self.teleports = tuple([t for t in self.teleports if t != self.position])
#             self.teleport = True

        

#     def get_agent_code(self):
#         return 'r'

#     def get_agent_goal_code(self):
#         return 'g'
    
#     def get_checkpoint_code(self):
#         return 'b'
    
#     def get_teleport_code(self):
#         return 'y'

#     def get_legal_positions(self):
#         # moguci smerovi kretanja robota (desno, levo, dole, gore)
#         actions = [(0, 1), (0, -1), (1, 0), (-1, 0)]

#         # TODO 7: teleport
#         if self.teleport and len(self.teleports) != 0:
#             new_positions = [self.teleports[0]]
#             return new_positions

#         row, col = self.position  # trenutno pozicija
#         new_positions = []
#         for d_row, d_col in actions:  # za sve moguce smerove
#             new_row = row + d_row  # nova pozicija po redu
#             new_col = col + d_col  # nova pozicija po koloni
#             # ako nova pozicija nije van table i ako nije zid ('w'), ubaci u listu legalnih pozicija
#             if 0 <= new_row < self.board.rows and 0 <= new_col < self.board.cols and self.board.data[new_row][new_col] != 'w':
#                 new_positions.append((new_row, new_col))
#         return new_positions

#     def is_final_state(self):
#         return self.position == self.goal_position and len(self.checkpoints) == 0

#     def unique_hash(self):
#         return str(self.position)+";"+str(self.checkpoints)
    
#     def get_cost_estimate(self):
#         pos = self.position
#         goal = self.goal_position
        
#         return abs(pos[0]-goal[0]) + abs(pos[1] - goal[1])
    
#     def get_current_cost(self):
#         return self.cost
from abc import *


class State(object):
    """
    Apstraktna klasa koja opisuje stanje pretrage.
    """

    @abstractmethod
    def __init__(self, board, parent=None, position=None, goal_position=None, checkpoints=None, teleports=None, teleport=False):
        """
        :param board: Board (tabla)
        :param parent: roditeljsko stanje
        :param position: pozicija stanja
        :param goal_position: pozicija krajnjeg stanja
        :return:
        """
        self.board = board
        self.parent = parent  # roditeljsko stanje

        if checkpoints == None:
            self.checkpoints = tuple(board.find_all_positions(self.get_checkpoint_code()))
        else:
            self.checkpoints = checkpoints

        self.teleport = teleport
        if teleports == None:
            self.teleports = tuple(board.find_all_positions(self.get_teleport_code()))
        else:
            self.teleports = teleports

        if self.parent is None:  # ako nema roditeljsko stanje, onda je ovo inicijalno stanje
            self.position = board.find_position(self.get_agent_code())  # pronadji pocetnu poziciju
            self.goal_position = board.find_position(self.get_agent_goal_code())  # pronadji krajnju poziciju
        else:  # ako ima roditeljsko stanje, samo sacuvaj vrednosti parametara
            self.position = position
            self.goal_position = goal_position
        self.depth = parent.depth + 1 if parent is not None else 1  # povecaj dubinu/nivo pretrage

    def get_next_states(self):
        new_positions = self.get_legal_positions()  # dobavi moguce (legalne) sledece pozicije iz trenutne pozicije
        next_states = []
        # napravi listu mogucih sledecih stanja na osnovu mogucih sledecih pozicija
        for new_position in new_positions:
            next_state = self.__class__(self.board, self, new_position, self.goal_position, self.checkpoints, self.teleports, self.teleport)
            next_states.append(next_state)
        return next_states


    @abstractmethod
    def get_agent_code(self):
        """
        Apstraktna metoda koja treba da vrati kod agenta na tabli.
        :return: str
        """
        pass

    @abstractmethod
    def get_agent_goal_code(self):
        """
        Apstraktna metoda koja treba da vrati kod agentovog cilja na tabli.
        :return: str
        """
        pass

    @abstractmethod
    def get_checkpoint_code(self):
        """
        Apstraktna metoda koja treba da vrati kod checkpoint-a na tabli.
        :return: str
        """
        pass

    
    @abstractmethod
    def get_teleport_code(self):
        """
        Apstraktna metoda koja treba da vrati kod teleporta-a na tabli.
        :return: str
        """
        pass

    @abstractmethod
    def get_legal_positions(self):
        """
        Apstraktna metoda koja treba da vrati moguce (legalne) sledece pozicije na osnovu trenutne pozicije.
        :return: list
        """
        pass

    @abstractmethod
    def is_final_state(self):
        """
        Apstraktna metoda koja treba da vrati da li je treuntno stanje zapravo zavrsno stanje.
        :return: bool
        """
        pass

    @abstractmethod
    def unique_hash(self):
        """
        Apstraktna metoda koja treba da vrati string koji je JEDINSTVEN za ovo stanje
        (u odnosu na ostala stanja).
        :return: str
        """
        pass
    
    @abstractmethod
    def get_cost_estimate(self):
        """
        Apstraktna metoda koja treba da vrati procenu cene
        (vrednost heuristicke funkcije - h(n)) za ovo stanje.
        Koristi se za vodjene pretrage.
        :return: float
        """
        pass
    
    @abstractmethod
    def get_current_cost(self):
        """
        Apstraktna metoda koja treba da vrati stvarnu trenutnu cenu za ovo stanje, odnosno g(n)
        Koristi se za vodjene pretrage.
        :return: float
        """
        pass


class RobotState(State):

    
    def __init__(self, board, parent=None, position=None, goal_position=None, checkpoints=None, teleports=None, teleport=False, closest=True):
        super(self.__class__, self).__init__(board, parent, position, goal_position, checkpoints, teleports, teleport)

        if closest:
            self.closest = self.find_closest()
        # posle pozivanja super konstruktora, mogu se dodavati "custom" stvari vezani za stanje
        # TODO 5: prosiriti stanje sa informacijom da li je robot pokupio kutiju
        # TODO 6: prosiriti stanje sa informacijom o preostalim kutijama
        # TODO 7: prosiriti stanje sa informacijom o teleportovanju

        print(self.closest)
        if self.closest:
            if self.position == self.closest:
                self.closest = False
                closest = False
                self.took_box = True


                # self.checkpoints = tuple([c for c in self.checkpoints if c != self.position])

        # if self.position in self.checkpoints:
        #     new_checkpoints = []
        #     for c in self.checkpoints:
        #         if c != self.position:
        #             new_checkpoints.append(c)
        #     self.checkpoints = tuple(new_checkpoints)

        

    def get_agent_code(self):
        return 'r'

    def get_agent_goal_code(self):
        return 'g'
    
    def get_checkpoint_code(self):
        return 'b'
    
    def get_teleport_code(self):
        return 'y'

    def get_legal_positions(self):
        invalid_boxes = tuple(c for c in self.checkpoints if c != self.closest)
        print(invalid_boxes)

        # moguci smerovi kretanja robota (desno, levo, dole, gore)
        actions = [(0, 1), (0, -1), (1, 0), (-1, 0)]

        # TODO 7: implementirati teleportovanje

        row, col = self.position  # trenutno pozicija
        new_positions = []
        for d_row, d_col in actions:  # za sve moguce smerove
            new_row = row + d_row  # nova pozicija po redu
            new_col = col + d_col  # nova pozicija po koloni
            # ako nova pozicija nije van table i ako nije zid ('w'), ubaci u listu legalnih pozicija
            if 0 <= new_row < self.board.rows and 0 <= new_col < self.board.cols and self.board.data[new_row][new_col] != 'w' and (new_row, new_col) not in invalid_boxes:
                new_positions.append((new_row, new_col))
        return new_positions

    def is_final_state(self):
        return self.position == self.goal_position and hasattr(RobotState, 'took_box')

    def unique_hash(self):
        return str(self.position)+";"+str(self.checkpoints)
    
    def get_cost_estimate(self):
        return abs(self.position[0] - self.goal_position[0]) + abs(self.position[1] - self.goal_position[1]) 
    
    def get_current_cost(self):
        # cena je cena roditelja + 1
        return self.cost
    
    def find_closest(self):
        closest = self.checkpoints[0]
        for i in range(1, len(self.checkpoints)-1):
            if abs(10 - closest[0]) + closest[1] > abs(10 - self.checkpoints[i][0]) + self.checkpoints[i][1]:
                closest = self.checkpoints[i]
        return closest

