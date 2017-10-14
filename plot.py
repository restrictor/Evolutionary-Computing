import pandas as pd
import matplotlib as mpl
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
import numpy as np
import seaborn as sns

with open('Katsuura.txt') as f:
    lines = f.readlines()

pop = []
upd = []
learning = []
score = []

for i in range(0,len(lines)):
    print(lines[i])
    if lines[i].split()[0] == 'Score:':
        score.append(float(lines[i].split()[1]))
    if lines[i].split()[0] == 'Settings:':
        pop.append(float(lines[i].split()[1].strip(',')))
        learning.append(float(lines[i].split()[2].strip(',')))
        upd.append(float(lines[i].split()[3]))

d = pd.DataFrame({'pop' : pop, 'upd' : upd, 'learning' : learning, 'score' : score})

uniUpd = d['upd'].unique()
l = []
for up in uniUpd:
    temp = (d.loc[d['upd']==up])
    temp = temp.groupby(temp.index // 5).mean()
    temp = temp.reset_index(drop=True)
    l.append(temp)

label = []
label.append("update = 1")
label.append("update = 5")
label.append("update = 10")
label.append("update = 15")
label.append("update = 20")

fig, axn = plt.subplots(1, 5, sharex=True, sharey=True)
cbar_ax = fig.add_axes([.91, .3, .03, .4])
fig.set_figheight(4)
fig.set_figwidth(22)
for i, ax in enumerate(axn.flat):
    sns.heatmap(l[i].set_index(['learning', 'pop']).score.unstack(0), ax=ax,
                cbar=i == 0,
                vmin=0, vmax=10,
                cbar_ax=None if i else cbar_ax)
    print(l[i].set_index(['learning', 'pop']).score.unstack(0))
    ax.set_title(label[i])
plt.savefig('Katsuura.png',bbox_inches='tight')
