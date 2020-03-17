from sys import maxsize

def get_closest_node(line, visited):
    smallest = maxsize
    ind = -1
    i = -1
    for node in line:
        i = i+1
        if node != 0 and node < smallest and  i not in visited:
            smallest = node
            ind = i
    return ind

def tsp(s,  graph):
    visited = []
    minPath = 0
    current = s
    minPathNodes = []
    minPathNodes.append(s+1)
    visited.append(s)
    for i in range(len(graph)):
        closest = get_closest_node(graph[current], visited)
        if closest == -1:
            break
        visited.append(closest)
        minPath += graph[current][closest]
        minPathNodes.append(closest+1)
        current = closest
    minPath += graph[current][s]
    return minPath, minPathNodes

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

    print(tsp(0, graph))
    print(tsp(0, graph2))
    print(tsp(0, graph3))
    print(tsp(0, graph4))

