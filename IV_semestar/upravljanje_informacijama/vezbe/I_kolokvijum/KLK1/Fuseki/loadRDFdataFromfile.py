from rdflib import Graph
from pyfuseki import FusekiUpdate

g = Graph()

# g.parse('data/fk.nt')
g.parse('rdf/fk.rdf')
fuseki = FusekiUpdate('http://localhost:3030', 'dataset')
fuseki.insert_graph(g)
