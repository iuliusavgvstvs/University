
# problem using naive approach.
from sys import maxsize



# implementation of traveling Salesman Problem
def travellingSalesmanProblem(graph, s, V):
    # store all vertex apart from source vertex
    vertex = []
    shortestPa = [];
    for i in range(V):
        if i != s:
            vertex.append(i)
            # store minimum weight Hamiltonian Cycle
    min_path = maxsize
    while True:
        pa = []
        # store current Path weight(cost)
        current_pathweight = 0

        # compute current path weight
        k = s
        pa.append(s)
        for i in range(len(vertex)):
            current_pathweight += graph[k][vertex[i]]
            k = vertex[i]
            pa.append(k)
        current_pathweight += graph[k][s]

        # update minimum
        if min_path > current_pathweight:
            min_path = current_pathweight
            shortestPa = pa
       # pa.append(s)
        if not next_permutation(vertex):
            break

    return min_path, shortestPa


# next_permutation implementation
def next_permutation(L):
    n = len(L)
    i = n - 2
    while i >= 0 and L[i] >= L[i + 1]:
        i -= 1
    if i == -1:
        return False
    j = i + 1
    while j < n and L[j] > L[i]:
        j += 1
    j -= 1
    L[i], L[j] = L[j], L[i]
    left = i + 1
    right = n - 1
    while left < right:
        L[left], L[right] = L[right], L[left]
        left += 1
        right -= 1
    return True



# Driver Code
if __name__ == "__main__":
    # matrix representation of graph
    graph = [[0, 1, 2, 4], [1, 0, 3, 15],
             [2, 3, 0, 6], [4, 15, 6, 0]]
    graph2 = [[0, 10, 20, 40], [10, 0, 30, 150],
              [20, 30, 0, 60], [40, 150, 60, 0]]
    graph3 = [[0, 11, 2, 4, 3, 7], [11, 0, 3, 5, 9, 7],
              [2, 3, 0, 6, 10, 8], [4, 5, 6, 0, 12, 9],
              [3, 9, 10, 12, 0, 5], [7, 7, 8, 9, 5, 0]]
    graph4 = [[0, 11, 2, 4, 3, 7, 2, 4], [11, 0, 3, 5, 9, 7, 3, 3],
              [2, 3, 0, 6, 10, 8, 4, 2], [4, 5, 6, 0, 12, 9, 5, 1],
              [3, 9, 10, 12, 0, 5, 6, 3], [7, 7, 8, 9, 5, 0, 7, 5],
              [2, 3, 4, 5, 6, 7, 0, 6], [4, 3, 2, 1, 3, 5, 6, 0]]
    s = 0
    #mi, pa = travellingSalesmanProblem(graph, s, 4)
    #print(travellingSalesmanProblem(graph2, s, 4))
    #print(travellingSalesmanProblem(graph3, s, 6))
    #print(travellingSalesmanProblem(graph4, s, 8))
    print(tsp(0, graph3))




