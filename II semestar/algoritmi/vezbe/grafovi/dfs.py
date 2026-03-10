# (graf, cvor, recnik)
def dfs(g, u, discovered):
    """ Primenjuje DFS na neotkriven deo grafa(Graph) g počevši od cvora(Vertex) u.

    discovered je recnik koji mapira svaki cvor na ivicu koja je koriscen za njegovo pronalazenje
    prilikom DFS.
    Nove ivice se dodaju u ovaj recnik.
    """
    for e in g.incident_edges(u):    # za svaku odlaznu granu iz u
        v = e.opposite(u)
        if v not in discovered:        # provera da li je čvor već posećen
            discovered[v] = e            # e je ivica kojom je otkriven čvor v
            dfs(g, v, discovered)        # rekurzivna pretraga od v


def construct_path(u, v, discovered):
    """
    Rekonstrukcija puta. Vraca listu cvorova koje cine direktan put od u do v,
    ili prazna lista ako se do v ne moze doci iz u.

    discovered je rezultujuci recnik iz prethodnog poziva DFS sa pocetkom u u.
    """
    path = []                        # počinje s od prazne putanje
    if v in discovered:
        # formiramo listu od v do u i onda je na kraju obrnemo
        path.append(v)
        walk = v
        while walk is not u:
            e = discovered[walk]
            parent = e.opposite(walk)
            path.append(parent)
            walk = parent
        path.reverse()                 # obrtanje putanje
    return path


def dfs_complete(g):
    """Primenjuje DFS nad celim grafom i vraca cumu kao recnik.

    Rezultat mapira svaki cvor v na ivicu koja je koriscena za njegovo otkrivanje.
    (Cvorovi koji su koreni DFS stabla su mapirani na None.)
    """
    forest = {}
    for u in g.vertices():
        if u not in forest:
            forest[u] = None             # u će biti koren stabla
            dfs(g, u, forest)
    return forest
