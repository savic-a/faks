import requests
import pprint

query = """SELECT * WHERE 
    ?person <http://www.ftn.uns.ac.rs/rdf/examples/predicate/iz> "London"}"""

result = requests.post('http://localhost:3030/dataset/sparql', 
    params={'query': query})
result = result.json()['results']['bindings']

pprint.pprint(result)