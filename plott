import pandas as pd
import matplotlib as mpl
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
import numpy as np
import seaborn as sns
import scipy.stats as stats

with open('Schaffers.txt') as f:
    lines = f.readlines()

experiment = "update"

iters = []
iter = 0
max_iter = 0
for i in range(0,len(lines)):
    if lines[i].split() != []:
        if lines[i].split()[0] == "Iter:":
            iter = iter + 1

        if lines[i].split()[0] == "Run:":
            iters.append(iter)
            max_iter = max(iter, max_iter)
            iter = 0

iters.append(iter)
iters = iters[1:]
iter = max(max_iter,iter)

for k in range(0,3):
    print "parameter", k
    sigma = np.zeros((iter, 20))
    update = np.zeros((iter, 20))
    maxx = np.zeros((iter, 20))
    min = np.zeros((iter, 20))
    avg = np.zeros((iter, 20))
    uni = np.zeros((iter, 20))
    dist = np.zeros((iter, 20))
    max_end = []
    dist_list = []
    uni_list = []
    dist_start = 0
    uni_start = 0

    run = -1
    index = -1
    old_m = 0
    run_old_m = 0
    new_m = 0

    old_d = 0
    run_old_d = 0

    old_u = 0
    run_old_u = 0

    start = False
    for i in range(0,len(lines)):
        if (lines[i].split()[0] == 'Run:' and float(lines[i].split()[1]) > (k+1) * 20):
            break
        if (lines[i].split()[0] == 'Run:' and float(lines[i].split()[1]) >= (k * 20) + 1):
            start = True
        if start == True:
            #print(lines[i])
            if lines[i].split()[0] == 'Run:':
                run = run + 1# float(lines[i].split()[1]) - 1
                index = -1
            if lines[i].split()[0] == 'sigma:':
                index = index + 1
                sigma[index][run] = float(lines[i].split()[1])

            if lines[i].split()[0] == 'Update:':
                update[index][run] = float(lines[i].split()[1])

            if lines[i].split()[0] == 'max:':
                maxx[index][run] = float(lines[i].split()[1])
                if (run == run_old_m):
                    new_m = max( maxx[index][run], old_m )
                    old_m = new_m
                else:
                    max_end.append(old_m)
                    run_old_m = run_old_m + 1
                    old_m = 0

            if lines[i].split()[0] == 'min:':
                min[index][run] = float(lines[i].split()[1])

            if lines[i].split()[0] == 'Average:':
                avg[index][run] = float(lines[i].split()[1])

            if lines[i].split()[0] == 'Uni:':
                uni[index][run] = float(lines[i].split()[1])
                if (run == run_old_u):
                    uni_start = uni_start + float(lines[i].split()[1])
                else:
                    uni_list.append(uni_start/iters[(run-1) + (k*20)])
                    uni_start = 0
                    run_old_u = run_old_u + 1

            if lines[i].split()[0] == 'Dist:':
                dist[index][run] = float(lines[i].split()[1])
                if (run == run_old_d):
                    dist_start = dist_start + float(lines[i].split()[1])
                else:
                    dist_list.append(dist_start/iters[(run-1) + (k*20)])
                    dist_start = 0
                    run_old_d = run_old_d + 1


    dist_list.append(dist_start/iters[(k+1)*20 -1])
    uni_list.append(uni_start/iters[(k+1)*20 -1])
    max_end.append(new_m)

    #dist_list = [ round(elem, 1) for elem in dist_list ]
    #uni_list = [ round(elem, 0) for elem in uni_list ]
    #max_end = [ round(elem, 1) for elem in max_end ]

    print "dist_list" + experiment + str(k)  + " = ", dist_list
    print "uni_list" + experiment + str(k)  + " = ", uni_list
    print "max_end" + experiment + str(k)  + " = ", max_end

    print "dist_list_mean" + experiment + str(k)  + " = ", np.mean(dist_list)
    print "uni_list_mean" + experiment + str(k)  + " = ", np.mean(uni_list)
    print "max_end_mean" + experiment + str(k)  + " = ", np.mean(max_end)

    print "dist_list_std_" + experiment + str(k)  + " = ", np.std(dist_list)
    print "uni_list_std" + experiment + str(k)  + " = ", np.std(uni_list)
    print "max_end_std" + experiment + str(k)  + " = ", np.std(max_end)

    fig, axes = plt.subplots(nrows=4,ncols=5)
    fig.set_figheight(20)
    fig.set_figwidth(22)
    for i in range(0,4):
        for j in range(0,5):
            ax =pd.DataFrame({'sigma': 30*sigma[:, i+j],'update': update[:, i+j],'max': maxx[:, i+j],'min': min[:, i+j],
                      'avg': avg[:, i+j], 'uni': uni[:, i+j], 'dist': dist[:, i+j]}).plot(ax=axes[i,j])
        ax.set_title("test")
    name = "Katsuura" + str(k) + experiment + ".png"
    plt.savefig(name)

    fig = plt.plot()
    pd.DataFrame({'sigma': 30*sigma[:, 0],'update': update[:, 0],'max': maxx[:, 0],'min': min[:, 0],
                      'avg': avg[:, 0], 'uni': uni[:, 0], 'dist': dist[:, 0]}).plot()
    name = "Katsuura_one" + str(k) + experiment + ".png"
    plt.savefig(name)


    # DO STATS
    print "statistics sigma old update new"

    dist_o = [0.0536871336843667, 0.048802791250301354, 0.05317920432362226, 0.04537663761965294,
             0.04315713589681974, 0.040611398912280945, 0.059093820877224174, 0.050893547854067654,
             0.05170348466468317, 0.04072963663299945, 0.03589183815307649, 0.034086368225792155,
             0.04721887092639463, 0.04554171755389774, 0.04921059662659298, 0.04093383061895294,
             0.04519856205206964, 0.05199224903970534, 0.038339753654287066, 0.05815345293329621]
    uni_o = [1.0272, 1.02976, 1.0272, 1.02464, 1.0288, 1.024, 1.02496, 1.02528, 1.02944, 1.0256, 1.02656,
             1.01856, 1.02912, 1.0288, 1.02656, 1.01952, 1.03104, 1.03712, 1.02656, 1.03552]
    fit_o = [6.6237564843328602, 8.9265038451395728, 9.8450068708380467, 3.5742649886176832, 9.8956553084700545,
             4.9616223102552102, 7.1520092246419678, 4.8719263465392828, 7.2778966646393997, 9.2233181976686609,
             8.452187519765884, 4.3322738025819918, 8.8456559339767651, 6.5917932861468644, 9.5648574894033551,
             7.4910302094616075, 6.5834762742609287, 8.9631735591976014, 7.0115181338695178, 8.9664348986380755]
    mean_dist_o = 0.046690101575
    mean_uni_o = 1.027312
    mean_fit_o = 7.45771806742
    std_dist_o = 0.00680766887229
    std_uni_o = 0.00427445622273
    std_fit_o = 1.8589970168

    # normality
    print stats.shapiro(max_end)
    print stats.shapiro(dist_list)
    print stats.shapiro(uni_list)

    print "improvement fitness = ", np.mean(max_end) - mean_fit_o
    print stats.mannwhitneyu(max_end, fit_o)
    print stats.ranksums(max_end, fit_o)
    print "improvement distance = ", np.mean(dist_list) - mean_dist_o
    print stats.mannwhitneyu(dist_list, dist_o)
    print stats.ranksums(dist_list, dist_o)
    print "improvement uniqness = ", np.mean(uni_list) - mean_uni_o
    print stats.mannwhitneyu(uni_list, uni_o)
    print stats.ranksums(uni_list, uni_o)
    print "end"
