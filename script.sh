#!/bin/bash
echo "set terminal png size 700,500 enhanced background rgb 'white'" > gnuplot_script
echo "set palette gray" >> gnuplot_script
echo "set palette negative" >> gnuplot_script
for i in $(seq 1 100)
do
echo "set output 'wImgs/w"$i".png'" >> gnuplot_script
echo "plot 'w/w"$i".d' matrix with image">> gnuplot_script
done

gnuplot gnuplot_script
