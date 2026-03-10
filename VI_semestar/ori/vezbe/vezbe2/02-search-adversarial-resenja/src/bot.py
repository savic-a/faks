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
        Perspective = True ? na potezu je beli
        Implementirati heuristiku
        :return:
        """
        # TODO 2: Implementirati evaluacionu funkciju
        score = 0
        material = {"P": 1, "N": 3, "B": 3, "R": 5, "Q": 9, "K": 100}
        for square in chess.SQUARES:
            piece = position.piece_at(square)
            if piece is not None:
                value = material[piece.symbol().upper()]
                if piece.color == chess.WHITE:
                    score += value
                else:
                    score -= value

        if position.is_checkmate():
            return 100 if position.turn else -100
        if position.is_stalemate() or position.is_insufficient_material() or position.is_fivefold_repetition():
            return 0

        white_pawns = position.pieces(chess.PAWN, chess.WHITE)
        black_pawns = position.pieces(chess.PAWN, chess.BLACK)
        score += len(white_pawns) - len(black_pawns)
        for square in white_pawns:
            if chess.square_file(square) in [0, 7]:
                score -= 0.5
        for square in black_pawns:
            if chess.square_file(square) in [0, 7]:
                score += 0.5

        white_pawns = position.pieces(chess.PAWN, chess.WHITE)
        pawns_on_second_rank = {chess.A2, chess.B2, chess.F2, chess.G2}
        white_pawns_on_second_rank = white_pawns & pawns_on_second_rank
        if len(white_pawns_on_second_rank) == 4:
            score += 0.35

        black_pawns = position.pieces(chess.PAWN, chess.BLACK)
        pawns_on_seventh_rank = {chess.A7, chess.B7, chess.F7, chess.G7, chess.H7}
        black_pawns_on_seventh_rank = black_pawns & pawns_on_seventh_rank
        if len(black_pawns_on_seventh_rank) == 4:
            score -= 0.35

        white_mobility = len(list(position.legal_moves))
        black_mobility = len(list(position.copy().legal_moves))
        score += white_mobility - black_mobility

        knights = position.pieces(chess.KNIGHT, chess.WHITE)
        for knight_sq in knights:
            centralization_score = 4 - self.distance(chess.E4, knight_sq)
            score += centralization_score

        bishops = position.pieces(chess.BISHOP, chess.WHITE)
        for bishop_sq in bishops:
            score += sum(1 for sq in chess.SQUARES if self.same_diagonal(bishop_sq, sq) and position.piece_at(sq) is None)

        return score

    def distance(self, s1, s2):
        r1, f1 = self.get_rank_file(s1)
        r2, f2 = self.get_rank_file(s2)

        dr = abs(r1 - r2)
        df = abs(f1 - f2)

        return max(dr, df)

    def get_rank_file(self, square):
        rank = int(square / 8)
        file = square % 8
        return rank, file

    def same_diagonal(self, square1, square2):
        # get the rank and file of each square
        rank1, file1 = divmod(square1, 8)
        rank2, file2 = divmod(square2, 8)
        dr = abs(rank1 - rank2)
        df = abs(file1 - file2)

        return dr == df


class Minimax(AdversarialSearch):

    def perform_adversarial_search(self):
        """
        self.board ima atribut legal_moves koji se moze iskoristiti
        za dobavljanje svih legalnih poteza u jednoj poziciji
        """
        # TODO 1: Implementirati minimax algoritam
        best_score, best_move = self.minimax(self.board, 0, self.turn)
        return best_move

    def minimax(self, position, depth, maximizing):
        if depth == self.max_depth:
            return self.evaluate_position(position), None

        legal_moves = list(position.legal_moves)
        random.shuffle(legal_moves)

        if maximizing:
            best_score = -float('inf')
            best_move = None
            for move in legal_moves:
                updated_board = position.copy()
                updated_board.push(move)
                score, _ = self.minimax(updated_board, depth + 1, False)
                if score > best_score:
                    best_score = score
                    best_move = move
            return best_score, best_move
        else:
            best_score = float('inf')
            best_move = None
            for move in legal_moves:
                updated_board = position.copy()
                updated_board.push(move)
                score, _ = self.minimax(updated_board, depth + 1, True)
                score = -score
                if score < best_score:
                    best_score = score
                    best_move = move
            return best_score, best_move


class AlphaBeta(AdversarialSearch):

    def perform_adversarial_search(self):
        # TODO 3: Implementirati alpha-beta-algoritam
        pass
        best_score, best_move = self.alpha_beta_pruning(self.board, 0, self.turn)
        return best_move

    def alpha_beta_pruning(self, position, depth, maximizing, alpha=-float('inf'), beta=float('inf')):
        if depth == self.max_depth:
            return self.evaluate_position(position), None

        legal_moves = list(position.legal_moves)
        random.shuffle(legal_moves)

        if maximizing:
            best_score = -float('inf')
            best_move = None
            for move in legal_moves:
                updated_board = position.copy()
                updated_board.push(move)
                score, _ = self.alpha_beta_pruning(updated_board, depth + 1, False, alpha, beta)
                alpha = max(score, alpha)
                if score > best_score:
                    best_score = score
                    best_move = move
                if beta <= alpha:
                    break
            if best_move is None and len(legal_moves) > 0:  # ako mu sledi mat
                best_move = legal_moves[0]
            return best_score, best_move
        else:
            best_score = float('inf')
            best_move = None
            for move in legal_moves:
                updated_board = position.copy()
                updated_board.push(move)
                score, _ = self.alpha_beta_pruning(updated_board, depth + 1, True, alpha, beta)
                beta = min(score, beta)
                if score < best_score:
                    best_score = score
                    best_move = move
                if beta <= alpha:
                    break

            if best_move is None and len(legal_moves) > 0:  # ako mu sledi mat
                best_move = legal_moves[0]
            return best_score, best_move
