import random
from abc import abstractmethod

import chess


class AdversarialSearch(object):
    """
    Apstraktna klasa za suparnicku/protivnicku pretragu.
    """

    def __init__(self, board, max_depth, turn):
        """
        :param board: tabla koja predstavlja pocetno stanje
        :param max_depth: maksimalna dubina pretrage (koliko poteza unapred)
        :param turn: da li bot igra sa belim ili crnim figurama
        """
        self.board = board
        self.max_depth = max_depth
        self.turn = turn


    @abstractmethod
    def perform_adversarial_search(self):
        """
        Apstraktna metoda koja vrsi pretragu i vraca sledece stanje
        :return: odigran potez
        """
        pass

    def evaluate_position(self, position):
        """
        position: chess.Board instanca
        :return: number - vrednost evaluacije
        """
        # Jednostavna evaluacino funkcija

        # Provera da li je došlo do kraja partije
        if position.is_checkmate():
            return 10000 if position.turn else -10000
        if position.is_stalemate() or position.is_insufficient_material() or position.is_fivefold_repetition():
            return 0
        
        # Poredjenje ukupne vrednost crnih i belih figura
        score = 0
        material = {"P": 1, "N": 3, "B": 3, "R": 5, "Q": 9, "K": 100} # vrednovanje figura
        for square in chess.SQUARES:
            piece = position.piece_at(square)
            if piece is not None:
                value = material[piece.symbol().upper()]
                if piece.color == chess.WHITE:
                    score += value
                else:
                    score -= value

        return score



class Minimax(AdversarialSearch):

    def perform_adversarial_search(self):
        """
        self.board ima atribut legal_moves koji se moze iskoristiti
        za dobavljanje svih legalnih poteza u jednoj poziciji
        """
        # TODO 1: Implementirati minimax algoritam
        move = list(self.board.legal_moves)[0]  # prvi legalan potez
        return move


class AlphaBeta(AdversarialSearch):

    def perform_adversarial_search(self):
        # TODO 3: Implementirati alpha-beta-algoritam
        pass
