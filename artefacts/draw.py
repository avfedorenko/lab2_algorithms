import csv

import matplotlib.pyplot as plt
import os


def parse_csv(solution):
    n, preparation, answering = [], [], []
    with open(solution + ".csv") as f:
        file_reader = csv.reader(f, delimiter=";")
        for row in file_reader:
            n.append(int(row[0]))
            preparation.append(int(row[1])/1000)
            answering.append(int(row[2])/1000)
    return n, preparation, answering


def draw(n, bf, m, pst, title, is_log=False):
    plt.title(title)
    if is_log:
        plt.xscale('log')
        plt.yscale('log')
    plt.plot(n, bf, label="BruteForceSolution")
    plt.plot(n, m, label="MapSolution")
    plt.plot(n, pst, label="PersistentSegmentTreeSolution")
    plt.legend()
    plt.xlabel('rectangles and points')
    plt.ylabel('time, milliseconds)')
    plt.show()


bf_n, bf_p, bf_a = parse_csv("BruteForceSolution")
m_n, m_p, m_a = parse_csv("MapSolution")
pst_n, pst_p, pst_a = parse_csv("PersistentSegmentTreeSolution")
draw(bf_n, bf_p, m_p, pst_p, "Preparation")
draw(bf_n, bf_p, m_p, pst_p, "Preparation (log)", is_log=True)
draw(bf_n, bf_a, m_a, pst_a, "Answering")
draw(bf_n, bf_a, m_a, pst_a, "Answering (log)", is_log=True)
