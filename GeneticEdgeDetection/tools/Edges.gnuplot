
# usage:
#
# in project replace:
#            //System.out.println("iteration: " + i + ", " + specimens.size() + " speciments");
#            System.out.println(specimens.size());

#        print '%2d: estimation error: %3f' % (i, error)
# to:
#        print error
# 
# bash-3.2$ python SRRestorer.py input > error.log
# bash-3.2$ gnuplot ErrorEstimation.gnuplot 

# output:
# estimation_error.png in the directory where the script is

set terminal png
set output "edges.png"

set title "Edge pixels found"
set xlabel "generation"
set ylabel "amount of pixels"
unset key 

plot "edges.log"
