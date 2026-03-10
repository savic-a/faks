def bfs(g, s, discovered):
    """Primenjuje BFS za neotkriven deo grafa(Graph) g pocevsi od cvora(Vertex) s.

        discovered je recnik koji mapira svaki cvor na ivicu koja je koriscen za njegovo pronalazenje
        prilikom BFS (prvobitno treba da bude mapiran na None).
        Nove ivice se dodaju u ovaj recnik.
    """

    level = [s]                        # prvi nivo uključuje samo s
    while len(level) > 0:
        next_level = []                  # priprema za skupljanje novih čvorova
        for u in level:
            for e in g.incident_edges(u):  # za svaki odlazni čvor iz u
                v = e.opposite(u)
                if v not in discovered:      # v je neposećen čvor
                    discovered[v] = e          # e je ivica preko koje je otkriven čvor v
                    next_level.append(v)       # v će biti razmotreno u sledećem prolazu
        level = next_level               # menjamo oznaku 'next' sledećeg nivoa da postane trenutni


def bfs_complete(g):
    """Primenjuje BFS nad celim grafom i vraca sumu(шуму) kao recnik.

        Rezultat mapira svaki cvor v na ivicu koja je koriscena za njegovo otkrivanje.
        (Cvorovi koji su koreni BFS stabla su mapirani na None.)
    """
    forest = {}
    for u in g.vertices():
        if u not in forest:
            forest[u] = None            # u će biti koren stabla
            bfs(g, u, forest)
    return forest
