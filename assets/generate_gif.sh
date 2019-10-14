#!/bin/sh
# Require 3 files:
# - chatter-http.mp4
# - chatter-error.mp4
# - chatter-multiwindow.mp4

ffmpeg -y -i chatter-http.mp4 -vf fps=10,scale=310:-1:flags=lanczos,palettegen palette.png
ffmpeg -i chatter-http.mp4 -i palette.png -filter_complex "fps=10,scale=310:-1:flags=lanczos[x];[x][1:v]paletteuse" chatter-http.gif

ffmpeg -y -i chatter-error.mp4 -vf fps=10,scale=310:-1:flags=lanczos,palettegen palette.png
ffmpeg -i chatter-error.mp4 -i palette.png -filter_complex "fps=10,scale=310:-1:flags=lanczos[x];[x][1:v]paletteuse" chatter-error.gif

ffmpeg -y -i chatter-multiwindow.mp4 -vf fps=10,scale=720:-1:flags=lanczos,palettegen palette.png
ffmpeg -i chatter-multiwindow.mp4 -i palette.png -filter_complex "fps=10,scale=720:-1:flags=lanczos[x];[x][1:v]paletteuse" chatter-multiwindow.gif
