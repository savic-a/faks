import numpy as np


# UKRSTANJE
def crossover(parents):
    children = []
    # za svaki par roditelja
    for father, mother in parents:

        r = np.random.uniform(0, 1)
        child1, child2 = [], []
        # za svaku vrednost roditelja
        for i in range(0, len(father)):
            # svako dete dobija deo gena od oba roditelja
            child1.append(r*father[i] + (1 - r)*mother[i])
            child2.append((1 - r)*father[i] + r*mother[i])
        children.append(child1)
        children.append(child2)

    return children


# MUTACIJA
def mutation(population, mutation_rate, mutation_width):  # jedinke, vrednost verovatnoce mutacije, sirina mutacije
    mutated_individuals = []
    # za svaku jedinku u populaciji
    for individual in population:
        x = []
        # za svaku vrednost u jedinci
        for i in range(0, len(individual)):
            if np.random.uniform(0, 1) < mutation_rate:  # uslov da li ce se izvrsiti mutacija
                r = np.random.uniform(0, 1)
                x.append(individual[i] + mutation_width * 2 * (r - 0.5))   # [-mutation_width, mutation_width]
            else:
                x.append(individual[i])
        mutated_individuals.append(x)

    return mutated_individuals


# ELITIZAM - biranje nove populacije
def elitis(parents, children, elitis_rate, population_size):
    parents_size = int(np.round(population_size * elitis_rate))
    # ostavljamo deo roditelja i u narednoj generaciji
    return parents[:parents_size] + children[:(population_size - parents_size)]
