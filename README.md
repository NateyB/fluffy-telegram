# fluffy-telegram
CS 7333: Machine Learning

This repository contains all of my projects and the corresponding reports from
my Machine Learning course at the University of Tulsa. What follows is a
remarkably brief description of each project; for a more in-depth description
(and analyses), check the report subfolder of each project.

# Project 1: Unsupervised Clustering
This first project consisted of investigating unsupervised clustering, using two
algorithms in particular: EM for a Mixture of Guassians and k-means. These
two algorithms are iterative. EM assigns soft probabilities to the points that
it's clustering, and k-means assigns a single label to each point.

I implemented k-means in Python and EM in C++, using the matrix library
armadillo. EM performed much better than k-means on our datasets.

This project was incredibly frustrating for me---despite the simplicity of the
algorithms, I ran into countless numerical stability issues. This project
taught me how cool unsupervised learning and logarithmic math are.

# Project 2: Support Vector Machines
The second project had us implement the SMO algorithm for Support Vector
Machines and explore the results on six datasets, five of our own choosing
and the typical two spirals dataset, using a Gaussian kernel and a polynomial
kernel. I chose a quintic polynomial for my kernel.

The Gaussian kernel significantly outperformed the quintic kernel on every
dataset. SVMs are a beautifully designed mechanism, and I highly recommend
the Stanford CS229 lecture notes explaining them.

My most important takeaway from this project was how effective SVMs were for
classifying datasets; all that you have to do is change the kernel, and they
learn to classify a different distribution of data. Unfortunately, my algorithm
was rather slow, but that may have been the naïve way that SMO was implemented.

# Project 3: Naive Bayes
This project was the simple implementation of the Naive Bayes classifier. I
won't go into much detail on this project (because it was a small project and
the algorithm is very commonplace), but it was fun.

# Project 4: Reinforcement Learning
The fourth project had us implement the Q-learning and SARSA algorithms (and
their corresponding lambda versions) on the Tic-Tac-Toe domain.

Fascinating about this project was that we tested our algorithm against a
minimax player (which I had to implement in my first AI course!)---that player
was slow, even with such a small game tree. Our reinforcement player learned
quickly, and played optimally even more quickly, a fascinating advantage
that comes from memorizing the correct move in each possible state.

# Project 5: Deep Learning
For this project, I wanted to investigate a variety of deep learning techniques
to gain a better understanding of when these methods can be applied, as opposed
to a more in-depth exploration of a single technique. Mostly I followed
TensorFlow tutorials and used others' code; I messed with some parameters and
tried to understand the various algorithms.

I ran a convolutional neural network, a recurrent neural network, an
autoencoder, a wide and deep linear combined classifier, and the word2vec
algorithm. See the report for more explanations and details.
