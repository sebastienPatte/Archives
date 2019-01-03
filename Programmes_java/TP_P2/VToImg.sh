#!/bin/bash

echo "set terminal png size 700,500 enhanced background rgb 'white'" > gnuplot_script

echo "set palette negative" >> gnuplot_script
echo "set palette gray" >> gnuplot_script
for i in $(seq 0 1)
do
	if [ $i -eq 0 ]
	then
		for j in $(seq 0 14)
		do
			echo "set output 'V/Inversion/V"$j".png'" >> gnuplot_script
			echo "plot 'V/Inversion/V"$j".txt' matrix with image">> gnuplot_script
		done
	
	else
		for j in $(seq 0 14)
		do
			echo "set output 'V/Iteration/V"$j".png'" >> gnuplot_script
			echo "plot 'V/Iteration/V"$j".txt' matrix with image">> gnuplot_script
		done
	fi
done
gnuplot gnuplot_script
